package dev.mettan.paseto;

import com.google.gson.GsonBuilder;
import dev.paseto.jpaseto.Pasetos;
import dev.paseto.jpaseto.io.gson.GsonSerializer;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Map;

public class Paseto {

    private final String privateKey = "4f67efbf5bac0ac01c30f359e603c79d45b2b81d503c1c8b0fada469a3b9d50e2d9f331a7ebb8a870dc642a2351bad47ff82b88f096aff3e85ef543fed9cf7b8";
    private final String publicKey = "2d9f331a7ebb8a870dc642a2351bad47ff82b88f096aff3e85ef543fed9cf7b8";

    /**
     * This method is returning the generated PASETO token
     * @return a public v2 PASETO token
     */
    public String signToken() {
        PrivateKey privateKey = getPrivateKey();
        return Pasetos.V2.PUBLIC.builder()
                .setSerializer(getSerializer())
                .setIssuer("example-issuer")
                .setSubject("example-subject")
                .setIssuedAt(Instant.now())
                .claim("yourCustomClaim", "value")
                .claim("otherCustomClaim", "otherValue")
                .setPrivateKey(privateKey)
                .setFooter("optionalFooterAsString")
                .compact();
    }

    /**
     * Get a GsonSerializer used by JPaseto for serialize the data in the token
     * We need to register TypeAdapter for handling the date format serializing
     * @return GsonSerializer
     */
    private GsonSerializer<Map<String, Object>> getSerializer() {
        return new GsonSerializer<>(new GsonBuilder().registerTypeAdapter(Instant.class, new InstantTypeConverter()).create());
    }

    /**
     * Generate a java.security.PrivateKey from a hex encoded private
     * key. You should store it in a secure location. For the example
     * the keys are directly hardcoded in the code
     * @return java.security.PrivateKey
     */
    private PrivateKey getPrivateKey() {
        Security.addProvider(new BouncyCastleProvider());
        byte[] pvKey = FormatUtils.decodeHex(privateKey);
        try {
            KeyFactory keyFact = KeyFactory.getInstance("Ed25519");
            PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed25519), new DEROctetString(pvKey));
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyInfo.getEncoded());
            return keyFact.generatePrivate(pkcs8KeySpec);
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            throw new Error(e);
        }
    }
}
