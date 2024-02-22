package vn.loto.rest01.dao;

import vn.loto.rest01.metier.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CouleurDAO extends DAO<Couleur, Couleur, Integer> {
    @Override
    public Couleur getByID(Integer id) {
        String sqlRequest = "Select id_couleur, nom_couleur from couleur where id_couleur = " + id;
        Couleur couleur = null;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            if(resultSet.next()) return new Couleur(resultSet.getInt(1),resultSet.getString(2));
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Couleur> getAll(){
        ArrayList<Couleur> listCouleur = new ArrayList<>();
        String sqlRequest = "SELECT id_couleur, NOM_COULEUR FROM COULEUR";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listCouleur.add(new Couleur(resultSet.getInt(1),resultSet.getString(2)));
            } resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCouleur;
    }

    @Override
    public ArrayList<Couleur> getLike(Couleur object) {
        String sqlCommand = "SELECT ID_COULEUR, NOM_COULEUR FROM COULEUR WHERE ID_COULEUR LIKE '%" + object.getNomCouleur()+"%'";
        ArrayList<Couleur> listCouleur = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                listCouleur.add(new Couleur(resultSet.getInt(1),resultSet.getString(2)));
            } resultSet.close();
        } catch (Exception e) {e.printStackTrace();}
        return listCouleur;
    }
    @Override
    public boolean update(Couleur couleur) {
        String sqlRequest = "UPDATE COULEUR SET NOM_Couleur = ? WHERE id_couleur = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, couleur.getNomCouleur());
            preparedStatement.setInt(2, couleur.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean insert(Couleur couleur) {
        String sqlRequest = "INSERT INTO COULEUR VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, couleur.getNomCouleur());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                couleur.setId(resultSet.getInt(1));
                return true;
            }
            return false;
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean delete(Couleur object) {
        String sqlRequest = "Delete from COULEUR WHERE ID_COULEUR = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            return false;
        }
    }
    public boolean deleteMultiple(List<Couleur> couleurs) {
        String sqlRequest = "DELETE FROM COULEUR WHERE ID_COULEUR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            for (Couleur couleur : couleurs) {
                preparedStatement.setInt(1, couleur.getId());
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
