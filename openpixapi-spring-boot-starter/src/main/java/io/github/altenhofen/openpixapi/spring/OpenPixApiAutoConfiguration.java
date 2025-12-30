package io.github.altenhofen.openpixapi.spring;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(OpenPixApiProperties.class)
public class OpenPixApiAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public PixClient pixClient(OpenPixApiProperties properties) {
    return new PixClient(properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public QrCodeService qrCodeService(PixQrCodeProperties properties) {
    return new QrCodeService(properties);
  }
}
