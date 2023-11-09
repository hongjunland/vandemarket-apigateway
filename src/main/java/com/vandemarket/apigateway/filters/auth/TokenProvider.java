package com.vandemarket.apigateway.filters.auth;

import com.vandemarket.apigateway.exception.ErrorMessage;
import com.vandemarket.apigateway.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;


@Slf4j
@Component
public class TokenProvider{
    private Key key;
    private Long accessTokenExp;
    private Long refreshTokenExp;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.expiration.access}") Long accessTokenExp,
                         @Value("${jwt.expiration.refresh}") Long refreshTokenExp){
        byte[] secretByteKey = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
    }

    public boolean isValidToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e){
            log.error(ErrorMessage.INVALID_TOKEN.getMessage(), e);
            throw new TokenException(ErrorMessage.INVALID_TOKEN.getMessage());
        }catch (ExpiredJwtException e){
            log.error(ErrorMessage.EXPIRED_TOKEN.getMessage(), e);
            throw new TokenException(ErrorMessage.EXPIRED_TOKEN.getMessage());
        }catch (UnsupportedJwtException e){
            log.error(ErrorMessage.UNSUPPORTED_TOKEN.getMessage(), e);
            throw new TokenException(ErrorMessage.UNSUPPORTED_TOKEN.getMessage());
        }catch (IllegalArgumentException e){
            log.error(ErrorMessage.EMPTY_CLAIMS.getMessage(), e);
            throw new TokenException(ErrorMessage.EMPTY_CLAIMS.getMessage());
        }
    }

    public Claims parseClaims(String accessToken) throws ExpiredJwtException {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    }


}