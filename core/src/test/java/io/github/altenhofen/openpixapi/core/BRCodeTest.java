package io.github.altenhofen.openpixapi.core;

import io.github.altenhofen.openpixapi.core.pixbuilder.PixBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BRCodeTest {

    @Test
    void contextLoads() {
        String pixao = PixBuilder
                .staticPix()
                .merchantName("PAULO KOGOS")
                .merchantCity("SAO PAULO")
                .merchantAmount(BigDecimal.valueOf(1.99))
                .txid("0512TX123456789")
                .pixKey("a2eb0360-493e-46b1-ac86-22df16706d7a")
                .build();
        assertNotNull(pixao);
    }
}
