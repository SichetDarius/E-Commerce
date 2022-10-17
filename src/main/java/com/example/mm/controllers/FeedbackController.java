package com.example.mm.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import static com.example.mm.utils.ControllerUtils.renderView;

public class FeedbackController {

    @FXML
    private void onShopButtonClick(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        renderView(currentStage, "shop-view.fxml");
    }
}
