package vn.loto.rest01.metier;

import io.swagger.v3.oas.annotations.media.Schema;

public class Continent {
    @Schema(required = true, example = "0")
    private Integer id;
    @Schema(required = true, example = "Europe")
    private String libelle;

    public Continent() {
        id = 0;
        libelle = "";
    }

    public Continent(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    @Override
    public String toString()
    {
        return libelle;
    }

}
