package me.secosme.jwtexample;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {

        // pick the algorith, the key is recomended to have the same size of HASH algorithm.

        Algorithm algorithm = Algorithm.HMAC256("9&,is.-GnaO~NhcR2WjpVbNP;+P2<Y@H");
        final String issuer = "votre";

        // create the token

        final Function<LocalDateTime, Date> transform = (ldt) -> Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime exp = now.plusMinutes(30);

        String token = JWT.create()
            .withIssuer(issuer)
            .withIssuedAt(transform.apply(now))
            .withNotBefore(transform.apply(now))
            .withExpiresAt(transform.apply(exp))
            .withClaim("user", "checholin")
            .withClaim("isAdmin", false)
            .withArrayClaim("companies", new String[] {"001", "480"})
            .sign(algorithm);

        System.out.println(token);

        // validate the token

        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build();

        DecodedJWT jwt = verifier.verify(token);

        jwt.getClaims().forEach((name, claim) -> {
            System.out.println(name);
            switch (name) {
                case "issuer":
                    System.out.println(claim.asString());
                    break;
                case "companies":
                    System.out.println(Arrays.asList(claim.asArray(String.class)));
                    break;
                default:
                    System.out.println("N/A");
            }
        });
    }

}
