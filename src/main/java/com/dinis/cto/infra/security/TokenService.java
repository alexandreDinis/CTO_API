package com.dinis.cto.infra.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dinis.cto.model.person.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String SECRET;


    public String gerarToken(User user) {
        try {
            var algoritmo = Algorithm.HMAC256(SECRET);

            return JWT.create()
                    .withIssuer("cto")
                    .withSubject(user.getUsername())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    private Instant dataExpiracao() {

        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            System.out.println("Verifying token: {}" + tokenJWT);

            var algoritmo = Algorithm.HMAC256(SECRET);

            return JWT.require(algoritmo)
                    .withIssuer("cto")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (TokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expirado. Faça login novamente.");
        } catch (JWTVerificationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido.");
        }
    }
}