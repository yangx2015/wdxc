package com.ldz.biz.module.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@Table(name = "CL_XL")
public class ClXl implements Serializable {
    /**
     * id
     */
    @Id
    private String id;

    /**
     * 线路名称
     */
    @Column(name = "XLMC")
    @NotNull(message="线路名称不为空")
    private String xlmc;

    /**
     * 线路编号
     */
    @Column(name = "XLBH")
    private String xlbh;

    /**
     * 长度
     */
    @Column(name = "CD")
    private Double cd;

    /**
     * 平均时间
     */
    @Column(name = "PJSJ")
    private Double pjsj;

    /**
     * 运行开始时间
     */
    @Column(name = "YXKSSJ")
    @NotNull(message="运行开始时间不为空")
    private String yxkssj;

    /**
     * 运行结束时间
     */
    @Column(name = "YXJSSJ")
    @NotNull(message="运行结束时间不为空")
    private String yxjssj;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private Date cjsj;

    /**
     * 修改人
     */
    @Column(name = "XGR")
    private String xgr;

    /**
     * 修改时间
     */
    @Column(name = "XGSJ")
    private Date xgsj;

    /**
     * 机构代码
     */
    @Column(name = "JGDM")
    private String jgdm;

    /**
     * 机构名称
     */
    @Column(name = "JGMC")
    private String jgmc;

    /**
     * 状态
     */
    @Column(name = "ZT")
    private String zt;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     * 运行方式
     */
    @Column(name = "YXFS")
    @NotNull(message="运行方式不为空")
    private String yxfs;

    /**
     * 类型
     */
    @Column(name = "LX")
    @NotNull(message="类型不为空")
    private String lx;

    @Transient
    private String zdIds;

    @Transient
    private ClZd startStation;
    @Transient
    private ClZd endStation;
    @Transient
    private List<ClZd> stationList;

    private static final long serialVersionUID = 1L;


    public enum InnerColumn {
        id("id"),
        xlmc("XLMC"),
        xlbh("XLBH"),
        cd("CD"),
        pjsj("PJSJ"),
        yxkssj("YXKSSJ"),
        yxjssj("YXJSSJ"),
        cjr("CJR"),
        cjsj("CJSJ"),
        xgr("XGR"),
        xgsj("XGSJ"),
        jgdm("JGDM"),
        jgmc("JGMC"),
        zt("ZT"),
        bz("BZ"),
        yxfs("YXFS"),
        lx("LX");

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