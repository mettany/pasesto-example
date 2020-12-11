package dev.mettan.paseto;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class PasetoTest {

    private final Paseto paseto = new Paseto();

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

    @Test
    public void verifyTokenWithInvalidTokenShouldReturnFalse() {
        String token = "v2.public.eyJpZCI6ICI0MTBkZjI5Ni04OWQ1LTQzODAtODQyMy02ZjJkNzMwNDA3NDQiLCAibmFtZSI6ICJSYW5kYWxsIERlZ2dlcyIsICJleHAiOiAiMjAxOS0xMC0xMFQxMTowMzoyNC0wNzowMCJ9xe6hZBYn8IZoJmgL9k1VjTcl7Dz4T-lo2FvIxeFXQNtNY3QAyCaa5XW-29n-9nV-beU6z7P-YF97lPFvnPfnDA.eyJraWQiOiAiMTIzNDUifQ";

        boolean result = paseto.verifyToken(token);

        assertFalse(result);
    }

    @Test
    public void verifyTokenWithValidTokenShouldReturnTrue() {
        String token = "v2.public.eyJzdWIiOiJleGFtcGxlLXN1YmplY3QiLCJvdGhlckN1c3RvbUNsYWltIjoib3RoZXJWYWx1ZSIsImlzcyI6ImV4YW1wbGUtaXNzdWVyIiwiaWF0IjoiMjAyMC0xMi0xMVQyMzoyMDozMSswMTAwIiwieW91ckN1c3RvbUNsYWltIjoidmFsdWUifWz2qqLLRS02_nRvWFKAMNIEcbWFnDLBgf8DyGIMopyiTHihSZAAMNPdPiUrvw-gYWdvRwJAUTfFnygKpg2l-wc.b3B0aW9uYWxGb290ZXJBc1N0cmluZw";

        boolean result = paseto.verifyToken(token);

        assertTrue(result);
    }

}
