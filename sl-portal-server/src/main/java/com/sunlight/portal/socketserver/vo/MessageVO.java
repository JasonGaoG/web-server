package com.sunlight.portal.socketserver.vo;

import lombok.Data;

/**
 * @author Administrator
 * @package com.components.socketserver.vo
 * @description socket message 对象
 * @date 2019/4/3
 */
@Data
public class MessageVO {
    private String messageType;
    private Object data;
}
