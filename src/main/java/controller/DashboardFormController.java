package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {
    public AnchorPane pane;
    public JFXButton btnCustomer;
    public Button btnItem;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnPlaceOrder;
    public Button btnOrderDetails;

    public void initialize(){
        calculateTime();
        calculateDate();
    }

    private void calculateDate() {
        Timeline dateLine = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))),
                new KeyFrame(Duration.hours(24))
        );

        dateLine.setCycleCount(Animation.INDEFINITE);
        dateLine.play();
    }
    private void calculateTime() {
       Timeline timeLine = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),new KeyFrame(Duration.seconds(1)));

        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
    }

    public void customerButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
        stage.setTitle("Customer Form");
        stage.show();
    }

    public void ItemButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemForm.fxml"))));
        stage.setTitle("Item Form");
        stage.show();
    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"))));
        stage.setTitle("Place Order Form");
        stage.show();
    }

    public void orderDetailsButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderDetailsForm.fxml"))));
        stage.setTitle("Order Details Form");
        stage.show();
    }
}
