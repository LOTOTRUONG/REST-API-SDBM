package vn.loto.rest01.dao;

import vn.loto.rest01.metier.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FabricantDAO extends DAO<Fabricant, Fabricant, Integer> {
    @Override
    public Fabricant getByID(Integer id) {
        String sqlRequest = "Select id_fabricant, nom_fabricant from fabricant where id_fabricant= " + id;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            if(resultSet.next()) return new Fabricant(resultSet.getInt(1),resultSet.getString(2));
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Fabricant getByLibelle(String libelle) {
        String sqlRequest = "Select id_fabricant, nom_fabricant from fabricant where nom_fabricant = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, libelle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return new Fabricant(resultSet.getInt(1),resultSet.getString(2));
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Fabricant> getAll() {
        ArrayList<Fabricant> liste = new ArrayList<>();
        String sqlRequest = "SELECT * FROM FABRICANT";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()){
                liste.add(new Fabricant(resultSet.getInt(1),resultSet.getString(2)));
            } resultSet.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public ArrayList<Fabricant> getLike(Fabricant object) {
        String sqlCommand = "SELECT ID_FABRICANT, NOM_FABRICANT FROM FABRICANT WHERE ID_CONTINENT LIKE '%" + object.getNomFabricant() + "%'";
        ArrayList<Fabricant> liste = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                liste.add(new Fabricant(resultSet.getInt(1), resultSet.getString(2)));
            }
            resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return liste;
    }


    @Override
    public boolean update(Fabricant object) {
        String sqlRequest = "update FABRICANT set NOM_FABRICANT = ? WHERE ID_FABRICANT = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1,object.getNomFabricant());
            preparedStatement.setInt(2, object.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            E.printStackTrace();
            return false;}
    }

    @Override
    public boolean insert(Fabricant object) {
        String sqlRequest = "insert into FABRICANT values (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getNomFabricant());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()){
                    object.setId(resultSet.getInt(1));
                    return true;
                }
            }
            return false;
        }catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Fabricant object) {
        String sqlRequest = "Delete from FABRICANT WHERE ID_FABRICANT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException E) {
            return false;
        }
    }

    public List<Marque> getFabricantByBrand(int fabricantId) {
        List<Marque> marques = new ArrayList<>();
        String sqlRequest = "Select id_marque, nom_marque, id_fabricant, (select nom_fabricant from fabricant where fabricant.id_fabricant = marque.id_fabricant) as nom_fabricant from MARQUE WHERE ID_FABRICANT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, fabricantId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Marque marque = new Marque(resultSet.getInt("id_marque"), resultSet.getString("nom_marque"));
                Fabricant fabricant = new Fabricant();
                fabricant.setId(resultSet.getInt("id_fabricant"));
                fabricant.setNomFabricant(resultSet.getString("nom_fabricant"));
                marque.setFabricant(fabricant);
                marques.add(marque);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marques;
    }

}

