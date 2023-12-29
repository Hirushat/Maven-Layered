package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderDetailBo;
import bo.custom.OrdersBo;
import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderDetailBoImpl;
import bo.custom.impl.OrdersBoImpl;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.DaoFactory;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dao.custom.impl.OrderDaoImpl;
import dao.util.BoType;
import dao.util.DaoType;
import dto.CustomerDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.tm.ItemDto;
import dto.tm.OrderTm;
import dto.tm.OrdersTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {

    public JFXComboBox cmbCustId;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtCustName;
    public JFXTextField txtItemDesc;
    public JFXTextField txtBuyingQty;
    public JFXTextField txtUnitPrice;
    public JFXButton btnAddToCart;
    public Label lblTotal;
    public TreeTableColumn colOption;
    public TreeTableColumn colAmount;
    public TreeTableColumn colQty;
    public TreeTableColumn colUnit;
    public TreeTableColumn colDesc;
    public TreeTableColumn colCode;
    public Label lblOrderId;
    @FXML
    private JFXButton btdAddToCart;

    @FXML
    private JFXTreeTableView<OrderTm> tblOrder;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnBack;
    private List<CustomerDto> customers;
    private List<ItemDto> items;
    private double tot = 0;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    private OrdersBo ordersBo = BoFactory.getInstance().getBo(BoType.ORDER);
    //private OrderDao ordersDao = DaoFactory.getInstance().getDao(DaoType.ORDER);
    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();



    public void initialize() throws SQLException {
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        generateId();

        loadCustomerIds();
        loadItemCodes();

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, customerId) -> {
            for (CustomerDto dto : customers){
                if (dto.getId().equals(customerId)){
                    txtCustName.setText(dto.getName());
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, orderId) ->{
            for (ItemDto dto: items){
                if (dto.getCode().equals(orderId)){
                    txtItemDesc.setText(dto.getDescription());
                    txtUnitPrice.setText(String.valueOf(dto.getPrice()));
                }
            }
        });
    }

    private void loadItemCodes() {
        try {
            items = itemBo.allItems();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (ItemDto dto:items) {
                list.add(dto.getCode());
            }
            cmbItemCode.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            customers = customerBo.allCustomers();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (CustomerDto dto : customers) {
                list.add(dto.getId());
            }
            cmbCustId.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)tblOrder.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"))));
    }

    public void generateId(){
        try {
            lblOrderId.setText(ordersBo.generateId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeOrderbuttonOnAction(ActionEvent actionEvent) {
        if (!tmList.isEmpty()){
//            orderModel.saveOrder()
            List<OrderDetailsDto> list = new ArrayList<>();
            for (OrderTm tm:tmList) {
                list.add(new OrderDetailsDto(
                        lblOrderId.getText(),
                        tm.getCode(),
                        tm.getQty(),
                        tm.getAmount()/tm.getQty()
                ));
            }
//        if (!tmList.isEmpty()){
            boolean isSaved = false;
            try {
                isSaved = ordersBo.saveOrder(new OrderDto(
                        lblOrderId.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString(),
                        cmbCustId.getValue().toString(),
                        list
                ));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Order Saved!").show();
                generateId();
                cmbCustId.getSelectionModel().clearSelection();
                cmbItemCode.getSelectionModel().clearSelection();
                txtCustName.setText("");
                txtItemDesc.setText("");
                txtUnitPrice.setText("");
                txtBuyingQty.setText("");
                tmList.clear();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }
//        }
        }
    }

    public void addToCartButtonOnAction(ActionEvent actionEvent) {
        String itemCode = cmbItemCode.getValue().toString();
        for (ItemDto dto : items) {
            if (dto.getCode().equals(itemCode)) {
                if (Integer.parseInt(txtBuyingQty.getText()) > dto.getQty()) {
                    new Alert(Alert.AlertType.ERROR, "Out of Stocks!").show();
                }else{
                    try {
                        double amount = itemBo.getItem(cmbItemCode.getValue().toString()).getPrice() * Integer.parseInt(txtBuyingQty.getText());
                        JFXButton btn= new JFXButton("Delete");
                        OrderTm tm = new OrderTm(
                                cmbItemCode.getValue().toString(),
                                txtItemDesc.getText(),
                                Integer.parseInt(txtBuyingQty.getText()),
                                amount,
                                btn
                        );

                        btn.setOnAction(actionEvent1 -> {
                            tmList.remove(tm);
                            tblOrder.refresh();
                            tot-= tm.getAmount();
                            lblTotal.setText(String.format("%.2f",tot));
                        });

                        boolean isExist = false;


                        for(OrderTm order : tmList) {
                            if(order.getCode().equals(tm.getCode())){
                                order.setQty(order.getQty()+tm.getQty());
                                order.setAmount(order.getAmount()+tm.getAmount());
                                isExist = true;
                                tot+=tm.getAmount();
                            }
                        }

                        if(!isExist){
                            tmList.add(tm);
                            tot+=tm.getAmount();
                        }

                        RecursiveTreeItem<OrderTm> treeItem = new RecursiveTreeItem<OrderTm>(tmList, RecursiveTreeObject::getChildren);
                        tblOrder.setRoot(treeItem);
                        tblOrder.setShowRoot(false);

                        lblTotal.setText(String.format("%.2f",tot));

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
