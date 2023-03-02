package com.sunlight.portal.accounts.vo;

import com.sunlight.portal.accounts.model.Configs;
import lombok.Data;

@Data
public class ConfigsVO {
    private Integer id;
    private String itemKey;
    private String itemValue;
    private String itemDesc;

    public ConfigsVO(){}
    public ConfigsVO(Configs config){
        this.itemDesc = config.getItemDesc();
        this.id = config.getId();
        this.itemKey = config.getItemKey();
        this.itemValue = config.getItemValue();
    }

}
