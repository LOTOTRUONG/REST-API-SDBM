package vn.loto.rest01.metier;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class Type {
    @Schema(required = true, example = "0")
    private Integer id;
    @Schema(required = true, example = "type")
    private String libelle;

    @Getter @Setter
    private Integer nbArticle;

    public Type()
    {
        id=0;
        libelle = "";
    }
    public Type(Integer id, String libelle)
    {
        this.id = id;
        this.libelle = libelle;
    }

    public Type(Integer id, String libelle, Integer nbArticle){
        this.id = id;
        this.libelle = libelle;
        this.nbArticle = nbArticle;
    }

    public Type(String libelle)
    {
        this.libelle = libelle;
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




    @Override
    public String toString()
    {
        return libelle;
    }
}
