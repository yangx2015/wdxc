package com.ldz.biz.module.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "CL_WF")
public class ClWf implements Serializable {
    /**
     * PKID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 违法类型 [ZDCLK0052] （10 车辆违法、20驾驶员违法）
     */
    @Column(name = "WF_TYPE")
    private String wfType;

    /**
     * 违法车辆ID 关联cl_cl表
     */
    @Column(name = "WF_CL")
    private String wfCl;

    /**
     * 违法车牌号
     */
    @Column(name = "WF_CPH")
    private String wfCph;

    /**
     * 违法驾证号
     */
    @Column(name = "WF_JSZ")
    private String wfJsz;

    /**
     * 违法人姓名
     */
    @Column(name = "WF_NAME")
    private String wfName;
    
    /**
     * 违法行为
     */
    @Column(name = "WFXW")
    private String wfxw;

    /**
     * 违法时间
     */
    @Column(name = "WF_DATE")
    private String wfDate;

    /**
     * 违法地点
     */
    @Column(name = "WF_SITE")
    private String wfSite;

    /**
     * 违法记分
     */
    @Column(name = "WF_SCORING")
    private String wfScoring;

    /**
     * 违法金额
     */
    @Column(name = "WF_MONEY")
    private String wfMoney;

    /**
     * 违法状态  ZDCLK0053 (0未处理 1已处理)
     */
    @Column(name = "WF_STATUS")
    private Short wfStatus;

    private static final long serialVersionUID = 1L;

    public String getWfxw() {
		return wfxw;
	}

	public void setWfxw(String wfxw) {
		this.wfxw = wfxw;
	}

	/**
     * 获取PKID
     *
     * @return ID - PKID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置PKID
     *
     * @param id PKID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取违法类型 [ZDCLK0052] （10 车辆违法、20驾驶员违法）
     *
     * @return WF_TYPE - 违法类型 [ZDCLK0052] （10 车辆违法、20驾驶员违法）
     */
    public String getWfType() {
        return wfType;
    }

    /**
     * 设置违法类型 [ZDCLK0052] （10 车辆违法、20驾驶员违法）
     *
     * @param wfType 违法类型 [ZDCLK0052] （10 车辆违法、20驾驶员违法）
     */
    public void setWfType(String wfType) {
        this.wfType = wfType;
    }

    /**
     * 获取违法车辆ID 关联cl_cl表
     *
     * @return WF_CL - 违法车辆ID 关联cl_cl表
     */
    public String getWfCl() {
        return wfCl;
    }

    /**
     * 设置违法车辆ID 关联cl_cl表
     *
     * @param wfCl 违法车辆ID 关联cl_cl表
     */
    public void setWfCl(String wfCl) {
        this.wfCl = wfCl;
    }

    /**
     * 获取违法车牌号
     *
     * @return WF_CPH - 违法车牌号
     */
    public String getWfCph() {
        return wfCph;
    }

    /**
     * 设置违法车牌号
     *
     * @param wfCph 违法车牌号
     */
    public void setWfCph(String wfCph) {
        this.wfCph = wfCph;
    }

    /**
     * 获取违法驾证号
     *
     * @return WF_JSZ - 违法驾证号
     */
    public String getWfJsz() {
        return wfJsz;
    }

    /**
     * 设置违法驾证号
     *
     * @param wfJsz 违法驾证号
     */
    public void setWfJsz(String wfJsz) {
        this.wfJsz = wfJsz;
    }

    /**
     * 获取违法人姓名
     *
     * @return WF_NAME - 违法人姓名
     */
    public String getWfName() {
        return wfName;
    }

    /**
     * 设置违法人姓名
     *
     * @param wfName 违法人姓名
     */
    public void setWfName(String wfName) {
        this.wfName = wfName;
    }

    /**
     * 获取违法时间
     *
     * @return WF_DATE - 违法时间
     */
    public String getWfDate() {
        return wfDate;
    }

    /**
     * 设置违法时间
     *
     * @param wfDate 违法时间
     */
    public void setWfDate(String wfDate) {
        this.wfDate = wfDate;
    }

    /**
     * 获取违法地点
     *
     * @return WF_SITE - 违法地点
     */
    public String getWfSite() {
        return wfSite;
    }

    /**
     * 设置违法地点
     *
     * @param wfSite 违法地点
     */
    public void setWfSite(String wfSite) {
        this.wfSite = wfSite;
    }

    /**
     * 获取违法记分
     *
     * @return WF_SCORING - 违法记分
     */
    public String getWfScoring() {
        return wfScoring;
    }

    /**
     * 设置违法记分
     *
     * @param wfScoring 违法记分
     */
    public void setWfScoring(String wfScoring) {
        this.wfScoring = wfScoring;
    }

    /**
     * 获取违法金额
     *
     * @return WF_MONEY - 违法金额
     */
    public String getWfMoney() {
        return wfMoney;
    }

    /**
     * 设置违法金额
     *
     * @param wfMoney 违法金额
     */
    public void setWfMoney(String wfMoney) {
        this.wfMoney = wfMoney;
    }

    /**
     * 获取违法状态  ZDCLK0053 (0未处理 1已处理)
     *
     * @return WF_STATUS - 违法状态  ZDCLK0053 (0未处理 1已处理)
     */
    public Short getWfStatus() {
        return wfStatus;
    }

    /**
     * 设置违法状态  ZDCLK0053 (0未处理 1已处理)
     *
     * @param wfStatus 违法状态  ZDCLK0053 (0未处理 1已处理)
     */
    public void setWfStatus(Short wfStatus) {
        this.wfStatus = wfStatus;
    }

    public enum InnerColumn {
        id("ID"),
        wfType("WF_TYPE"),
        wfCl("WF_CL"),
        wfCph("WF_CPH"),
        wfJsz("WF_JSZ"),
        wfName("WF_NAME"),
        wfDate("WF_DATE"),
        wfSite("WF_SITE"),
        wfScoring("WF_SCORING"),
        wfMoney("WF_MONEY"),
        wfStatus("WF_STATUS");

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