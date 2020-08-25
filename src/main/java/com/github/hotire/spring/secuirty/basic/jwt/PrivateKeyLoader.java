package com.github.hotire.spring.secuirty.basic.jwt;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Base64;
import java.util.Enumeration;

public class PrivateKeyLoader {


    private static final String PKCS_1_HEADER = "-----BEGIN RSA PRIVATE KEY-----\n";
    private static final String PKCS_1_FOOT = "-----END RSA PRIVATE KEY-----";

    public static PrivateKey load(final String key) {
        String privateKey = key.replace(
                PKCS_1_HEADER, "")
                               .replace(PKCS_1_FOOT, "");

        // Base64 decode the data
        byte[] encodedPrivateKey = Base64.getDecoder().decode(privateKey);

        try {
            ASN1Sequence primitive = (ASN1Sequence) ASN1Sequence
                    .fromByteArray(encodedPrivateKey);
            Enumeration<?> e = primitive.getObjects();
            BigInteger v = ((DERInteger) e.nextElement()).getValue();

            int version = v.intValue();
            if (version != 0 && version != 1) {
                throw new IllegalArgumentException("wrong version for RSA private key");
            }
            /**
             * In fact only modulus and private exponent are in use.
             */
            BigInteger modulus = ((DERInteger) e.nextElement()).getValue();
            BigInteger publicExponent = ((DERInteger) e.nextElement()).getValue();
            BigInteger privateExponent = ((DERInteger) e.nextElement()).getValue();
            BigInteger prime1 = ((DERInteger) e.nextElement()).getValue();
            BigInteger prime2 = ((DERInteger) e.nextElement()).getValue();
            BigInteger exponent1 = ((DERInteger) e.nextElement()).getValue();
            BigInteger exponent2 = ((DERInteger) e.nextElement()).getValue();
            BigInteger coefficient = ((DERInteger) e.nextElement()).getValue();

            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, privateExponent);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (IOException e2) {
            throw new IllegalStateException();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }
}
