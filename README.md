# OpenPixApi

This is an early-stage prototype of the Pix standard implementation.

We plan to offer state-of-the-art APIs for making Pix integration seamless in your project.

**It is not suitable for use in production as of this commit**

## State of the project

I can already create valid Pix EMV Codes that can be used to generate a QRCode
at [pix-qr-decoder](https://pix.nascent.com.br/tools/pix-qr-decoder/).
We plan to have full ASCII, svg, png, base64-encoded, you name it, QRCode support. But for now it's out of scope.

## Features

### Features for the current branch

| Feature                               | Status | Notes                           |
|---------------------------------------|--------|---------------------------------|
| Static Pix payload generation         | ✅      | Fully EMV & Pix compliant       |
| Fixed-amount Pix                      | ✅      | `BigDecimal`-safe formatting    |
| Amount-optional Pix                   | ✅      | Valid static Pix without amount |
| CRC16 calculation                     | ✅      | EMV CRC16-CCITT                 |
| CRC validation                        | ✅      | Verifies generated payloads     |
| EMV field length enforcement          | ✅      | Byte-accurate                   |
| Pix-specific validation               | ✅      | BCB rules enforced              |
| Merchant Account Information          | ✅      | GUI + Pix Key                   |
| Additional Data (TXID)                | ✅      | Optional, validated             |
| Builder-style API                     | ✅      | Simple & explicit               |
| Factory-method-style API              | ✅      | Simple                          |
| Minimal dependencies for core package | ✅      | Lightweight                     |
| Java 17+ compatible                   | ✅      | Modern baseline                 |
| Input normalization                   | ✅      | Trim, uppercase, charset-safe   |
| Immutable payload generation          | ✅      | Thread-safe                     |

### Partially implemented (TODO)

| Feature                  | Status | Notes               |
|--------------------------|--------|---------------------|
| Clear exception messages | -      | Field-level context |

### Testing roadmap

Tests are partially implemented until I figure out the API design.

| Feature                | Status | Notes             |
|------------------------|--------|-------------------|
| Golden test vectors    | ❌      | Real Pix examples |
| CRC correctness tests  | ❌      | Deterministic     |
| Length edge-case tests | ❌      | Max field sizes   |
| Regression test suite  | ❌      | Prevents breakage |

## Roadmap

Currently, we're working with the 0.01-DEV.
The commits are not standard, there is no CI/CD, etc. Working on core library right now.

| Version | Planned Features                       |
|---------|----------------------------------------|
| v0.1    | Static pix, Builder API, Factory API,  |
| v0.2    | Dynamic Pix, QR SVG                    |
| v0.3    | Pix parsing (decode QR → fields)       |
| v0.4    | CLI tool                               |
| v0.5    | spring-boot-starter                    |
| v0...   | ...                                    |
| v1.0    | Production-ready library and utilities |
