package com.example.mm.controllers;

import com.example.mm.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.mm.models.Product.getCurrentProductId;
import static com.example.mm.services.ProductsService.addProductToCurrentCart;
import static com.example.mm.services.ProductsService.getProductById;
import static com.example.mm.utils.ControllerUtils.renderView;

public class ProductController implements Initializable {
    @FXML
    private Label productTitle;

    @FXML
    private Label productPrice;

    @FXML
    private Label feedbackLabel;
    @FXML
    private Label productDescription;
    public ProductController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        feedbackLabel.setVisible(false);
        try {
            Product currentProduct = getProductById(getCurrentProductId());
            productPrice.setText(currentProduct.getPrice().toString());
            productTitle.setText(currentProduct.getName());
            productDescription.setText(currentProduct.getDescription());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onAddToCartButtonClick(ActionEvent actionEvent) throws SQLException {
        boolean addToCartResult = addProductToCurrentCart(getCurrentProductId());
        if (addToCartResult) {
            feedbackLabel.setVisible(true);
        }
    }

    @FXML
    private void onShopButtonClick(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "shop-view.fxml");
    }

    @FXML
    private void onCartButtonClick(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "cart-view.fxml");
    }

}
