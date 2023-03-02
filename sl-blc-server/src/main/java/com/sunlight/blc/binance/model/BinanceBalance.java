package com.sunlight.blc.binance.model;

import lombok.Data;

@Data
public class BinanceBalance {
    private String asset;
    private String free;
    private String locked;
}
