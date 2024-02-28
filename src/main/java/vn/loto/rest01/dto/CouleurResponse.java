package vn.loto.rest01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.loto.rest01.hateoas.HateOas;
import vn.loto.rest01.metier.Couleur;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouleurResponse {
    private Couleur couleur;
    private HateOas hateOas;
}
