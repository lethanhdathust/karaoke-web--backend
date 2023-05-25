package com.programming.karaoke.configure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //    todo Function<Claims,T> là một đối tượng có thể nhận đối tượng
//   todo  Claims làm tham số đầu vào và trả về một giá trị có kiểu dữ liệu tùy thuộc vào tham số kiểu T.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
//
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(
            Map<String ,Object> extraClaims,
            UserDetails userDetails
    ){
        long nowMillis = System.currentTimeMillis();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + 3600000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private static final String secretKey = "5367566B5970337336763979244226452948404D6351665468576D5A71347437";

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//       return key;
    }
}
