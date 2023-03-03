package com.sunlight.blc.model;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table blc_exchange
 *
 * @mbg.generated do_not_delete_during_merge Tue May 07 14:01:35 CST 2019
 */
public class Exchange extends BaseObject {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.id
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.delstatus
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private Integer delstatus;

    /**
     * Database Column Remarks:
     *   账户 -- 电话号码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.account
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.order_id
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private String orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.create_time
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.finished_time
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private Date finishedTime;

    /**
     * Database Column Remarks:
     *   交易数量
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.amount
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private Double amount;

    /**
     * Database Column Remarks:
     *   手续费
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.fees
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private String fees;

    /**
     * Database Column Remarks:
     *   btc 数量
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.filled_btc_amount
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private String filledBtcAmount;

    /**
     * Database Column Remarks:
     *   备注
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.remarks
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private String remarks;

    /**
     * Database Column Remarks:
     *   xvgbtc dcrbtc 等
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blc_exchange.symbol
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    private String symbol;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.id
     *
     * @return the value of blc_exchange.id
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.id
     *
     * @param id the value for blc_exchange.id
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.delstatus
     *
     * @return the value of blc_exchange.delstatus
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public Integer getDelstatus() {
        return delstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.delstatus
     *
     * @param delstatus the value for blc_exchange.delstatus
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.account
     *
     * @return the value of blc_exchange.account
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.account
     *
     * @param account the value for blc_exchange.account
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.order_id
     *
     * @return the value of blc_exchange.order_id
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.order_id
     *
     * @param orderId the value for blc_exchange.order_id
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.create_time
     *
     * @return the value of blc_exchange.create_time
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.create_time
     *
     * @param createTime the value for blc_exchange.create_time
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.finished_time
     *
     * @return the value of blc_exchange.finished_time
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public Date getFinishedTime() {
        return finishedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.finished_time
     *
     * @param finishedTime the value for blc_exchange.finished_time
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.amount
     *
     * @return the value of blc_exchange.amount
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.amount
     *
     * @param amount the value for blc_exchange.amount
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.fees
     *
     * @return the value of blc_exchange.fees
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public String getFees() {
        return fees;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.fees
     *
     * @param fees the value for blc_exchange.fees
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setFees(String fees) {
        this.fees = fees == null ? null : fees.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.filled_btc_amount
     *
     * @return the value of blc_exchange.filled_btc_amount
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public String getFilledBtcAmount() {
        return filledBtcAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.filled_btc_amount
     *
     * @param filledBtcAmount the value for blc_exchange.filled_btc_amount
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setFilledBtcAmount(String filledBtcAmount) {
        this.filledBtcAmount = filledBtcAmount == null ? null : filledBtcAmount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.remarks
     *
     * @return the value of blc_exchange.remarks
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.remarks
     *
     * @param remarks the value for blc_exchange.remarks
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blc_exchange.symbol
     *
     * @return the value of blc_exchange.symbol
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blc_exchange.symbol
     *
     * @param symbol the value for blc_exchange.symbol
     *
     * @mbg.generated Tue May 07 14:01:35 CST 2019
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }
}