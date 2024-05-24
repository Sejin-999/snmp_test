package com.snmp_test.domain.snmp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MIBSortingUtilTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testFindData() {
        // 테스트할 파일 경로
        String filePath = "~/Users/yangsejin/Desktop/data.txt";

        // 테스트할 클래스의 인스턴스 생성
        MIBSortingUtilTest reader = new MIBSortingUtilTest();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        MIBSortingUtil sortingUtil = new MIBSortingUtil();
        sortingUtil.findData();
        String output = outputStream.toString().trim();
        // 콘솔 출력을 원래대로 돌려놓음
        //System.setOut(originalOut);
    }
}