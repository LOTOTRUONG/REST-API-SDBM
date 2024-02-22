package vn.loto.rest01.dao;

import vn.loto.rest01.metier.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MarqueDAO extends DAO<Marque, Marque, Integer> {
    @Override
    public Marque getByID(Integer id) {
        String sqlReques = "SELECT ID_MARQUE, NOM_MARQUE FROM MARQUE WHERE ID_MARQUE = " +id;
        Marque marque;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlReques);
            if(resultSet.next()) return new Marque(resultSet.getInt(1),resultSet.getString(2));
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Marque> getAll() {
        ArrayList<Marque> liste = new ArrayList<>();
        String sqlRequest = "SELECT ID_MARQUE, NOM_MARQUE, PAYS.ID_PAYS, PAYS.NOM_PAYS, CONTINENT.ID_CONTINENT, CONTINENT.NOM_CONTINENT, " +
                "ISNULL(FABRICANT.ID_FABRICANT, (SELECT MAX(ID_FABRICANT) FROM FABRICANT) + 1) AS ID_FABRICANT, " +
                "ISNULL(FABRICANT.NOM_FABRICANT, 'Autre') AS NOM_FABRICANT " +
                "FROM MARQUE " +
                "LEFT JOIN FABRICANT ON MARQUE.ID_FABRICANT = FABRICANT.ID_FABRICANT " +
                "LEFT JOIN PAYS ON MARQUE.ID_PAYS = PAYS.ID_PAYS " +
                "JOIN CONTINENT ON PAYS.ID_CONTINENT = CONTINENT.ID_CONTINENT";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                Continent continent = new Continent(resultSet.getInt("ID_CONTINENT"), resultSet.getString("NOM_CONTINENT"));
                Pays pays = new Pays(resultSet.getInt("ID_PAYS"), resultSet.getString("NOM_PAYS"), continent);
                Fabricant fabricant = new Fabricant(resultSet.getInt("ID_FABRICANT"), resultSet.getString("NOM_FABRICANT"));

                Marque marque = new Marque(resultSet.getInt("ID_MARQUE"), resultSet.getString("NOM_MARQUE"));
                marque.setPays(pays);
                marque.setFabricant(fabricant);
                liste.add(marque);

            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }


    @Override
    public ArrayList<Marque> getLike(Marque object) {
        String sqlCommand = "SELECT ID_Marque, NOM_Marque FROM Marque WHERE ID_Marque LIKE ?";
        ArrayList<Marque> liste = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, "%" + object.getId() + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                liste.add(new Marque(resultSet.getInt(1), resultSet.getString(2)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public boolean update(Marque object) {
        String sqlRequest = "update Marque set NOM_Marque = ? WHERE ID_Marque = ?";
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
    public boolean insert(Marque object) {
        String sqlRequest = "insert into Marque values " + object.getLibelle();
        try(Statement statement = connection.createStatement()) {
            statement.execute(sqlRequest);
            return true;
        }catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Marque object) {
        String sqlRequest = "Delete from Marque WHERE ID_Marque = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, object.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            return false;
        }
    }

    public List<Marque> getByPays(Pays pays) {
        List<Marque> marqueList = new ArrayList<>();
        String sqlRequest = "SELECT id_marque, nom_marque FROM Marque WHERE id_pays = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, pays.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                marqueList.add(new Marque(resultSet.getInt(1), resultSet.getString(2), pays));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marqueList;
    }

}
