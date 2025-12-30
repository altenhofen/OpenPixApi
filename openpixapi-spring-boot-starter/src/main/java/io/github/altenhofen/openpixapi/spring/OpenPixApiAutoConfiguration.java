package io.github.altenhofen.openpixapi.spring;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/** OpenPixApi Spring Boot Auto Configuration entrypoint. */
@AutoConfiguration
@EnableConfigurationProperties(OpenPixApiProperties.class)
public class OpenPixApiAutoConfiguration {
  /**
   * Bean for injection of pixClient.
   *
   * @param properties the PixClient config
   * @return the PixClient instance
   */
  @Bean
  @ConditionalOnMissingBean
  public PixClient pixClient(OpenPixApiProperties properties) {
    return new PixClient(properties);
  }

  /**
   * Bean for injection of QrCode creation service.
   *
   * @param properties the QrCode configuration
   * @return the QrCodeService instance
   */
  @Bean
  @ConditionalOnMissingBean
  public QrCodeService qrCodeService(PixQrCodeProperties properties) {
    return new QrCodeService(properties);
  }
}
