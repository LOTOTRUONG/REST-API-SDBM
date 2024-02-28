package vn.loto.rest01.hateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class HateOas {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Link> links;
    public void addLink(Link link) {
        if(links == null)
            links = new ArrayList<>();
        links.add(link);
    }
}
