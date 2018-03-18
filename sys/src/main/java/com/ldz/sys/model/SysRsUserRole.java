package com.ldz.sys.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sys_rs_user_role")
public class SysRsUserRole implements Serializable {
    /**
     * 用户角色关系ID
     */
    @Id
    @Column(name = "rs_user_role_id")
    @GeneratedValue(generator = "JDBC")
    private Long rsUserRoleId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户角色关系ID
     *
     * @return rs_user_role_id - 用户角色关系ID
     */
    public Long getRsUserRoleId() {
        return rsUserRoleId;
    }

    /**
     * 设置用户角色关系ID
     *
     * @param rsUserRoleId 用户角色关系ID
     */
    public void setRsUserRoleId(Long rsUserRoleId) {
        this.rsUserRoleId = rsUserRoleId;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public enum InnerColumn {
        rsUserRoleId("rs_user_role_id"),
        userId("user_id"),
        roleId("role_id");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }
}