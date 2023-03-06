package com.sunlight.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunShellUtils {
    public static String run(String shPath) throws Exception {
        Process ps = Runtime.getRuntime().exec(shPath);
        ps.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
