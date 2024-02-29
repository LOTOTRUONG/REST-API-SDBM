package vn.loto.rest01.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Data;
import vn.loto.rest01.hateoas.HateOas;
import vn.loto.rest01.metier.Couleur;

import java.util.ArrayList;
import java.util.List;

@Data
public class CouleurDTO  extends HateOas {
    @JsonProperty(index = 1)
    private Integer idCouleur;
    @JsonProperty(index = 2)
    private String libelleCouleur;

    public CouleurDTO(Couleur couleur){
        this.idCouleur = couleur.getId();
        this.libelleCouleur = couleur.getNomCouleur();
    }

    public CouleurDTO(Couleur couleur, UriBuilder uriBuilder){
        {
            idCouleur = couleur.getId();
            libelleCouleur = couleur.getNomCouleur();
            addLink("Couleur", HttpMethod.GET, uriBuilder.clone().path(idCouleur.toString()).build());
            if (couleur.getNbArticle() == 0)
                addLink("Delete Couleur", HttpMethod.DELETE, uriBuilder.clone().path(idCouleur.toString()).build() );
            addLink("All", HttpMethod.GET, uriBuilder.clone().build() );
        }
    }

    public static List<CouleurDTO> toDtoList(List<Couleur> couleurList, UriBuilder uriBuilder){
            List<CouleurDTO> couleurDTOList = new ArrayList<>();
            for (Couleur couleur : couleurList){
                couleurDTOList.add(new CouleurDTO(couleur, uriBuilder));
            }
            return couleurDTOList;
    }
}
