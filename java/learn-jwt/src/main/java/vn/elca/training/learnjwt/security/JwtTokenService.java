package vn.elca.training.learnjwt.security;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import vn.elca.training.learnjwt.model.TokenData;

/**
 *
 * @author vlp
 */
@Service
@Slf4j
public class JwtTokenService {
    @Value("${learn.jwt.expirationMs}")
    private int jwtExpirationMs;

    private List<TokenData> tokenData = new LinkedList<>();

    public String generateJwtToken(UserDetails userDetails) {
        String jwtSessionId = UUID.randomUUID().toString();
        Date now = new Date();
        Date expiration = DateUtils.addMilliseconds(now, jwtExpirationMs);
        tokenData.add(new TokenData(jwtSessionId, true, expiration));
        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setId(jwtSessionId)
                .signWith(SignatureAlgorithm.HS512, userDetails.getPassword())
                .compact();
    }

    public String getSessionIdFromToken(String token, UserDetails userDetails) {
        return getClaimFromToken(token, Claims::getId, userDetails);
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token, UserDetails userDetails) {
        return getClaimFromToken(token, Claims::getSubject, userDetails);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token, UserDetails userDetails) {
        return getClaimFromToken(token, Claims::getExpiration, userDetails);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, UserDetails userDetails) {
        final Claims claims = Optional.ofNullable(getAllClaimsFromToken(token, userDetails))
                .orElseThrow();
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token, UserDetails userDetails) {
        try {
            return Jwts.parser().setSigningKey(userDetails.getPassword()).parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        throw new JwtException("Can't extract claims from the token");
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token, UserDetails userDetails) {
        final Date expiration = getExpirationDateFromToken(token, userDetails);
        return expiration.before(new Date());
    }

    //validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token, userDetails);
        boolean usernameValid = username.equals(userDetails.getUsername());
        boolean tokenNotExpired = !isTokenExpired(token, userDetails);
        boolean tokenNotRefreshed = tokenData.stream()
                .anyMatch(tokenItem -> tokenItem.getSessionId().equals(getSessionIdFromToken(token, userDetails))
                                        && tokenItem.isValid());
        return usernameValid && tokenNotExpired && tokenNotRefreshed;
    }

    public void invalidaToken(String tokenId) {
        tokenData.stream()
                .filter(tokenItem -> tokenItem.getSessionId().equals(tokenId))
                .findAny()
                .ifPresent(tokenItem -> tokenItem.setValid(false));
    }
}
