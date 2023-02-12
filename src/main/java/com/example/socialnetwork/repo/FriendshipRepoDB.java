package com.example.socialnetwork.repo;

import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.Tuple;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FriendshipRepoDB implements Repository<Tuple<Long, Long>, Friendship> {

    private String url;
    private String username;
    private String password;

    public FriendshipRepoDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }



    @Override
    public void save(Friendship entity) {
        String sql = "INSERT INTO friendships (id1, id2, friends_from, status, r_from) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getUser1());
            statement.setLong(2, entity.getUser2());
            statement.setString(3, entity.getStringFriendsFrom());
            statement.setInt(4, entity.getStatus());
            statement.setLong(5, entity.getFrom());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Tuple<Long, Long> aLong) {
        String sql = "DELETE FROM friendships WHERE id1=? AND id2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong.getE1());
            statement.setLong(2, aLong.getE2());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Friendship findOneById(Tuple<Long, Long> aLong) {
        Friendship friendship = null;
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships WHERE id1=? AND id2=?");) {

            statement.setLong(1, aLong.getE1());
            statement.setLong(2, aLong.getE2());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String friendsFrom = resultSet.getString("friends_from");
                int status = resultSet.getInt("status");
                Long from = resultSet.getLong("r_from");

                friendship = new Friendship(id1, id2, friendsFrom, status, from);
            }
            return friendship;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Friendship entity, Friendship newEntity) {
        String sql = "UPDATE friendships SET status=?, r_from=null WHERE id1=? AND id2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, newEntity.getStatus());
            statement.setLong(2, entity.getUser1());
            statement.setLong(3, entity.getUser2());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String friendsFrom = resultSet.getString("friends_from");
                int status = resultSet.getInt("status");
                Long from = resultSet.getLong("r_from");

                Friendship friendship = new Friendship(id1, id2, friendsFrom, status, from);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
