package br.com.devmedia.webservice.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenJWTUtil {

	private static KeyGenerator keyGenerator = new KeyGenerator();

    public static String gerarToken(String username, List<String> roles) {
        Key key = keyGenerator.generateKey();

        String jwtToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key)
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuer("DevMedia")
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(60L)))
                .claim("roles", roles)
                .compact();
        return jwtToken;
    }

    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean tokenValido(String token, Key key) {
        try {
        	// validação do token
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String recuperarNome(String token, Key key) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    public static List<String> recuperarRoles(String token, Key key) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return claimsJws.getBody().get("roles", List.class);
    }
}
