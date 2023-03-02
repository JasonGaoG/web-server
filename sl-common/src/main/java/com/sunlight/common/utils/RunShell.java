package com.sunlight.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunShell {
    public static String run(String shPath){
        try {
            Process ps = Runtime.getRuntime().exec(shPath);
            ps.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
