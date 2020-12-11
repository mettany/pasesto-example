package dev.mettan.paseto;

import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class Paseto {

    private PrivateKey getPrivateKey() {
        byte[] pvKey = FormatUtils.decodeHex("4f67efbf5bac0ac01c30f359e603c79d45b2b81d503c1c8b0fada469a3b9d50e2d9f331a7ebb8a870dc642a2351bad47ff82b88f096aff3e85ef543fed9cf7b8");
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
