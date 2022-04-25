package ru.irlix.learnit.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.issuer}")
    private String jwtIssuer;

    @Value("${app.jwt.lifetimeMs}")
    private int jwtLifetimeMs;

    public String generateJwtToken(String login) {
        Date currentDate = new Date();
        Date expirationDate = new Date((currentDate).getTime() + jwtLifetimeMs);
        return JWT.create()
                .withSubject(login)
                .withIssuedAt(currentDate)
                .withExpiresAt(expirationDate)
                .withIssuer(jwtIssuer)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public boolean validateJwtToken(String jwt) {
        getDecodedJwt(jwt);
        return true;
    }

    public String getUserNameFromJwtToken(String jwt) {
        DecodedJWT decodedJwt = getDecodedJwt(jwt);
        return decodedJwt.getSubject();
    }

    private DecodedJWT getDecodedJwt(String jwt) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(jwtIssuer)
                .build();

        return verifier.verify(jwt);
    }
}
