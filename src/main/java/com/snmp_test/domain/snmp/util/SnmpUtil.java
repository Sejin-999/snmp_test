package com.snmp_test.domain.snmp.util;

import lombok.extern.slf4j.Slf4j;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SnmpUtil {

    public String snmpGet() {
            log.info("snmpGet");
            String return_text;
           try {
            // SNMP 통신 설정
            Address targetAddress = GenericAddress.parse("udp:127.0.0.1/161");
            TransportMapping<?> transport = new DefaultUdpTransportMapping();
            transport.listen();

            // 커뮤니티 타겟 설정
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("mycommunity"));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version2c);

            // PDU 생성
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.1.0"))); // sysDescr OID
            pdu.setType(PDU.GET);

            // SNMP 객체 생성 및 요청 전송
            Snmp snmp = new Snmp(transport);
            ResponseEvent response = snmp.get(pdu, target);

            // 응답 처리
            if (response != null && response.getResponse() != null) {
                log.info("Received response from:  {}" , response.getPeerAddress() );
                PDU responsePDU = response.getResponse();
                int errorStatus = responsePDU.getErrorStatus();
                if (errorStatus == PDU.noError) {
                    log.info("SNMP GET Response: {}" ,responsePDU.getVariableBindings() );
                    return_text = String.valueOf(responsePDU.getVariableBindings());

                } else {
                    log.warn("Error: " + responsePDU.getErrorStatusText());
                    return_text = String.valueOf(responsePDU.getErrorStatusText());
                }
            } else {
                log.warn("Error: No response from SNMP agent.");
                return_text = "no response from snmp agent";
            }

            snmp.close();

        } catch (Exception e) {
            e.printStackTrace();
            return_text = "error";

        }
        return return_text;
    }
}
