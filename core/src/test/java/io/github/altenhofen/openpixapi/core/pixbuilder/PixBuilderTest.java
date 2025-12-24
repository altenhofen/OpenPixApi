package io.github.altenhofen.openpixapi.core.pixbuilder;

import io.github.altenhofen.openpixapi.core.payload.PixPayload;
import io.github.altenhofen.openpixapi.core.payload.StaticPixPayload;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PixBuilderTest {

    @Test
    public void shouldBuildValidStaticPix() {
        StaticPixPayload validPix = PixBuilder
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
    public void shouldBuildValidDynamicPix() {
        PixPayload validPix = PixBuilder
                .dynamicPix()
                .pspUrl("https://pix.example.com/api/webhook")
                .merchantName("John Doe")
                .merchantCity("Porto Alegre")
                .merchantAmount(BigDecimal.valueOf(123.99))
                .txid("0512TX123456789")
                .build();

        assertNotNull(validPix);
    }

    @Test
    public void shouldBuildValidStaticPix_NoTxid() {
        StaticPixPayload validPix = assertDoesNotThrow(
                () -> PixBuilder
                        .staticPix()
                        .merchantName("John Doe")
                        .merchantCity("Porto Alegre")
                        .merchantAmount(BigDecimal.valueOf(52.00))
                        .pixKey("+5551999999999")
                        .txid(null)
                        .build());

        assertNotNull(validPix);
    }
    @Test
    public void shouldNotBuildDynamicPix_NoTxid() {
        IllegalArgumentException validPix = assertThrows(IllegalArgumentException.class,
                () -> PixBuilder
                        .dynamicPix()
                        .pspUrl("https://pix.example.com/api/webhook")
                        .merchantName("John Doe")
                        .merchantCity("Porto Alegre")
                        .merchantAmount(BigDecimal.valueOf(52.00))
                        .txid(null)
                        .build()
        );
        assertNotNull(validPix);
    }


    @Test
    public void shouldBuildValidPix_NoAmount() {
        StaticPixPayload validPix = assertDoesNotThrow(
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
    public void shouldTruncateLargeMerchantName() {
        String name = "A".repeat(100);
        StaticPixPayload payload = PixBuilder
                .staticPix()
                .merchantName(name)
                .merchantCity("Porto Alegre")
                .merchantAmount(BigDecimal.valueOf(123.99))
                .txid("0512TX123456789")
                .pixKey("+5551999999999")
                .build();

        assertNotEquals(payload.getMerchantName(), name);
    }
}
