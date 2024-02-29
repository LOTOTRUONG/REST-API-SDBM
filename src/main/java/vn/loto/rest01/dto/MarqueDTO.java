package vn.loto.rest01.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Data;
import vn.loto.rest01.hateoas.HateOas;
import vn.loto.rest01.metier.Marque;

import java.util.ArrayList;
import java.util.List;

@Data
public class MarqueDTO extends HateOas {
    @JsonProperty(index = 1)
    private Integer idMarque;
    @JsonProperty(index = 2)
    private String libelleMarque;
    @JsonProperty(index = 3)
    private String libellePays;
    @JsonProperty(index = 4)
    private String libelleFabricant;

    public MarqueDTO(Marque marque){
        this.idMarque = marque.getId();
        this.libelleMarque = marque.getLibelle();
        this.libellePays = marque.getPays().getLibelle();
        this.libelleFabricant = marque.getFabricant().getNomFabricant();
    }

    public MarqueDTO(Marque marque, UriBuilder uriBuilder){
        idMarque = marque.getId();
        libelleMarque = marque.getLibelle();
        libellePays = marque.getPays().getLibelle();
        libelleFabricant = marque.getFabricant().getNomFabricant();

        addLink("Marque", HttpMethod.GET, uriBuilder.clone().path(idMarque.toString()).build());
        if (marque.getNbArticle() == 0 )
            addLink("DELETE", HttpMethod.DELETE, uriBuilder.clone().path(idMarque.toString()).build());
        addLink("All", HttpMethod.GET, uriBuilder.clone().build());

    }

    public static List<MarqueDTO> toDtoList(List<Marque> marqueList, UriBuilder uriBuilder){
        List<MarqueDTO> marqueDTOList = new ArrayList<>();
        for (Marque marque : marqueList){
            marqueDTOList.add(new MarqueDTO(marque, uriBuilder));
        }
        return marqueDTOList;
    }
}
