package com.example.mm.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.example.mm.services.UserService.buildInvoiceForCurrentUser;
import static com.example.mm.services.UserService.buildNewCartForCurrentUser;
import static com.example.mm.utils.ControllerUtils.renderView;

public class CheckoutController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;

    @FXML
    private void onPayButtonClick(ActionEvent actionEvent) throws SQLException {
        boolean newInvoiceResult = buildInvoiceForCurrentUser(firstName.getText(), lastName.getText(), addressField.getText(), phoneNumberField.getText());
        if (newInvoiceResult) {
            boolean newCartResult = buildNewCartForCurrentUser();
            if (newCartResult) {
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                renderView(currentStage, "feedback-view.fxml");
            }
        }
    }

    @FXML
    private void onCartButtonClick(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "cart-view.fxml");
    }

    @FXML
    private void onShopButtonClick(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "shop-view.fxml");
    }
}
