package com.sunlight.blc.binance.model;

import lombok.Data;

@Data
public class SnapshotVos {
    private String type;
    private String updateTime;
    private BiananceAccount data;
}
