package com.sunlight.blc.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table blc_daily_profit
 *
 * @mbg.generated do_not_delete_during_merge Sat May 11 00:11:41 CST 2019
 */
public class DailyProfit extends BaseObject {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_daily_profit.id
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_daily_profit.delstatus
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    private Integer delstatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_daily_profit.account_xvg_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    private Double accountXvgBtc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_daily_profit.account_dcr_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    private Double accountDcrBtc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_daily_profit.total_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    private Double totalBtc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_daily_profit.btc_price
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    private Double btcPrice;

    /**
     * Database Column Remarks:
     *   日期
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_daily_profit.day
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    private String day;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_daily_profit.remarks
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    private String remarks;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_daily_profit.id
     *
     * @return the value of blc_daily_profit.id
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_daily_profit.id
     *
     * @param id the value for blc_daily_profit.id
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_daily_profit.delstatus
     *
     * @return the value of blc_daily_profit.delstatus
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public Integer getDelstatus() {
        return delstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_daily_profit.delstatus
     *
     * @param delstatus the value for blc_daily_profit.delstatus
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_daily_profit.account_xvg_btc
     *
     * @return the value of blc_daily_profit.account_xvg_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public Double getAccountXvgBtc() {
        return accountXvgBtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_daily_profit.account_xvg_btc
     *
     * @param accountXvgBtc the value for blc_daily_profit.account_xvg_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public void setAccountXvgBtc(Double accountXvgBtc) {
        this.accountXvgBtc = accountXvgBtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_daily_profit.account_dcr_btc
     *
     * @return the value of blc_daily_profit.account_dcr_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public Double getAccountDcrBtc() {
        return accountDcrBtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_daily_profit.account_dcr_btc
     *
     * @param accountDcrBtc the value for blc_daily_profit.account_dcr_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public void setAccountDcrBtc(Double accountDcrBtc) {
        this.accountDcrBtc = accountDcrBtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_daily_profit.total_btc
     *
     * @return the value of blc_daily_profit.total_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public Double getTotalBtc() {
        return totalBtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_daily_profit.total_btc
     *
     * @param totalBtc the value for blc_daily_profit.total_btc
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public void setTotalBtc(Double totalBtc) {
        this.totalBtc = totalBtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_daily_profit.btc_price
     *
     * @return the value of blc_daily_profit.btc_price
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public Double getBtcPrice() {
        return btcPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_daily_profit.btc_price
     *
     * @param btcPrice the value for blc_daily_profit.btc_price
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public void setBtcPrice(Double btcPrice) {
        this.btcPrice = btcPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_daily_profit.day
     *
     * @return the value of blc_daily_profit.day
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public String getDay() {
        return day;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_daily_profit.day
     *
     * @param day the value for blc_daily_profit.day
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_daily_profit.remarks
     *
     * @return the value of blc_daily_profit.remarks
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_daily_profit.remarks
     *
     * @param remarks the value for blc_daily_profit.remarks
     *
     * @mbg.generated Sat May 11 00:11:41 CST 2019
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}