package com.snmp_test;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpTest {

    public static void main(String[] args) {
        try {
            // SNMP 통신 설정
            Address targetAddress = GenericAddress.parse("udp:127.0.0.1/161");
            TransportMapping<?> transport = new DefaultUdpTransportMapping();
            transport.listen();

            // 커뮤니티 타겟 설정
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
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
                System.out.println("Received response from: " + response.getPeerAddress());
                PDU responsePDU = response.getResponse();
                int errorStatus = responsePDU.getErrorStatus();
                if (errorStatus == PDU.noError) {
                    System.out.println("SNMP GET Response: " + responsePDU.getVariableBindings());
                } else {
                    System.out.println("Error: " + responsePDU.getErrorStatusText());
                }
            } else {
                System.out.println("Error: No response from SNMP agent.");
            }

            snmp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
