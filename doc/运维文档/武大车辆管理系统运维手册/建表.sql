create table CL_CD
(
  CDBH VARCHAR2(32) not null
    constraint PK_CL_CD
    primary key,
  CDMC VARCHAR2(32),
  DZBH VARCHAR2(32),
  DZXM VARCHAR2(32),
  JGDM VARCHAR2(20),
  JGMC VARCHAR2(32),
  ZT   CHAR(2),
  CJSJ TIMESTAMP(6),
  CJR  VARCHAR2(64),
  XGSJ TIMESTAMP(6),
  XGR  VARCHAR2(64),
  SJHM VARCHAR2(20)
)
comment on table CL_CD is '车队表'
comment on column CL_CD.CDBH is '车队编号'
comment on column CL_CD.CDMC is '车队名称'
comment on column CL_CD.DZBH is '队长编号'
comment on column CL_CD.DZXM is '队长姓名'
comment on column CL_CD.JGDM is '机构代码'
comment on column CL_CD.JGMC is '机构名称'
comment on column CL_CD.ZT is '状态'
comment on column CL_CD.CJSJ is '创建时间'
comment on column CL_CD.CJR is '创建人'
comment on column CL_CD.XGSJ is '修改时间'
comment on column CL_CD.XGR is '修改人'
comment on column CL_CD.SJHM is '手机号码'
create table CL_CL
(
  CL_ID    VARCHAR2(32) not null
    constraint PK_CL_CL
    primary key,
  CPH      VARCHAR2(10),
  JGDM     VARCHAR2(20),
  JGMC     VARCHAR2(32),
  CX       CHAR(2),
  ZKL      NUMBER,
  DL       CHAR(2),
  CJSJ     TIMESTAMP(6),
  CJR      VARCHAR2(64),
  XGSJ     TIMESTAMP(6),
  XGR      VARCHAR2(64),
  SJ_ID    VARCHAR2(32),
  SJXM     VARCHAR2(32),
  ZT       CHAR(2),
  TP       VARCHAR2(255),
  SCS      VARCHAR2(16),
  XH       VARCHAR2(16),
  ZDBH     VARCHAR2(32),
  CCDJRQ   TIMESTAMP(6),
  CDBH     VARCHAR2(32),
  BXGSMC   VARCHAR2(32),
  BXSJ     TIMESTAMP(6),
  NSSJ     TIMESTAMP(6),
  OBD_CODE VARCHAR2(32),
  SDSX     NUMBER default 0
)
comment on table CL_CL is '车辆表'
comment on column CL_CL.CL_ID is '车辆id'
comment on column CL_CL.CPH is '车牌号'
comment on column CL_CL.JGDM is '机构代码'
comment on column CL_CL.JGMC is '机构名称'
comment on column CL_CL.CX is '车型  字典项：ZDCLK0019：车辆类型 10、小车 20、大车 30、校巴'
comment on column CL_CL.ZKL is '载客量'
comment on column CL_CL.DL is '等级'
comment on column CL_CL.CJSJ is '创建时间'
comment on column CL_CL.CJR is '创建人'
comment on column CL_CL.XGSJ is '修改时间'
comment on column CL_CL.XGR is '修改人'
comment on column CL_CL.SJ_ID is '司机id'
comment on column CL_CL.SJXM is '司机姓名'
comment on column CL_CL.ZT is '状态 字典项：ZDCLK0016  00、正常 10、停用'
comment on column CL_CL.TP is '图片'
comment on column CL_CL.SCS is '生产商'
comment on column CL_CL.XH is '型号'
comment on column CL_CL.ZDBH is '终端编号'
comment on column CL_CL.CCDJRQ is '初次登记日期'
comment on column CL_CL.CDBH is '车队编号'
comment on column CL_CL.BXGSMC is '保险公司名称'
comment on column CL_CL.BXSJ is '保险时间'
comment on column CL_CL.NSSJ is '年审时间'
comment on column CL_CL.OBD_CODE is 'OBD编号'
comment on column CL_CL.SDSX is '速度上限'
create table CL_CLYXJL
(
  ID    VARCHAR2(32) not null
    constraint PK_CL_CLYXJL
    primary key,
  CL_ID VARCHAR2(32),
  CPHM  VARCHAR2(32),
  ZDBH  VARCHAR2(32),
  CJSJ  TIMESTAMP(6),
  JD    NUMBER       default NULL,
  WD    NUMBER       default NULL,
  ZDJL  NUMBER,
  ZD_ID VARCHAR2(32),
  ZDMC  VARCHAR2(32),
  XL_ID VARCHAR2(32),
  XLMC  VARCHAR2(32),
  YXFX  CHAR(2),
  JID   NUMBER,
  ZT    VARCHAR2(20) default NULL
)
comment on table CL_CLYXJL is '车辆运行记录表'
comment on column CL_CLYXJL.ID is 'ID'
comment on column CL_CLYXJL.CL_ID is '车辆ID'
comment on column CL_CLYXJL.CPHM is '车牌号码'
comment on column CL_CLYXJL.ZDBH is '站点编号'
comment on column CL_CLYXJL.CJSJ is '创建时间'
comment on column CL_CLYXJL.JD is '经度'
comment on column CL_CLYXJL.WD is '纬度'
comment on column CL_CLYXJL.ZDJL is '站点距离'
comment on column CL_CLYXJL.ZD_ID is '站点ID'
comment on column CL_CLYXJL.ZDMC is '站点名称'
comment on column CL_CLYXJL.XL_ID is '线路ID'
comment on column CL_CLYXJL.XLMC is '线路名称'
comment on column CL_CLYXJL.YXFX is '运行方向'
comment on column CL_CLYXJL.JID is '精度'
comment on column CL_CLYXJL.ZT is '状态(inStation进站，runing正常  off离线)'
create table CL_CSSD
(
  ID   VARCHAR2(32) not null
    constraint PK_CL_CSSD
    primary key,
  CPH  VARCHAR2(64) default NULL,
  SDSX NUMBER,
  CJSJ TIMESTAMP(6),
  CJR  VARCHAR2(64),
  XGSJ TIMESTAMP(6),
  XGR  VARCHAR2(64),
  JGDM VARCHAR2(20),
  JGMC VARCHAR2(32)
)
comment on table CL_CSSD is '车辆超速表'
comment on column CL_CSSD.ID is 'id'
comment on column CL_CSSD.CPH is '车型'
comment on column CL_CSSD.SDSX is '速度上限'
comment on column CL_CSSD.CJSJ is '创建时间'
comment on column CL_CSSD.CJR is '创建人'
comment on column CL_CSSD.XGSJ is '修改时间'
comment on column CL_CSSD.XGR is '修改人'
comment on column CL_CSSD.JGDM is '机构代码'
comment on column CL_CSSD.JGMC is '机构名称'
create table CL_DD
(
  ID     VARCHAR2(32) not null
    constraint PK_CL_DD
    primary key,
  SJQRSJ TIMESTAMP(6),
  HCDZ   VARCHAR2(255),
  MDD    VARCHAR2(255),
  CPH    VARCHAR2(10),
  SJ     VARCHAR2(32),
  SJXM   VARCHAR2(32),
  ZJ     FLOAT,
  SC     NUMBER,
  DJ     FLOAT,
  LC     FLOAT,
  SCF    FLOAT,
  LCF    FLOAT,
  CK     VARCHAR2(16),
  CKZW   CHAR(2),
  CKLXDH VARCHAR2(16),
  ZWS    NUMBER,
  CKLX   CHAR(2),
  YYSJ   TIMESTAMP(6),
  DDZT   CHAR(2),
  FKZT   CHAR(2),
  FKFS   CHAR(2),
  FKSJ   TIMESTAMP(6),
  PJDJ   NUMBER,
  PJNR   VARCHAR2(255),
  CJSJ   TIMESTAMP(6),
  CJR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64),
  JGDM   VARCHAR2(20),
  JGMC   VARCHAR2(32),
  CL_ID  VARCHAR2(32),
  GLF    FLOAT,
  SY     VARCHAR2(255),
  CLLX   CHAR(2),
  WF     CHAR(2),
  DZQRSJ TIMESTAMP(6),
  SHSJ   TIMESTAMP(6),
  DZXM   VARCHAR2(32),
  FKBZ   VARCHAR2(255),
  SJ_SX  CHAR(2),
  DZBH   VARCHAR2(32),
  CDBH   VARCHAR2(32),
  ZDBH   VARCHAR2(32),
  BHYY   VARCHAR2(255),
  KTCODE CHAR(6),
  CK_CJL VARCHAR2(32),
  GQF    FLOAT default NULL,
  JBSC   NUMBER
)
comment on table CL_DD is '平台订单表'
comment on column CL_DD.ID is 'ID'
comment on column CL_DD.SJQRSJ is '司机确认时间'
comment on column CL_DD.HCDZ is '候车地址'
comment on column CL_DD.MDD is '目的地'
comment on column CL_DD.CPH is '车牌号'
comment on column CL_DD.SJ is '司机'
comment on column CL_DD.SJXM is '司机姓名'
comment on column CL_DD.ZJ is '总价'
comment on column CL_DD.SC is '时长'
comment on column CL_DD.DJ is '单价'
comment on column CL_DD.LC is '里程'
comment on column CL_DD.SCF is '时长费'
comment on column CL_DD.LCF is '里程费'
comment on column CL_DD.CK is '乘客姓名'
comment on column CL_DD.CKZW is '乘客职务'
comment on column CL_DD.CKLXDH is '乘客联系电话'
comment on column CL_DD.ZWS is '座位数'
comment on column CL_DD.CKLX is '乘客类型'
comment on column CL_DD.YYSJ is '预约时间'
comment on column CL_DD.DDZT is '订单状态'
comment on column CL_DD.FKZT is '付款状态'
comment on column CL_DD.FKFS is '付款方式 费用来源 [ZDCLK0043]  10行政费用  20课题费用  30自费'
comment on column CL_DD.FKSJ is '付款时间'
comment on column CL_DD.PJDJ is '评价等级'
comment on column CL_DD.PJNR is '评价内容'
comment on column CL_DD.CJSJ is '创建时间'
comment on column CL_DD.CJR is '创建人'
comment on column CL_DD.XGSJ is '修改时间'
comment on column CL_DD.XGR is '修改人'
comment on column CL_DD.JGDM is '机构代码'
comment on column CL_DD.JGMC is '机构名称'
comment on column CL_DD.CL_ID is '车辆id'
comment on column CL_DD.GLF is '过路费'
comment on column CL_DD.SY is '事由'
comment on column CL_DD.CLLX is '车辆类型 字典项：ZDCLK0019：车辆类型 10、小车 20、大车 30、校巴'
comment on column CL_DD.WF is '往返		订单往返 [ZDCLK0042] 10单程 20往返'
comment on column CL_DD.DZQRSJ is '队长确认时间'
comment on column CL_DD.SHSJ is '审核时间'
comment on column CL_DD.DZXM is '队长姓名'
comment on column CL_DD.FKBZ is '付款备注'
comment on column CL_DD.SJ_SX is '属性：字典项(ZDCLK0039) 10:内部司机，关联CL_JSY表 11：外部车，关联临时车表'
comment on column CL_DD.DZBH is '队长编号'
comment on column CL_DD.CDBH is '车队编号'
comment on column CL_DD.ZDBH is '终端编号'
comment on column CL_DD.BHYY is '驳回原因'
comment on column CL_DD.KTCODE is '课题编号  课题字典 [ZDCLK0045]'
comment on column CL_DD.CK_CJL is '乘客创建人，这个订单是由乘客创建时，这里填写申请的人姓名'
comment on column CL_DD.GQF is '过桥费'
comment on column CL_DD.JBSC is '加班时长'
create table CL_DDRZ
(
  ID    VARCHAR2(32) not null
    constraint PK_CL_DDRZ
    primary key,
  DD_ID VARCHAR2(32),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  BZ    VARCHAR2(255),
  JGDM  VARCHAR2(20),
  JGMC  VARCHAR2(32),
  DDZT  CHAR(2)
)
comment on table CL_DDRZ is '订单日期表(订单流程日志表)'
comment on column CL_DDRZ.ID is 'ID'
comment on column CL_DDRZ.DD_ID is '订单id'
comment on column CL_DDRZ.CJSJ is '创建时间'
comment on column CL_DDRZ.CJR is '创建人'
comment on column CL_DDRZ.BZ is '备注'
comment on column CL_DDRZ.JGDM is '机构代码'
comment on column CL_DDRZ.JGMC is '机构名称'
comment on column CL_DDRZ.DDZT is '订单状态'
create table CL_DZWL
(
  ID     VARCHAR2(32) not null
    constraint PK_CL_DZWL
    primary key,
  WLMC   VARCHAR2(32),
  JGDM   VARCHAR2(20),
  JGMC   VARCHAR2(32),
  MJ     FLOAT default 1000,
  DLXXZB VARCHAR2(4000),
  KSJD   NUMBER(10, 2),
  KSWD   NUMBER(10, 2),
  ZT     CHAR(2),
  CJSJ   TIMESTAMP(6),
  CJR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64)
)
comment on table CL_DZWL is '电子围栏表'
comment on column CL_DZWL.ID is 'ID'
comment on column CL_DZWL.WLMC is '围栏名称'
comment on column CL_DZWL.JGDM is '机构代码'
comment on column CL_DZWL.JGMC is '机构名称'
comment on column CL_DZWL.MJ is '面积'
comment on column CL_DZWL.DLXXZB is '地理信息坐标'
comment on column CL_DZWL.KSJD is '开始经度'
comment on column CL_DZWL.KSWD is '开始纬度'
comment on column CL_DZWL.ZT is '状态'
comment on column CL_DZWL.CJSJ is '创建时间'
comment on column CL_DZWL.CJR is '创建人'
comment on column CL_DZWL.XGSJ is '修改时间'
comment on column CL_DZWL.XGR is '修改人'
create table CL_DZWL_CL
(
  ID    VARCHAR2(32) not null
    constraint PK_DZWL_CL
    primary key,
  WL_ID VARCHAR2(32),
  CL_ID VARCHAR2(32),
  CPH   VARCHAR2(10),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64)
)
comment on table CL_DZWL_CL is '电子围栏车辆表'
comment on column CL_DZWL_CL.ID is 'ID'
comment on column CL_DZWL_CL.WL_ID is '围栏id'
comment on column CL_DZWL_CL.CL_ID is '车辆id'
comment on column CL_DZWL_CL.CPH is '车牌号'
comment on column CL_DZWL_CL.CJSJ is '创建时间'
comment on column CL_DZWL_CL.CJR is '创建人'
create table CL_GPS
(
  ZDBH VARCHAR2(32) not null
    constraint PK_CL_GPS
    primary key,
  LX   CHAR(5),
  JD   NUMBER,
  WD   NUMBER,
  GGJD NUMBER,
  GGWD NUMBER,
  BDJD NUMBER,
  BDWD NUMBER,
  GDJD NUMBER,
  GDWD NUMBER,
  CJSJ TIMESTAMP(6),
  GXSJ TIMESTAMP(6),
  DWJD NUMBER,
  FXJ  NUMBER,
  YXSD VARCHAR2(32)
)
comment on column CL_GPS.ZDBH is '终端编号'
comment on column CL_GPS.LX is '类型'
comment on column CL_GPS.JD is '经度'
comment on column CL_GPS.WD is '纬度'
comment on column CL_GPS.GGJD is '谷歌经度'
comment on column CL_GPS.GGWD is '谷歌纬度'
comment on column CL_GPS.BDJD is '百度经度'
comment on column CL_GPS.BDWD is '百度纬度'
comment on column CL_GPS.GDJD is '高德经度'
comment on column CL_GPS.GDWD is '高德纬度'
comment on column CL_GPS.CJSJ is '创建时间'
comment on column CL_GPS.GXSJ is '更新时间'
comment on column CL_GPS.DWJD is '定位精度'
comment on column CL_GPS.FXJ is '方向角'
comment on column CL_GPS.YXSD is '运行速度'
create table CL_GPS_LS
(
  ID   VARCHAR2(32) not null
    constraint PK_CL_GPS_LS
    primary key,
  ZDBH VARCHAR2(32),
  CJSJ TIMESTAMP(8),
  JD   NUMBER(32, 10),
  WD   NUMBER(32, 10),
  GGJD NUMBER(32, 10),
  GGWD NUMBER(32, 10),
  BDJD NUMBER(32, 10),
  BDWD NUMBER(32, 10),
  GDJD NUMBER(32, 10),
  GDWD NUMBER(32, 10),
  LX   CHAR(22),
  DWJD NUMBER(32),
  FXJ  NUMBER(32, 1),
  YXSD VARCHAR2(32)
)
comment on column CL_GPS_LS.ID is 'ID'
comment on column CL_GPS_LS.ZDBH is '终端编号'
comment on column CL_GPS_LS.CJSJ is '创建时间'
comment on column CL_GPS_LS.JD is '经度'
comment on column CL_GPS_LS.WD is '纬度'
comment on column CL_GPS_LS.GGJD is '谷歌经度'
comment on column CL_GPS_LS.GGWD is '谷歌纬度'
comment on column CL_GPS_LS.BDJD is '百度经度'
comment on column CL_GPS_LS.BDWD is '百度纬度'
comment on column CL_GPS_LS.GDJD is '高德经度'
comment on column CL_GPS_LS.GDWD is '高德纬度'
comment on column CL_GPS_LS.LX is '类型'
comment on column CL_GPS_LS.DWJD is '定位精度'
comment on column CL_GPS_LS.FXJ is '方向角'
comment on column CL_GPS_LS.YXSD is '运行速度'
create table CL_JSY
(
  SFZHM VARCHAR2(18) not null
    constraint PK_CL_JSY
    primary key,
  XM    VARCHAR2(32),
  JGDM  VARCHAR2(20),
  XB    CHAR,
  NL    NUMBER,
  JL    NUMBER,
  ZT    CHAR(2),
  TX    VARCHAR2(255),
  ZJCX  CHAR(2),
  DJ    CHAR(2),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  XGR   VARCHAR2(64),
  CDBH  VARCHAR2(32),
  DABH  VARCHAR2(32),
  CLRQ  TIMESTAMP(6),
  SJH   VARCHAR2(12),
  PWD   VARCHAR2(255),
  XX    VARCHAR2(2),
  JKZK  VARCHAR2(255),
  JZYXQ VARCHAR2(20)
)
comment on column CL_JSY.SFZHM is '身份证号码'
comment on column CL_JSY.XM is '姓名'
comment on column CL_JSY.JGDM is '机构代码'
comment on column CL_JSY.XB is '性别'
comment on column CL_JSY.NL is '年龄'
comment on column CL_JSY.JL is '驾龄'
comment on column CL_JSY.ZT is '状态 字典项：ZDCLK0018 	(00、在班 10、休息)'
comment on column CL_JSY.TX is '头像'
comment on column CL_JSY.ZJCX is '准驾车型'
comment on column CL_JSY.DJ is '等级'
comment on column CL_JSY.CJSJ is '创建时间'
comment on column CL_JSY.CJR is '创建人'
comment on column CL_JSY.XGSJ is '修改时间'
comment on column CL_JSY.XGR is '修改人'
comment on column CL_JSY.CDBH is '车队编号'
comment on column CL_JSY.DABH is '档案编号'
comment on column CL_JSY.CLRQ is '初领日期'
comment on column CL_JSY.SJH is '驾驶员手机号'
comment on column CL_JSY.PWD is '微信登陆密码'
comment on column CL_JSY.XX is '血型'
comment on column CL_JSY.JKZK is '健康状况'
comment on column CL_JSY.JZYXQ is '驾照有效期'
create table CL_LSC
(
  ID      VARCHAR2(32) not null
    constraint PK_CL_LSC
    primary key,
  CPH     VARCHAR2(10),
  CJR     VARCHAR2(64),
  DJCS    NUMBER,
  LSDW_ID VARCHAR2(32),
  LSDWMC  VARCHAR2(32),
  CLLX    CHAR(2),
  ZWS     NUMBER,
  ZT      CHAR(2),
  CL_ID   VARCHAR2(32),
  CJSJ    TIMESTAMP(6)
)
comment on column CL_LSC.ID is 'ID'
comment on column CL_LSC.CPH is '车牌号'
comment on column CL_LSC.CJR is '创建人'
comment on column CL_LSC.DJCS is '登记次数'
comment on column CL_LSC.LSDW_ID is '临时单位id'
comment on column CL_LSC.LSDWMC is '临时单位名称'
comment on column CL_LSC.CLLX is '车辆类型'
comment on column CL_LSC.ZWS is '座位数'
comment on column CL_LSC.ZT is '状态'
comment on column CL_LSC.CL_ID is '车辆id'
create table CL_LSDW
(
  ID   VARCHAR2(32) not null
    constraint PK_CL_LSDW
    primary key,
  DWMC VARCHAR2(32),
  CJR  VARCHAR2(64),
  CJSJ TIMESTAMP(6),
  DJCS NUMBER,
  XGR  VARCHAR2(64),
  XGSJ TIMESTAMP(6),
  DWBH VARCHAR2(32),
  ZT   CHAR(2),
  LXR  VARCHAR2(32),
  LXDH VARCHAR2(16)
)
comment on column CL_LSDW.ID is 'ID'
comment on column CL_LSDW.DWMC is '单位名称'
comment on column CL_LSDW.CJR is '创建人'
comment on column CL_LSDW.CJSJ is '创建时间'
comment on column CL_LSDW.DJCS is '登记次数'
comment on column CL_LSDW.XGR is '修改人'
comment on column CL_LSDW.XGSJ is '修改时间'
comment on column CL_LSDW.DWBH is '单位编号'
comment on column CL_LSDW.ZT is '状态'
comment on column CL_LSDW.LXR is '联系人'
comment on column CL_LSDW.LXDH is '联系电话'
create table CL_PB
(
  ID    VARCHAR2(32) not null
    constraint PK_CL_PB
    primary key,
  CPH   VARCHAR2(10),
  XL_ID VARCHAR2(32),
  PBSJ  TIMESTAMP(6),
  SJ    VARCHAR2(32),
  SJXM  VARCHAR2(32),
  JGDM  VARCHAR2(20),
  JGMC  VARCHAR2(32),
  CL_ID VARCHAR2(32),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGJ   TIMESTAMP(6),
  XGR   VARCHAR2(64)
)
comment on column CL_PB.ID is 'ID'
comment on column CL_PB.CPH is '车牌号'
comment on column CL_PB.XL_ID is '线路id'
comment on column CL_PB.PBSJ is '排班时间'
comment on column CL_PB.SJ is '司机'
comment on column CL_PB.SJXM is '司机姓名'
comment on column CL_PB.JGDM is '机构代码'
comment on column CL_PB.JGMC is '机构名称'
comment on column CL_PB.CL_ID is '车辆id'
comment on column CL_PB.CJSJ is '创建时间'
comment on column CL_PB.CJR is '创建人'
comment on column CL_PB.XGJ is '修改时间'
comment on column CL_PB.XGR is '修改人'
create table CL_SBYCSJJL
(
  ID   VARCHAR2(32) not null
    constraint PK_CL_SBYCSJJL
    primary key,
  ZDBH VARCHAR2(32),
  LX   CHAR(2),
  CJSJ TIMESTAMP(6),
  CJR  VARCHAR2(64),
  JD   NUMBER(3, 10),
  WD   NUMBER(3, 10)
)
comment on table CL_SBYCSJJL is '设备异常事件记录表'
comment on column CL_SBYCSJJL.ID is 'ID'
comment on column CL_SBYCSJJL.ZDBH is '终端编号'
comment on column CL_SBYCSJJL.LX is '类型'
comment on column CL_SBYCSJJL.CJSJ is '创建时间'
comment on column CL_SBYCSJJL.CJR is '创建人'
comment on column CL_SBYCSJJL.JD is '经度'
comment on column CL_SBYCSJJL.WD is '纬度'
create table CL_SBYXSJJL
(
  ID   VARCHAR2(32) not null
    constraint PK_CL_SBYXSJJL
    primary key,
  ZDBH VARCHAR2(32),
  SJJB CHAR(2),
  CJSJ TIMESTAMP(6),
  JD   NUMBER,
  WD   NUMBER,
  JID  NUMBER,
  SJLX CHAR(2),
  YXFX FLOAT,
  BZ   VARCHAR2(100),
  SJXM VARCHAR2(255),
  CPH  VARCHAR2(255),
  CX   VARCHAR2(255)
)
comment on table CL_SBYXSJJL is '设备运行事件记录表'
comment on column CL_SBYXSJJL.ID is 'ID'
comment on column CL_SBYXSJJL.ZDBH is '终端编号'
comment on column CL_SBYXSJJL.SJJB is '事件级别'
comment on column CL_SBYXSJJL.CJSJ is '创建时间'
comment on column CL_SBYXSJJL.JD is '经度'
comment on column CL_SBYXSJJL.WD is '纬度'
comment on column CL_SBYXSJJL.JID is '精度'
comment on column CL_SBYXSJJL.SJLX is '事件类型'
comment on column CL_SBYXSJJL.YXFX is '运行方向'
comment on column CL_SBYXSJJL.BZ is '备注'
comment on column CL_SBYXSJJL.SJXM is '司机姓名'
comment on column CL_SBYXSJJL.CPH is '车牌号'
comment on column CL_SBYXSJJL.CX is '车型'
create table CL_SG
(
  ID    VARCHAR2(32) not null
    constraint PK_CL_SG
    primary key,
  CPH   VARCHAR2(10),
  CDBH  VARCHAR2(32),
  JGDM  VARCHAR2(20),
  SJ    VARCHAR2(32),
  SJXM  VARCHAR2(32),
  SGDD  VARCHAR2(255),
  SGSJ  TIMESTAMP(6),
  CLJG  VARCHAR2(255),
  CLR   VARCHAR2(64),
  SGMS  VARCHAR2(500),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  XGR   VARCHAR2(64),
  CL_ID VARCHAR2(32),
  CLSJ  TIMESTAMP(6),
  BZ    VARCHAR2(255),
  LXDH  VARCHAR2(16)
)
comment on column CL_SG.ID is 'ID'
comment on column CL_SG.CPH is '车牌号'
comment on column CL_SG.CDBH is '车队编号'
comment on column CL_SG.JGDM is '机构代码'
comment on column CL_SG.SJ is '司机'
comment on column CL_SG.SJXM is '司机姓名'
comment on column CL_SG.SGDD is '事故地点'
comment on column CL_SG.SGSJ is '事故时间'
comment on column CL_SG.CLJG is '处理结果'
comment on column CL_SG.CLR is '处理人'
comment on column CL_SG.SGMS is '事故描述'
comment on column CL_SG.CJSJ is '创建时间'
comment on column CL_SG.CJR is '创建人'
comment on column CL_SG.XGSJ is '修改时间'
comment on column CL_SG.XGR is '修改人'
comment on column CL_SG.CL_ID is '车辆id'
comment on column CL_SG.CLSJ is '处理时间'
comment on column CL_SG.BZ is '备注'
comment on column CL_SG.LXDH is '联系电话'
create table CL_SGWJ
(
  ID    VARCHAR2(32) not null
    constraint PK_SGWJ
    primary key,
  SG_ID VARCHAR2(32),
  WJLX  VARCHAR2(16),
  FWQDZ VARCHAR2(255),
  WLDZ  VARCHAR2(255),
  WJDX  FLOAT,
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64)
)
comment on column CL_SGWJ.ID is 'ID'
comment on column CL_SGWJ.SG_ID is '事故id'
comment on column CL_SGWJ.WJLX is '文件类型'
comment on column CL_SGWJ.FWQDZ is '服务器地址'
comment on column CL_SGWJ.WLDZ is '网络地址'
comment on column CL_SGWJ.WJDX is '文件大小'
comment on column CL_SGWJ.CJSJ is '创建时间'
comment on column CL_SGWJ.CJR is '创建人'
create table CL_SPK
(
  ID    VARCHAR2(32) not null
    constraint PK_CL_SPK
    primary key,
  WJM   VARCHAR2(32),
  DZ    VARCHAR2(255),
  ZDBH  VARCHAR2(32),
  KSSJ  TIMESTAMP(6),
  JSSJ  TIMESTAMP(6),
  SC    NUMBER,
  URL   VARCHAR2(255),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  XGR   VARCHAR2(64),
  BJ    VARCHAR2(32),
  SPLY  VARCHAR2(32),
  SPLX  CHAR(10),
  JGDM  VARCHAR2(20),
  JGMC  VARCHAR2(32),
  CPH   VARCHAR2(48),
  CL_ID VARCHAR2(32)
)
comment on column CL_SPK.ID is 'ID'
comment on column CL_SPK.WJM is '文件名'
comment on column CL_SPK.DZ is '地址'
comment on column CL_SPK.ZDBH is '终端编号'
comment on column CL_SPK.KSSJ is '开始时间'
comment on column CL_SPK.JSSJ is '结束时间'
comment on column CL_SPK.SC is '时长'
comment on column CL_SPK.URL is 'url'
comment on column CL_SPK.CJSJ is '创建时间'
comment on column CL_SPK.CJR is '创建人'
comment on column CL_SPK.XGSJ is '修改时间'
comment on column CL_SPK.XGR is '修改人'
comment on column CL_SPK.BJ is '标记'
comment on column CL_SPK.SPLY is '视频来源'
comment on column CL_SPK.SPLX is '视频类型'
comment on column CL_SPK.JGDM is '机构代码'
comment on column CL_SPK.JGMC is '机构名称'
comment on column CL_SPK.CPH is '车牌号'
comment on column CL_SPK.CL_ID is '车辆id'
create table CL_XL
(
  ID     VARCHAR2(32) not null
    constraint PK_CL_XL
    primary key,
  XLMC   VARCHAR2(32),
  XLBH   VARCHAR2(6),
  CD     FLOAT,
  PJSJ   FLOAT,
  YXKSSJ VARCHAR2(32) default NULL,
  YXJSSJ VARCHAR2(32) default NULL,
  CJR    VARCHAR2(64),
  CJSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  JGDM   VARCHAR2(20),
  JGMC   VARCHAR2(32),
  ZT     CHAR(2),
  BZ     VARCHAR2(255),
  YXFS   CHAR(2),
  LX     CHAR(2)
)
comment on column CL_XL.ID is 'ID'
comment on column CL_XL.XLMC is '线路名称'
comment on column CL_XL.XLBH is '线路编号'
comment on column CL_XL.CD is '长度'
comment on column CL_XL.PJSJ is '平均时间'
comment on column CL_XL.YXKSSJ is '运行开始时间'
comment on column CL_XL.YXJSSJ is '运行结束时间'
comment on column CL_XL.CJR is '创建人'
comment on column CL_XL.CJSJ is '创建时间'
comment on column CL_XL.XGR is '修改人'
comment on column CL_XL.XGSJ is '修改时间'
comment on column CL_XL.JGDM is '机构代码'
comment on column CL_XL.JGMC is '机构名称'
comment on column CL_XL.ZT is '状态'
comment on column CL_XL.BZ is '备注'
comment on column CL_XL.YXFS is '运行方式'
comment on column CL_XL.LX is '类型'
create table CL_XLCL
(
  ID     VARCHAR2(32) not null
    constraint PK_CL_XLCL
    primary key,
  "线路id" VARCHAR2(32),
  "车牌号"  VARCHAR2(10),
  "状态"   CHAR(2),
  "创建时间" TIMESTAMP(6),
  "创建人"  VARCHAR2(64),
  "修改时间" TIMESTAMP(6),
  "修改人"  VARCHAR2(64),
  "车辆id" VARCHAR2(32)
)
create table CL_XLZD
(
  ID     VARCHAR2(32) not null
    constraint PK_CL_XLZD
    primary key,
  XL_ID  VARCHAR2(32),
  ZD_ID  VARCHAR2(32),
  ZT     CHAR(2),
  CJR    VARCHAR2(64),
  CJSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  XH     NUMBER,
  FX     CHAR(2),
  YJDZSJ NUMBER
)
comment on column CL_XLZD.ID is 'ID'
comment on column CL_XLZD.XL_ID is '线路id'
comment on column CL_XLZD.ZD_ID is '站点id'
comment on column CL_XLZD.ZT is '状态'
comment on column CL_XLZD.CJR is '创建人'
comment on column CL_XLZD.CJSJ is '创建时间'
comment on column CL_XLZD.XGR is '修改人'
comment on column CL_XLZD.XGSJ is '修改时间'
comment on column CL_XLZD.XH is '序号'
comment on column CL_XLZD.FX is '方向'
comment on column CL_XLZD.YJDZSJ is '预计到站时间'
create table CL_ZD
(
  ID   VARCHAR2(32) not null
    constraint PK_CL_ZD
    primary key,
  DZ   VARCHAR2(255),
  MC   VARCHAR2(64),
  JD   FLOAT,
  WD   FLOAT,
  CJSJ TIMESTAMP(6),
  CJR  VARCHAR2(64),
  XGSJ TIMESTAMP(6),
  XGR  VARCHAR2(64),
  JGDM VARCHAR2(20),
  JGMC VARCHAR2(32),
  ZT   CHAR(2),
  BZ   VARCHAR2(255),
  YXFS CHAR(2),
  FW   NUMBER,
  LX   CHAR(2)
)
comment on column CL_ZD.ID is 'id'
comment on column CL_ZD.DZ is '地址'
comment on column CL_ZD.MC is '名称'
comment on column CL_ZD.JD is '经度'
comment on column CL_ZD.WD is '纬度'
comment on column CL_ZD.CJSJ is '创建时间'
comment on column CL_ZD.CJR is '创建人'
comment on column CL_ZD.XGSJ is '修改时间'
comment on column CL_ZD.XGR is '修改人'
comment on column CL_ZD.JGDM is '机构代码'
comment on column CL_ZD.JGMC is '机构名称'
comment on column CL_ZD.ZT is '状态'
comment on column CL_ZD.BZ is '备注'
comment on column CL_ZD.YXFS is '运行方式'
comment on column CL_ZD.FW is '范围'
comment on column CL_ZD.LX is '类型'
create table CL_ZDGL
(
  ZDBH    VARCHAR2(32) not null
    constraint PK_CL_ZDGL
    primary key,
  XH      VARCHAR2(32),
  MC      VARCHAR2(32),
  CS      VARCHAR2(32),
  ZT      CHAR(2),
  CJR     VARCHAR2(64),
  CJSJ    TIMESTAMP(6),
  XGR     VARCHAR2(64),
  XGSJ    TIMESTAMP(6),
  ZXZT    CHAR(2),
  ZXSJ    TIMESTAMP(6),
  JSLMD   VARCHAR2(255),
  SCMS    VARCHAR2(255),
  PZLMD   VARCHAR2(255),
  CMD     VARCHAR2(255),
  SPSCMS  VARCHAR2(255),
  GPSXT   VARCHAR2(255),
  SFYY    VARCHAR2(255),
  JGDM    VARCHAR2(20) default NULL,
  JGMC    VARCHAR2(20),
  VERSION VARCHAR2(20)
)
comment on table CL_ZDGL is '终端管理表'
comment on column CL_ZDGL.ZDBH is '终端编号'
comment on column CL_ZDGL.XH is '型号'
comment on column CL_ZDGL.MC is '名称'
comment on column CL_ZDGL.CS is '厂商'
comment on column CL_ZDGL.ZT is '状态'
comment on column CL_ZDGL.CJR is '创建人'
comment on column CL_ZDGL.CJSJ is '创建时间'
comment on column CL_ZDGL.XGR is '修改人'
comment on column CL_ZDGL.XGSJ is '修改时间'
comment on column CL_ZDGL.ZXZT is '在线状态 ZDCLK0032 (00点火 10 熄火 20离线)'
comment on column CL_ZDGL.ZXSJ is '在线时间'
comment on column CL_ZDGL.JSLMD is '加速灵敏度'
comment on column CL_ZDGL.SCMS is '数据上传模式'
comment on column CL_ZDGL.PZLMD is '碰撞灵敏度'
comment on column CL_ZDGL.CMD is '上报地址'
comment on column CL_ZDGL.SPSCMS is '视屏上传模式'
comment on column CL_ZDGL.GPSXT is 'GPS心跳'
comment on column CL_ZDGL.SFYY is '是否将设备上传到鹰眼'
comment on column CL_ZDGL.JGDM is '机构代码'
comment on column CL_ZDGL.JGMC is '机构名称'
comment on column CL_ZDGL.VERSION is '设备版本号'
create table CL_ZNZP
(
  ZDBH  VARCHAR2(32)           not null
    constraint PK_CL_ZNZP
    primary key,
  MC    VARCHAR2(32),
  XH    VARCHAR2(32),
  CS    VARCHAR2(255),
  DZ    VARCHAR2(255),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  XGR   VARCHAR2(64),
  ZD_ID VARCHAR2(32),
  TIPS  VARCHAR2(255),
  ZXZT  VARCHAR2(4) default 10 not null
)
comment on column CL_ZNZP.ZDBH is '终端编号'
comment on column CL_ZNZP.MC is '名称'
comment on column CL_ZNZP.XH is '型号'
comment on column CL_ZNZP.CS is '厂商'
comment on column CL_ZNZP.DZ is '地址'
comment on column CL_ZNZP.CJSJ is '创建时间'
comment on column CL_ZNZP.CJR is '创建人'
comment on column CL_ZNZP.XGSJ is '修改时间'
comment on column CL_ZNZP.XGR is '修改人'
comment on column CL_ZNZP.ZD_ID is '站点ID'
comment on column CL_ZNZP.TIPS is '备注'
comment on column CL_ZNZP.ZXZT is '在线状态 /* 00:在线 10：离线*/'
create table CL_ZP_XL
(
  ID    VARCHAR2(32) not null
    constraint PK_CL_ZP_XL
    primary key,
  ZP_ID VARCHAR2(32),
  XL_ID VARCHAR2(32),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64)
)
comment on column CL_ZP_XL.ID is 'ID'
comment on column CL_ZP_XL.ZP_ID is '站牌ID'
comment on column CL_ZP_XL.XL_ID is '线路ID'
comment on column CL_ZP_XL.CJSJ is '创建时间'
comment on column CL_ZP_XL.CJR is '创建人'
create table SYS_CLK_PTJS
(
  JS_ID VARCHAR2(32) not null
    constraint PK_SYS_CLK_PTJS
    primary key,
  JSMC  VARCHAR2(32),
  JSLX  VARCHAR2(32),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  ZT    CHAR(2),
  JGDM  VARCHAR2(20),
  SM    VARCHAR2(1000)
)
comment on column SYS_CLK_PTJS.JS_ID is '角色id'
comment on column SYS_CLK_PTJS.JSMC is '角色名称'
comment on column SYS_CLK_PTJS.JSLX is '角色类型'
comment on column SYS_CLK_PTJS.CJSJ is '创建时间'
comment on column SYS_CLK_PTJS.CJR is '创建人'
comment on column SYS_CLK_PTJS.XGR is '修改人'
comment on column SYS_CLK_PTJS.XGSJ is '修改时间'
comment on column SYS_CLK_PTJS.ZT is '状态'
comment on column SYS_CLK_PTJS.JGDM is '机构代码'
comment on column SYS_CLK_PTJS.SM is '说明'
create table SYS_CLK_PTYH
(
  YHID  VARCHAR2(32) not null
    constraint PK_SYS_CLK_PTYH
    primary key,
  ZH    VARCHAR2(32),
  MM    VARCHAR2(32),
  SJH   VARCHAR2(32),
  CJR   VARCHAR2(64),
  CJSJ  TIMESTAMP(6),
  XGR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  ZT    CHAR(2),
  JGDM  VARCHAR2(20),
  XM    VARCHAR2(32),
  LX    CHAR(2),
  XB    CHAR,
  ZJHM  VARCHAR2(32),
  MMYXQ TIMESTAMP(6),
  ZW    VARCHAR2(32)
)
comment on table SYS_CLK_PTYH is '平台用户表'
comment on column SYS_CLK_PTYH.YHID is '用户id'
comment on column SYS_CLK_PTYH.ZH is '账号'
comment on column SYS_CLK_PTYH.MM is '密码'
comment on column SYS_CLK_PTYH.SJH is '手机号'
comment on column SYS_CLK_PTYH.CJR is '创建人'
comment on column SYS_CLK_PTYH.CJSJ is '创建时间'
comment on column SYS_CLK_PTYH.XGR is '修改人'
comment on column SYS_CLK_PTYH.XGSJ is '修改时间'
comment on column SYS_CLK_PTYH.ZT is '状态'
comment on column SYS_CLK_PTYH.JGDM is '机构代码'
comment on column SYS_CLK_PTYH.XM is '姓名'
comment on column SYS_CLK_PTYH.LX is '类型'
comment on column SYS_CLK_PTYH.XB is '性别'
comment on column SYS_CLK_PTYH.ZJHM is '证件号码'
comment on column SYS_CLK_PTYH.MMYXQ is '密码有效期'
comment on column SYS_CLK_PTYH.ZW is '职务'
create table SYS_FWGN
(
  GNDM   VARCHAR2(32) not null
    constraint PK_FWGN
    primary key,
  GNMC   VARCHAR2(32),
  FWDM   VARCHAR2(32),
  CJSJ   TIMESTAMP(6),
  CJR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64),
  ZT     CHAR(2),
  BZ     VARCHAR2(255),
  URL    VARCHAR2(255),
  FJD    VARCHAR2(32),
  TZDZ   VARCHAR2(32),
  TB     VARCHAR2(32),
  API_QZ VARCHAR2(64),
  API_HZ VARCHAR2(64),
  PX     FLOAT default 0
)
comment on column SYS_FWGN.GNDM is '功能代码'
comment on column SYS_FWGN.GNMC is '功能名称'
comment on column SYS_FWGN.FWDM is '服务代码'
comment on column SYS_FWGN.CJSJ is '创建时间'
comment on column SYS_FWGN.CJR is '创建人'
comment on column SYS_FWGN.XGSJ is '修改时间'
comment on column SYS_FWGN.XGR is '修改人'
comment on column SYS_FWGN.ZT is '状态'
comment on column SYS_FWGN.BZ is '备注'
comment on column SYS_FWGN.URL is 'URL'
comment on column SYS_FWGN.FJD is '父节点'
comment on column SYS_FWGN.TZDZ is '跳转地址'
comment on column SYS_FWGN.TB is '图标'
comment on column SYS_FWGN.API_QZ is 'API前缀'
comment on column SYS_FWGN.API_HZ is 'API后缀'
comment on column SYS_FWGN.PX is '排序'
create table SYS_HDYX
(
  HD_ID VARCHAR2(32) not null
    constraint PK_SYS_HDYX
    primary key,
  KSSJ  TIMESTAMP(6),
  JSSJ  TIMESTAMP(6),
  HDLX  VARCHAR2(32),
  JGDM  VARCHAR2(20),
  ZT    CHAR(2),
  CJR   VARCHAR2(64),
  CJSJ  TIMESTAMP(6),
  XGR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  HDBT  VARCHAR2(32),
  URL   VARCHAR2(255),
  WJLX  VARCHAR2(32) default 00,
  WZ    VARCHAR2(32) default 10
)
comment on table SYS_HDYX is '活动营销'
comment on column SYS_HDYX.HD_ID is '活动id'
comment on column SYS_HDYX.KSSJ is '活动时间开始'
comment on column SYS_HDYX.JSSJ is '活动时间结束'
comment on column SYS_HDYX.HDLX is '活动类型(00 微信、10智能站牌)'
comment on column SYS_HDYX.JGDM is '机构代码'
comment on column SYS_HDYX.ZT is '状态(00未开始 10 已开始  20 已结束)'
comment on column SYS_HDYX.CJR is '创建人'
comment on column SYS_HDYX.CJSJ is '创建时间'
comment on column SYS_HDYX.XGR is '修改人'
comment on column SYS_HDYX.XGSJ is '修改时间'
comment on column SYS_HDYX.HDBT is '活动标题'
comment on column SYS_HDYX.URL is 'URL'
comment on column SYS_HDYX.WJLX is '文件类型,图片、视频'
comment on column SYS_HDYX.WZ is '智能站牌位置(10上 20中 30下)'
create table SYS_HSGS
(
  ID   VARCHAR2(32) not null
    constraint PK_SYS_HSGS
    primary key,
  CX   CHAR(2),
  LX   CHAR(2),
  NR   VARCHAR2(32),
  JE   NUMBER(10, 2),
  CJR  VARCHAR2(64),
  CJSJ TIMESTAMP(6),
  XGR  VARCHAR2(64),
  XGSJ TIMESTAMP(6)
)
comment on table SYS_HSGS is '核算公式'
comment on column SYS_HSGS.ID is 'ID'
comment on column SYS_HSGS.CX is '车型'
comment on column SYS_HSGS.LX is '类型'
comment on column SYS_HSGS.NR is '内容'
comment on column SYS_HSGS.JE is '金额'
comment on column SYS_HSGS.CJR is '创建人'
comment on column SYS_HSGS.CJSJ is '创建时间'
comment on column SYS_HSGS.XGR is '修改人'
comment on column SYS_HSGS.XGSJ is '修改时间'
create table SYS_JGSQLB
(
  ID   VARCHAR2(32) not null,
  JGDM VARCHAR2(20),
  FWDM VARCHAR2(32),
  GNDM VARCHAR2(32),
  CJSJ TIMESTAMP(6),
  CJR  VARCHAR2(64),
  XGSJ TIMESTAMP(6),
  XGR  VARCHAR2(64),
  YXQ  TIMESTAMP(6)
)
comment on column SYS_JGSQLB.ID is 'ID'
comment on column SYS_JGSQLB.JGDM is '机构代码'
comment on column SYS_JGSQLB.FWDM is '服务代码'
comment on column SYS_JGSQLB.GNDM is '功能代码'
comment on column SYS_JGSQLB.CJSJ is '创建时间'
comment on column SYS_JGSQLB.CJR is '创建人'
comment on column SYS_JGSQLB.XGSJ is '修改时间'
comment on column SYS_JGSQLB.XGR is '修改人'
comment on column SYS_JGSQLB.YXQ is '有效期'
create table SYS_JS_FW
(
  "id" VARCHAR2(32) not null,
  JSDM VARCHAR2(32),
  FWDM VARCHAR2(32),
  CJSJ TIMESTAMP(6),
  CJR  VARCHAR2(64)
)
comment on column SYS_JS_FW."id" is 'id'
comment on column SYS_JS_FW.JSDM is '角色代码'
comment on column SYS_JS_FW.FWDM is '服务代码'
comment on column SYS_JS_FW.CJSJ is '创建时间'
comment on column SYS_JS_FW.CJR is '创建人'
create table SYS_JS_GN
(
  ID    VARCHAR2(32) not null
    constraint PK_SYS_JS_GN
    primary key,
  JSDM  VARCHAR2(32),
  GNDM  VARCHAR2(32),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  FWDM  VARCHAR2(32),
  FGNDM VARCHAR2(32)
)
comment on column SYS_JS_GN.ID is 'id'
comment on column SYS_JS_GN.JSDM is '角色代码'
comment on column SYS_JS_GN.GNDM is '功能代码'
comment on column SYS_JS_GN.CJSJ is '创建时间'
comment on column SYS_JS_GN.CJR is '创建人'
comment on column SYS_JS_GN.FWDM is '服务代码'
comment on column SYS_JS_GN.FGNDM is '父功能代码'
create table SYS_JZGXX
(
  ID   VARCHAR2(32) not null
    constraint PK_SYS_JZGXX
    primary key,
  XM   VARCHAR2(32),
  XB   CHAR(2),
  ZW   VARCHAR2(16),
  ZJHM VARCHAR2(16),
  JGDM VARCHAR2(32),
  JDMC VARCHAR2(32),
  ZGLX CHAR(4),
  CJR  VARCHAR2(64),
  CJSJ TIMESTAMP(6),
  XGR  VARCHAR2(64),
  XGSJ TIMESTAMP(6),
  SJHM VARCHAR2(16)
)
comment on column SYS_JZGXX.ID is 'ID'
comment on column SYS_JZGXX.XM is '姓名'
comment on column SYS_JZGXX.XB is '性别'
comment on column SYS_JZGXX.ZW is '职务'
comment on column SYS_JZGXX.ZJHM is '证件号码'
comment on column SYS_JZGXX.JGDM is '机构代码'
comment on column SYS_JZGXX.JDMC is '机构名称'
comment on column SYS_JZGXX.ZGLX is '职工类型'
comment on column SYS_JZGXX.CJR is '创建人'
comment on column SYS_JZGXX.CJSJ is '创建时间'
comment on column SYS_JZGXX.XGR is '修改人'
comment on column SYS_JZGXX.XGSJ is '修改时间'
comment on column SYS_JZGXX.SJHM is '手机号码'
create table SYS_PTFW
(
  FW_ID  VARCHAR2(32) not null
    constraint PK_SYS_PTFW
    primary key,
  FWMC   VARCHAR2(32),
  FWDM   VARCHAR2(32),
  CJSJ   TIMESTAMP(6),
  CJR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64),
  ZT     CHAR(2),
  API_QZ VARCHAR2(64),
  TB     VARCHAR2(32)
)
comment on column SYS_PTFW.FW_ID is '服务id'
comment on column SYS_PTFW.FWMC is '服务名称'
comment on column SYS_PTFW.FWDM is '服务代码'
comment on column SYS_PTFW.CJSJ is '创建时间'
comment on column SYS_PTFW.CJR is '创建人'
comment on column SYS_PTFW.XGSJ is '修改时间'
comment on column SYS_PTFW.XGR is '修改人'
comment on column SYS_PTFW.ZT is '状态'
comment on column SYS_PTFW.API_QZ is 'API前缀'
comment on column SYS_PTFW.TB is '图标'
create table SYS_PTJG
(
  JGDM  VARCHAR2(20) not null
    constraint PK_SYS_PTJG
    primary key,
  JGMC  VARCHAR2(32),
  JGLX  CHAR(2),
  ZT    CHAR(2),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  GLY   VARCHAR2(32),
  GLYXM VARCHAR2(32),
  FJGDM VARCHAR2(32),
  BZ    VARCHAR2(255),
  JGDJ  NUMBER default 1,
  JGBM  VARCHAR2(64),
  JGSM  VARCHAR2(255)
)
comment on table SYS_PTJG is '平台机构表'
comment on column SYS_PTJG.JGDM is '机构代码'
comment on column SYS_PTJG.JGMC is '机构名称'
comment on column SYS_PTJG.JGLX is '机构类型'
comment on column SYS_PTJG.ZT is '状态'
comment on column SYS_PTJG.CJSJ is '创建时间'
comment on column SYS_PTJG.CJR is '创建人'
comment on column SYS_PTJG.XGR is '修改人'
comment on column SYS_PTJG.XGSJ is '修改时间'
comment on column SYS_PTJG.GLY is '管理员'
comment on column SYS_PTJG.GLYXM is '管理员姓名'
comment on column SYS_PTJG.FJGDM is '父机构代码'
comment on column SYS_PTJG.BZ is '备注'
comment on column SYS_PTJG.JGDJ is '机构等级'
comment on column SYS_PTJG.JGBM is '机构编码'
comment on column SYS_PTJG.JGSM is '机构说明'
create table SYS_PTRZ
(
  RZ_ID VARCHAR2(32) not null
    constraint PK_SYS_PTRZ
    primary key,
  CZLX  VARCHAR2(32),
  CZSJ  TIMESTAMP(6),
  CZR   VARCHAR2(64),
  DX_ID VARCHAR2(32),
  DXLX  VARCHAR2(32),
  CS    VARCHAR2(1024),
  JG    VARCHAR2(1112),
  ZXSJ  NUMBER,
  SM    VARCHAR2(32),
  FF    VARCHAR2(64)
)
comment on table SYS_PTRZ is '平台日志表'
comment on column SYS_PTRZ.RZ_ID is '日志id'
comment on column SYS_PTRZ.CZLX is '操作类型'
comment on column SYS_PTRZ.CZSJ is '操作时间'
comment on column SYS_PTRZ.CZR is '操作人'
comment on column SYS_PTRZ.DX_ID is '对象id'
comment on column SYS_PTRZ.DXLX is '对象类型'
comment on column SYS_PTRZ.CS is '参数'
comment on column SYS_PTRZ.JG is '结果'
comment on column SYS_PTRZ.ZXSJ is '执行时间'
comment on column SYS_PTRZ.SM is '说明'
comment on column SYS_PTRZ.FF is '方法'
create table SYS_RLB
(
  RQDM VARCHAR2(8) not null
    constraint PK_SYS_RLB
    primary key,
  XQ   NUMBER,
  ZT   CHAR(2),
  RQ   VARCHAR2(16)
)
comment on table SYS_RLB is '日历表'
comment on column SYS_RLB.RQDM is '日期代码'
comment on column SYS_RLB.XQ is '星期'
comment on column SYS_RLB.ZT is '状态'
comment on column SYS_RLB.RQ is '日期'
create table SYS_WXYH
(
  OPENID VARCHAR2(32) not null
    constraint PK_SYS_WXYH
    primary key,
  XM     VARCHAR2(32),
  DH     VARCHAR2(16),
  YHLX   CHAR(2),
  XY     VARCHAR2(16),
  ZJHM   VARCHAR2(16),
  ZW     VARCHAR2(16),
  CJSJ   TIMESTAMP(6),
  CJR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64),
  JGDM   VARCHAR2(32),
  JGMC   VARCHAR2(32)
)
comment on column SYS_WXYH.OPENID is 'OPENID'
comment on column SYS_WXYH.XM is '姓名'
comment on column SYS_WXYH.DH is '电话'
comment on column SYS_WXYH.YHLX is '用户类型'
comment on column SYS_WXYH.XY is '学院'
comment on column SYS_WXYH.ZJHM is '证件号码'
comment on column SYS_WXYH.ZW is '职务'
comment on column SYS_WXYH.CJSJ is '创建时间'
comment on column SYS_WXYH.CJR is '创建人'
comment on column SYS_WXYH.XGSJ is '修改时间'
comment on column SYS_WXYH.XGR is '修改人'
create table SYS_XXTS
(
  XX_ID VARCHAR2(32) not null
    constraint PK_SYS_XXTS
    primary key,
  XXNR  VARCHAR2(1000),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  XGR   VARCHAR2(64),
  KJFW  VARCHAR2(32),
  KSSJ  TIMESTAMP(6),
  JSSJ  TIMESTAMP(6)
)
comment on column SYS_XXTS.XX_ID is '消息id'
comment on column SYS_XXTS.XXNR is '消息内容'
comment on column SYS_XXTS.CJSJ is '创建时间'
comment on column SYS_XXTS.CJR is '创建人'
comment on column SYS_XXTS.XGSJ is '修改时间'
comment on column SYS_XXTS.XGR is '修改人'
comment on column SYS_XXTS.KJFW is '可见范围'
comment on column SYS_XXTS.KSSJ is '开始时间'
comment on column SYS_XXTS.JSSJ is '结束时间'
create table SYS_YH_JS
(
  YHJS_ID VARCHAR2(32) not null
    constraint PK_SYS_YH_JS
    primary key,
  YH_ID   VARCHAR2(32),
  JS_ID   VARCHAR2(32),
  CJR     VARCHAR2(64),
  CJSJ    TIMESTAMP(6)
)
comment on column SYS_YH_JS.YHJS_ID is '用户角色关联表id'
comment on column SYS_YH_JS.YH_ID is '用户id'
comment on column SYS_YH_JS.JS_ID is '角色id'
comment on column SYS_YH_JS.CJR is '创建人'
comment on column SYS_YH_JS.CJSJ is '创建时间'
create table SYS_YH_XX
(
  "id"  VARCHAR2(32) not null,
  YH_ID VARCHAR2(32),
  XX_ID VARCHAR2(32),
  CJSJ  TIMESTAMP(6),
  ZT    CHAR(2)
)
comment on column SYS_YH_XX."id" is 'id'
comment on column SYS_YH_XX.YH_ID is '用户id'
comment on column SYS_YH_XX.XX_ID is '消息id'
comment on column SYS_YH_XX.CJSJ is '创建时间'
comment on column SYS_YH_XX.ZT is '状态'
create table SYS_YJFK
(
  YJ_ID VARCHAR2(32) not null
    constraint PK_SYS_YJFK
    primary key,
  YH_ID VARCHAR2(32),
  NR    VARCHAR2(1000),
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  ZT    CHAR(2),
  YJLX  CHAR(2),
  CLJG  VARCHAR2(512),
  LXRXM VARCHAR2(32),
  LXFS  VARCHAR2(32),
  XGR   VARCHAR2(32),
  XGSJ  TIMESTAMP(6)
)
comment on column SYS_YJFK.YJ_ID is '意见id'
comment on column SYS_YJFK.YH_ID is '用户id'
comment on column SYS_YJFK.NR is '内容'
comment on column SYS_YJFK.CJSJ is '创建时间'
comment on column SYS_YJFK.CJR is '创建人'
comment on column SYS_YJFK.ZT is '状态'
comment on column SYS_YJFK.YJLX is '意见类型'
comment on column SYS_YJFK.CLJG is '处理结果'
comment on column SYS_YJFK.LXRXM is '联系人姓名'
comment on column SYS_YJFK.LXFS is '联系方式'
comment on column SYS_YJFK.XGR is '修改人'
comment on column SYS_YJFK.XGSJ is '修改时间'
create table SYS_YXHDWJ
(
  ID    VARCHAR2(32) not null
    constraint PK_SYS_YXHDWJ
    primary key,
  HD_ID VARCHAR2(32),
  WJLX  VARCHAR2(32),
  WJLJ  VARCHAR2(255),
  WLLJ  VARCHAR2(255) default NULL,
  CJSJ  TIMESTAMP(6),
  CJR   VARCHAR2(64),
  XGSJ  TIMESTAMP(6),
  XGR   VARCHAR2(64)
)
comment on column SYS_YXHDWJ.ID is 'ID'
comment on column SYS_YXHDWJ.HD_ID is '活动id'
comment on column SYS_YXHDWJ.WJLX is '文件类型'
comment on column SYS_YXHDWJ.WJLJ is '文件路径'
comment on column SYS_YXHDWJ.WLLJ is '网络路径'
comment on column SYS_YXHDWJ.CJSJ is '创建时间'
comment on column SYS_YXHDWJ.CJR is '创建人'
comment on column SYS_YXHDWJ.XGSJ is '修改时间'
comment on column SYS_YXHDWJ.XGR is '修改人'
create table SYS_ZDLM
(
  LMDM VARCHAR2(32) not null
    constraint PK_ZDLM
    primary key,
  LMMC VARCHAR2(32),
  CJSJ TIMESTAMP(6),
  CJR  VARCHAR2(64),
  XGSJ TIMESTAMP(6),
  XGR  VARCHAR2(64),
  QZ   FLOAT
)
comment on table SYS_ZDLM is '平台字典类目表(字典类别)'
comment on column SYS_ZDLM.LMDM is '类目代码'
comment on column SYS_ZDLM.LMMC is '类目名称'
comment on column SYS_ZDLM.CJSJ is '创建时间'
comment on column SYS_ZDLM.CJR is '创建人'
comment on column SYS_ZDLM.XGSJ is '修改时间'
comment on column SYS_ZDLM.XGR is '修改人'
comment on column SYS_ZDLM.QZ is '权重'
create table SYS_ZDXM
(
  ZD_ID  VARCHAR2(32) not null
    constraint PK_SYS_ZDXM
    primary key,
  ZDLMDM VARCHAR2(32),
  ZDDM   VARCHAR2(32),
  ZDMC   VARCHAR2(32),
  QZ     FLOAT(8),
  CJSJ   TIMESTAMP(6),
  CJR    VARCHAR2(64),
  BY1    VARCHAR2(32),
  BY2    VARCHAR2(32),
  BY3    VARCHAR2(32)
)
comment on table SYS_ZDXM is '平台字典表'
comment on column SYS_ZDXM.ZD_ID is '字典id'
comment on column SYS_ZDXM.ZDLMDM is '字典类目代码'
comment on column SYS_ZDXM.ZDDM is '字典代码'
comment on column SYS_ZDXM.ZDMC is '字典名称'
comment on column SYS_ZDXM.QZ is '权重'
comment on column SYS_ZDXM.CJSJ is '创建时间'
comment on column SYS_ZDXM.CJR is '创建人'
comment on column SYS_ZDXM.BY1 is '备用1'
comment on column SYS_ZDXM.BY2 is '备用2'
comment on column SYS_ZDXM.BY3 is '备用3'
create table CL_DDLSB
(
  ID     VARCHAR2(32) not null
    constraint PK_CL_DDLSB
    primary key,
  SJQRSJ TIMESTAMP(6),
  HCDZ   VARCHAR2(255),
  MDD    VARCHAR2(255),
  CPH    VARCHAR2(10),
  SJ     VARCHAR2(32),
  SJXM   VARCHAR2(32),
  ZJ     FLOAT,
  SC     NUMBER,
  DJ     FLOAT,
  LC     FLOAT,
  SCF    FLOAT,
  LCF    FLOAT,
  CK     VARCHAR2(16),
  CKZW   CHAR(2),
  CKLXDH VARCHAR2(16),
  ZWS    NUMBER,
  CKLX   CHAR(2),
  YYSJ   TIMESTAMP(6),
  DDZT   CHAR(2),
  FKZT   CHAR(2),
  FKFS   CHAR(2),
  FKSJ   TIMESTAMP(6),
  PJDJ   NUMBER,
  PJNR   VARCHAR2(255),
  CJSJ   TIMESTAMP(6),
  CJR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64),
  JGDM   VARCHAR2(20),
  JGMC   VARCHAR2(32),
  CL_ID  VARCHAR2(32),
  GLF    FLOAT,
  SY     VARCHAR2(255),
  CLLX   CHAR(2),
  WF     CHAR(2),
  DZQRSJ TIMESTAMP(6),
  SHSJ   TIMESTAMP(6),
  DZXM   VARCHAR2(32),
  FKBZ   VARCHAR2(255),
  DD_ID  VARCHAR2(32),
  XGCS   NUMBER,
  DDXGSJ TIMESTAMP(6),
  DDCJSJ TIMESTAMP(6),
  DDXGR  VARCHAR2(64)
)
comment on table CL_DDLSB is '订单历史表(原始单据表)'
comment on column CL_DDLSB.ID is 'ID'
comment on column CL_DDLSB.SJQRSJ is '司机确认时间'
comment on column CL_DDLSB.HCDZ is '候车地址'
comment on column CL_DDLSB.MDD is '目的地'
comment on column CL_DDLSB.CPH is '车牌号'
comment on column CL_DDLSB.SJ is '司机'
comment on column CL_DDLSB.SJXM is '司机姓名'
comment on column CL_DDLSB.ZJ is '总价'
comment on column CL_DDLSB.SC is '时长'
comment on column CL_DDLSB.DJ is '单价'
comment on column CL_DDLSB.LC is '里程'
comment on column CL_DDLSB.SCF is '时长费'
comment on column CL_DDLSB.LCF is '里程费'
comment on column CL_DDLSB.CK is '乘客姓名'
comment on column CL_DDLSB.CKZW is '乘客职务'
comment on column CL_DDLSB.CKLXDH is '乘客联系电话'
comment on column CL_DDLSB.ZWS is '座位数'
comment on column CL_DDLSB.CKLX is '乘客类型'
comment on column CL_DDLSB.YYSJ is '预约时间'
comment on column CL_DDLSB.DDZT is '订单状态'
comment on column CL_DDLSB.FKZT is '付款状态'
comment on column CL_DDLSB.FKFS is '付款方式'
comment on column CL_DDLSB.FKSJ is '付款时间'
comment on column CL_DDLSB.PJDJ is '评价等级'
comment on column CL_DDLSB.PJNR is '评价内容'
comment on column CL_DDLSB.CJSJ is '创建时间'
comment on column CL_DDLSB.CJR is '创建人'
comment on column CL_DDLSB.XGSJ is '修改时间'
comment on column CL_DDLSB.XGR is '修改人'
comment on column CL_DDLSB.JGDM is '机构代码'
comment on column CL_DDLSB.JGMC is '机构名称'
comment on column CL_DDLSB.CL_ID is '车辆id'
comment on column CL_DDLSB.GLF is '过路费'
comment on column CL_DDLSB.SY is '事由'
comment on column CL_DDLSB.CLLX is '车辆类型'
comment on column CL_DDLSB.WF is '往返'
comment on column CL_DDLSB.DZQRSJ is '队长确认时间'
comment on column CL_DDLSB.SHSJ is '审核时间'
comment on column CL_DDLSB.DZXM is '队长姓名'
comment on column CL_DDLSB.FKBZ is '付款备注'
comment on column CL_DDLSB.DD_ID is '订单ID'
comment on column CL_DDLSB.XGCS is '修改次数'
comment on column CL_DDLSB.DDXGSJ is '订单修改时间'
comment on column CL_DDLSB.DDCJSJ is '订单创建时间'
comment on column CL_DDLSB.DDXGR is '操订单作人'
create table "SYS_FWGN_copy"
(
  GNDM   VARCHAR2(32) not null
    primary key,
  GNMC   VARCHAR2(32),
  FWDM   VARCHAR2(32),
  CJSJ   TIMESTAMP(6),
  CJR    VARCHAR2(64),
  XGSJ   TIMESTAMP(6),
  XGR    VARCHAR2(64),
  ZT     CHAR(2),
  BZ     VARCHAR2(255),
  URL    VARCHAR2(255),
  FJD    VARCHAR2(32),
  TZDZ   VARCHAR2(32),
  TB     VARCHAR2(32),
  API_QZ VARCHAR2(64),
  API_HZ VARCHAR2(64)
)
comment on column "SYS_FWGN_copy".GNDM is '功能代码'
comment on column "SYS_FWGN_copy".GNMC is '功能名称'
comment on column "SYS_FWGN_copy".FWDM is '服务代码'
comment on column "SYS_FWGN_copy".CJSJ is '创建时间'
comment on column "SYS_FWGN_copy".CJR is '创建人'
comment on column "SYS_FWGN_copy".XGSJ is '修改时间'
comment on column "SYS_FWGN_copy".XGR is '修改人'
comment on column "SYS_FWGN_copy".ZT is '状态'
comment on column "SYS_FWGN_copy".BZ is '备注'
comment on column "SYS_FWGN_copy".URL is 'URL'
comment on column "SYS_FWGN_copy".FJD is '父节点'
comment on column "SYS_FWGN_copy".TZDZ is '跳转地址'
comment on column "SYS_FWGN_copy".TB is '图标'
comment on column "SYS_FWGN_copy".API_QZ is 'API前缀'
comment on column "SYS_FWGN_copy".API_HZ is 'API后缀'
create table SYS_MESSAGE
(
  ID            VARCHAR2(32),
  CREATION_TIME TIMESTAMP(6) default SYSDATE,
  MESSAGE       VARCHAR2(1000),
  TYPE          CHAR(3),
  TITLE         VARCHAR2(200),
  STATUS        CHAR(3)      default 0,
  SEND_COUNT    CHAR(3)      default 0,
  SEND_TIME     TIMESTAMP(6) default SYSDATE,
  REMARK        VARCHAR2(2000),
  SENDEE_CODE   VARCHAR2(200),
  BIZ_ID        VARCHAR2(32)
)
comment on table SYS_MESSAGE is '消息表-平台所有消息发送'
comment on column SYS_MESSAGE.ID is '主键'
comment on column SYS_MESSAGE.CREATION_TIME is '创建时间'
comment on column SYS_MESSAGE.MESSAGE is '短信标题，按业务不同，也许是正文，也许是JSON报文'
comment on column SYS_MESSAGE.TYPE is '1、短信'
comment on column SYS_MESSAGE.TITLE is '标题'
comment on column SYS_MESSAGE.STATUS is '0、未发送 1、已发送  2、发送失败'
comment on column SYS_MESSAGE.SEND_COUNT is '发送次数'
comment on column SYS_MESSAGE.SEND_TIME is '发送时间'
comment on column SYS_MESSAGE.REMARK is '备注'
comment on column SYS_MESSAGE.SENDEE_CODE is '接收方编号(短信是手机号、微信是open_id)'
comment on column SYS_MESSAGE.BIZ_ID is '业务编号(操作业务) (短信保存模板ID)'
create table CL_BXJZ
(
  ID   VARCHAR2(32),
  BXR  VARCHAR2(32),
  BXSX VARCHAR2(255),
  BXSJ TIMESTAMP(6),
  BXJE FLOAT,
  FPSL NUMBER,
  CJR  VARCHAR2(64),
  CJSJ TIMESTAMP(6),
  JGDM VARCHAR2(32),
  JGMC VARCHAR2(32)
)
comment on table CL_BXJZ is '报销记账'
comment on column CL_BXJZ.ID is 'ID'
comment on column CL_BXJZ.BXR is '报销人'
comment on column CL_BXJZ.BXSX is '报销事项'
comment on column CL_BXJZ.BXSJ is '报销时间'
comment on column CL_BXJZ.BXJE is '报销金额'
comment on column CL_BXJZ.FPSL is '发票数量'
comment on column CL_BXJZ.CJR is '创建人'
comment on column CL_BXJZ.CJSJ is '创建时间'
comment on column CL_BXJZ.JGDM is '机构代码'
comment on column CL_BXJZ.JGMC is '机构名称'
create table CL_YY
(
  ID        VARCHAR2(32) not null
    constraint PK_BAIDU_YINGYAN
    primary key,
  ZDBH      VARCHAR2(32),
  LONGITUDE FLOAT,
  LATITUDE  FLOAT,
  LOCTIME   VARCHAR2(32),
  OBJECTKEY VARCHAR2(32),
  DIRECTION VARCHAR2(32) default NULL,
  SPEED     FLOAT
)
comment on column CL_YY.ID is '主键'
comment on column CL_YY.ZDBH is '终端编号对应的百度鹰眼entityname'
comment on column CL_YY.LONGITUDE is '经度'
comment on column CL_YY.LATITUDE is '纬度'
comment on column CL_YY.LOCTIME is '上传的点位时间'
comment on column CL_YY.OBJECTKEY is '预留字段'
comment on column CL_YY.DIRECTION is '方向角'
comment on column CL_YY.SPEED is '速度'
create table CL_XC
(
  ID           VARCHAR2(32) not null
    primary key,
  XC_KSSJ      VARCHAR2(32),
  XC_JSSJ      VARCHAR2(32),
  CL_ZDBH      VARCHAR2(32),
  XC_START_END VARCHAR2(64)
)
comment on table CL_XC is '车辆行程表'
comment on column CL_XC.XC_KSSJ is '车辆行程开始时间'
comment on column CL_XC.XC_JSSJ is'行程结束时间'
comment on column CL_XC.CL_ZDBH is '终端编号'
comment on column CL_XC.XC_START_END is '行程的开始和结束点'
