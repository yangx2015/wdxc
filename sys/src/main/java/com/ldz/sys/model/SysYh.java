package com.ldz.sys.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "SYS_CLK_PTYH")
public class SysYh implements Serializable {
    @Id
    @Column(name = "YHID")
    @GeneratedValue(generator = "JDBC")
    private String yhid;

    @Column(name = "ZH")
    private String zh;

    @Column(name = "MM")
    private String mm;

    @Column(name = "SJH")
    private String sjh;

    @Column(name = "CJR")
    private String cjr;

    @Column(name = "CJSJ")
    private Date cjsj;

    @Column(name = "XGR")
    private String xgr;

    @Column(name = "XGSJ")
    private Date xgsj;

    @Column(name = "ZT")
    private String zt;

    @Column(name = "JGDM")
    private String jgdm;

    @Column(name = "XM")
    private String xm;

    @Column(name = "LX")
    private String lx;

    @Column(name = "XB")
    private String xb;

    @Column(name = "ZJHM")
    private String zjhm;

    @Column(name = "MMYXQ")
    private Date mmyxq;

    @Column(name = "ZW")
    private String zw;

    private static final long serialVersionUID = 1L;

    /**
     * @return YHID
     */
    public String getYhid() {
        return yhid;
    }

    /**
     * @param yhid
     */
    public void setYhid(String yhid) {
        this.yhid = yhid;
    }

    /**
     * @return ZH
     */
    public String getZh() {
        return zh;
    }

    /**
     * @param zh
     */
    public void setZh(String zh) {
        this.zh = zh;
    }

    /**
     * @return MM
     */
    public String getMm() {
        return mm;
    }

    /**
     * @param mm
     */
    public void setMm(String mm) {
        this.mm = mm;
    }

    /**
     * @return SJH
     */
    public String getSjh() {
        return sjh;
    }

    /**
     * @param sjh
     */
    public void setSjh(String sjh) {
        this.sjh = sjh;
    }

    /**
     * @return CJR
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * @param cjr
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    /**
     * @return CJSJ
     */
    public Date getCjsj() {
        return cjsj;
    }

    /**
     * @param cjsj
     */
    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * @return XGR
     */
    public String getXgr() {
        return xgr;
    }

    /**
     * @param xgr
     */
    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    /**
     * @return XGSJ
     */
    public Date getXgsj() {
        return xgsj;
    }

    /**
     * @param xgsj
     */
    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    /**
     * @return ZT
     */
    public String getZt() {
        return zt;
    }

    /**
     * @param zt
     */
    public void setZt(String zt) {
        this.zt = zt;
    }

    /**
     * @return JGDM
     */
    public String getJgdm() {
        return jgdm;
    }

    /**
     * @param jgdm
     */
    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    /**
     * @return XM
     */
    public String getXm() {
        return xm;
    }

    /**
     * @param xm
     */
    public void setXm(String xm) {
        this.xm = xm;
    }

    /**
     * @return LX
     */
    public String getLx() {
        return lx;
    }

    /**
     * @param lx
     */
    public void setLx(String lx) {
        this.lx = lx;
    }

    /**
     * @return XB
     */
    public String getXb() {
        return xb;
    }

    /**
     * @param xb
     */
    public void setXb(String xb) {
        this.xb = xb;
    }

    /**
     * @return ZJHM
     */
    public String getZjhm() {
        return zjhm;
    }

    /**
     * @param zjhm
     */
    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    /**
     * @return MMYXQ
     */
    public Date getMmyxq() {
        return mmyxq;
    }

    /**
     * @param mmyxq
     */
    public void setMmyxq(Date mmyxq) {
        this.mmyxq = mmyxq;
    }

    /**
     * @return ZW
     */
    public String getZw() {
        return zw;
    }

    /**
     * @param zw
     */
    public void setZw(String zw) {
        this.zw = zw;
    }

    public enum InnerColumn {
        yhid("YHID"),
        zh("ZH"),
        mm("MM"),
        sjh("SJH"),
        cjr("CJR"),
        cjsj("CJSJ"),
        xgr("XGR"),
        xgsj("XGSJ"),
        zt("ZT"),
        jgdm("JGDM"),
        xm("XM"),
        lx("LX"),
        xb("XB"),
        zjhm("ZJHM"),
        mmyxq("MMYXQ"),
        zw("ZW");

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