package vn.loto.rest01.metier;

import lombok.Data;

@Data
public class Continent {
    private Integer id;
    private String libelle;

    public Continent() {
        id = 0;
        libelle = "";
    }

    public Continent(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }



}
