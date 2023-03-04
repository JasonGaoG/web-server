package com.sunlight.invest.dao;

import com.sunlight.invest.model.PolicyLimit;

public interface PolicyLimitMapper extends BaseMapper<PolicyLimit> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_limit
     *
     * @mbg.generated Thu Aug 04 16:21:35 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_limit
     *
     * @mbg.generated Thu Aug 04 16:21:35 CST 2022
     */
    int insert(PolicyLimit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_limit
     *
     * @mbg.generated Thu Aug 04 16:21:35 CST 2022
     */
    int insertSelective(PolicyLimit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_limit
     *
     * @mbg.generated Thu Aug 04 16:21:35 CST 2022
     */
    PolicyLimit selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_limit
     *
     * @mbg.generated Thu Aug 04 16:21:35 CST 2022
     */
    int updateByPrimaryKeySelective(PolicyLimit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_limit
     *
     * @mbg.generated Thu Aug 04 16:21:35 CST 2022
     */
    int updateByPrimaryKey(PolicyLimit record);
}