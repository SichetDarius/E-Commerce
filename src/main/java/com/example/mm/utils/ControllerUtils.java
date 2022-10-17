package com.example.mm.utils;

import com.example.mm.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility methods used inside {@link com.example.mm.controllers}
 */
public class ControllerUtils {
    private static Stage stage;
    private static Scene scene;

    /**
     * Renders a new view, based on the current stage and a view file name.
     * HelloApplication.class is used to get the file path to the resources (views).
     *
     * @param currentStage
     * @param viewName
     */
    public static void renderView(Stage currentStage, String viewName) {
        try {
            Parent root = FXMLLoader.load(HelloApplication.class.getResource(viewName));
            stage = currentStage;
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
