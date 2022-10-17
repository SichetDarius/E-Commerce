package com.example.mm.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.mm.services.UserService.buildUser;
import static com.example.mm.services.UserStateService.setCurrentUser;
import static com.example.mm.utils.ControllerUtils.renderView;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private Label loginNegativeFeedback;

    @FXML
    private Label signupNegativeFeedback;

    @FXML
    private PasswordField passwordField;

    public LoginController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginNegativeFeedback.setVisible(false);
        signupNegativeFeedback.setVisible(false);
    }

    @FXML
    private void onLoginButtonClick(ActionEvent actionEvent) throws SQLException {
        boolean loginResult = setCurrentUser(usernameField.getText(), passwordField.getText());
        if (loginResult == true) {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            renderView(currentStage, "shop-view.fxml");
        } else {
            // return negative feedback to user
            loginNegativeFeedback.setVisible(true);
            System.out.println("Wrong username or password");
        }
    }

    @FXML
    private void onSignUpButtonClick(ActionEvent actionEvent) throws SQLException {
        boolean buildResult = buildUser(usernameField.getText(), passwordField.getText());
        if (buildResult) {
            boolean loginResult = setCurrentUser(usernameField.getText(), passwordField.getText());
            if (loginResult == true) {
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                renderView(currentStage, "shop-view.fxml");
            } else {
                signupNegativeFeedback.setVisible(true);
                System.out.println("Username or password already taken!");
            }
        }
    }

}