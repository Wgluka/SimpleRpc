package com.wgluka.rpc.common.utils;

import com.wgluka.rpc.common.annotation.RpcClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

@RpcClient
public class ScannerTest {
    @Test
    public void testScanClass() throws IOException {
        List<Class<?>> resultList = Scanner.getClassAnnotatedBy("com.wgluka", RpcClient.class);
        boolean isMatch = resultList.stream()
                .anyMatch(clazz -> clazz.getName().equals("com.wgluka.rpc.common.utils.ScannerTest"));
        Assert.assertTrue(isMatch);
    }
}
