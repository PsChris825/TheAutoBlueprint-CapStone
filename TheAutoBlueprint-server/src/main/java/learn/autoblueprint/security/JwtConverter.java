package learn.autoblueprint.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learn.autoblueprint.models.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import learn.autoblueprint.security.AppUserService;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    private Key key;
    private final String ISSUER = "auto-blueprint";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public JwtConverter(@Value("${jwt.signing.secret}") String secret) {
        this.key = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
    }


    public String getTokenFromUser(AppUser user) {
        System.out.println("Generating token for user: " + user.getUsername());

        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername())
                .claim("app_user_id", user.getAppUserId())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public AppUser getUserFromToken(String token) {
        System.out.println("Parsing token: " + token);

        if (token == null) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .requireIssuer(ISSUER)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jws.getBody();
            String username = claims.getSubject();
            String authStr = claims.get("authorities", String.class);
            int appUserId = claims.get("app_user_id", Integer.class);

            List<String> roles = Arrays.asList(authStr.split(","));
            return new AppUser(appUserId, username, null, true, roles);

        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (JwtException e) {
            System.err.println("JWT parsing failed: " + e.getMessage());
        }

        return null;
    }

}