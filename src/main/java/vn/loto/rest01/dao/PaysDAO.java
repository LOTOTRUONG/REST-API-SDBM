package vn.loto.rest01.dao;

import vn.loto.rest01.metier.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaysDAO extends DAO<Pays, Continent, Integer> {
    @Override
    public Pays getByID(Integer id) {
        String sqlRequest = "Select id_pays, nom_pays, id_continent, continent.nom_continent from pays" +
                "join continent on continent.id_continent = pays.id_continent" +
                " where id_pays = " +id;
        Pays pays;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            if(resultSet.next()) return new Pays(resultSet.getInt(1),resultSet.getString(2));
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    @Override
    public ArrayList<Pays> getAll() {
        String sqlRequest = "{Call ps_PaysWithMarque}";
        ArrayList <Pays> liste = new ArrayList<>();
        try(CallableStatement callableStatement = connection.prepareCall(sqlRequest)) {
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Pays pay = new Pays(resultSet.getInt("ID_PAYS"), resultSet.getString("NOM_PAYS"));
                Continent continent = new Continent();
                continent.setLibelle(resultSet.getString("NOM_CONTINENT"));
                pay.setContinent(continent);
                pay.setCountMarque(resultSet.getInt("NUMBER_MARQUE"));
                liste.add(pay);
            }
            resultSet.close();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return liste;
    }


    @Override
    public ArrayList<Pays> getLike(Continent continent) {
        ArrayList<Pays> liste = new ArrayList<>();
        String sqlCommand = "SELECT ID_PAYS, NOM_PAYS, CONTINENT.NOM_CONTINENT FROM PAYS " +
                "JOIN CONTINENT ON CONTINENT.ID_CONTINENT = PAYS.ID_CONTINENT WHERE PAYS.ID_CONTINENT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, continent.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idPays = resultSet.getInt("ID_PAYS");
                String nomPays = resultSet.getString("NOM_PAYS");
                liste.add(new Pays(idPays, nomPays, continent));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public boolean update(Pays object) {
        String sqlRequest = "update PAYS set NOM_PAYS = ? WHERE ID_PAYS = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getLibelle());
            preparedStatement.setInt(2, object.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(Pays object) {
        String sqlRequest = "insert into PAYS values (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getLibelle());
            preparedStatement.setInt(2, object.getContinent().getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1){
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
    public boolean delete(Pays object) {
        String sqlRequest = "Delete from PAYS WHERE ID_PAYS = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            return false;
        }
    }

}
