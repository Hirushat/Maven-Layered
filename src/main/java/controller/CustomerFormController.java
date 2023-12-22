package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.impl.CustomerBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import dao.custom.CustomerDao;
import dao.custom.impl.CustomerDaoImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class CustomerFormController{

    public JFXButton btnBack;
    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnReload;

    private CustomerBo customerBo = (CustomerBo) BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(CustomerTm newValue) {
        if (newValue != null) {
            txtId.setEditable(false);
            txtId.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }

    private void loadCustomerTable() {

        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = customerBo.allCustomers();

            for(CustomerDto dto: dtoList){
                Button btn = new Button("Delete");
                CustomerTm c = new CustomerTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c.getId());
                    loadCustomerTable();

                });

                tmList.add(c);
                tblCustomer.setItems(tmList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void deleteCustomer(String id){
        try {
            boolean isDeleted = customerBo.deleteCustomer(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted");
                loadCustomerTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateButtonOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            boolean isSaved = customerBo.updateCustomer(new CustomerDto(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
                loadCustomerTable();
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }

    }
    public void ReloadButtOnAction(ActionEvent actionEvent) {
        loadCustomerTable();
        clearFields();
    }

    private void clearFields(){
        tblCustomer.refresh();
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtId.setEditable(true);
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        try {
            boolean isSaved = customerBo.saveCustomer(new CustomerDto(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }
        }catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry!").show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)tblCustomer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"))));
    }
}
