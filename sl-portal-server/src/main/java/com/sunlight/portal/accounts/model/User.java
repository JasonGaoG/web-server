package com.sunlight.portal.accounts.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table sl_user
 *
 * @mbg.generated do_not_delete_during_merge Mon Mar 06 15:38:58 CST 2023
 */
public class User extends BaseObject {
    /**
     * Database Column Remarks:
     *   id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sl_user.id
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   用户名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sl_user.user_name
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    private String userName;

    /**
     * Database Column Remarks:
     *   密码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sl_user.password
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    private String password;

    /**
     * Database Column Remarks:
     *   角色
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sl_user.user_role_code
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    private String userRoleCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sl_user.delstatus
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    private Integer delstatus;

    /**
     * Database Column Remarks:
     *   所属公司id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sl_user.company_id
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    private Integer companyId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sl_user.id
     *
     * @return the value of sl_user.id
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sl_user.id
     *
     * @param id the value for sl_user.id
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sl_user.user_name
     *
     * @return the value of sl_user.user_name
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sl_user.user_name
     *
     * @param userName the value for sl_user.user_name
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sl_user.password
     *
     * @return the value of sl_user.password
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sl_user.password
     *
     * @param password the value for sl_user.password
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sl_user.user_role_code
     *
     * @return the value of sl_user.user_role_code
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public String getUserRoleCode() {
        return userRoleCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sl_user.user_role_code
     *
     * @param userRoleCode the value for sl_user.user_role_code
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public void setUserRoleCode(String userRoleCode) {
        this.userRoleCode = userRoleCode == null ? null : userRoleCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sl_user.delstatus
     *
     * @return the value of sl_user.delstatus
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public Integer getDelstatus() {
        return delstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sl_user.delstatus
     *
     * @param delstatus the value for sl_user.delstatus
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sl_user.company_id
     *
     * @return the value of sl_user.company_id
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sl_user.company_id
     *
     * @param companyId the value for sl_user.company_id
     *
     * @mbg.generated Mon Mar 06 15:38:58 CST 2023
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}