package com.alura.forohub.infra.security;

import com.alura.forohub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

//...
@Service
public class TokenService {

    private final String secret;

    public TokenService(@Value("${api.security.secret}") String secret) {
        this.secret = secret;
    }

    public String generarTokenEmail(String email) {
        Algorithm alg = Algorithm.HMAC256(secret);
        Instant ahora = Instant.now();
        return JWT.create()
                .withIssuer("forohub")
                .withSubject(email)
                .withIssuedAt(Date.from(ahora))
                .withExpiresAt(Date.from(ahora.plus(2, ChronoUnit.HOURS)))
                .sign(alg);
    }

    // Si quieres conservarlo para otros usos, puedes dejar este:
    public String generarToken(com.alura.forohub.domain.usuario.Usuario usuario) {
        return generarTokenEmail(usuario.getEmail());
    }

    public String getSubject(String tokenJWT) {
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.require(alg)
                .withIssuer("forohub")
                .build()
                .verify(tokenJWT)
                .getSubject();
    }
}

