package com.exam.authen;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.exam.authen.user.User;
import com.exam.authen.exception.HttpException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class TokenService{

    @Value("${token.maxAgeSeconds}")
    private long maxAge;

    @Value("${token.secret}")
    private String secret;

    private Algorithm algorithm;

    private JWTVerifier jwtVerifier;

    public TokenService (){

    }

    @PostConstruct
    private void init(){
        algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm).build();
    }

    public String createToken(User user){
        LocalDateTime now = LocalDateTime.now();
        try{
            return JWT.create()
                    .withIssuer("UserService")
                    .withIssuedAt(Date.from(now
                            .atZone(ZoneId.systemDefault()).toInstant()))
                    .withExpiresAt(Date.from(now
                            .plusSeconds(this.maxAge)
                            .atZone(ZoneId.systemDefault()).toInstant()))
                    .withClaim("username", user.getUsername())
                    .withClaim("firstname", user.getFirstname())
                    .withClaim("lastname", user.getLastname())
                    .sign(algorithm);
        }catch(JWTCreationException e){
            throw new JWTCreationException("Cannot properly create token", e);
        }
    }

    public DecodedJWT checkToken(String token) throws JWTVerificationException{
        DecodedJWT djwt = jwtVerifier.verify(token);
        return djwt;
    }

    public String getUser(String token){
        DecodedJWT djwt = checkToken(token);
        Claim claim = djwt.getClaim("username");
        return claim.asString();
    }

    public void validateToken (String auth) {
        if(auth.trim().isEmpty()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
            checkToken(auth);
    }


}