//package com.snmp_test;
//
//import lombok.extern.slf4j.Slf4j;
//import org.snmp4j.*;
//import org.snmp4j.event.ResponseEvent;
//import org.snmp4j.mp.SnmpConstants;
//import org.snmp4j.smi.*;
//import org.snmp4j.transport.DefaultUdpTransportMapping;
//import java.io.IOException;
//
//@Slf4j
//public class SampleTest {
//    private Snmp snmp = null;
//    private String address = null;
//
//    public SampleTest(String getAddress) {
//        address = getAddress;
//    }
//
//    public static void main(String[] args) throws IOException {
//        SampleTest client = new SampleTest("udp:127.0.0.1/161");
//        client.start();
//        String sysDescr = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
//        log.info("---TEST---");
//        log.info(sysDescr);
//    }
//
//    private void start() throws IOException {
//        TransportMapping port = new DefaultUdpTransportMapping();
//        snmp = new Snmp(port);
//        port.listen();
//    }
//
//    public String getAsString(OID oid) throws IOException {
//        ResponseEvent event = get(new OID[] { oid });
//        if (event != null && event.getResponse() != null && event.getResponse().get(0) != null) {
//            return event.getResponse().get(0).getVariable().toString();
//        }
//        throw new RuntimeException("GET time out");
//    }
//
//    private ResponseEvent get(OID[] oids) throws IOException {
//        PDU pdu = new PDU();
//        for (OID oid : oids) {
//            pdu.add(new VariableBinding(oid));
//        }
//        pdu.setType(PDU.GET);
//        ResponseEvent ev = snmp.send(pdu, getTarget(), null);
//        if (ev != null) {
//            return ev;
//        }
//        throw new RuntimeException("GET time out");
//    }
//
//    private Target getTarget() {
//        Address targetAddress = GenericAddress.parse(address);
//        CommunityTarget target = new CommunityTarget();
//        target.setCommunity(new OctetString("public"));
//        target.setAddress(targetAddress);
//        target.setRetries(3);
//        target.setTimeout(1000);
//        target.setVersion(SnmpConstants.version2c);
//        return target;
//    }
//}