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
comment on table CL_CD is '���ӱ�'
comment on column CL_CD.CDBH is '���ӱ��'
comment on column CL_CD.CDMC is '��������'
comment on column CL_CD.DZBH is '�ӳ����'
comment on column CL_CD.DZXM is '�ӳ�����'
comment on column CL_CD.JGDM is '��������'
comment on column CL_CD.JGMC is '��������'
comment on column CL_CD.ZT is '״̬'
comment on column CL_CD.CJSJ is '����ʱ��'
comment on column CL_CD.CJR is '������'
comment on column CL_CD.XGSJ is '�޸�ʱ��'
comment on column CL_CD.XGR is '�޸���'
comment on column CL_CD.SJHM is '�ֻ�����'
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
comment on table CL_CL is '������'
comment on column CL_CL.CL_ID is '����id'
comment on column CL_CL.CPH is '���ƺ�'
comment on column CL_CL.JGDM is '��������'
comment on column CL_CL.JGMC is '��������'
comment on column CL_CL.CX is '����  �ֵ��ZDCLK0019���������� 10��С�� 20���� 30��У��'
comment on column CL_CL.ZKL is '�ؿ���'
comment on column CL_CL.DL is '�ȼ�'
comment on column CL_CL.CJSJ is '����ʱ��'
comment on column CL_CL.CJR is '������'
comment on column CL_CL.XGSJ is '�޸�ʱ��'
comment on column CL_CL.XGR is '�޸���'
comment on column CL_CL.SJ_ID is '˾��id'
comment on column CL_CL.SJXM is '˾������'
comment on column CL_CL.ZT is '״̬ �ֵ��ZDCLK0016  00������ 10��ͣ��'
comment on column CL_CL.TP is 'ͼƬ'
comment on column CL_CL.SCS is '������'
comment on column CL_CL.XH is '�ͺ�'
comment on column CL_CL.ZDBH is '�ն˱��'
comment on column CL_CL.CCDJRQ is '���εǼ�����'
comment on column CL_CL.CDBH is '���ӱ��'
comment on column CL_CL.BXGSMC is '���չ�˾����'
comment on column CL_CL.BXSJ is '����ʱ��'
comment on column CL_CL.NSSJ is '����ʱ��'
comment on column CL_CL.OBD_CODE is 'OBD���'
comment on column CL_CL.SDSX is '�ٶ�����'
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
comment on table CL_CLYXJL is '�������м�¼��'
comment on column CL_CLYXJL.ID is 'ID'
comment on column CL_CLYXJL.CL_ID is '����ID'
comment on column CL_CLYXJL.CPHM is '���ƺ���'
comment on column CL_CLYXJL.ZDBH is 'վ����'
comment on column CL_CLYXJL.CJSJ is '����ʱ��'
comment on column CL_CLYXJL.JD is '����'
comment on column CL_CLYXJL.WD is 'γ��'
comment on column CL_CLYXJL.ZDJL is 'վ�����'
comment on column CL_CLYXJL.ZD_ID is 'վ��ID'
comment on column CL_CLYXJL.ZDMC is 'վ������'
comment on column CL_CLYXJL.XL_ID is '��·ID'
comment on column CL_CLYXJL.XLMC is '��·����'
comment on column CL_CLYXJL.YXFX is '���з���'
comment on column CL_CLYXJL.JID is '����'
comment on column CL_CLYXJL.ZT is '״̬(inStation��վ��runing����  off����)'
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
comment on table CL_CSSD is '�������ٱ�'
comment on column CL_CSSD.ID is 'id'
comment on column CL_CSSD.CPH is '����'
comment on column CL_CSSD.SDSX is '�ٶ�����'
comment on column CL_CSSD.CJSJ is '����ʱ��'
comment on column CL_CSSD.CJR is '������'
comment on column CL_CSSD.XGSJ is '�޸�ʱ��'
comment on column CL_CSSD.XGR is '�޸���'
comment on column CL_CSSD.JGDM is '��������'
comment on column CL_CSSD.JGMC is '��������'
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
comment on table CL_DD is 'ƽ̨������'
comment on column CL_DD.ID is 'ID'
comment on column CL_DD.SJQRSJ is '˾��ȷ��ʱ��'
comment on column CL_DD.HCDZ is '�򳵵�ַ'
comment on column CL_DD.MDD is 'Ŀ�ĵ�'
comment on column CL_DD.CPH is '���ƺ�'
comment on column CL_DD.SJ is '˾��'
comment on column CL_DD.SJXM is '˾������'
comment on column CL_DD.ZJ is '�ܼ�'
comment on column CL_DD.SC is 'ʱ��'
comment on column CL_DD.DJ is '����'
comment on column CL_DD.LC is '���'
comment on column CL_DD.SCF is 'ʱ����'
comment on column CL_DD.LCF is '��̷�'
comment on column CL_DD.CK is '�˿�����'
comment on column CL_DD.CKZW is '�˿�ְ��'
comment on column CL_DD.CKLXDH is '�˿���ϵ�绰'
comment on column CL_DD.ZWS is '��λ��'
comment on column CL_DD.CKLX is '�˿�����'
comment on column CL_DD.YYSJ is 'ԤԼʱ��'
comment on column CL_DD.DDZT is '����״̬'
comment on column CL_DD.FKZT is '����״̬'
comment on column CL_DD.FKFS is '���ʽ ������Դ [ZDCLK0043]  10��������  20�������  30�Է�'
comment on column CL_DD.FKSJ is '����ʱ��'
comment on column CL_DD.PJDJ is '���۵ȼ�'
comment on column CL_DD.PJNR is '��������'
comment on column CL_DD.CJSJ is '����ʱ��'
comment on column CL_DD.CJR is '������'
comment on column CL_DD.XGSJ is '�޸�ʱ��'
comment on column CL_DD.XGR is '�޸���'
comment on column CL_DD.JGDM is '��������'
comment on column CL_DD.JGMC is '��������'
comment on column CL_DD.CL_ID is '����id'
comment on column CL_DD.GLF is '��·��'
comment on column CL_DD.SY is '����'
comment on column CL_DD.CLLX is '�������� �ֵ��ZDCLK0019���������� 10��С�� 20���� 30��У��'
comment on column CL_DD.WF is '����		�������� [ZDCLK0042] 10���� 20����'
comment on column CL_DD.DZQRSJ is '�ӳ�ȷ��ʱ��'
comment on column CL_DD.SHSJ is '���ʱ��'
comment on column CL_DD.DZXM is '�ӳ�����'
comment on column CL_DD.FKBZ is '���ע'
comment on column CL_DD.SJ_SX is '���ԣ��ֵ���(ZDCLK0039) 10:�ڲ�˾��������CL_JSY�� 11���ⲿ����������ʱ����'
comment on column CL_DD.DZBH is '�ӳ����'
comment on column CL_DD.CDBH is '���ӱ��'
comment on column CL_DD.ZDBH is '�ն˱��'
comment on column CL_DD.BHYY is '����ԭ��'
comment on column CL_DD.KTCODE is '������  �����ֵ� [ZDCLK0045]'
comment on column CL_DD.CK_CJL is '�˿ʹ����ˣ�����������ɳ˿ʹ���ʱ��������д�����������'
comment on column CL_DD.GQF is '���ŷ�'
comment on column CL_DD.JBSC is '�Ӱ�ʱ��'
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
comment on table CL_DDRZ is '�������ڱ�(����������־��)'
comment on column CL_DDRZ.ID is 'ID'
comment on column CL_DDRZ.DD_ID is '����id'
comment on column CL_DDRZ.CJSJ is '����ʱ��'
comment on column CL_DDRZ.CJR is '������'
comment on column CL_DDRZ.BZ is '��ע'
comment on column CL_DDRZ.JGDM is '��������'
comment on column CL_DDRZ.JGMC is '��������'
comment on column CL_DDRZ.DDZT is '����״̬'
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
comment on table CL_DZWL is '����Χ����'
comment on column CL_DZWL.ID is 'ID'
comment on column CL_DZWL.WLMC is 'Χ������'
comment on column CL_DZWL.JGDM is '��������'
comment on column CL_DZWL.JGMC is '��������'
comment on column CL_DZWL.MJ is '���'
comment on column CL_DZWL.DLXXZB is '������Ϣ����'
comment on column CL_DZWL.KSJD is '��ʼ����'
comment on column CL_DZWL.KSWD is '��ʼγ��'
comment on column CL_DZWL.ZT is '״̬'
comment on column CL_DZWL.CJSJ is '����ʱ��'
comment on column CL_DZWL.CJR is '������'
comment on column CL_DZWL.XGSJ is '�޸�ʱ��'
comment on column CL_DZWL.XGR is '�޸���'
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
comment on table CL_DZWL_CL is '����Χ��������'
comment on column CL_DZWL_CL.ID is 'ID'
comment on column CL_DZWL_CL.WL_ID is 'Χ��id'
comment on column CL_DZWL_CL.CL_ID is '����id'
comment on column CL_DZWL_CL.CPH is '���ƺ�'
comment on column CL_DZWL_CL.CJSJ is '����ʱ��'
comment on column CL_DZWL_CL.CJR is '������'
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
comment on column CL_GPS.ZDBH is '�ն˱��'
comment on column CL_GPS.LX is '����'
comment on column CL_GPS.JD is '����'
comment on column CL_GPS.WD is 'γ��'
comment on column CL_GPS.GGJD is '�ȸ辭��'
comment on column CL_GPS.GGWD is '�ȸ�γ��'
comment on column CL_GPS.BDJD is '�ٶȾ���'
comment on column CL_GPS.BDWD is '�ٶ�γ��'
comment on column CL_GPS.GDJD is '�ߵ¾���'
comment on column CL_GPS.GDWD is '�ߵ�γ��'
comment on column CL_GPS.CJSJ is '����ʱ��'
comment on column CL_GPS.GXSJ is '����ʱ��'
comment on column CL_GPS.DWJD is '��λ����'
comment on column CL_GPS.FXJ is '�����'
comment on column CL_GPS.YXSD is '�����ٶ�'
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
comment on column CL_GPS_LS.ZDBH is '�ն˱��'
comment on column CL_GPS_LS.CJSJ is '����ʱ��'
comment on column CL_GPS_LS.JD is '����'
comment on column CL_GPS_LS.WD is 'γ��'
comment on column CL_GPS_LS.GGJD is '�ȸ辭��'
comment on column CL_GPS_LS.GGWD is '�ȸ�γ��'
comment on column CL_GPS_LS.BDJD is '�ٶȾ���'
comment on column CL_GPS_LS.BDWD is '�ٶ�γ��'
comment on column CL_GPS_LS.GDJD is '�ߵ¾���'
comment on column CL_GPS_LS.GDWD is '�ߵ�γ��'
comment on column CL_GPS_LS.LX is '����'
comment on column CL_GPS_LS.DWJD is '��λ����'
comment on column CL_GPS_LS.FXJ is '�����'
comment on column CL_GPS_LS.YXSD is '�����ٶ�'
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
comment on column CL_JSY.SFZHM is '���֤����'
comment on column CL_JSY.XM is '����'
comment on column CL_JSY.JGDM is '��������'
comment on column CL_JSY.XB is '�Ա�'
comment on column CL_JSY.NL is '����'
comment on column CL_JSY.JL is '����'
comment on column CL_JSY.ZT is '״̬ �ֵ��ZDCLK0018 	(00���ڰ� 10����Ϣ)'
comment on column CL_JSY.TX is 'ͷ��'
comment on column CL_JSY.ZJCX is '׼�ݳ���'
comment on column CL_JSY.DJ is '�ȼ�'
comment on column CL_JSY.CJSJ is '����ʱ��'
comment on column CL_JSY.CJR is '������'
comment on column CL_JSY.XGSJ is '�޸�ʱ��'
comment on column CL_JSY.XGR is '�޸���'
comment on column CL_JSY.CDBH is '���ӱ��'
comment on column CL_JSY.DABH is '�������'
comment on column CL_JSY.CLRQ is '��������'
comment on column CL_JSY.SJH is '��ʻԱ�ֻ���'
comment on column CL_JSY.PWD is '΢�ŵ�½����'
comment on column CL_JSY.XX is 'Ѫ��'
comment on column CL_JSY.JKZK is '����״��'
comment on column CL_JSY.JZYXQ is '������Ч��'
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
comment on column CL_LSC.CPH is '���ƺ�'
comment on column CL_LSC.CJR is '������'
comment on column CL_LSC.DJCS is '�ǼǴ���'
comment on column CL_LSC.LSDW_ID is '��ʱ��λid'
comment on column CL_LSC.LSDWMC is '��ʱ��λ����'
comment on column CL_LSC.CLLX is '��������'
comment on column CL_LSC.ZWS is '��λ��'
comment on column CL_LSC.ZT is '״̬'
comment on column CL_LSC.CL_ID is '����id'
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
comment on column CL_LSDW.DWMC is '��λ����'
comment on column CL_LSDW.CJR is '������'
comment on column CL_LSDW.CJSJ is '����ʱ��'
comment on column CL_LSDW.DJCS is '�ǼǴ���'
comment on column CL_LSDW.XGR is '�޸���'
comment on column CL_LSDW.XGSJ is '�޸�ʱ��'
comment on column CL_LSDW.DWBH is '��λ���'
comment on column CL_LSDW.ZT is '״̬'
comment on column CL_LSDW.LXR is '��ϵ��'
comment on column CL_LSDW.LXDH is '��ϵ�绰'
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
comment on column CL_PB.CPH is '���ƺ�'
comment on column CL_PB.XL_ID is '��·id'
comment on column CL_PB.PBSJ is '�Ű�ʱ��'
comment on column CL_PB.SJ is '˾��'
comment on column CL_PB.SJXM is '˾������'
comment on column CL_PB.JGDM is '��������'
comment on column CL_PB.JGMC is '��������'
comment on column CL_PB.CL_ID is '����id'
comment on column CL_PB.CJSJ is '����ʱ��'
comment on column CL_PB.CJR is '������'
comment on column CL_PB.XGJ is '�޸�ʱ��'
comment on column CL_PB.XGR is '�޸���'
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
comment on table CL_SBYCSJJL is '�豸�쳣�¼���¼��'
comment on column CL_SBYCSJJL.ID is 'ID'
comment on column CL_SBYCSJJL.ZDBH is '�ն˱��'
comment on column CL_SBYCSJJL.LX is '����'
comment on column CL_SBYCSJJL.CJSJ is '����ʱ��'
comment on column CL_SBYCSJJL.CJR is '������'
comment on column CL_SBYCSJJL.JD is '����'
comment on column CL_SBYCSJJL.WD is 'γ��'
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
comment on table CL_SBYXSJJL is '�豸�����¼���¼��'
comment on column CL_SBYXSJJL.ID is 'ID'
comment on column CL_SBYXSJJL.ZDBH is '�ն˱��'
comment on column CL_SBYXSJJL.SJJB is '�¼�����'
comment on column CL_SBYXSJJL.CJSJ is '����ʱ��'
comment on column CL_SBYXSJJL.JD is '����'
comment on column CL_SBYXSJJL.WD is 'γ��'
comment on column CL_SBYXSJJL.JID is '����'
comment on column CL_SBYXSJJL.SJLX is '�¼�����'
comment on column CL_SBYXSJJL.YXFX is '���з���'
comment on column CL_SBYXSJJL.BZ is '��ע'
comment on column CL_SBYXSJJL.SJXM is '˾������'
comment on column CL_SBYXSJJL.CPH is '���ƺ�'
comment on column CL_SBYXSJJL.CX is '����'
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
comment on column CL_SG.CPH is '���ƺ�'
comment on column CL_SG.CDBH is '���ӱ��'
comment on column CL_SG.JGDM is '��������'
comment on column CL_SG.SJ is '˾��'
comment on column CL_SG.SJXM is '˾������'
comment on column CL_SG.SGDD is '�¹ʵص�'
comment on column CL_SG.SGSJ is '�¹�ʱ��'
comment on column CL_SG.CLJG is '������'
comment on column CL_SG.CLR is '������'
comment on column CL_SG.SGMS is '�¹�����'
comment on column CL_SG.CJSJ is '����ʱ��'
comment on column CL_SG.CJR is '������'
comment on column CL_SG.XGSJ is '�޸�ʱ��'
comment on column CL_SG.XGR is '�޸���'
comment on column CL_SG.CL_ID is '����id'
comment on column CL_SG.CLSJ is '����ʱ��'
comment on column CL_SG.BZ is '��ע'
comment on column CL_SG.LXDH is '��ϵ�绰'
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
comment on column CL_SGWJ.SG_ID is '�¹�id'
comment on column CL_SGWJ.WJLX is '�ļ�����'
comment on column CL_SGWJ.FWQDZ is '��������ַ'
comment on column CL_SGWJ.WLDZ is '�����ַ'
comment on column CL_SGWJ.WJDX is '�ļ���С'
comment on column CL_SGWJ.CJSJ is '����ʱ��'
comment on column CL_SGWJ.CJR is '������'
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
comment on column CL_SPK.WJM is '�ļ���'
comment on column CL_SPK.DZ is '��ַ'
comment on column CL_SPK.ZDBH is '�ն˱��'
comment on column CL_SPK.KSSJ is '��ʼʱ��'
comment on column CL_SPK.JSSJ is '����ʱ��'
comment on column CL_SPK.SC is 'ʱ��'
comment on column CL_SPK.URL is 'url'
comment on column CL_SPK.CJSJ is '����ʱ��'
comment on column CL_SPK.CJR is '������'
comment on column CL_SPK.XGSJ is '�޸�ʱ��'
comment on column CL_SPK.XGR is '�޸���'
comment on column CL_SPK.BJ is '���'
comment on column CL_SPK.SPLY is '��Ƶ��Դ'
comment on column CL_SPK.SPLX is '��Ƶ����'
comment on column CL_SPK.JGDM is '��������'
comment on column CL_SPK.JGMC is '��������'
comment on column CL_SPK.CPH is '���ƺ�'
comment on column CL_SPK.CL_ID is '����id'
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
comment on column CL_XL.XLMC is '��·����'
comment on column CL_XL.XLBH is '��·���'
comment on column CL_XL.CD is '����'
comment on column CL_XL.PJSJ is 'ƽ��ʱ��'
comment on column CL_XL.YXKSSJ is '���п�ʼʱ��'
comment on column CL_XL.YXJSSJ is '���н���ʱ��'
comment on column CL_XL.CJR is '������'
comment on column CL_XL.CJSJ is '����ʱ��'
comment on column CL_XL.XGR is '�޸���'
comment on column CL_XL.XGSJ is '�޸�ʱ��'
comment on column CL_XL.JGDM is '��������'
comment on column CL_XL.JGMC is '��������'
comment on column CL_XL.ZT is '״̬'
comment on column CL_XL.BZ is '��ע'
comment on column CL_XL.YXFS is '���з�ʽ'
comment on column CL_XL.LX is '����'
create table CL_XLCL
(
  ID     VARCHAR2(32) not null
    constraint PK_CL_XLCL
    primary key,
  "��·id" VARCHAR2(32),
  "���ƺ�"  VARCHAR2(10),
  "״̬"   CHAR(2),
  "����ʱ��" TIMESTAMP(6),
  "������"  VARCHAR2(64),
  "�޸�ʱ��" TIMESTAMP(6),
  "�޸���"  VARCHAR2(64),
  "����id" VARCHAR2(32)
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
comment on column CL_XLZD.XL_ID is '��·id'
comment on column CL_XLZD.ZD_ID is 'վ��id'
comment on column CL_XLZD.ZT is '״̬'
comment on column CL_XLZD.CJR is '������'
comment on column CL_XLZD.CJSJ is '����ʱ��'
comment on column CL_XLZD.XGR is '�޸���'
comment on column CL_XLZD.XGSJ is '�޸�ʱ��'
comment on column CL_XLZD.XH is '���'
comment on column CL_XLZD.FX is '����'
comment on column CL_XLZD.YJDZSJ is 'Ԥ�Ƶ�վʱ��'
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
comment on column CL_ZD.DZ is '��ַ'
comment on column CL_ZD.MC is '����'
comment on column CL_ZD.JD is '����'
comment on column CL_ZD.WD is 'γ��'
comment on column CL_ZD.CJSJ is '����ʱ��'
comment on column CL_ZD.CJR is '������'
comment on column CL_ZD.XGSJ is '�޸�ʱ��'
comment on column CL_ZD.XGR is '�޸���'
comment on column CL_ZD.JGDM is '��������'
comment on column CL_ZD.JGMC is '��������'
comment on column CL_ZD.ZT is '״̬'
comment on column CL_ZD.BZ is '��ע'
comment on column CL_ZD.YXFS is '���з�ʽ'
comment on column CL_ZD.FW is '��Χ'
comment on column CL_ZD.LX is '����'
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
comment on table CL_ZDGL is '�ն˹����'
comment on column CL_ZDGL.ZDBH is '�ն˱��'
comment on column CL_ZDGL.XH is '�ͺ�'
comment on column CL_ZDGL.MC is '����'
comment on column CL_ZDGL.CS is '����'
comment on column CL_ZDGL.ZT is '״̬'
comment on column CL_ZDGL.CJR is '������'
comment on column CL_ZDGL.CJSJ is '����ʱ��'
comment on column CL_ZDGL.XGR is '�޸���'
comment on column CL_ZDGL.XGSJ is '�޸�ʱ��'
comment on column CL_ZDGL.ZXZT is '����״̬ ZDCLK0032 (00��� 10 Ϩ�� 20����)'
comment on column CL_ZDGL.ZXSJ is '����ʱ��'
comment on column CL_ZDGL.JSLMD is '����������'
comment on column CL_ZDGL.SCMS is '�����ϴ�ģʽ'
comment on column CL_ZDGL.PZLMD is '��ײ������'
comment on column CL_ZDGL.CMD is '�ϱ���ַ'
comment on column CL_ZDGL.SPSCMS is '�����ϴ�ģʽ'
comment on column CL_ZDGL.GPSXT is 'GPS����'
comment on column CL_ZDGL.SFYY is '�Ƿ��豸�ϴ���ӥ��'
comment on column CL_ZDGL.JGDM is '��������'
comment on column CL_ZDGL.JGMC is '��������'
comment on column CL_ZDGL.VERSION is '�豸�汾��'
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
comment on column CL_ZNZP.ZDBH is '�ն˱��'
comment on column CL_ZNZP.MC is '����'
comment on column CL_ZNZP.XH is '�ͺ�'
comment on column CL_ZNZP.CS is '����'
comment on column CL_ZNZP.DZ is '��ַ'
comment on column CL_ZNZP.CJSJ is '����ʱ��'
comment on column CL_ZNZP.CJR is '������'
comment on column CL_ZNZP.XGSJ is '�޸�ʱ��'
comment on column CL_ZNZP.XGR is '�޸���'
comment on column CL_ZNZP.ZD_ID is 'վ��ID'
comment on column CL_ZNZP.TIPS is '��ע'
comment on column CL_ZNZP.ZXZT is '����״̬ /* 00:���� 10������*/'
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
comment on column CL_ZP_XL.ZP_ID is 'վ��ID'
comment on column CL_ZP_XL.XL_ID is '��·ID'
comment on column CL_ZP_XL.CJSJ is '����ʱ��'
comment on column CL_ZP_XL.CJR is '������'
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
comment on column SYS_CLK_PTJS.JS_ID is '��ɫid'
comment on column SYS_CLK_PTJS.JSMC is '��ɫ����'
comment on column SYS_CLK_PTJS.JSLX is '��ɫ����'
comment on column SYS_CLK_PTJS.CJSJ is '����ʱ��'
comment on column SYS_CLK_PTJS.CJR is '������'
comment on column SYS_CLK_PTJS.XGR is '�޸���'
comment on column SYS_CLK_PTJS.XGSJ is '�޸�ʱ��'
comment on column SYS_CLK_PTJS.ZT is '״̬'
comment on column SYS_CLK_PTJS.JGDM is '��������'
comment on column SYS_CLK_PTJS.SM is '˵��'
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
comment on table SYS_CLK_PTYH is 'ƽ̨�û���'
comment on column SYS_CLK_PTYH.YHID is '�û�id'
comment on column SYS_CLK_PTYH.ZH is '�˺�'
comment on column SYS_CLK_PTYH.MM is '����'
comment on column SYS_CLK_PTYH.SJH is '�ֻ���'
comment on column SYS_CLK_PTYH.CJR is '������'
comment on column SYS_CLK_PTYH.CJSJ is '����ʱ��'
comment on column SYS_CLK_PTYH.XGR is '�޸���'
comment on column SYS_CLK_PTYH.XGSJ is '�޸�ʱ��'
comment on column SYS_CLK_PTYH.ZT is '״̬'
comment on column SYS_CLK_PTYH.JGDM is '��������'
comment on column SYS_CLK_PTYH.XM is '����'
comment on column SYS_CLK_PTYH.LX is '����'
comment on column SYS_CLK_PTYH.XB is '�Ա�'
comment on column SYS_CLK_PTYH.ZJHM is '֤������'
comment on column SYS_CLK_PTYH.MMYXQ is '������Ч��'
comment on column SYS_CLK_PTYH.ZW is 'ְ��'
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
comment on column SYS_FWGN.GNDM is '���ܴ���'
comment on column SYS_FWGN.GNMC is '��������'
comment on column SYS_FWGN.FWDM is '�������'
comment on column SYS_FWGN.CJSJ is '����ʱ��'
comment on column SYS_FWGN.CJR is '������'
comment on column SYS_FWGN.XGSJ is '�޸�ʱ��'
comment on column SYS_FWGN.XGR is '�޸���'
comment on column SYS_FWGN.ZT is '״̬'
comment on column SYS_FWGN.BZ is '��ע'
comment on column SYS_FWGN.URL is 'URL'
comment on column SYS_FWGN.FJD is '���ڵ�'
comment on column SYS_FWGN.TZDZ is '��ת��ַ'
comment on column SYS_FWGN.TB is 'ͼ��'
comment on column SYS_FWGN.API_QZ is 'APIǰ׺'
comment on column SYS_FWGN.API_HZ is 'API��׺'
comment on column SYS_FWGN.PX is '����'
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
comment on table SYS_HDYX is '�Ӫ��'
comment on column SYS_HDYX.HD_ID is '�id'
comment on column SYS_HDYX.KSSJ is '�ʱ�俪ʼ'
comment on column SYS_HDYX.JSSJ is '�ʱ�����'
comment on column SYS_HDYX.HDLX is '�����(00 ΢�š�10����վ��)'
comment on column SYS_HDYX.JGDM is '��������'
comment on column SYS_HDYX.ZT is '״̬(00δ��ʼ 10 �ѿ�ʼ  20 �ѽ���)'
comment on column SYS_HDYX.CJR is '������'
comment on column SYS_HDYX.CJSJ is '����ʱ��'
comment on column SYS_HDYX.XGR is '�޸���'
comment on column SYS_HDYX.XGSJ is '�޸�ʱ��'
comment on column SYS_HDYX.HDBT is '�����'
comment on column SYS_HDYX.URL is 'URL'
comment on column SYS_HDYX.WJLX is '�ļ�����,ͼƬ����Ƶ'
comment on column SYS_HDYX.WZ is '����վ��λ��(10�� 20�� 30��)'
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
comment on table SYS_HSGS is '���㹫ʽ'
comment on column SYS_HSGS.ID is 'ID'
comment on column SYS_HSGS.CX is '����'
comment on column SYS_HSGS.LX is '����'
comment on column SYS_HSGS.NR is '����'
comment on column SYS_HSGS.JE is '���'
comment on column SYS_HSGS.CJR is '������'
comment on column SYS_HSGS.CJSJ is '����ʱ��'
comment on column SYS_HSGS.XGR is '�޸���'
comment on column SYS_HSGS.XGSJ is '�޸�ʱ��'
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
comment on column SYS_JGSQLB.JGDM is '��������'
comment on column SYS_JGSQLB.FWDM is '�������'
comment on column SYS_JGSQLB.GNDM is '���ܴ���'
comment on column SYS_JGSQLB.CJSJ is '����ʱ��'
comment on column SYS_JGSQLB.CJR is '������'
comment on column SYS_JGSQLB.XGSJ is '�޸�ʱ��'
comment on column SYS_JGSQLB.XGR is '�޸���'
comment on column SYS_JGSQLB.YXQ is '��Ч��'
create table SYS_JS_FW
(
  "id" VARCHAR2(32) not null,
  JSDM VARCHAR2(32),
  FWDM VARCHAR2(32),
  CJSJ TIMESTAMP(6),
  CJR  VARCHAR2(64)
)
comment on column SYS_JS_FW."id" is 'id'
comment on column SYS_JS_FW.JSDM is '��ɫ����'
comment on column SYS_JS_FW.FWDM is '�������'
comment on column SYS_JS_FW.CJSJ is '����ʱ��'
comment on column SYS_JS_FW.CJR is '������'
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
comment on column SYS_JS_GN.JSDM is '��ɫ����'
comment on column SYS_JS_GN.GNDM is '���ܴ���'
comment on column SYS_JS_GN.CJSJ is '����ʱ��'
comment on column SYS_JS_GN.CJR is '������'
comment on column SYS_JS_GN.FWDM is '�������'
comment on column SYS_JS_GN.FGNDM is '�����ܴ���'
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
comment on column SYS_JZGXX.XM is '����'
comment on column SYS_JZGXX.XB is '�Ա�'
comment on column SYS_JZGXX.ZW is 'ְ��'
comment on column SYS_JZGXX.ZJHM is '֤������'
comment on column SYS_JZGXX.JGDM is '��������'
comment on column SYS_JZGXX.JDMC is '��������'
comment on column SYS_JZGXX.ZGLX is 'ְ������'
comment on column SYS_JZGXX.CJR is '������'
comment on column SYS_JZGXX.CJSJ is '����ʱ��'
comment on column SYS_JZGXX.XGR is '�޸���'
comment on column SYS_JZGXX.XGSJ is '�޸�ʱ��'
comment on column SYS_JZGXX.SJHM is '�ֻ�����'
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
comment on column SYS_PTFW.FW_ID is '����id'
comment on column SYS_PTFW.FWMC is '��������'
comment on column SYS_PTFW.FWDM is '�������'
comment on column SYS_PTFW.CJSJ is '����ʱ��'
comment on column SYS_PTFW.CJR is '������'
comment on column SYS_PTFW.XGSJ is '�޸�ʱ��'
comment on column SYS_PTFW.XGR is '�޸���'
comment on column SYS_PTFW.ZT is '״̬'
comment on column SYS_PTFW.API_QZ is 'APIǰ׺'
comment on column SYS_PTFW.TB is 'ͼ��'
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
comment on table SYS_PTJG is 'ƽ̨������'
comment on column SYS_PTJG.JGDM is '��������'
comment on column SYS_PTJG.JGMC is '��������'
comment on column SYS_PTJG.JGLX is '��������'
comment on column SYS_PTJG.ZT is '״̬'
comment on column SYS_PTJG.CJSJ is '����ʱ��'
comment on column SYS_PTJG.CJR is '������'
comment on column SYS_PTJG.XGR is '�޸���'
comment on column SYS_PTJG.XGSJ is '�޸�ʱ��'
comment on column SYS_PTJG.GLY is '����Ա'
comment on column SYS_PTJG.GLYXM is '����Ա����'
comment on column SYS_PTJG.FJGDM is '����������'
comment on column SYS_PTJG.BZ is '��ע'
comment on column SYS_PTJG.JGDJ is '�����ȼ�'
comment on column SYS_PTJG.JGBM is '��������'
comment on column SYS_PTJG.JGSM is '����˵��'
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
comment on table SYS_PTRZ is 'ƽ̨��־��'
comment on column SYS_PTRZ.RZ_ID is '��־id'
comment on column SYS_PTRZ.CZLX is '��������'
comment on column SYS_PTRZ.CZSJ is '����ʱ��'
comment on column SYS_PTRZ.CZR is '������'
comment on column SYS_PTRZ.DX_ID is '����id'
comment on column SYS_PTRZ.DXLX is '��������'
comment on column SYS_PTRZ.CS is '����'
comment on column SYS_PTRZ.JG is '���'
comment on column SYS_PTRZ.ZXSJ is 'ִ��ʱ��'
comment on column SYS_PTRZ.SM is '˵��'
comment on column SYS_PTRZ.FF is '����'
create table SYS_RLB
(
  RQDM VARCHAR2(8) not null
    constraint PK_SYS_RLB
    primary key,
  XQ   NUMBER,
  ZT   CHAR(2),
  RQ   VARCHAR2(16)
)
comment on table SYS_RLB is '������'
comment on column SYS_RLB.RQDM is '���ڴ���'
comment on column SYS_RLB.XQ is '����'
comment on column SYS_RLB.ZT is '״̬'
comment on column SYS_RLB.RQ is '����'
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
comment on column SYS_WXYH.XM is '����'
comment on column SYS_WXYH.DH is '�绰'
comment on column SYS_WXYH.YHLX is '�û�����'
comment on column SYS_WXYH.XY is 'ѧԺ'
comment on column SYS_WXYH.ZJHM is '֤������'
comment on column SYS_WXYH.ZW is 'ְ��'
comment on column SYS_WXYH.CJSJ is '����ʱ��'
comment on column SYS_WXYH.CJR is '������'
comment on column SYS_WXYH.XGSJ is '�޸�ʱ��'
comment on column SYS_WXYH.XGR is '�޸���'
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
comment on column SYS_XXTS.XX_ID is '��Ϣid'
comment on column SYS_XXTS.XXNR is '��Ϣ����'
comment on column SYS_XXTS.CJSJ is '����ʱ��'
comment on column SYS_XXTS.CJR is '������'
comment on column SYS_XXTS.XGSJ is '�޸�ʱ��'
comment on column SYS_XXTS.XGR is '�޸���'
comment on column SYS_XXTS.KJFW is '�ɼ���Χ'
comment on column SYS_XXTS.KSSJ is '��ʼʱ��'
comment on column SYS_XXTS.JSSJ is '����ʱ��'
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
comment on column SYS_YH_JS.YHJS_ID is '�û���ɫ������id'
comment on column SYS_YH_JS.YH_ID is '�û�id'
comment on column SYS_YH_JS.JS_ID is '��ɫid'
comment on column SYS_YH_JS.CJR is '������'
comment on column SYS_YH_JS.CJSJ is '����ʱ��'
create table SYS_YH_XX
(
  "id"  VARCHAR2(32) not null,
  YH_ID VARCHAR2(32),
  XX_ID VARCHAR2(32),
  CJSJ  TIMESTAMP(6),
  ZT    CHAR(2)
)
comment on column SYS_YH_XX."id" is 'id'
comment on column SYS_YH_XX.YH_ID is '�û�id'
comment on column SYS_YH_XX.XX_ID is '��Ϣid'
comment on column SYS_YH_XX.CJSJ is '����ʱ��'
comment on column SYS_YH_XX.ZT is '״̬'
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
comment on column SYS_YJFK.YJ_ID is '���id'
comment on column SYS_YJFK.YH_ID is '�û�id'
comment on column SYS_YJFK.NR is '����'
comment on column SYS_YJFK.CJSJ is '����ʱ��'
comment on column SYS_YJFK.CJR is '������'
comment on column SYS_YJFK.ZT is '״̬'
comment on column SYS_YJFK.YJLX is '�������'
comment on column SYS_YJFK.CLJG is '������'
comment on column SYS_YJFK.LXRXM is '��ϵ������'
comment on column SYS_YJFK.LXFS is '��ϵ��ʽ'
comment on column SYS_YJFK.XGR is '�޸���'
comment on column SYS_YJFK.XGSJ is '�޸�ʱ��'
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
comment on column SYS_YXHDWJ.HD_ID is '�id'
comment on column SYS_YXHDWJ.WJLX is '�ļ�����'
comment on column SYS_YXHDWJ.WJLJ is '�ļ�·��'
comment on column SYS_YXHDWJ.WLLJ is '����·��'
comment on column SYS_YXHDWJ.CJSJ is '����ʱ��'
comment on column SYS_YXHDWJ.CJR is '������'
comment on column SYS_YXHDWJ.XGSJ is '�޸�ʱ��'
comment on column SYS_YXHDWJ.XGR is '�޸���'
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
comment on table SYS_ZDLM is 'ƽ̨�ֵ���Ŀ��(�ֵ����)'
comment on column SYS_ZDLM.LMDM is '��Ŀ����'
comment on column SYS_ZDLM.LMMC is '��Ŀ����'
comment on column SYS_ZDLM.CJSJ is '����ʱ��'
comment on column SYS_ZDLM.CJR is '������'
comment on column SYS_ZDLM.XGSJ is '�޸�ʱ��'
comment on column SYS_ZDLM.XGR is '�޸���'
comment on column SYS_ZDLM.QZ is 'Ȩ��'
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
comment on table SYS_ZDXM is 'ƽ̨�ֵ��'
comment on column SYS_ZDXM.ZD_ID is '�ֵ�id'
comment on column SYS_ZDXM.ZDLMDM is '�ֵ���Ŀ����'
comment on column SYS_ZDXM.ZDDM is '�ֵ����'
comment on column SYS_ZDXM.ZDMC is '�ֵ�����'
comment on column SYS_ZDXM.QZ is 'Ȩ��'
comment on column SYS_ZDXM.CJSJ is '����ʱ��'
comment on column SYS_ZDXM.CJR is '������'
comment on column SYS_ZDXM.BY1 is '����1'
comment on column SYS_ZDXM.BY2 is '����2'
comment on column SYS_ZDXM.BY3 is '����3'
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
comment on table CL_DDLSB is '������ʷ��(ԭʼ���ݱ�)'
comment on column CL_DDLSB.ID is 'ID'
comment on column CL_DDLSB.SJQRSJ is '˾��ȷ��ʱ��'
comment on column CL_DDLSB.HCDZ is '�򳵵�ַ'
comment on column CL_DDLSB.MDD is 'Ŀ�ĵ�'
comment on column CL_DDLSB.CPH is '���ƺ�'
comment on column CL_DDLSB.SJ is '˾��'
comment on column CL_DDLSB.SJXM is '˾������'
comment on column CL_DDLSB.ZJ is '�ܼ�'
comment on column CL_DDLSB.SC is 'ʱ��'
comment on column CL_DDLSB.DJ is '����'
comment on column CL_DDLSB.LC is '���'
comment on column CL_DDLSB.SCF is 'ʱ����'
comment on column CL_DDLSB.LCF is '��̷�'
comment on column CL_DDLSB.CK is '�˿�����'
comment on column CL_DDLSB.CKZW is '�˿�ְ��'
comment on column CL_DDLSB.CKLXDH is '�˿���ϵ�绰'
comment on column CL_DDLSB.ZWS is '��λ��'
comment on column CL_DDLSB.CKLX is '�˿�����'
comment on column CL_DDLSB.YYSJ is 'ԤԼʱ��'
comment on column CL_DDLSB.DDZT is '����״̬'
comment on column CL_DDLSB.FKZT is '����״̬'
comment on column CL_DDLSB.FKFS is '���ʽ'
comment on column CL_DDLSB.FKSJ is '����ʱ��'
comment on column CL_DDLSB.PJDJ is '���۵ȼ�'
comment on column CL_DDLSB.PJNR is '��������'
comment on column CL_DDLSB.CJSJ is '����ʱ��'
comment on column CL_DDLSB.CJR is '������'
comment on column CL_DDLSB.XGSJ is '�޸�ʱ��'
comment on column CL_DDLSB.XGR is '�޸���'
comment on column CL_DDLSB.JGDM is '��������'
comment on column CL_DDLSB.JGMC is '��������'
comment on column CL_DDLSB.CL_ID is '����id'
comment on column CL_DDLSB.GLF is '��·��'
comment on column CL_DDLSB.SY is '����'
comment on column CL_DDLSB.CLLX is '��������'
comment on column CL_DDLSB.WF is '����'
comment on column CL_DDLSB.DZQRSJ is '�ӳ�ȷ��ʱ��'
comment on column CL_DDLSB.SHSJ is '���ʱ��'
comment on column CL_DDLSB.DZXM is '�ӳ�����'
comment on column CL_DDLSB.FKBZ is '���ע'
comment on column CL_DDLSB.DD_ID is '����ID'
comment on column CL_DDLSB.XGCS is '�޸Ĵ���'
comment on column CL_DDLSB.DDXGSJ is '�����޸�ʱ��'
comment on column CL_DDLSB.DDCJSJ is '��������ʱ��'
comment on column CL_DDLSB.DDXGR is '�ٶ�������'
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
comment on column "SYS_FWGN_copy".GNDM is '���ܴ���'
comment on column "SYS_FWGN_copy".GNMC is '��������'
comment on column "SYS_FWGN_copy".FWDM is '�������'
comment on column "SYS_FWGN_copy".CJSJ is '����ʱ��'
comment on column "SYS_FWGN_copy".CJR is '������'
comment on column "SYS_FWGN_copy".XGSJ is '�޸�ʱ��'
comment on column "SYS_FWGN_copy".XGR is '�޸���'
comment on column "SYS_FWGN_copy".ZT is '״̬'
comment on column "SYS_FWGN_copy".BZ is '��ע'
comment on column "SYS_FWGN_copy".URL is 'URL'
comment on column "SYS_FWGN_copy".FJD is '���ڵ�'
comment on column "SYS_FWGN_copy".TZDZ is '��ת��ַ'
comment on column "SYS_FWGN_copy".TB is 'ͼ��'
comment on column "SYS_FWGN_copy".API_QZ is 'APIǰ׺'
comment on column "SYS_FWGN_copy".API_HZ is 'API��׺'
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
comment on table SYS_MESSAGE is '��Ϣ��-ƽ̨������Ϣ����'
comment on column SYS_MESSAGE.ID is '����'
comment on column SYS_MESSAGE.CREATION_TIME is '����ʱ��'
comment on column SYS_MESSAGE.MESSAGE is '���ű��⣬��ҵ��ͬ��Ҳ�������ģ�Ҳ����JSON����'
comment on column SYS_MESSAGE.TYPE is '1������'
comment on column SYS_MESSAGE.TITLE is '����'
comment on column SYS_MESSAGE.STATUS is '0��δ���� 1���ѷ���  2������ʧ��'
comment on column SYS_MESSAGE.SEND_COUNT is '���ʹ���'
comment on column SYS_MESSAGE.SEND_TIME is '����ʱ��'
comment on column SYS_MESSAGE.REMARK is '��ע'
comment on column SYS_MESSAGE.SENDEE_CODE is '���շ����(�������ֻ��š�΢����open_id)'
comment on column SYS_MESSAGE.BIZ_ID is 'ҵ����(����ҵ��) (���ű���ģ��ID)'
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
comment on table CL_BXJZ is '��������'
comment on column CL_BXJZ.ID is 'ID'
comment on column CL_BXJZ.BXR is '������'
comment on column CL_BXJZ.BXSX is '��������'
comment on column CL_BXJZ.BXSJ is '����ʱ��'
comment on column CL_BXJZ.BXJE is '�������'
comment on column CL_BXJZ.FPSL is '��Ʊ����'
comment on column CL_BXJZ.CJR is '������'
comment on column CL_BXJZ.CJSJ is '����ʱ��'
comment on column CL_BXJZ.JGDM is '��������'
comment on column CL_BXJZ.JGMC is '��������'
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
comment on column CL_YY.ID is '����'
comment on column CL_YY.ZDBH is '�ն˱�Ŷ�Ӧ�İٶ�ӥ��entityname'
comment on column CL_YY.LONGITUDE is '����'
comment on column CL_YY.LATITUDE is 'γ��'
comment on column CL_YY.LOCTIME is '�ϴ��ĵ�λʱ��'
comment on column CL_YY.OBJECTKEY is 'Ԥ���ֶ�'
comment on column CL_YY.DIRECTION is '�����'
comment on column CL_YY.SPEED is '�ٶ�'
create table CL_XC
(
  ID           VARCHAR2(32) not null
    primary key,
  XC_KSSJ      VARCHAR2(32),
  XC_JSSJ      VARCHAR2(32),
  CL_ZDBH      VARCHAR2(32),
  XC_START_END VARCHAR2(64)
)
comment on table CL_XC is '�����г̱�'
comment on column CL_XC.XC_KSSJ is '�����г̿�ʼʱ��'
comment on column CL_XC.XC_JSSJ is'�г̽���ʱ��'
comment on column CL_XC.CL_ZDBH is '�ն˱��'
comment on column CL_XC.XC_START_END is '�г̵Ŀ�ʼ�ͽ�����'
