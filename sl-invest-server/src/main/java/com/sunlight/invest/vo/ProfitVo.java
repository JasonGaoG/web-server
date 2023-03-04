package com.sunlight.invest.vo;

import com.sunlight.common.utils.DateUtils;
import com.sunlight.invest.model.Profit;
import lombok.Data;

@Data
public class ProfitVo {

    private Integer id;
    private String date;
    private Integer userId;
    private String stockName;
    private Double profit;
    private String remarks;
    private Integer settled;
    private Integer companyId;

    public ProfitVo(){}

    public ProfitVo(Profit pt){
        this.id = pt.getId();
        this.date = DateUtils.getDateString(pt.getDate(), "yyyy-MM-dd");
        this.userId = pt.getUserId();
        this.stockName = pt.getStockName();
        this.profit = pt.getProfit();
        this.remarks = pt.getRemarks();
        this.settled = pt.getSettled();
        this.companyId = pt.getCompanyId();
    }
}
