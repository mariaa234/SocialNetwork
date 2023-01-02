package com.example.socialnetwork.controller;

import com.example.socialnetwork.repo.UserRepoDB;
import com.example.socialnetwork.service.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    ServiceUser serviceUser;
    @FXML
    TextField loginUsername;
    @FXML
    PasswordField loginPassword;
    @FXML
    Button loginButton;
    @FXML
    Label welcomeText;

    private void init() {

        UserRepoDB userDatabaseRepository = new UserRepoDB("jdbc:postgresql://localhost:5432/admin", "postgres", "123");
        this.serviceUser = new ServiceUser(userDatabaseRepository);
    }
    void loginButtonClicked() throws IOException{}
}
