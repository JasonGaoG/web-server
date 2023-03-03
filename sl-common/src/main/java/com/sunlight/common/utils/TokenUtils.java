package com.sunlight.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenUtils {
    private static final String pubKey = "admin123#admin";

    public static String generateToken(Map<String, String> params) {
        try{
            long now = new Date().getTime();
            Algorithm algorithm = Algorithm.HMAC256(pubKey);
            JWTCreator.Builder builder = JWT.create()
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(now + 30*60*1000))
                    .withIssuer("");
            for (String key: params.keySet()) {
                builder.withClaim(key, params.get(key));
            }
            return builder.sign(algorithm);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> parseToken(String token) {
        try {
            Map<String, String> ret = new HashMap<>();
            Algorithm algorithm = Algorithm.HMAC256(pubKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            for(String key: claims.keySet()) {
                if (claims.get(key).asString() != null) {
                    ret.put(key, claims.get(key).asString());
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Map<String, String> test = new HashMap<>();
        test.put("testKey", "testValue222");
        String token = generateToken(test);
        System.out.println(token);
        Map<String, String> mp = parseToken(token);
        assert mp != null;
        for(String key: mp.keySet()) {
            System.out.println("key: " + key + ", value: " + mp.get(key));
        }

    }
}
