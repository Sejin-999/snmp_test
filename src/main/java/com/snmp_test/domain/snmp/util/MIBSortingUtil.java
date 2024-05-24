package com.snmp_test.domain.snmp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class MIBSortingUtil {

    public void findData(){
        String homeDirectory = System.getProperty("user.home");
        String filePath = homeDirectory + "/Desktop/data.txt";
        BufferedReader reader = null;
        int flag = 0;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                stringBuilder.append(line);
                if(line.trim().isEmpty()){
                    //log.info("줄바꿈");
                }else{
//                    log.info("{} : {} ", flag++ , line);
                    String pattern = "^#define\\s+(\\w+)\\s+(.+)$";
                    Pattern r = Pattern.compile(pattern);
                    Matcher m = r.matcher(line);
                    if (m.find()) {
                        String defineName = m.group(1);
                        defineName = defineName.replace("#define", "").trim();
                        String defineValue = m.group(2);
                        defineValue = defineValue.replace("\"", "");
                        log.info("{}  -  {}" , defineName , defineValue);
                    }
                }
                stringBuilder.append(System.lineSeparator());
            }

            String content = stringBuilder.toString();
            //System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }



}
