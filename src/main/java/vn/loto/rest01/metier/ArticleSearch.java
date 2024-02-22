package vn.loto.rest01.metier;

import lombok.Getter;
import lombok.Setter;
import vn.loto.rest01.metier.submetier.*;

public class ArticleSearch {
    @Getter @Setter
    private int id ;
    @Getter @Setter
    private String libelle = "";
    private Titrage titrageMin = new Titrage(0F);
    private Titrage titrageMax = new Titrage(30F);
    @Getter @Setter
    private Volume volume = new Volume();
    @Getter @Setter
    private Stock stock=new Stock();
    @Getter @Setter
    private Float prix;

    @Getter @Setter
    private Pays pays = new Pays();
    @Getter @Setter
    private Continent continent = new Continent();
    @Getter @Setter
    private Couleur couleur = new Couleur();
    @Getter @Setter
    private Type typeBiere = new Type();
    @Getter @Setter
    private Marque marque = new Marque();
    @Getter @Setter
    private Fabricant fabricant = new Fabricant();

    public Titrage getTitrageMin() {
        return titrageMin;
    }

    public void setTitrageMin(Titrage titrageMin) {
        this.titrageMin = titrageMin;
    }

    public Titrage getTitrageMax() {
        return titrageMax;
    }

    public void setTitrageMax(Titrage titrageMax) {
        this.titrageMax = titrageMax;
    }

}
