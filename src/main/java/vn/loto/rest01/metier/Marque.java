package vn.loto.rest01.metier;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Schema
public class Marque {
    private Integer id;
    private String libelle;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Pays pays;
    private Fabricant fabricant;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer nbArticle;
    public Marque()
    {
        id=0;
        libelle = "";
        fabricant = new Fabricant();
        pays = new Pays();
    }

    public Marque(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }
    public Marque(Integer id, String libelle, int nbArticle) {
        this.id = id;
        this.libelle = libelle;
        this.nbArticle = nbArticle;
    }
    public Marque(Integer id, String libelle, Pays pays, Fabricant fabricant) {
        this.id = id;
        this.libelle = libelle;
        this.pays = pays;
        this.fabricant = fabricant;
    }
    public Marque(Integer id, String libelle, Pays pays) {
        this.id = id;
        this.libelle = libelle;
        this.pays = pays;
    }
    public Marque(Integer id, String libelle, Fabricant fabricant) {
        this.id = id;
        this.libelle = libelle;
        this.fabricant = fabricant;
    }
}
