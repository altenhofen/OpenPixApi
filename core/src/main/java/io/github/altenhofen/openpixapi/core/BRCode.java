package io.github.altenhofen.openpixapi.core;

import io.github.altenhofen.openpixapi.core.field.CompositeEMVField;
import io.github.altenhofen.openpixapi.core.field.EMVCRC16;
import io.github.altenhofen.openpixapi.core.field.EMVField;
import io.github.altenhofen.openpixapi.core.formatter.*;

import java.util.List;

public class BRCode {
//
//
//    // fields for additional data field template
//    EMVField<String> referenceLabel = new EMVField<String>(
//            "Reference Label",
//            "05",
//            "***",
//            new StringFormatter(3, CharsetPolicy.ALPHANUMERIC, PaddingPolicy.NONE)
//    );
//
//    CompositeEMVField additionalDataFieldTemplate = new CompositeEMVField(
//            "Additional Data Field Template",
//            "62",
//            List.of(referenceLabel)
//    );
//
//    String payloadWithoutCRC =
//            payloadFormatIndicator.serialize()
//                    + pointOfInitiationMethod.serialize()
//                    + merchantAccountInformation.serialize()
//                    + merchantCategoryCode.serialize()
//                    + transactionCurrency.serialize()
//                    + transactionAmount.serialize()
//                    + countryCode.serialize()
//                    + merchantName.serialize()
//                    + merchantCity.serialize()
//                    + additionalDataFieldTemplate.serialize();
//
//    String payloadToSign = payloadWithoutCRC + "6304";
//
//    String crc = EMVCRC16.calculate(payloadToSign);
//    String finalPayload = payloadToSign + crc;
}
