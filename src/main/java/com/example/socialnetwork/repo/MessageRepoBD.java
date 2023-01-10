package com.example.socialnetwork.repo;

import com.example.socialnetwork.domain.Message;
import com.example.socialnetwork.domain.Tuple;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MessageRepoBD implements Repository<Tuple<Long, Long>, Message> {

    private String url;
    private String username;
    private String password;

    public MessageRepoBD(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id1) AS size FROM messages"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Message entity) {
        String sql = "INSERT INTO messages (userFrom, userTo, subject, content) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getUserFrom());
            statement.setLong(2, entity.getUserTo());
            statement.setString(3, entity.getSubject());
            statement.setString(4, entity.getContent());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Tuple<Long, Long> longLongTuple) {

    }

    @Override
    public Message findOneById(Tuple<Long, Long> longLongTuple) {
        return null;
    }

    @Override
    public void update(Message entity, Message newEntity) {

    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long userFrom = resultSet.getLong("userFrom");
                Long userTo = resultSet.getLong("userTo");
                String subject = resultSet.getString("subject");
                String content = resultSet.getString("content");

                Message message = new Message(subject, content, userFrom, userTo);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
