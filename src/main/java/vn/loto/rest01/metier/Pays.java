package vn.loto.rest01.metier;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Pays {
    private Integer id;
    private String libelle;
    private Continent continent;

    @Getter
    @Setter
    private Integer countMarque;


    public Pays()
    {
        id=0;
        libelle = "";
        continent = new Continent();
    }
    public Pays(Integer id, String libelle)
    {
        this.id = id;
        this.libelle = libelle;
        this.continent = new Continent();
    }

    public Pays(Integer id, String libelle, Continent continent)
    {
        this.id = id;
        this.libelle = libelle;
        this.continent = continent;
    }

    public Pays(Integer id, String libelle, Continent continent, int countMarque)
    {
        this.id = id;
        this.libelle = libelle;
        this.continent = continent;
        this.countMarque = countMarque;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getLibelle()
    {
        return libelle;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

    public Continent getContinent()
    {
        return continent;
    }

    public void setContinent(Continent continent)
    {
        this.continent = continent;
    }


    @Override
    public String toString()
    {
        return libelle;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pays pays = (Pays) o;
        return id == pays.id && Objects.equals(libelle, pays.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }

    public Object getComparatorByValue(String field){
        field = field.toLowerCase();
       if (field.equals("nom_pays")){
           return libelle;
       }
       if (field.equals("id_pays")){
           return id;
       }
       if (field.equals("continent.libelle")){
           return continent.getLibelle();
       }
        return null;
    }


}

