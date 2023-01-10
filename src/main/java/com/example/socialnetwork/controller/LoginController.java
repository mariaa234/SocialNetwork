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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    ServiceUser serviceUser;
    @FXML
    TextField loginUsername1;
    @FXML
    PasswordField loginPassword1;
    @FXML
    Button loginButton1;
    @FXML
    Label welcomeText;

    ServiceFriendship serviceFriendship;

    private void init() {

        UserRepoDB userDatabaseRepository = new UserRepoDB("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
        this.serviceUser = new ServiceUser(userDatabaseRepository);
        FriendshipRepoDB friendshipRepoDB = new FriendshipRepoDB("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
        this.serviceFriendship = new ServiceFriendship(userDatabaseRepository, friendshipRepoDB);
    }

    @FXML
    void loginButtonClicked() throws IOException {
        if (loginUsername1.getText().isEmpty()) {
            ErrorMessage msg = new ErrorMessage("invalid username");
            return;
        }
        if (loginPassword1.getText().isEmpty()) {
            ErrorMessage msg = new ErrorMessage("Invalid password!");
            return;
        }

        init();

        User user = serviceUser.findUserByUsernameAndPassword(loginUsername1.getText(), loginPassword1.getText());
        if (user == null) {
            ErrorMessage msg = new ErrorMessage("Password or username incorrect!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("home.fxml"));
        AnchorPane root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setScene(new Scene(root));
        HomeController ctrl = loader.getController();
        ctrl.setService(serviceUser, serviceFriendship, user);
        dialogStage.setTitle("Home");
        dialogStage.show();
        Stage thisStage = (Stage) loginButton1.getScene().getWindow();
        thisStage.close();


    }


}
