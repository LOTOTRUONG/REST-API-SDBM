package vn.loto.rest01.metier;

import io.swagger.v3.oas.annotations.media.Schema;

public class Couleur {
    @Schema(required = true, example = "0")
    private Integer id;
    @Schema(required = true, example = "Rose")
    private String nomCouleur;

    public Couleur(){
        id=0;
        nomCouleur = "";
    }
    public Couleur(Integer id, String nomCouleur)
    {
        this.id = id;
        this.nomCouleur = nomCouleur;
    }
    public Couleur(String nomCouleur){
        this.nomCouleur = nomCouleur;
    }


    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getNomCouleur()
    {
        return nomCouleur;
    }

    public void setNomCouleur(String nomCouleur)
    {
        this.nomCouleur = nomCouleur;
    }

    @Override
    public String toString()
    {
        return nomCouleur;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Couleur couleur = (Couleur) object;

        if (!id.equals(couleur.id)) return false;
        return nomCouleur.equals(couleur.nomCouleur);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nomCouleur.hashCode();
        return result;
    }
}
