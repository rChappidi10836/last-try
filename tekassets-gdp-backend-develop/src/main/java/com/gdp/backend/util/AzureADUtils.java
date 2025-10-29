package com.gdp.backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdp.backend.azure.KeyBean;
import com.gdp.backend.azure.OpenIdConfigurationBean;
import com.gdp.backend.azure.OpenIdKeysBean;
import com.gdp.backend.common.Constants;
import com.gdp.backend.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Component
public class AzureADUtils {

    @Value("${azure.openid.config.url}")
    private String azureOpenIdConfigUrl;

    @Value("${azure.applicationId}")
    private String applicationId;

    private Map<String, Object> getTokenComponents(String idToken) {
        final Base64.Decoder decoder = Base64.getDecoder();
        final StringTokenizer tokenizer = new StringTokenizer(idToken, ".");
        int i = 0;
        Map<String, Object> tokenHeader = new HashMap<>();
        Map<String, Object> tokenBody = new HashMap<>();
        String signatureJws = "";
        final Map<String, Object> tokenMapParts = new HashMap<>();

        //(1) DECODE THE 3 PARTS OF THE JWT TOKEN
        try {
            while (tokenizer.hasMoreElements()) {
                if (i == 0) {
                    tokenHeader = string2JSONMap(new String(decoder.decode(tokenizer.nextToken())));
                } else if (i == 1) {
                    tokenBody = string2JSONMap(new String(decoder.decode(tokenizer.nextToken())));
                } else {
                    signatureJws = tokenizer.nextToken();
                }
                i++;
            }
        } catch (IOException e) {
            throw new InvalidTokenException(e.getMessage());
        }

        //(1.1) THE 3 PARTS OF THE TOKEN SHOULD BE IN PLACE
        if (tokenHeader == null || tokenBody == null || signatureJws == null || tokenHeader.isEmpty() || tokenBody.isEmpty() || signatureJws.isEmpty()) {
            throw new InvalidTokenException("Invalid Token");
        }

        tokenMapParts.put("header", tokenHeader);
        tokenMapParts.put("body", tokenBody);
        tokenMapParts.put("signature", signatureJws);

        return tokenMapParts;
    }

    public String getUsernameFromToken(String idToken) {
        String username = "";
        final Map<String, Object> map = getTokenComponents(idToken);
        Map<String, Object> tokenBody = (Map<String, Object>) map.get(Constants.BODY);
        if (tokenBody != null && tokenBody.containsKey(Constants.USER_NAME)) {
            username = (String) tokenBody.get(Constants.USER_NAME);
        }
        return username;
    }

    public boolean isNewUsernameFromToken(String idToken) {
        boolean isNewUser = false;
        final Map<String, Object> map = getTokenComponents(idToken);
        Map<String, Object> tokenBody = (Map<String, Object>) map.get("body");
        if (tokenBody != null && tokenBody.containsKey("newUser")) {
            Boolean isnewObj = (Boolean) tokenBody.get("newUser");
            if (isnewObj != null && isnewObj.booleanValue()) {
                isNewUser = true;
            }
        }
        return isNewUser;
    }

    public boolean validateToken(String idToken) {
        boolean isValidToken = false;
        try {
            final Map<String, Object> mapTokenComponents = getTokenComponents(idToken);
            final Map<String, Object> tokenHeader = (Map<String, Object>) mapTokenComponents.get("header");

            //(2) GET OPENID CONFIGURATIONS AND SELECT THE MATCHING KEY BEAN
            final String keysUrl = callOpenidConfiguration().getJwksUri();
            KeyBean keyBeanForAccess = null;
            for (KeyBean keyBean : discoveryKeys(keysUrl).getKeys()) {
                if (keyBean.getKid().equals(tokenHeader.get("kid"))) {
                    keyBeanForAccess = keyBean;
                    break;
                }
            }

            //(3) VALIDATE THE JWT CLAIMS
            PublicKey pubKeyNew;
            Claims claims;
            if (keyBeanForAccess == null) {
                throw new InvalidTokenException("Unauthorized access");
            }
            byte[] modulusBytes = Base64.getUrlDecoder().decode(keyBeanForAccess.getN());
            byte[] exponentBytes = Base64.getUrlDecoder().decode(keyBeanForAccess.getE());
            BigInteger modulusInt = new BigInteger(1, modulusBytes);
            BigInteger exponentInt = new BigInteger(1, exponentBytes);
            KeySpec publicSpec = null;

            KeyFactory keyFactory = KeyFactory.getInstance(keyBeanForAccess.getKty());
            if ("RSA".equals(keyBeanForAccess.getKty())) {
                publicSpec = new RSAPublicKeySpec(modulusInt, exponentInt);
            }
            pubKeyNew = keyFactory.generatePublic(publicSpec);
            claims = Jwts.parser()
                    .setSigningKey(pubKeyNew)
                    .parseClaimsJws(idToken).getBody();


            if (claims == null || !applicationId.equals(claims.getAudience())) {
                throw new InvalidTokenException("Invalid audience claim");
            }

            //(4) VERIFY SIGNATURE
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(pubKeyNew);
            sig.update(idToken.getBytes());

            isValidToken = true;

        } catch (InvalidTokenException | NoSuchAlgorithmException
                | InvalidKeyException | SignatureException | InvalidKeySpecException e) {
            throw new InvalidTokenException("Unauthorized access: ", e);
        }

        return isValidToken;
    }

    public OpenIdConfigurationBean callOpenidConfiguration() {
        OpenIdConfigurationBean openIdConfigurationBean = new OpenIdConfigurationBean();
        try {
            final URL url = new URL(azureOpenIdConfigUrl);
            final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            if (con != null) {
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(con.getInputStream()));
                String input;
                StringBuilder builder = new StringBuilder();

                while ((input = br.readLine()) != null) {
                    builder.append(input);
                }
                br.close();

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                openIdConfigurationBean = mapper.readValue(builder.toString(), OpenIdConfigurationBean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openIdConfigurationBean;
    }

    public OpenIdKeysBean discoveryKeys(String keysURL) {
        OpenIdKeysBean openIdKeysBean = new OpenIdKeysBean();
        try {
            final URL url = new URL(keysURL);
            final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            if (con != null) {
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String input;
                StringBuilder builder = new StringBuilder();

                while ((input = br.readLine()) != null) {
                    builder.append(input);
                }
                br.close();

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                openIdKeysBean = mapper.readValue(builder.toString(), OpenIdKeysBean.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return openIdKeysBean;
    }

    public Map<String, Object> string2JSONMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // convert JSON string to Map
        return mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });

    }
}