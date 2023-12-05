package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.tm.ItemDto;

import dto.tm.ItemTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import model.ItemModel;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.function.Predicate;

public class ItemFormController {

    public JFXButton btnUpdate;
    public JFXButton btnSave;
    public JFXTextField txtDesc;
    public JFXTextField txtPrice;
    public JFXTextField txtCode;
    public JFXTextField txtQty;
    public JFXButton btnSearch;
    public JFXTextField txtSearch;

    @FXML
    private JFXTreeTableView<ItemTm> tblItem;

    @FXML
    private TreeTableColumn<?, ?> colCode;

    @FXML
    private TreeTableColumn<?, ?> colDesc;

    @FXML
    private TreeTableColumn<?, ?> colPrice;

    @FXML
    private TreeTableColumn<?, ?> colQty;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    ItemModel itemModel = new ItemModelImpl();

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
            setData(newValue)
        );

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                         return treeItem.getValue().getCode().contains(newValue) || treeItem.getValue().getDescription().contains(newValue);
                    }
                });
            }
        });

    }

    private void setData(TreeItem<ItemTm> newValue) {
        if (newValue != null) {
            txtCode.setEditable(false);
            txtCode.setText(newValue.getValue().getCode());
            txtDesc.setText(newValue.getValue().getDescription());
            txtPrice.setText(String.valueOf(newValue.getValue().getPrice()));
            txtQty.setText(String.valueOf(newValue.getValue().getQty()));
        }
    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        try {
            List<ItemDto> dtoList = itemModel.allItems();

            for(ItemDto dto: dtoList){
                JFXButton btn = new JFXButton("Delete");
                ItemTm item = new ItemTm(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getPrice(),
                        dto.getQty(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(item.getCode());
                    loadItemTable();
                });

                tmList.add(item);
            }

            TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblItem.setRoot(treeItem);
            tblItem.setShowRoot(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String code) {
        boolean isDeleted = false;
        try {
            isDeleted = itemModel.deleteItem(code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
            loadItemTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went Wrong!").show();
        }
    }

    @FXML
    void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)tblItem.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }


    public void SaveButtonOnAction(ActionEvent actionEvent) {
        ItemDto dto = new ItemDto(txtCode.getText(), txtDesc.getText(), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtQty.getText()));
        String sql = "INSERT INTO item VALUES(?,?,?,?)";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1,dto.getCode());
            pstm.setString(2,dto.getDescription());
            pstm.setDouble(3,dto.getPrice());
            pstm.setInt(4,dto.getQty());

            int result = pstm.executeUpdate();
            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
                loadItemTable();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateButtonOnAction(ActionEvent actionEvent) {
        ItemDto dto = new ItemDto(txtCode.getText(), txtDesc.getText(), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtQty.getText()));
        String sql = "UPDATE item SET description = ?, unitPrice = ?, qtyOnHand = ? WHERE code = ?";
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1,dto.getDescription());
            pstm.setDouble(2,dto.getPrice());
            pstm.setInt(3,dto.getQty());
            pstm.setString(4,dto.getCode());

            int result = pstm.executeUpdate();
            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item"+dto.getCode()+" Updated!").show();
                loadItemTable();
                clearFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void clearFields() {
        tblItem.refresh();
        txtCode.clear();
        txtDesc.clear();
        txtPrice.clear();
        txtQty.clear();
        txtCode.setEditable(true);
    }

    public void searchButtonOnAction(ActionEvent actionEvent){
        String sql = "SELECT code, description, unitPrice, qtyOnHand FROM Item WHERE code ='"+txtSearch.getText()+"'";
        try {
            Statement stm =  DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery(sql);

            if (rst.next()) {
                String code = rst.getString(1);
                String desc = rst.getString(2);
                Double price = rst.getDouble(3);
                int qty = rst.getInt(4);

                clearFields();
                txtCode.setEditable(false);
                txtCode.setText(code);
                txtDesc.setText(desc);
                txtPrice.setText(String.valueOf(price));
                txtQty.setText(String.valueOf(qty));
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid Code!").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}



