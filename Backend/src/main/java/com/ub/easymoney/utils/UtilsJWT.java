/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ub.easymoney.models.commons.exceptions.TokenExpiradoException;
import com.ub.easymoney.models.commons.exceptions.TokenInvalidoException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsJWT {

    //llave de encriptacion por text
    private static final String STRING_KEY = "LLAVE ULTRA SECRETA";

    public static String generateSessionToken(String userId) throws JsonProcessingException {
        JwtBuilder builder = Jwts.builder();        
        builder.setSubject(userId); //poner el sujeto en jwt
        return builder.signWith(SignatureAlgorithm.HS512, STRING_KEY).compact();
    }
    
    public static String getBodyToken(String token) throws TokenInvalidoException, TokenExpiradoException {
        try {
            return Jwts.parser().setSigningKey(STRING_KEY).parseClaimsJws(token).getBody().getSubject();
        } catch (SignatureException | IllegalArgumentException e) {
            throw new TokenInvalidoException("Token Invalido");
        } catch (ExpiredJwtException exe) {
            throw new TokenExpiradoException("Token expirado");
        }
    }

    public static Long getUserIdFrom(String token) throws TokenInvalidoException, TokenExpiradoException {
        try {
            return Long.parseLong(Jwts.parser().setSigningKey(STRING_KEY).parseClaimsJws(token).getBody().getSubject());
        } catch (SignatureException | IllegalArgumentException e) {
            throw new TokenInvalidoException("Token Invalido");
        } catch (ExpiredJwtException exe) {
            throw new TokenExpiradoException("Token expirado");
        }
    }

    public static void validateSessionToken(String token) throws TokenExpiradoException, TokenInvalidoException {
        try {
            Jwts.parser().setSigningKey(STRING_KEY).parseClaimsJws(token);
        } catch (SignatureException | IllegalArgumentException e) {
            throw new TokenInvalidoException("Token Invalido");
        } catch (ExpiredJwtException exe) {
            throw new TokenExpiradoException("Token expirado");
        }
    }

}
