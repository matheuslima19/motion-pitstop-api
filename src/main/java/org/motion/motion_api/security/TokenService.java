package org.motion.motion_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.motion.motion_api.domain.entities.pitstop.Gerente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_SECRET:my-secret}")
    private String secret;

    public String generateToken(Gerente gerente) {
        var algoritmo = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("motion-api")
                .withSubject(gerente.getEmail())
                .withExpiresAt(LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00")))
                .sign(algoritmo);
    }

    public String validateToken(String token) {
        var algoritmo = Algorithm.HMAC256(secret);
        return JWT.require(algoritmo)
                .withIssuer("motion-api")
                .build()
                .verify(token)
                .getSubject();
    }
}
