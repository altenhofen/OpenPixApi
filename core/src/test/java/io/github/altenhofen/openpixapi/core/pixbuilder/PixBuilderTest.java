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
                .pixKey("a2eb0360-493e-96b1-ac86-22df16706d7a")
                .build();

        assertNotNull(validPix);
    }

    @Test
    public void shouldNotBuildValidPi_nullMerchantName() {
        // null
        IllegalStateException nullException = assertThrows(IllegalStateException.class, () -> PixBuilder
                .staticPix()
                .merchantName(null)
                .merchantCity("Porto Alegre")
                .merchantAmount(BigDecimal.valueOf(123.99))
                .txid("0512TX123456789")
                .pixKey("a2eb0360-493e-96b1-ac86-22df16706d7a")
                .build());

        assertTrue(nullException.getMessage().contains("required"));
    }

    @Test
    public void shouldNotBuildValidPi_longMerchantName() {
        IllegalArgumentException sizeException = assertThrows(IllegalArgumentException.class, () -> PixBuilder
                .staticPix()
                .merchantName("A".repeat(100))
                .merchantCity("Porto Alegre")
                .merchantAmount(BigDecimal.valueOf(123.99))
                .txid("0512TX123456789")
                .pixKey("a2eb0360-493e-96b1-ac86-22df16706d7a")
                .build());

        assertTrue(sizeException.getMessage().contains("length"));
    }
}
