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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private Button backButton;
    @FXML
    private TableView messagesTableView;
    @FXML
    private TableColumn subjectColumn;
    @FXML
    private TableColumn fromColumn;

    @FXML
    private Text messageText;
    ObservableList<MessageDTO> model = FXCollections.observableArrayList();
    public void set(User userLogedin, ServiceFriendship serviceFriendship, ServiceUser serviceUser, ServiceHandleMessages serviceHandleMessages) {
        this.user=userLogedin;
        this.serviceUser=serviceUser;
        this.serviceFriendship=serviceFriendship;
        this.serviceHandleMessages=serviceHandleMessages;
        initModel();
    }
    @FXML
    public void initialize() {
        this.fromColumn.setCellValueFactory(new PropertyValueFactory<MessageDTO, String>("from"));
        this.subjectColumn.setCellValueFactory(new PropertyValueFactory<MessageDTO, String>("subject"));
        this.messagesTableView.setItems(this.model);
         }

    private void initModel() {

       Set<MessageDTO> messages = getMessages();
        List<MessageDTO> friendsList = StreamSupport.stream(messages.spliterator(), false).collect(Collectors.toList());
        this.model.setAll(friendsList);
    }

    private Set<MessageDTO> getMessages() {

        Set<MessageDTO> friends = new HashSet<>();
        for (Message friendship : serviceHandleMessages.getAllMessagesToThisUser(user.getId())) {
            if (friendship.getUserTo() == this.user.getId()) {

                friends.add(new MessageDTO(serviceUser.getUserById(friendship.getUserFrom()).getUsername(),friendship.getSubject()));

            }

        }
        return friends;
    }
    @FXML
    public void messageSelected() throws IOException
    {
        MessageDTO messageDTO=(MessageDTO) messagesTableView.getSelectionModel().getSelectedItem();
        messageText.setText(serviceHandleMessages.findMessege(serviceUser.findByUsername(messageDTO.getFrom()),user.getId()));
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
