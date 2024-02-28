package vn.loto.rest01.dao;
import vn.loto.rest01.metier.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO  extends DAO<User, User, Integer> {
    @Override
    public User getByID(Integer id) {
        return null;    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> listUser = new ArrayList<>();

        String sqlRequest = "SELECT * FROM UTILISATEUR";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listUser.add(new User(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUser;
    }
    public User getByUsername(User user) {
        String query = "SELECT * FROM UTILISATEUR WHERE NOM_UTILISATEUR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            User storedUser = new User();
            while (resultSet.next()) {
                storedUser.setLogin(resultSet.getString("NOM_UTILISATEUR"));
                storedUser.setPassword(resultSet.getString("PASSWORD"));
                storedUser.setRoles(resultSet.getString("NOM_ROLE"));
            }
            return storedUser;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ArrayList<User> getLike(User object) {
        return null;
    }

    @Override
    public boolean insert(User utilisateur) {
        String query = "INSERT INTO UTILISATEUR (NOM_UTILISATEUR, PASSWORD, NOM_ROLE, EMAIL) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, utilisateur.getLogin());
            preparedStatement.setString(2, utilisateur.getPassword());
            preparedStatement.setString(3, "user");
            preparedStatement.setString(4, utilisateur.getEmail());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User utilisateur) {
        String sqlRequest = "UPDATE UTILISATEUR SET PASSWORD = ? WHERE NOM_UTILISATEUR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, utilisateur.getPassword());
            preparedStatement.setString(2, utilisateur.getLogin());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(User object) {
        String sqlRequest = "Delete from UTILISATEUR WHERE NOM_UTILISATEUR = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, object.getLogin());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            return false;
        }
    }


}
