package vn.loto.rest01.dao;

import vn.loto.rest01.metier.*;
import vn.loto.rest01.metier.submetier.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticleDAO extends DAO<Article, ArticleSearch, Integer> {
    @Override
    public Article getByID(Integer id) {
        String sqlRequest = "{call ps_AllArticles}";
        Article article = null;
        try (CallableStatement callableStatement = connection.prepareCall(sqlRequest)) {
            //callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next())
                return new Article(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(3), resultSet.getInt(4), resultSet.getFloat(5), resultSet.getInt(6), resultSet.getInt(7),
                        resultSet.getString(8), resultSet.getInt(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12), resultSet.getInt(13), resultSet.getString(14),
                        resultSet.getInt(15), resultSet.getString(16), resultSet.getInt(17), resultSet.getString(18));
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Article> getAll() {
        ArrayList<Article> liste = new ArrayList<>();
        String sqlRequest = "{call ps_allArticles}";
        try {
            CallableStatement callableStatement = connection.prepareCall(sqlRequest);
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Article");
                String nomBiere = resultSet.getString("Nom_Article");
                float prixachat = resultSet.getFloat("prix_achat");
                int volumn = resultSet.getInt("volume");
                float titrage = resultSet.getFloat("titrage");
                int stock = resultSet.getInt("stock");
                int idType = resultSet.getInt("ID_TYPE");
                String nomType = resultSet.getString("NOM_TYPE");
                int idCouleur = resultSet.getInt("ID_COULEUR");
                String nomCouleur = resultSet.getString("NOM_COULEUR");
                int idFabricant = resultSet.getInt("ID_FABRICANT");
                String nomFabricant = resultSet.getString("NOM_FABRICANT");
                int idMarque = resultSet.getInt("ID_MARQUE");
                String nomMarque = resultSet.getString("NOM_MARQUE");
                int idPays = resultSet.getInt("ID_PAYS");
                String nomPays = resultSet.getString("NOM_PAYS");
                int idContinent = resultSet.getInt("ID_CONTINENT");
                String nomContinent = resultSet.getString("NOM_CONTINENT");


                Article article = new Article(id, nomBiere, prixachat, volumn, titrage, stock, idType, nomType, idCouleur, nomCouleur, idMarque, nomMarque, idFabricant, nomFabricant, idPays, nomPays, idContinent, nomContinent);
                liste.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public ArrayList<Article> getLike(ArticleSearch articleSearch) {
        String sqlCommand = "{call ps_SearchArticle (?,?,?,?,?,?,?,?,?,?,?)}";

        ArrayList<Article> liste = new ArrayList<>();

        try (CallableStatement callableStatement = connection.prepareCall(sqlCommand)) {
            callableStatement.setString(1, articleSearch.getLibelle());
            callableStatement.setInt(2, articleSearch.getVolume().getVolume());
            callableStatement.setFloat(3, articleSearch.getTitrageMin().getTitrage());
            callableStatement.setFloat(4, articleSearch.getTitrageMax().getTitrage());
            callableStatement.setInt(5, articleSearch.getCouleur().getId());
            callableStatement.setInt(6, articleSearch.getTypeBiere().getId());
            callableStatement.setInt(7, articleSearch.getMarque().getId());
            callableStatement.setInt(8, articleSearch.getPays().getId());
            callableStatement.setInt(9, articleSearch.getFabricant().getId());
            callableStatement.setInt(10, articleSearch.getContinent().getId());
            callableStatement.setInt(11, articleSearch.getStock().getStock());

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                Article article = new Article(  resultSet.getInt("ID_ARTICLE"),
                                                resultSet.getString("NOM_ARTICLE")
                                                );
                Volume volume = new Volume(resultSet.getInt("VOLUME"));
                Stock stock = new Stock(resultSet.getInt("STOCK"));
                Titrage titrage = new Titrage(resultSet.getFloat("TITRAGE"));
                Type type = new Type(resultSet.getInt("ID_TYPE"), resultSet.getString("TYPE"));
                Couleur couleur = new Couleur(resultSet.getInt("ID_COULEUR"), resultSet.getString("COULEUR"));
                Continent continent = new Continent(resultSet.getInt("ID_CONTINENT"),resultSet.getString("CONTINENT"));
                Pays pays = new Pays(resultSet.getInt("ID_PAYS"), resultSet.getString("PAYS"));
                Fabricant fabricant = new Fabricant(resultSet.getInt("ID_FABRICANT"), resultSet.getString("FABRICANT"));
                Marque marque = new Marque(resultSet.getInt("ID_MARQUE"), resultSet.getString("MARQUE"));
                marque.setPays(pays);
                marque.setFabricant(fabricant);

                article.setStockArticle(stock);
                article.setPrixArticle(resultSet.getFloat("PRIX_ACHAT"));
                article.setVolumeArticle(volume);
                article.setTitrageArticle(titrage);
                article.setTypeArticle(type);
                article.setCouleurArticle(couleur);
                article.setMarqueArticle(marque);
                article.setPaysArticle(pays);
                article.setContinentArticle(continent);
                article.setFabricantArticle(fabricant);

                liste.add(article);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }



    @Override
    public boolean insert(Article object) {
        String updateQuery = "{call ps_InsertArticle2(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}";
        try (CallableStatement callableStatement = connection.prepareCall(updateQuery))
        {
            callableStatement.setInt(1, object.getIdArticle());
            callableStatement.setString(2, object.getNomArticle());
            callableStatement.setFloat(3, object.getPrixArticle());
            callableStatement.setInt(4, object.getVolumeArticle().getVolume());
            callableStatement.setFloat(5, object.getTitrageArticle().getTitrage());
            callableStatement.setInt(6, object.getStockArticle().getStock());
            callableStatement.setInt(7, object.getTypeArticle().getId());
            callableStatement.setString(8, object.getTypeArticle().getLibelle());
            callableStatement.setInt(9, object.getCouleurArticle().getId());
            callableStatement.setString(10, object.getCouleurArticle().getNomCouleur());
            callableStatement.setInt(11, object.getMarqueArticle().getId());
            callableStatement.setString(12, object.getMarqueArticle().getLibelle());
            callableStatement.setInt(13, object.getFabricantArticle().getId());
            callableStatement.setString(14, object.getFabricantArticle().getNomFabricant());
            callableStatement.setInt(15, object.getPaysArticle().getId());
            callableStatement.setString(16, object.getPaysArticle().getLibelle());
            callableStatement.setInt(17, object.getContinentArticle().getId());
            callableStatement.setString(18, object.getContinentArticle().getLibelle());

            int rowsAffected = callableStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Article updatedArticle) {
        String updateQuery = "{call ps_UpdateArticle2(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}";
        try (CallableStatement callableStatement = connection.prepareCall(updateQuery))
           {
                callableStatement.setInt(1, updatedArticle.getIdArticle());
                callableStatement.setString(2, updatedArticle.getNomArticle());
               callableStatement.setFloat(3, updatedArticle.getPrixArticle());
               callableStatement.setInt(4, updatedArticle.getVolumeArticle().getVolume());
               callableStatement.setFloat(5, updatedArticle.getTitrageArticle().getTitrage());
               callableStatement.setInt(6, updatedArticle.getStockArticle().getStock());
               callableStatement.setInt(7, updatedArticle.getTypeArticle().getId());
               callableStatement.setString(8, updatedArticle.getTypeArticle().getLibelle());
               callableStatement.setInt(9, updatedArticle.getCouleurArticle().getId());
               callableStatement.setString(10, updatedArticle.getCouleurArticle().getNomCouleur());
               callableStatement.setInt(11, updatedArticle.getMarqueArticle().getId());
               callableStatement.setString(12, updatedArticle.getMarqueArticle().getLibelle());
               callableStatement.setInt(13, updatedArticle.getFabricantArticle().getId());
               callableStatement.setString(14, updatedArticle.getFabricantArticle().getNomFabricant());
               callableStatement.setInt(15, updatedArticle.getPaysArticle().getId());
               callableStatement.setString(16, updatedArticle.getPaysArticle().getLibelle());
               callableStatement.setInt(17, updatedArticle.getContinentArticle().getId());
               callableStatement.setString(18, updatedArticle.getContinentArticle().getLibelle());

                int rowsAffected = callableStatement.executeUpdate();

                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


    @Override
    public boolean delete(Article object) {
        String sqlRequest = "Delete from Article WHERE ID_BIERE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, object.getIdArticle());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException E) {
            return false;
        }
    }


}
