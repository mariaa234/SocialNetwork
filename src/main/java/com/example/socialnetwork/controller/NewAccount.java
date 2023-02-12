package com.example.socialnetwork.controller;

import com.example.socialnetwork.Main;
import com.example.socialnetwork.domain.ErrorMessage;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.repo.FriendshipRepoDB;
import com.example.socialnetwork.repo.UserRepoDB;
import com.example.socialnetwork.service.ServiceFriendship;
import com.example.socialnetwork.service.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewAccount {
    ServiceUser serviceUser;
    ServiceFriendship serviceFriendship;
    @FXML
    Button NewAccountCreate;

    @FXML
    Button backToLoginButton;
    @FXML
    TextField firstnameText, lastnameText, usernameText, emailText;
    @FXML
    PasswordField passwordText, passwordConfText;

    @FXML
    void NewAccountCreateClicked() throws IOException {
        init();
        if (firstnameText.getText().isEmpty() || lastnameText.getText().isEmpty() || usernameText.getText().isEmpty() || emailText.getText().isEmpty() || passwordConfText.getText().isEmpty() || passwordText.getText().isEmpty()) {
            ErrorMessage msg = new ErrorMessage("you need to complete all fields");
            return;
        } else if (!passwordConfText.getText().equals(passwordText.getText())) {
            ErrorMessage msg = new ErrorMessage("Password confirm field need to be identically with password field ");
            return;
        } else {
            User user = new User(usernameText.getText(), lastnameText.getText(), firstnameText.getText(), emailText.getText(), passwordText.getText());
            serviceUser.addUser(user);
            firstnameText.clear();
            lastnameText.clear();
            usernameText.clear();
            emailText.clear();
            passwordText.clear();
            passwordConfText.clear();
            return;
        }


    }

    @FXML
    void backToLoginButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        GridPane root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Main");

        primaryStage.show();
        Stage thisStage = (Stage) backToLoginButton.getScene().getWindow();
        thisStage.close();
    }

    public void setService(ServiceUser serviceUser, ServiceFriendship serviceFriendship) {
        this.serviceFriendship = serviceFriendship;
        this.serviceUser = serviceUser;
    }

    private void init() {
        UserRepoDB userDatabaseRepository = new UserRepoDB("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
        this.serviceUser = new ServiceUser(userDatabaseRepository);
        FriendshipRepoDB friendshipRepoDB = new FriendshipRepoDB("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
        this.serviceFriendship = new ServiceFriendship(userDatabaseRepository, friendshipRepoDB);
    }
}
