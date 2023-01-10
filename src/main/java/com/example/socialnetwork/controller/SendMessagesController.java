package com.example.socialnetwork.controller;

import com.example.socialnetwork.Main;
import com.example.socialnetwork.domain.ErrorMessage;
import com.example.socialnetwork.domain.Message;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.service.ServiceFriendship;
import com.example.socialnetwork.service.ServiceHandleMessages;
import com.example.socialnetwork.service.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SendMessagesController {
    private ServiceHandleMessages serviceHandleMessages;
    private ServiceUser serviceUser;
    @FXML
    private TextField toTextField;
    @FXML
    private TextField subjectTextField;
    @FXML
    private TextArea contentTextField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button sendButton;
    @FXML
    private Button backButton;
    private ServiceFriendship serviceFriendship;
    private User user;

    @FXML
    private void backButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("home.fxml"));
        AnchorPane root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setScene(new Scene(root));
        HomeController ctrl = loader.getController();
        ctrl.setService(serviceUser, serviceFriendship, user);
        dialogStage.setTitle("Home");
        dialogStage.show();
        Stage thisStage = (Stage) backButton.getScene().getWindow();
        thisStage.close();
    }

    public void set(User userLogedin, ServiceFriendship serviceFriendship, ServiceUser serviceUser, ServiceHandleMessages serviceHandleMessages) {
        user=userLogedin;
        this.serviceUser=serviceUser;
        this.serviceFriendship=serviceFriendship;
        this.serviceHandleMessages=serviceHandleMessages;
    }
    @FXML
    public void deleteButtonClck() throws IOException
    {
        toTextField.clear();
        subjectTextField.clear();
        contentTextField.clear();
    }

    @FXML
    public void sendButtonClck() throws IOException
    {
        Message message=new Message(subjectTextField.getText(),contentTextField.getText(),user.getId(),serviceUser.findByUsername(toTextField.getText()));
        if(message.getUserTo()==-1) {
            ErrorMessage msg = new ErrorMessage("user invalid");
            return;
        }
        toTextField.clear();
        subjectTextField.clear();
        contentTextField.clear();
        serviceHandleMessages.addNewMessage(message);
    }

}
