package dev.mettan.paseto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatUtilsTest {

    @Test
    public void decodeHexShouldDecodeStringToByte() {
        // Given
        final String hex = "e53782281b";
        byte[] expected = new byte[5];
        expected[0] = -27;
        expected[1] = 55;
        expected[2] = -126;
        expected[3] = 40;
        expected[4] = 27;

        // When
        byte[] result = FormatUtils.decodeHex(hex);

        // Then
        assertEquals(expected.length, result.length);
        assertEquals(expected[0], result[0]);
        assertEquals(expected[1], result[1]);
        assertEquals(expected[2], result[2]);
        assertEquals(expected[3], result[3]);
        assertEquals(expected[4], result[4]);
    }
}
