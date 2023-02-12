package com.example.socialnetwork.controller;

import com.example.socialnetwork.Main;
import com.example.socialnetwork.domain.*;
import com.example.socialnetwork.service.ServiceFriendship;
import com.example.socialnetwork.service.ServiceHandleMessages;
import com.example.socialnetwork.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MyMessagesController {
    private ServiceHandleMessages serviceHandleMessages;
    private ServiceUser serviceUser;
    private User user;
    private ServiceFriendship serviceFriendship;
    Set<Button> labelSet = new HashSet<>();
    @FXML
    private TableView messagesTableView;
    @FXML
    GridPane messegeGrid;
    @FXML
    private TableColumn fromColumn;
    @FXML
    TextField messegeText;
    @FXML
    ScrollPane conversation;
    ObservableList<UserDTO> model = FXCollections.observableArrayList();
    @FXML
    private Button backButton, sendButton;

    public void set(User userLogedin, ServiceFriendship serviceFriendship, ServiceUser serviceUser, ServiceHandleMessages serviceHandleMessages) {
        this.user = userLogedin;
        this.serviceUser = serviceUser;
        this.serviceFriendship = serviceFriendship;
        this.serviceHandleMessages = serviceHandleMessages;
        conversation.setVisible(false);

        initModel();
    }

    void initConversation() {
        conversation.setVisible(true);

        UserDTO userDTO = (UserDTO) messagesTableView.getSelectionModel().getSelectedItem();

        Integer i = 0;
        for (Message msg : serviceHandleMessages.findMessege(user.getId(), serviceUser.findByUsername(userDTO.getName())).stream().sorted(Comparator.comparing(Message::getData)).toList()) {
            if (msg.getUserFrom() == user.getId()) {
                Button txt = new Button(msg.getContent());
                txt.setBackground(new Background(new BackgroundFill(Color.valueOf("#8477bf"), CornerRadii.EMPTY, new Insets(2))));
                txt.setTextFill(Color.WHITE);
                labelSet.add(txt);
                txt.setFont(new Font("Calibri", 14));

                messegeGrid.add(txt, 1, i);
                i = i + 1;
            } else {
                Button txt = new Button(msg.getContent());
                txt.setBackground(new Background(new BackgroundFill(Color.valueOf("#87C8B0"), CornerRadii.EMPTY, new Insets(2))));
                txt.setTextFill(Color.WHITE);
                txt.setFont(new Font("Calibri", 14));

                labelSet.add(txt);
                messegeGrid.add(txt, 0, i);
                i = i + 1;
            }
        }
    }

    @FXML
    public void initialize() {
        this.fromColumn.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("name"));
        this.messagesTableView.setItems(this.model);
    }

    private void initModel() {

        Set<UserDTO> friends = getFriends();
        List<UserDTO> friendsList = StreamSupport.stream(friends.spliterator(), false).collect(Collectors.toList());
        this.model.setAll(friendsList);

    }

    Set<UserDTO> getFriends() {
        Set<UserDTO> friends = new HashSet<>();

        for (Friendship friendship : serviceFriendship.getAllFriendships()) {
            if (friendship.getUser1() == this.user.getId() && friendship.getStatus() == 1) {
                User user2 = serviceUser.findOneUser(friendship.getUser2());
                friends.add(new UserDTO(user2.getUsername()));

            }
            if (friendship.getUser2() == this.user.getId() && friendship.getStatus() == 1) {
                User user1 = serviceUser.findOneUser(friendship.getUser1());
                friends.add(new UserDTO(user1.getUsername()));
            }
        }
        return friends;
    }


    @FXML
    public void messageSelected() throws IOException {
        initConversation();
    }

    @FXML
    void sendButtonClicked() {
        if (messegeText.getText().isEmpty()) {
            ErrorMessage msg = new ErrorMessage("Message is empty");
            return;
        }
        UserDTO userDTO = (UserDTO) messagesTableView.getSelectionModel().getSelectedItem();
        serviceHandleMessages.addNewMessage(new Message(messegeText.getText(), user.getId(), serviceUser.findByUsername(userDTO.getName())));
        messegeText.clear();
        messegeGrid.getChildren().clear();
        initConversation();
    }

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

}
