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
        final String privateKey = key.replace(PKCS_1_HEADER, "")
                                     .replace(PKCS_1_FOOT, "");

        // Base64 decode the data
        final byte[] encodedPrivateKey = Base64.getDecoder().decode(privateKey);

        try {
            final ASN1Sequence primitive = (ASN1Sequence) ASN1Sequence.fromByteArray(encodedPrivateKey);
            final Enumeration<?> e = primitive.getObjects();
            final BigInteger v = ((DERInteger) e.nextElement()).getValue();

            final int version = v.intValue();
            if (version != 0 && version != 1) {
                throw new IllegalArgumentException("wrong version for RSA private key");
            }
            /**
             * In fact only modulus and private exponent are in use.
             */
            final BigInteger modulus = ((DERInteger) e.nextElement()).getValue();
            final BigInteger publicExponent = ((DERInteger) e.nextElement()).getValue();
            final BigInteger privateExponent = ((DERInteger) e.nextElement()).getValue();
            final BigInteger prime1 = ((DERInteger) e.nextElement()).getValue();
            final BigInteger prime2 = ((DERInteger) e.nextElement()).getValue();
            final BigInteger exponent1 = ((DERInteger) e.nextElement()).getValue();
            final BigInteger exponent2 = ((DERInteger) e.nextElement()).getValue();
            final BigInteger coefficient = ((DERInteger) e.nextElement()).getValue();

            final RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, privateExponent);
            final KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }
}
