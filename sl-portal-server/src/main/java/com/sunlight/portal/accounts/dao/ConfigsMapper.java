package com.sunlight.portal.accounts.dao;

import com.sunlight.portal.accounts.model.Configs;

public interface ConfigsMapper extends BaseMapper<Configs> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configs
     *
     * @mbg.generated Fri Jul 15 17:49:05 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configs
     *
     * @mbg.generated Fri Jul 15 17:49:05 CST 2022
     */
    int insert(Configs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configs
     *
     * @mbg.generated Fri Jul 15 17:49:05 CST 2022
     */
    int insertSelective(Configs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configs
     *
     * @mbg.generated Fri Jul 15 17:49:05 CST 2022
     */
    Configs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configs
     *
     * @mbg.generated Fri Jul 15 17:49:05 CST 2022
     */
    int updateByPrimaryKeySelective(Configs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table configs
     *
     * @mbg.generated Fri Jul 15 17:49:05 CST 2022
     */
    int updateByPrimaryKey(Configs record);
}