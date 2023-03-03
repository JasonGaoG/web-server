package com.sunlight.portal.accounts.dao;

import com.sunlight.portal.accounts.model.User;

public interface UserMapper extends BaseMapper<User> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_user
     *
     * @mbg.generated Fri Mar 03 13:19:52 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_user
     *
     * @mbg.generated Fri Mar 03 13:19:52 CST 2023
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_user
     *
     * @mbg.generated Fri Mar 03 13:19:52 CST 2023
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_user
     *
     * @mbg.generated Fri Mar 03 13:19:52 CST 2023
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_user
     *
     * @mbg.generated Fri Mar 03 13:19:52 CST 2023
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sl_user
     *
     * @mbg.generated Fri Mar 03 13:19:52 CST 2023
     */
    int updateByPrimaryKey(User record);
}