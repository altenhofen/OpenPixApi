package io.github.altenhofen.openpixapi.core.pixbuilder;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PixBuilderTest {

    @Test
    public void shoudBuildValidPix() {
        String validPix = PixBuilder
                .staticPix()
                .merchantName("John Doe")
                .merchantCity("Porto Alegre")
                .merchantAmount(BigDecimal.valueOf(123.99))
                .txid("0512TX123456789")
                .pixKey("+5551999999999")
                .build();

        assertNotNull(validPix);
    }

    @Test
    public void shouldBuildValidPix_NoAmount() {
        String validPix = assertDoesNotThrow(
                () -> PixBuilder
                .staticPix()
                .merchantName("John Doe")
                .merchantCity("Porto Alegre")
                .merchantAmount(null)
                .txid("0512TX123456789")
                .pixKey("+5551999999999")
                .build());

        assertNotNull(validPix);
    }

    @Test
    public void shouldNotBuildInvalidPix_MerchantIsNull() {
        // null
        IllegalStateException nullException = assertThrows(IllegalStateException.class, () -> PixBuilder
                .staticPix()
                .merchantName(null)
                .merchantCity("Porto Alegre")
                .merchantAmount(BigDecimal.valueOf(123.99))
                .txid("0512TX123456789")
                .pixKey("+5551999999999")
                .build());

        assertTrue(nullException.getMessage().contains("required"));
    }

    @Test
    public void shouldNotBuildInvalidPix_LargeMerchantName() {
        IllegalArgumentException sizeException = assertThrows(IllegalArgumentException.class, () -> PixBuilder
                .staticPix()
                .merchantName("A".repeat(100))
                .merchantCity("Porto Alegre")
                .merchantAmount(BigDecimal.valueOf(123.99))
                .txid("0512TX123456789")
                .pixKey("+5551999999999")
                .build());

        assertTrue(sizeException.getMessage().contains("length"));
    }
}
