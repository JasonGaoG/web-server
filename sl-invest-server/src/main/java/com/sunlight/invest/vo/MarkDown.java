package com.sunlight.invest.vo;

import lombok.Data;

import java.util.Calendar;

@Data
public class MarkDown {
    private String content;
    Calendar calendar = Calendar.getInstance();

    public MarkDown(){}
    public MarkDown(String content) {
        this.content = content;
    }

    public MarkDown (String exception, String address, String remoteIp, int secret){
        String content = "<font color=\"warning\">"+exception+": </font>，详情：\n"+
                ">address:<font color=\"comment\">"+ address +"</font>;\n"+
                ">remoteIp:<font color=\"comment\">"+ remoteIp +"</font>;\n"+
                ">secret:<font color=\"comment\">"+ secret +"</font>\n"+
                "date:<font color=\"comment\">"+ calendar.get(Calendar.DAY_OF_MONTH) +"</font>";
        this.content = content;
    }
}
