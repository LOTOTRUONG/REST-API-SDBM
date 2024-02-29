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
        String sqlRequest = "SELECT ID_MARQUE, NOM_MARQUE, " +
                "(Select nom_pays from pays where pays.id_pays = marque.id_pays) as NOM_PAYS, " +
                "ISNULL((Select nom_fabricant from fabricant where fabricant.id_fabricant = marque.id_fabricant), 'Autre') as NOM_FABRICANT, " +
                "(select count(*) from article where article.ID_MARQUE = marque.ID_MARQUE) as NbArticle " +
                "FROM MARQUE WHERE ID_MARQUE = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Marque marque = new Marque(resultSet.getInt("ID_MARQUE"), resultSet.getString("NOM_MARQUE"));
                Pays pays = new Pays();
                pays.setLibelle(resultSet.getString("NOM_PAYS"));
                Fabricant fabricant = new Fabricant();
                fabricant.setNomFabricant(resultSet.getString("NOM_FABRICANT"));
                marque.setPays(pays);
                marque.setFabricant(fabricant);
                marque.setNbArticle(resultSet.getInt("NbArticle"));
                return marque;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Marque> getAll() {
        ArrayList<Marque> liste = new ArrayList<>();
        String sqlRequest = "SELECT ID_MARQUE, NOM_MARQUE, " +
                "           (Select nom_pays from pays where pays.id_pays = marque.id_pays) as NOM_PAYS, " +
                "           ISNULL((Select nom_fabricant from fabricant where fabricant.id_fabricant = marque.id_fabricant), 'Autre') as NOM_FABRICANT, " +
                "           (select count(*) from article where article.ID_MARQUE = marque.ID_MARQUE) as NbArticle " +
                "           FROM MARQUE";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                Pays pays = new Pays();
                pays.setLibelle(resultSet.getString("NOM_PAYS"));
                Fabricant fabricant = new Fabricant();
                fabricant.setNomFabricant(resultSet.getString("NOM_FABRICANT"));
                Marque marque = new Marque(resultSet.getInt(1), resultSet.getString(2));
                marque.setPays(pays);
                marque.setFabricant(fabricant);
                marque.setNbArticle(resultSet.getInt("NbArticle"));
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
        String sqlRequest = "insert into Marque values (?,?,?) ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getLibelle());
            preparedStatement.setInt(2, object.getPays().getId());
            preparedStatement.setInt(3, object.getFabricant().getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1 ){
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
