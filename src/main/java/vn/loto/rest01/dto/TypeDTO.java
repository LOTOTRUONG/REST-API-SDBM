package vn.loto.rest01.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Data;
import vn.loto.rest01.hateoas.HateOas;
import vn.loto.rest01.metier.Type;

import java.util.ArrayList;
import java.util.List;

@Data
public class TypeDTO extends HateOas {
    @JsonProperty(index = 1)
    private Integer idType;
    @JsonProperty(index = 2)
    private String libelleType;

    public TypeDTO(Type type){
        this.idType = type.getId();
        this.libelleType = type.getLibelle();
    }
    public TypeDTO(Type type, UriBuilder uriBuilder){
        idType = type.getId();
        libelleType = type.getLibelle();
        addLink("Type", HttpMethod.GET, uriBuilder.clone().path(idType.toString()).build());
        if (type.getNbArticle() == 0)
            addLink("DELETE", HttpMethod.DELETE, uriBuilder.clone().path(idType.toString()).build());
        addLink("All", HttpMethod.GET, uriBuilder.clone().build());
    }

    public static List<TypeDTO> toDtoList(List<Type> typeList, UriBuilder uriBuilder){
        List<TypeDTO> typeDTOList = new ArrayList<>();
        for (Type type : typeList){
            typeDTOList.add(new TypeDTO(type, uriBuilder));
        }
        return typeDTOList;
    }
}
