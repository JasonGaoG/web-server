package com.sunlight.blc.binance.model;

import lombok.Data;

import java.util.List;

@Data
public class BiananceAccount {
    private String totalAssetOfBtc;
    private List<BinanceBalance> balances;
}
