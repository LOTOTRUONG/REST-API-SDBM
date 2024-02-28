package vn.loto.rest01.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import vn.loto.rest01.metier.User;

import java.util.Calendar;
import java.util.Date;

public class MyToken {
    private static final String SECRET_KEY = "MySecretK3Y";

    public static String generate(User user){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MINUTE, 10);
        Date expiration = calendar.getTime();
        String token = JWT.create()
                            .withClaim("Login", user.getLogin())
                            .withIssuedAt(now)
                            .withExpiresAt(expiration)
                            .sign(Algorithm.HMAC256(SECRET_KEY));
            return "Bearer" + token;
    }
    public static Boolean validate(String token){
        if (token != null && token.startsWith(("Bearer")))
            token = token.substring(7);
        Boolean retour = false;
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            retour = !jwt.getExpiresAt().before(now);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return retour;
    }
}
