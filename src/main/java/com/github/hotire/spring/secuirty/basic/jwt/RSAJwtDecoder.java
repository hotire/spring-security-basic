package com.github.hotire.spring.secuirty.basic.jwt;


import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAJwtDecoder extends JwtDecoderDecorator {

    @Getter
    private final RSAPublicKey rsaPublicKey;

    public RSAJwtDecoder(RSAPublicKey rsaPublicKey) {
        super(NimbusJwtDecoder.withPublicKey(rsaPublicKey).build());
        this.rsaPublicKey = rsaPublicKey;
    }

    public static RSAJwtDecoder withPublicKey(@NonNull final String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        final byte[] res = Base64.getDecoder().decode(publicKey.replaceAll("(\r\n|\r|\n|\n\r)", "").getBytes(StandardCharsets.UTF_8));
        final RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(res));
        return new RSAJwtDecoder(rsaPublicKey);
    }
}
