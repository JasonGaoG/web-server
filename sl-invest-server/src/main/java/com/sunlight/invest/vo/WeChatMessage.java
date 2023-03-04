package com.sunlight.invest.vo;

import lombok.Data;

@Data
public class WeChatMessage {

    private String msgtype;// text
    private Text text;
    private MarkDown markdown;
    public WeChatMessage(){}

    public WeChatMessage(String text) {
        Text tt = new Text();
        tt.setContent(text);
        this.setText(tt);
        this.setMsgtype("text");
    }

    public WeChatMessage(MarkDown md) {
        this.setMarkdown(md);
        this.setMsgtype("markdown");
    }
}
