package com.sunlight.portal.accounts.dao;

import com.sunlight.portal.accounts.model.Menu;

public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_menu
     *
     * @mbg.generated Wed Mar 08 14:18:53 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_menu
     *
     * @mbg.generated Wed Mar 08 14:18:53 CST 2023
     */
    int insert(Menu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_menu
     *
     * @mbg.generated Wed Mar 08 14:18:53 CST 2023
     */
    int insertSelective(Menu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_menu
     *
     * @mbg.generated Wed Mar 08 14:18:53 CST 2023
     */
    Menu selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_menu
     *
     * @mbg.generated Wed Mar 08 14:18:53 CST 2023
     */
    int updateByPrimaryKeySelective(Menu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_menu
     *
     * @mbg.generated Wed Mar 08 14:18:53 CST 2023
     */
    int updateByPrimaryKey(Menu record);
}