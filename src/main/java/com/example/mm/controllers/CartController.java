package com.example.mm.controllers;

import com.example.mm.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.mm.services.ProductsService.*;
import static com.example.mm.services.UserStateService.logoutUser;
import static com.example.mm.utils.ControllerUtils.renderView;

public class CartController implements Initializable {

    private ObservableList<Product> products;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private Label cartNegativeFeedback;

    @FXML
    private Label totalLabel;

    private void calculateTotal() throws SQLException {
        Double sum = 0.0;
        List<Product> productList = getCartProductsForCurrentUser();
        if (!productList.isEmpty()) {
            for (Product partialProduct : productList) {
                Product completeProduct = getProductById(partialProduct.getId());
                completeProduct.setQuantity(partialProduct.getQuantity());
                productTable.getItems().add(completeProduct);
                sum += completeProduct.getPrice() * completeProduct.getQuantity();
            }
            totalLabel.setText(sum.toString());
        }
    }

    public CartController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cartNegativeFeedback.setVisible(false);
        try {
            List<Product> productList = getCartProductsForCurrentUser();
            for (Product p : productList) {
                p = getProductById(p.getId());
            }
            productTable.setEditable(true);
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            products = FXCollections.observableArrayList(productList);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            calculateTotal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent actionEvent) throws SQLException {
        for (Product p : productTable.getItems()) {
            if (p.getQuantity() > 0) {
                changeProductQuantityInCurrentCart(p.getQuantity(), p.getId());
            } else {
                deleteProductFromCurrentCart(p.getId());
            }
        }
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "cart-view.fxml");
    }

    @FXML
    private void onOrderButtonClick(ActionEvent actionEvent) throws SQLException {
        if (!getCartProductsForCurrentUser().isEmpty()) {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            renderView(currentStage, "checkout-view.fxml");
        } else {
            cartNegativeFeedback.setVisible(true);
        }
    }

    @FXML
    private void onShopButtonClick(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "shop-view.fxml");
    }

    @FXML
    private void onLogoutButtonClick(ActionEvent actionEvent) {
        boolean logoutResult = logoutUser();
        if (logoutResult) {
            Stage currentStage3 = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            renderView(currentStage3, "login-view.fxml");
        }
    }

    @FXML
    private void onChangeQuantityInTable(TableColumn.CellEditEvent<Product, Integer> productIntegerCellEditEvent) {
        Product product = productTable.getSelectionModel().getSelectedItem();
        product.setQuantity((int) Math.floor(productIntegerCellEditEvent.getNewValue()));
    }

}
