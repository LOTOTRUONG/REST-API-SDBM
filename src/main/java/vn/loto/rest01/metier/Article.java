package vn.loto.rest01.metier;

import lombok.Getter;
import lombok.Setter;
import vn.loto.rest01.metier.submetier.*;

import java.util.Objects;
public class Article {
    @Getter
    @Setter
    private int idArticle;
    @Getter
    @Setter
    private String nomArticle;
    @Getter
    @Setter
    private Volume volumeArticle;
    @Getter
    @Setter
    private Float prixArticle;
    @Getter
    @Setter
    private Titrage titrageArticle;
    @Getter
    @Setter
    private Type typeArticle;
    @Getter
    @Setter
    private Couleur couleurArticle;
    @Getter
    @Setter
    private Marque marqueArticle;
    @Getter
    @Setter
    private Continent continentArticle;
    @Getter
    @Setter
    private Pays paysArticle;
    @Getter
    @Setter
    private Fabricant fabricantArticle;
    @Getter
    @Setter
    private Stock stockArticle;
    public Article(){

    }

    public Article(int idArticle, String nomArticle) {
        this.idArticle = idArticle;
        this.nomArticle = nomArticle;
        volumeArticle = new Volume();
        stockArticle = new Stock();
        titrageArticle = new Titrage(0F);
        typeArticle = new Type();
        couleurArticle = new Couleur();
        marqueArticle = new Marque();
        paysArticle = new Pays();
        continentArticle = new Continent();
        fabricantArticle = new Fabricant();
    }

    public Article(int idArticle, String nomArticle, float prixBiere, int volumnBiere, float titrageBiere, int stockBiere, int idType, String nomType, int idCouleur, String nomCouleur, int idMarque, String nomMarque, int idFabricant, String nomFabricant, int idPays, String nomPays, int idContinent, String nomContinent) {
        this.idArticle = idArticle;
        this.nomArticle = nomArticle;
        this.prixArticle = prixBiere;
        this.volumeArticle = new Volume(volumnBiere);
        this.titrageArticle = new Titrage(titrageBiere);
        this.typeArticle = new Type(idType, nomType);
        this.couleurArticle = new Couleur(idCouleur, nomCouleur);
        this.marqueArticle = new Marque(idMarque, nomMarque);
        this.fabricantArticle = new Fabricant(idFabricant, nomFabricant);
        this.paysArticle = new Pays(idPays, nomPays);
        this.continentArticle = new Continent(idContinent, nomContinent);
        this.stockArticle = new Stock(stockBiere);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Article article = (Article) object;

        if (idArticle != article.idArticle) return false;
        if (!Objects.equals(nomArticle, article.nomArticle)) return false;
        if (!Objects.equals(volumeArticle, article.volumeArticle))
            return false;
        if (!Objects.equals(prixArticle, article.prixArticle)) return false;
        if (!Objects.equals(titrageArticle, article.titrageArticle))
            return false;
        if (!Objects.equals(typeArticle, article.typeArticle)) return false;
        if (!Objects.equals(couleurArticle, article.couleurArticle))
            return false;
        if (!Objects.equals(marqueArticle, article.marqueArticle))
            return false;
        if (!Objects.equals(continentArticle, article.continentArticle))
            return false;
        if (!Objects.equals(paysArticle, article.paysArticle)) return false;
        if (!Objects.equals(fabricantArticle, article.fabricantArticle))
            return false;
        return Objects.equals(stockArticle, article.stockArticle);
    }

    @Override
    public int hashCode() {
        int result = idArticle;
        result = 31 * result + (nomArticle != null ? nomArticle.hashCode() : 0);
        result = 31 * result + (volumeArticle != null ? volumeArticle.hashCode() : 0);
        result = 31 * result + (prixArticle != null ? prixArticle.hashCode() : 0);
        result = 31 * result + (titrageArticle != null ? titrageArticle.hashCode() : 0);
        result = 31 * result + (typeArticle != null ? typeArticle.hashCode() : 0);
        result = 31 * result + (couleurArticle != null ? couleurArticle.hashCode() : 0);
        result = 31 * result + (marqueArticle != null ? marqueArticle.hashCode() : 0);
        result = 31 * result + (continentArticle != null ? continentArticle.hashCode() : 0);
        result = 31 * result + (paysArticle != null ? paysArticle.hashCode() : 0);
        result = 31 * result + (fabricantArticle != null ? fabricantArticle.hashCode() : 0);
        result = 31 * result + (stockArticle != null ? stockArticle.hashCode() : 0);
        return result;
    }
}
