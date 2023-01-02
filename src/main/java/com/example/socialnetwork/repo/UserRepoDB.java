package com.example.socialnetwork.repo;

import com.example.socialnetwork.domain.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserRepoDB implements  Repository<Long, User> {

    private String url;
    private String username;
    private String password;

    public UserRepoDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) AS size FROM users"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                size = resultSet.getInt("size");
            }
            return size;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO users (lastname, firstname, username, email,password) VALUES (?, ?, ?, ?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getLastname());
            statement.setString(2, entity.getFirstname());
            statement.setString(3, entity.getUsername());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long aLong) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User findOneById(Long l) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");) {

            statement.setLong(1, l);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String lastName = resultSet.getString("lastname");
                String firstName = resultSet.getString("firstname");
                String userName = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                user = new User(userName,lastName, firstName,  email,password);
                user.setId(l);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity, User newEntity) {
        String sql = "UPDATE users SET last_name=?, first_name=?,email=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newEntity.getLastname());
            statement.setString(2, newEntity.getFirstname());
            statement.setString(3, newEntity.getEmail());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM users"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String lastName = resultSet.getString("lastname");
                String firstName = resultSet.getString("firstname");
                String userName = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                User user = new User( userName,lastName, firstName,email, password);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
