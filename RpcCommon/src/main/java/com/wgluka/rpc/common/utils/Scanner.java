package com.wgluka.rpc.common.utils;

import com.wgluka.rpc.common.annotation.RpcService;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@RpcService
public class Scanner {
    public static List<Class<?>> getClassAnnotatedBy(String basePackage, Class<? extends Annotation> annotation) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String fileSeparator = File.separator;
        String basePath = basePackage.replace(".", fileSeparator);
        Enumeration<URL> enumeration = classLoader.getResources(basePath);
        List<Class<?>> result = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            String file = url.getFile();
            List<Class<?>> classList = travelForAllClass(file, basePackage);
            List<Class<?>> filteredClass = classList.stream().filter(clazz -> clazz.isAnnotationPresent(annotation))
                    .collect(Collectors.toList());
            result.addAll(filteredClass);
        }
        return result;
    }

    private static List<Class<?>> travelForAllClass(String filePath, String basePackage) {
        File file = new File(filePath);
        File[] subFiles = file.listFiles(subFile -> subFile.isDirectory() || subFile.getName().endsWith(".class"));
        if (subFiles == null || subFiles.length == 0) {
            return Collections.emptyList();
        }

        List<Class<?>> results = new ArrayList<>();
        for (File subFile : subFiles) {
            String subFileName = subFile.getName();
            if (subFile.isFile()) {
                String fileName = subFileName.substring(0, subFileName.indexOf(".class"));
                String fullClassName = String.join(".", basePackage, fileName);
                try {
                    Class clazz = Class.forName(fullClassName, false, Thread.currentThread().getContextClassLoader());
                    results.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                String subFilePath = String.join(File.separator, filePath, subFileName);
                String subPackage = String.join(".", basePackage, subFileName);
                List<Class<?>> traveledClass = travelForAllClass(subFilePath, subPackage);
                results.addAll(traveledClass);
            }
        }
        return results;
    }
}
