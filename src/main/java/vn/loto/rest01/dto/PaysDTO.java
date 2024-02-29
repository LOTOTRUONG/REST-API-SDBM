package vn.loto.rest01.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Data;
import org.glassfish.jersey.server.Uri;
import vn.loto.rest01.hateoas.HateOas;
import vn.loto.rest01.metier.Pays;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaysDTO extends HateOas {
    @JsonProperty(index = 1)
    private Integer idPays;
    @JsonProperty(index = 2)
    private String libellePays;
    @JsonProperty(index = 3)
    private String libelleContinent;

    public PaysDTO(Pays pays){
        this.idPays = pays.getId();
        this.libellePays = pays.getLibelle();
        this.libelleContinent = pays.getContinent().getLibelle();
    }

    public PaysDTO(Pays pays, UriBuilder uriBuilder){
        idPays = pays.getId();
        libellePays = pays.getLibelle();
        libelleContinent = pays.getContinent().getLibelle();
        addLink("Pays", HttpMethod.GET, uriBuilder.clone().path(idPays.toString()).build());
        if (pays.getCountMarque() == 0 )
            addLink("DELETE", HttpMethod.DELETE, uriBuilder.clone().path(idPays.toString()).build());
        addLink("All",HttpMethod.GET, uriBuilder.clone().build());
    }

    public static List<PaysDTO> toDtoList(List<Pays> paysList, UriBuilder uriBuilder){
        List<PaysDTO> paysDTOList = new ArrayList<>();
        for (Pays pays : paysList){
            paysDTOList.add(new PaysDTO(pays, uriBuilder));
        }
        return paysDTOList;
    }
}
