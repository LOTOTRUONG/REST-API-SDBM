package vn.loto.rest01.hateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
@Data
public class HateOas {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Link> links;
    public HateOas(){
        links = new ArrayList<>();
    }
    public void addLink(String name, String httpMethod, URI uri) {
       Link link = new Link(name, httpMethod, uri);
        links.add(link);
    }

    public void addLink(String name, String httpMethod, URI requestURI, Object object){
        Link link = new Link(name, httpMethod, requestURI, object);
        links.add(link);
    }
}
