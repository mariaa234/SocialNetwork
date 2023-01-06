package com.example.socialnetwork.controller;

import com.example.socialnetwork.Main;
import com.example.socialnetwork.domain.ErrorMessage;
import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.domain.UserDTO;
import com.example.socialnetwork.service.ServiceFriendship;
import com.example.socialnetwork.service.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class SearchController {

    private ServiceFriendship serviceFriendship;
    private User userSearched;
    private  ServiceUser serviceUser;
    @FXML
    Text userSearchedName;
    @FXML
    private Button backButton;
    private User userLogin;
    @FXML
    private Button removeFriendButton;
    @FXML
    private Button acceptRequestButton;
    @FXML
    private Button removeRequestButton;
    @FXML
    private Button addFriendButton;
    @FXML
    private void removeRequestButtonClicked()throws IOException{
        serviceFriendship.removeFriendship(userLogin.getId(),userSearched.getId());
        acceptRequestButton.setVisible(false);
        addFriendButton.setVisible(true);
        removeRequestButton.setVisible(false);
        removeFriendButton.setVisible(false);
    }
    @FXML
    private void acceptRequestButtonClicked()throws IOException
    {
        serviceFriendship.updateStatus(userLogin.getId(),userSearched.getId());
        acceptRequestButton.setVisible(false);
        addFriendButton.setVisible(false);
        removeRequestButton.setVisible(false);
        removeFriendButton.setVisible(true);
    }
    @FXML
    private void removeFriendButtonClicked()throws IOException
    {
        serviceFriendship.removeFriendship(userLogin.getId(),userSearched.getId());
        addFriendButton.setVisible(true);
        removeRequestButton.setVisible(false);
        removeFriendButton.setVisible(false);
    }
    @FXML
    private void backButtonClicked()throws IOException
    {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("home.fxml"));
        AnchorPane root = loader.load();
        Stage dialogStage=new Stage();
        dialogStage.setScene(new Scene(root));
        HomeController ctrl = loader.getController();
        ctrl.setService(serviceUser, serviceFriendship, userLogin);
        dialogStage.setTitle("Home");
        dialogStage.show();
        Stage thisStage = (Stage) backButton.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    private void addFriendButtonClicked() throws IOException{
        Friendship friendship=new Friendship(userLogin.getId(),userSearched.getId(),userLogin.getId());
        serviceFriendship.addFriendship(friendship);
        addFriendButton.setVisible(false);
        acceptRequestButton.setVisible(false);
        removeRequestButton.setVisible(true);
    }
    public void set(User user,User userLogin, ServiceFriendship serviceFriendship, ServiceUser serviceUser) {
        this.userSearched=user;
        this.userLogin=userLogin;
        this.serviceFriendship=serviceFriendship;
        this.serviceUser=serviceUser;
        userSearchedName.setText(user.getFirstname()+" "+user.getLastname());

        int status=serviceFriendship.statusUsers(userLogin.getId(),userSearched.getId());

        if (status==1){
            addFriendButton.setVisible(false);
            acceptRequestButton.setVisible(false);
            removeRequestButton.setVisible(false);
            return;
        }
        if(status==-1)
        {
            removeFriendButton.setVisible(false);
            acceptRequestButton.setVisible(false);
            removeRequestButton.setVisible(false);
            return;
        }
        Long r_from=serviceFriendship.requestFrom(userLogin.getId(),userSearched.getId());
        removeFriendButton.setVisible(false);
        addFriendButton.setVisible(false);
        if(r_from==userLogin.getId())
        {
            acceptRequestButton.setVisible(false);
        }
    }
}
