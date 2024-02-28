package vn.loto.rest01.metier;

import lombok.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String login;
    private String password;
    private String roles;
    private String email;

}
