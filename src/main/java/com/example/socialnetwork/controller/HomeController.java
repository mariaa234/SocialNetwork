package com.example.socialnetwork.controller;

import com.example.socialnetwork.Main;
import com.example.socialnetwork.domain.ErrorMessage;
import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.domain.UserDTO;
import com.example.socialnetwork.service.ServiceFriendship;
import com.example.socialnetwork.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomeController {

    private ServiceUser serviceUser;
    private ServiceFriendship serviceFriendship;
    private User userLogedin;
    ObservableList<UserDTO> model = FXCollections.observableArrayList();

    @FXML
    TableView userFriends;
    @FXML
    TableColumn columnFriends;

    @FXML
    Button searchButton;

    @FXML
    Button deleteFriendButton;

    @FXML
    Button requestButton;

    @FXML
    Button MyMessagesButton;

    @FXML
    Button sendMessageButton;

    @FXML
    TextField userToSearch;


    public void setService(ServiceUser serviceUser,ServiceFriendship serviceFriendship, User user) {
        this.serviceFriendship=serviceFriendship;
        this.serviceUser=serviceUser;
        this.userLogedin=user;
        initModel();
    }

    Set<UserDTO> getFriends() {
        Set<UserDTO> friends = new HashSet<>();
        for (Friendship friendship : serviceFriendship.getAllFriendships() ){
            if (friendship.getUser1() == this.userLogedin.getId() && friendship.getStatus() == 1)
            {
                User user2 = serviceUser.findOneUser(friendship.getUser2());
                friends.add(new UserDTO(user2.getFirstname() + " " + user2.getLastname()));

            }
            if (friendship.getUser2() == this.userLogedin.getId() && friendship.getStatus() == 1) {
                User user1 = serviceUser.findOneUser(friendship.getUser1());
                friends.add(new UserDTO(user1.getFirstname() + " " + user1.getLastname()));
            }
        }
        return friends;
    }
    @FXML
    public void initialize() {
        this.columnFriends.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("name"));
        this.userFriends.setItems(this.model);
    }
    private void initModel() {

        Set<UserDTO> friends = getFriends();
        List<UserDTO> friendsList = StreamSupport.stream(friends.spliterator(), false).collect(Collectors.toList());
        this.model.setAll(friendsList);
    }
    @FXML
    void deleteFriendButtonClicked() throws IOException {
        if(userFriends.getSelectionModel().getSelectedItem()==null){
            ErrorMessage msg=new ErrorMessage("you need to select a user");
            return;
        }
        UserDTO userDTO = (UserDTO) userFriends.getSelectionModel().getSelectedItem();
        model.remove(userDTO);
        deleteFriendButton.setDisable(true);

        User user2 = serviceUser.findUserByName(userDTO.getName().split(" ")[1], userDTO.getName().split(" ")[0]);
        serviceFriendship.removeFriendship(this.userLogedin.getId(), user2.getId());

    }

    @FXML
    void searchButtonClicked() throws IOException{
        if(userToSearch.getText().isEmpty())
        {
            ErrorMessage msg=new ErrorMessage("Search bar is empty");
            return;
        }
        User user=serviceUser.findUserByName(userToSearch.getText().split(" ")[0],userToSearch.getText().split(" ")[1]);
        if(user==null)
        {
            ErrorMessage msg=new ErrorMessage("User doesn't exist");
            return;
        }
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("search.fxml"));
        AnchorPane root = loader.load();
        Stage dialogStage=new Stage();
        dialogStage.setScene(new Scene(root));
        SearchController srch = loader.getController();
        srch.set( user, userLogedin, serviceFriendship,serviceUser);
        dialogStage.setTitle(user.getUsername());
        dialogStage.show();
        Stage thisStage = (Stage) searchButton.getScene().getWindow();
        thisStage.close();

    }
    @FXML
    void requestButtonClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("request.fxml"));
        AnchorPane root = loader.load();
        Stage dialogStage=new Stage();
        dialogStage.setScene(new Scene(root));
        RequestController rqst = loader.getController();
        rqst.set( userLogedin, serviceFriendship,serviceUser);
        dialogStage.setTitle("Friends Request");
        dialogStage.show();
        Stage thisStage = (Stage) requestButton.getScene().getWindow();
        thisStage.close();
    }
}
