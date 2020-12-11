package dev.mettan.paseto;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PasetoTest {

    private Paseto paseto = new Paseto();

    @Test
    public void signTokenShouldReturnV2PublicToken() {
        String token = paseto.signToken();

        assertNotNull(token);
        String[] splited = token.split("\\.");
        assertEquals(4, splited.length);
        assertEquals("v2", splited[0]);
        assertEquals("public", splited[1]);
        assertEquals("optionalFooterAsString", new String(Base64.getDecoder().decode(splited[3])));
    }

}
