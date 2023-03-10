package com.sunlight.invest.dao;

import com.sunlight.invest.model.PolicySelHighLowNotify;

public interface PolicySelHighLowNotifyMapper extends BaseMapper<PolicySelHighLowNotify> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_sel_high_low_notify
     *
     * @mbg.generated Wed Aug 10 16:46:28 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_sel_high_low_notify
     *
     * @mbg.generated Wed Aug 10 16:46:28 CST 2022
     */
    int insert(PolicySelHighLowNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_sel_high_low_notify
     *
     * @mbg.generated Wed Aug 10 16:46:28 CST 2022
     */
    int insertSelective(PolicySelHighLowNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_sel_high_low_notify
     *
     * @mbg.generated Wed Aug 10 16:46:28 CST 2022
     */
    PolicySelHighLowNotify selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_sel_high_low_notify
     *
     * @mbg.generated Wed Aug 10 16:46:28 CST 2022
     */
    int updateByPrimaryKeySelective(PolicySelHighLowNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_sel_high_low_notify
     *
     * @mbg.generated Wed Aug 10 16:46:28 CST 2022
     */
    int updateByPrimaryKey(PolicySelHighLowNotify record);
}