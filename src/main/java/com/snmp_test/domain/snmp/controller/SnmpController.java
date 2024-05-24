package com.snmp_test.domain.snmp.controller;

import com.snmp_test.domain.snmp.util.SnmpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/snmp")
@Slf4j
public class SnmpController {
        private final SnmpUtil snmpUtil;

        @GetMapping("/test")
        public ResponseEntity test(){
                log.info("test - snmp");
                return ResponseEntity.ok("test success");
        }

        @GetMapping("/snmpTest")
        public ResponseEntity snmpTest(){
                log.info("test - snmp");
                String return_text = snmpUtil.snmpGet();

                if(return_text.equals("error")){
                        return ResponseEntity.status(400).body("error");
                }else{
                        return ResponseEntity.ok("test success\n"+return_text);
                }

        }

}
