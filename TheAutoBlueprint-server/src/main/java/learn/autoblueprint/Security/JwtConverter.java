package learn.autoblueprint.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learn.autoblueprint.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Consider storing this securely
    private final String ISSUER = "auto-blueprint";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

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

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            String trimmedToken = token.substring(7); // Remove "Bearer " prefix

            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(trimmedToken);

            Claims claims = jws.getBody();
            String username = claims.getSubject();
            String authStr = claims.get("authorities", String.class);
            int appUserId = claims.get("app_user_id", Integer.class);

            return new AppUser(appUserId, username, null, true,
                    Arrays.stream(authStr.split(",")).toList());

        } catch (JwtException e) {
            // Log the exception for debugging and audit purposes
            System.err.println("JWT parsing failed: " + e.getMessage());
        }

        return null;
    }
}