package vn.loto.rest01.metier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;
@Data
@Builder
public class Pays {
    private Integer id;
    private String libelle;
    private Continent continent;
    private Integer countMarque;


    public Pays()
    {
        id=0;
        libelle = "";
    }
    public Pays(Integer id, String libelle)
    {
        this.id = id;
        this.libelle = libelle;
    }
    public Pays(Integer id, String libelle, Continent continent)
    {
        this.id = id;
        this.libelle = libelle;
        this.continent = continent;
    }
    public Pays(Integer id, String libelle, Continent continent, Integer countMarque )
    {
        this.id = id;
        this.libelle = libelle;
        this.continent = continent;
        this.countMarque = countMarque;
    }



}

