package io.github.altenhofen.openpixapi.core.pixbuilder;

import io.github.altenhofen.openpixapi.core.Pix;
import io.github.altenhofen.openpixapi.core.payload.PixPayload;
import io.github.altenhofen.openpixapi.core.payload.StaticPixPayload;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PixCreatorTest {

    @Test
    public void shouldBuildValidStaticPix() {
        StaticPixPayload validPix = Pix.builder().staticPix()
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
        PixPayload validPix = Pix.builder().dynamicPix()
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
                () -> Pix.builder()
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
                () -> Pix.builder()
                        .dynamicPix()
                        .pspUrl("https://pix.example.com/api/webhook")
                        .merchantName("John Doe")
                        .merchantCity("Porto Alegre")
                        .merchantAmount(BigDecimal.valueOf(52.00))
                        .build()
        );
        assertNotNull(validPix);
    }

    @Test
    public void shouldBuildDynamicPix_WithPspUrlInConstructor() {
        PixPayload validPix = Pix.builder()
                        .dynamicPix("https://pix.example.com/api/webhook")
                        .merchantName("John Doe")
                        .merchantCity("Porto Alegre")
                        .merchantAmount(BigDecimal.valueOf(52.00))
                        .txid("1234ABC")
                        .build();
        assertNotNull(validPix);
    }

    @Test
    public void shouldNotBuildDynamicPix_NoPspUrl() {
        IllegalArgumentException validPix = assertThrows(IllegalArgumentException.class,
                () -> Pix.builder()
                        .dynamicPix()
                        .merchantName("John Doe")
                        .merchantCity("Porto Alegre")
                        .merchantAmount(BigDecimal.valueOf(52.00))
                        .txid("1234ABC")
                        .build()
        );
        assertNotNull(validPix);
    }

    @Test
    public void shouldBuildValidPix_NoAmount() {
        StaticPixPayload validPix = assertDoesNotThrow(
                () -> Pix.builder()
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
        IllegalStateException nullException = assertThrows(IllegalStateException.class, () -> Pix.builder()
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
        PixPayload payload = Pix.builder().staticPix()
                .merchantName(name)
                .merchantCity("Porto Alegre")
                .merchantAmount(BigDecimal.valueOf(123.99))
                .txid("0512TX123456789")
                .pixKey("+5551999999999")
                .build();

        assertNotEquals(payload.getMerchantName(), name);
    }
}
