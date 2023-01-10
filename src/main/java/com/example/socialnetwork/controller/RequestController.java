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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RequestController {
    @FXML
    private TableView requestTableView;

    @FXML
    private TableColumn requesteTableColumn;
    ObservableList<UserDTO> model = FXCollections.observableArrayList();

    @FXML
    private Button acceptButton;
    @FXML
    private Button declineButton;

    @FXML
    private Button backButton;

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

    @FXML
    private void declineButtonClicked() throws IOException {
        if (requestTableView.getSelectionModel().getSelectedItem() == null) {
            ErrorMessage msg = new ErrorMessage("you need to select a user");
            return;
        }
        UserDTO userDTO = (UserDTO) requestTableView.getSelectionModel().getSelectedItem();
        model.remove(userDTO);

        User user2 = serviceUser.findUserByName(userDTO.getName().split(" ")[1], userDTO.getName().split(" ")[0]);
        serviceFriendship.removeFriendship(this.user.getId(), user2.getId());
    }

    @FXML
    private void acceptButtonClicked() throws IOException {
        if (requestTableView.getSelectionModel().getSelectedItem() == null) {
            ErrorMessage msg = new ErrorMessage("you need to select a user");
            return;
        }
        UserDTO userDTO = (UserDTO) requestTableView.getSelectionModel().getSelectedItem();
        model.remove(userDTO);

        User user2 = serviceUser.findUserByName(userDTO.getName().split(" ")[1], userDTO.getName().split(" ")[0]);
        serviceFriendship.updateStatus(this.user.getId(), user2.getId());
    }

    private User user;
    private ServiceFriendship serviceFriendship;
    private ServiceUser serviceUser;

    public void set(User userLogedin, ServiceFriendship serviceFriendship, ServiceUser serviceUser) {
        user = userLogedin;
        this.serviceFriendship = serviceFriendship;
        this.serviceUser = serviceUser;
        initModel();
    }

    Set<UserDTO> getFriends() {
        Set<UserDTO> friends = new HashSet<>();
        for (Friendship friendship : serviceFriendship.getAllFriendships()) {

            if (friendship.getUser1() == this.user.getId() && friendship.getStatus() == 0 && friendship.getFrom() != this.user.getId()) {
                User user2 = serviceUser.findOneUser(friendship.getUser2());
                friends.add(new UserDTO(user2.getFirstname() + " " + user2.getLastname()));

            }
            if (friendship.getUser2() == this.user.getId() && friendship.getStatus() == 0 && friendship.getFrom() != this.user.getId()) {
                User user1 = serviceUser.findOneUser(friendship.getUser1());
                friends.add(new UserDTO(user1.getFirstname() + " " + user1.getLastname()));
            }
        }
        return friends;
    }

    @FXML
    public void initialize() {
        this.requesteTableColumn.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("name"));
        this.requestTableView.setItems(this.model);
    }

    private void initModel() {

        Set<UserDTO> friends = getFriends();
        List<UserDTO> friendsList = StreamSupport.stream(friends.spliterator(), false).collect(Collectors.toList());
        this.model.setAll(friendsList);
    }
}
