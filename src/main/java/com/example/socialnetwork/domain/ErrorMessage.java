package com.example.socialnetwork.domain;

import javafx.scene.control.Alert;

public class ErrorMessage {
    private Alert alert;

    public ErrorMessage(String error) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText("Failed.");
        alert.setContentText(error);
        alert.show();
    }
}
