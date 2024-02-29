package vn.loto.rest01.dao;

import vn.loto.rest01.metier.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaysDAO extends DAO<Pays, Continent, Integer> {
    @Override
    public Pays getByID(Integer id) {
        String sqlRequest = "Select id_pays, nom_pays, (select nom_continent from continent where continent.id_continent = pays.id_continent) as nom_continent, (select count(*) from marque where MARQUE.ID_PAYS = PAYS.ID_PAYS) as NbMarque from Pays where id_pays = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Pays pays = new Pays(resultSet.getInt("id_pays"), resultSet.getString("nom_pays"));
                Continent continent = new Continent();
                continent.setLibelle(resultSet.getString("nom_continent"));
                pays.setContinent(continent);
                pays.setCountMarque(resultSet.getInt("NbMarque"));
                return pays;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public ArrayList<Pays> getAll() {
        String sqlRequest = "Select id_pays, nom_pays, (select nom_continent from continent where continent.id_continent = pays.id_continent) as nom_continent, (select count(*) from marque where MARQUE.ID_PAYS = PAYS.ID_PAYS) as NbMarque from Pays";
        ArrayList <Pays> liste = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()){
                Pays pays = new Pays(resultSet.getInt(1), resultSet.getString(2));
                Continent continent = new Continent();
                continent.setLibelle(resultSet.getString("NOM_CONTINENT"));
                pays.setContinent(continent);
                pays.setCountMarque(resultSet.getInt("NbMarque"));
                liste.add(pays);
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
        String sqlCommand = "Select id_pays, nom_pays, (select nom_continent from continent where continent.id_continent = pays.id_continent) as nom_continent from Pays WHERE PAYS.ID_CONTINENT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, continent.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pays pays = new Pays(resultSet.getInt(1), resultSet.getString(2));
                continent.setLibelle(resultSet.getString("NOM_CONTINENT"));
                pays.setContinent(continent);
                pays.setCountMarque(resultSet.getInt("NbMarque"));
                liste.add(pays);
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
