# OpenPixApi

OpenPixApi is a library for creating, parsing and validating Pix's BRCode focused on developer experience 
and customizability.

It's divided in three modules:

- **core**: zero-dependency BRCode parser, validator and generator.
- **qrcode**: QRCode parser and generator, with a great number of customization options and output formats.
Depends only on ZXing for QRCode generation and parsing.
- **openpixapi-spring-boot-starter**: plugin for spring boot

## Usage
Using fluent API with the Pix class:
```java
  // Create a StaticPix payload with fluent API.
  PixPayload staticPixPayload =
    Pix.builder()
      .staticPix()
      .merchantName("John Doe")
      .merchantCity("Porto Alegre")
      .amount(BigDecimal.valueOf(123.99))
      .txid("0512TX123456789")
      .pixKey("+5551999999999")
      .build();
```

Using factory method API with the Pix class:
```java
final PixPayload dynamicPixPayload = Pix.newDynamic(
    "https://pix.example.com/api/webhook",
    "JOAO SILVA",
    "SAO PAULO",
    "TX123");
```

They can also be directly typed to `StaticPixPayload` and `DynamicPixPayload` for more control.

## Installation

For the core module (EMV parsing, payload creation):
```xml
<dependency>
    <groupId>io.github.altenhofen</groupId>
    <artifactId>openpixapi</artifactId>
    <version>1.0.7</version>
</dependency>
```

For the QRCode module:
```xml
<dependency>
    <groupId>io.github.altenhofen.openpixapi</groupId>
    <artifactId>openpixapi-qrcode</artifactId>
    <version>1.0.7</version>
</dependency>
```

For the Spring Boot starter module:
```xml
<dependency>
    <groupId>io.github.altenhofen</groupId>
    <artifactId>openpixapi-spring-boot-starter</artifactId>
    <version>0.3.0</version>
</dependency>
```
## Features

| Feature                               | Status | Notes                           |
|---------------------------------------|------|---------------------------------|
| Static Pix payload generation         | ✅    | Fully EMV & Pix compliant       |
| Fixed-amount Pix                      | ✅    | `BigDecimal`-safe formatting    |
| Amount-optional Pix                   | ✅    | Valid static Pix without amount |
| CRC16 calculation                     | ✅    | EMV CRC16-CCITT                 |
| CRC validation                        | ✅    | Verifies generated payloads     |
| EMV field length enforcement          | ✅    | Byte-accurate                   |
| Pix-specific validation               | ✅    | BCB rules enforced              |
| Merchant Account Information          | ✅    | GUI + Pix Key                   |
| Additional Data (TXID)                | ✅    | Optional, validated             |
| Builder-style API                     | ✅    | Simple & explicit               |
| Factory-method-style API              | ✅    | Simple                          |
| Minimal dependencies for core package | ✅    | Lightweight                     |
| Java 17+ compatible                   | ✅    | Modern baseline                 |
| Input normalization                   | ✅    | Trim, uppercase, charset-safe   |
| Immutable payload generation          | ✅    | Thread-safe                     |

## Scope

This library does:

- Generate a valid EMV QR payload
- Insert the dynamic URL correctly
- Compute CRC
- Enforce EMV/Pix constraints
- Generate QR images
- Etc, see [roadmap](ROADMAP.md) and [changelog](CHANGELOG.md)

This library does **NOT**

- Create charges at the PSP
- Host the dynamic Pix endpoint
- Handle webhooks or payment confirmation

Think of it as a building block to your backend system.
