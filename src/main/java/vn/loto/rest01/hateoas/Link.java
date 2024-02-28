package vn.loto.rest01.hateoas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {
    private String name;
    private String method;
    private URI uri;

}
