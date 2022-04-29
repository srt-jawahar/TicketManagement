package com.foucsr.ticketmanager.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.foucsr.ticketmanager.mysql.database.model.User;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by FocusR on 19/08/17.
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        final String authorities = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String vendorID =userPrincipal.getVendorID();
        if(vendorID==null) {
        	vendorID="";
        }
        
        String agentId =userPrincipal.getAgentId();
        if(agentId==null) {
        	agentId="";
        }
        
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .claim("role", authorities).claim("orgname",userPrincipal.getOrgname())
                .claim("username",userPrincipal.getUsername())
                .claim("vendorID",vendorID)
                .claim("agentId",agentId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
   /* public String generateToken(UserVo u) {
		Claims claims = Jwts.claims().setSubject(u.getUsername());
		claims.put("roles", u.getRoles());
		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + tokenValidity;
		Date exp = new Date(expMillis);
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}*/

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
    
    
    public String generateTokenForgetPassword(User user) {

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        final String authorities = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String vendorID =userPrincipal.getVendorID();
        if(vendorID==null) {
        	vendorID="";
        }
        
        String agentId =userPrincipal.getAgentId();
        if(agentId==null) {
        	agentId="";
        }
        
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .claim("role", authorities).claim("orgname",userPrincipal.getOrgname())
                .claim("username",userPrincipal.getUsername())
                .claim("vendorID",vendorID)
                .claim("agentId",agentId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public String generateTokenForSupplierRegistration(String email , Long supplierRegisterId) {
      
        
        return Jwts.builder()
                .setSubject(Long.toString(supplierRegisterId))
                .claim("email", email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public String generateCoOrdinatorToken(Authentication authentication , String vendorID) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        final String authorities = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        if(vendorID==null) {
        	vendorID="";
        }
        
        String agentId =userPrincipal.getAgentId();
        if(agentId==null) {
        	agentId="";
        }
        
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .claim("role", authorities).claim("orgname",userPrincipal.getOrgname())
                .claim("username",userPrincipal.getUsername())
                .claim("vendorID",vendorID)
                .claim("agentId",agentId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
}
