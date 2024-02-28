package vn.loto.rest01.dao;

import vn.loto.rest01.metier.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypeDAO extends DAO<Type, Type, Integer> {
    @Override
    public Type getByID(Integer id) {
        String sqlRequest = "Select id_type, nom_type from TYPEBIERE where id_type = " + id;
        Type type;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            if(resultSet.next()) return new Type(resultSet.getInt(1),resultSet.getString(2));
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Type> getAll(){
        ArrayList<Type> listType = new ArrayList<>();
        String sqlRequest = "SELECT id_type, nom_type FROM TYPEBIERE";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listType.add(new Type(resultSet.getInt(1),resultSet.getString(2)));
            } resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listType;
    }

    @Override
    public ArrayList<Type> getLike(Type object) {
        String sqlCommand = "SELECT id_type, nom_type FROM TYPEBIERE WHERE id_type LIKE '%" + object.getLibelle()+"%'";
        ArrayList<Type> listType = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                listType.add(new Type(resultSet.getInt(1),resultSet.getString(2)));
            } resultSet.close();
        } catch (Exception e) {e.printStackTrace();}
        return listType;
    }
    @Override
    public boolean update(Type type) {
        String sqlRequest = "update TYPEBIERE set nom_type = ? WHERE id_type = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1,type.getLibelle());
            preparedStatement.setInt(2, type.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean insert(Type type) {
        String sqlRequest = "INSERT INTO TYPEBIERE (NOM_TYPE) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, type.getLibelle());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                type.setId(resultSet.getInt(1));
                return true;
            }
            return false;
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean delete(Type object) {
        String sqlRequest = "Delete from TYPEBIERE WHERE ID_Type = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            return false;
        }
    }
    public boolean deleteMultiple(List<Type> typeList) {
        String sqlRequest = "DELETE FROM TYPEBIERE WHERE ID_TYPE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            for (Type type : typeList) {
                preparedStatement.setInt(1, type.getId());
                preparedStatement.addBatch();
            }
            int[] deleteCounts = preparedStatement.executeBatch();
            // Check the result of batch execution
            for (int deleteCount : deleteCounts) {
                if (deleteCount != 1) {
                    // Handle failure to delete
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

