prompt PL/SQL Developer import file
prompt Created on 2018��9��18�� by Lee
set feedback off
set define off
prompt Creating CL_BXJZ...
create table CL_BXJZ
(
  id   VARCHAR2(32),
  bxr  VARCHAR2(32),
  bxsx VARCHAR2(255),
  bxsj TIMESTAMP(6),
  bxje FLOAT,
  fpsl NUMBER,
  cjr  VARCHAR2(64),
  cjsj TIMESTAMP(6),
  jgdm VARCHAR2(32),
  jgmc VARCHAR2(32)
)
;
comment on table CL_BXJZ
  is '��������';
comment on column CL_BXJZ.id
  is 'ID';
comment on column CL_BXJZ.bxr
  is '������';
comment on column CL_BXJZ.bxsx
  is '��������';
comment on column CL_BXJZ.bxsj
  is '����ʱ��';
comment on column CL_BXJZ.bxje
  is '�������';
comment on column CL_BXJZ.fpsl
  is '��Ʊ����';
comment on column CL_BXJZ.cjr
  is '������';
comment on column CL_BXJZ.cjsj
  is '����ʱ��';
comment on column CL_BXJZ.jgdm
  is '��������';
comment on column CL_BXJZ.jgmc
  is '��������';

prompt Creating CL_CD...
create table CL_CD
(
  cdbh VARCHAR2(32) not null,
  cdmc VARCHAR2(32),
  dzbh VARCHAR2(32),
  dzxm VARCHAR2(32),
  jgdm VARCHAR2(20),
  jgmc VARCHAR2(32),
  zt   CHAR(2),
  cjsj TIMESTAMP(6),
  cjr  VARCHAR2(64),
  xgsj TIMESTAMP(6),
  xgr  VARCHAR2(64),
  sjhm VARCHAR2(20)
)
;
comment on table CL_CD
  is '���ӱ�';
comment on column CL_CD.cdbh
  is '���ӱ��';
comment on column CL_CD.cdmc
  is '��������';
comment on column CL_CD.dzbh
  is '�ӳ����';
comment on column CL_CD.dzxm
  is '�ӳ�����';
comment on column CL_CD.jgdm
  is '��������';
comment on column CL_CD.jgmc
  is '��������';
comment on column CL_CD.zt
  is '״̬';
comment on column CL_CD.cjsj
  is '����ʱ��';
comment on column CL_CD.cjr
  is '������';
comment on column CL_CD.xgsj
  is '�޸�ʱ��';
comment on column CL_CD.xgr
  is '�޸���';
comment on column CL_CD.sjhm
  is '�ֻ�����';
alter table CL_CD
  add constraint PK_CL_CD primary key (CDBH);

prompt Creating CL_CL...
create table CL_CL
(
  cl_id    VARCHAR2(32) not null,
  cph      VARCHAR2(10),
  jgdm     VARCHAR2(20),
  jgmc     VARCHAR2(32),
  cx       CHAR(2),
  zkl      NUMBER,
  dl       CHAR(2),
  cjsj     TIMESTAMP(6),
  cjr      VARCHAR2(64),
  xgsj     TIMESTAMP(6),
  xgr      VARCHAR2(64),
  sj_id    VARCHAR2(32),
  sjxm     VARCHAR2(32),
  zt       CHAR(2),
  tp       VARCHAR2(255),
  scs      VARCHAR2(16),
  xh       VARCHAR2(16),
  zdbh     VARCHAR2(32),
  ccdjrq   TIMESTAMP(6),
  cdbh     VARCHAR2(32),
  bxgsmc   VARCHAR2(32),
  bxsj     TIMESTAMP(6),
  nssj     TIMESTAMP(6),
  obd_code VARCHAR2(32),
  sdsx     NUMBER default 0
)
;
comment on table CL_CL
  is '������';
comment on column CL_CL.cl_id
  is '����id';
comment on column CL_CL.cph
  is '���ƺ�';
comment on column CL_CL.jgdm
  is '��������';
comment on column CL_CL.jgmc
  is '��������';
comment on column CL_CL.cx
  is '����  �ֵ��ZDCLK0019���������� 10��С�� 20���� 30��У��';
comment on column CL_CL.zkl
  is '�ؿ���';
comment on column CL_CL.dl
  is '�ȼ�';
comment on column CL_CL.cjsj
  is '����ʱ��';
comment on column CL_CL.cjr
  is '������';
comment on column CL_CL.xgsj
  is '�޸�ʱ��';
comment on column CL_CL.xgr
  is '�޸���';
comment on column CL_CL.sj_id
  is '˾��id';
comment on column CL_CL.sjxm
  is '˾������';
comment on column CL_CL.zt
  is '״̬ �ֵ��ZDCLK0016  00������ 10��ͣ��';
comment on column CL_CL.tp
  is 'ͼƬ';
comment on column CL_CL.scs
  is '������';
comment on column CL_CL.xh
  is '�ͺ�';
comment on column CL_CL.zdbh
  is '�ն˱��';
comment on column CL_CL.ccdjrq
  is '���εǼ�����';
comment on column CL_CL.cdbh
  is '���ӱ��';
comment on column CL_CL.bxgsmc
  is '���չ�˾����';
comment on column CL_CL.bxsj
  is '����ʱ��';
comment on column CL_CL.nssj
  is '����ʱ��';
comment on column CL_CL.obd_code
  is 'OBD���';
comment on column CL_CL.sdsx
  is '�ٶ�����';
alter table CL_CL
  add constraint PK_CL_CL primary key (CL_ID);

prompt Creating CL_CLYXJL...
create table CL_CLYXJL
(
  id    VARCHAR2(32) not null,
  cl_id VARCHAR2(32),
  cphm  VARCHAR2(32),
  zdbh  VARCHAR2(32),
  cjsj  TIMESTAMP(6),
  jd    NUMBER,
  wd    NUMBER,
  zdjl  NUMBER,
  zd_id VARCHAR2(32),
  zdmc  VARCHAR2(32),
  xl_id VARCHAR2(32),
  xlmc  VARCHAR2(32),
  yxfx  CHAR(2),
  jid   NUMBER,
  zt    VARCHAR2(20)
)
;
comment on table CL_CLYXJL
  is '�������м�¼��';
comment on column CL_CLYXJL.id
  is 'ID';
comment on column CL_CLYXJL.cl_id
  is '����ID';
comment on column CL_CLYXJL.cphm
  is '���ƺ���';
comment on column CL_CLYXJL.zdbh
  is 'վ����';
comment on column CL_CLYXJL.cjsj
  is '����ʱ��';
comment on column CL_CLYXJL.jd
  is '����';
comment on column CL_CLYXJL.wd
  is 'γ��';
comment on column CL_CLYXJL.zdjl
  is 'վ�����';
comment on column CL_CLYXJL.zd_id
  is 'վ��ID';
comment on column CL_CLYXJL.zdmc
  is 'վ������';
comment on column CL_CLYXJL.xl_id
  is '��·ID';
comment on column CL_CLYXJL.xlmc
  is '��·����';
comment on column CL_CLYXJL.yxfx
  is '���з���';
comment on column CL_CLYXJL.jid
  is '����';
comment on column CL_CLYXJL.zt
  is '״̬(inStation��վ��runing����  off����)';
alter table CL_CLYXJL
  add constraint PK_CL_CLYXJL primary key (ID);

prompt Creating CL_CSSD...
create table CL_CSSD
(
  id   VARCHAR2(32) not null,
  cph  VARCHAR2(64),
  sdsx NUMBER,
  cjsj TIMESTAMP(6),
  cjr  VARCHAR2(64),
  xgsj TIMESTAMP(6),
  xgr  VARCHAR2(64),
  jgdm VARCHAR2(20),
  jgmc VARCHAR2(32)
)
;
comment on table CL_CSSD
  is '�������ٱ�';
comment on column CL_CSSD.id
  is 'id';
comment on column CL_CSSD.cph
  is '����';
comment on column CL_CSSD.sdsx
  is '�ٶ�����';
comment on column CL_CSSD.cjsj
  is '����ʱ��';
comment on column CL_CSSD.cjr
  is '������';
comment on column CL_CSSD.xgsj
  is '�޸�ʱ��';
comment on column CL_CSSD.xgr
  is '�޸���';
comment on column CL_CSSD.jgdm
  is '��������';
comment on column CL_CSSD.jgmc
  is '��������';
alter table CL_CSSD
  add constraint PK_CL_CSSD primary key (ID);

prompt Creating CL_DD...
create table CL_DD
(
  id              VARCHAR2(32) not null,
  sjqrsj          TIMESTAMP(6),
  hcdz            VARCHAR2(255),
  mdd             VARCHAR2(255),
  cph             VARCHAR2(10),
  sj              VARCHAR2(32),
  sjxm            VARCHAR2(32),
  zj              FLOAT,
  sc              NUMBER,
  dj              FLOAT,
  lc              FLOAT,
  scf             FLOAT,
  lcf             FLOAT,
  ck              VARCHAR2(16),
  ckzw            CHAR(2),
  cklxdh          VARCHAR2(16),
  zws             NUMBER,
  cklx            CHAR(2),
  yysj            TIMESTAMP(6),
  ddzt            CHAR(2),
  fkzt            CHAR(2),
  fkfs            CHAR(2),
  fksj            TIMESTAMP(6),
  pjdj            NUMBER,
  pjnr            VARCHAR2(255),
  cjsj            TIMESTAMP(6),
  cjr             VARCHAR2(64),
  xgsj            TIMESTAMP(6),
  xgr             VARCHAR2(64),
  jgdm            VARCHAR2(20),
  jgmc            VARCHAR2(32),
  cl_id           VARCHAR2(32),
  glf             FLOAT,
  sy              VARCHAR2(255),
  cllx            CHAR(2),
  wf              CHAR(2),
  dzqrsj          TIMESTAMP(6),
  shsj            TIMESTAMP(6),
  dzxm            VARCHAR2(32),
  fkbz            VARCHAR2(255),
  sj_sx           CHAR(2),
  dzbh            VARCHAR2(32),
  cdbh            VARCHAR2(32),
  zdbh            VARCHAR2(32),
  bhyy            VARCHAR2(255),
  ktcode          CHAR(6),
  ck_cjl          VARCHAR2(32),
  gqf             FLOAT,
  jbsc            NUMBER,
  destination_lng VARCHAR2(255),
  destination_lat VARCHAR2(255),
  origin_lng      VARCHAR2(255),
  origin_lat      VARCHAR2(255)
)
;
comment on table CL_DD
  is 'ƽ̨������';
comment on column CL_DD.id
  is 'ID';
comment on column CL_DD.sjqrsj
  is '˾��ȷ��ʱ��';
comment on column CL_DD.hcdz
  is '�򳵵�ַ';
comment on column CL_DD.mdd
  is 'Ŀ�ĵ�';
comment on column CL_DD.cph
  is '���ƺ�';
comment on column CL_DD.sj
  is '˾��';
comment on column CL_DD.sjxm
  is '˾������';
comment on column CL_DD.zj
  is '�ܼ�';
comment on column CL_DD.sc
  is 'ʱ��';
comment on column CL_DD.dj
  is '����';
comment on column CL_DD.lc
  is '���';
comment on column CL_DD.scf
  is 'ʱ����';
comment on column CL_DD.lcf
  is '��̷�';
comment on column CL_DD.ck
  is '�˿�����';
comment on column CL_DD.ckzw
  is '�˿�ְ��';
comment on column CL_DD.cklxdh
  is '�˿���ϵ�绰';
comment on column CL_DD.zws
  is '��λ��';
comment on column CL_DD.cklx
  is '�˿�����';
comment on column CL_DD.yysj
  is 'ԤԼʱ��';
comment on column CL_DD.ddzt
  is '����״̬';
comment on column CL_DD.fkzt
  is '����״̬';
comment on column CL_DD.fkfs
  is '���ʽ ������Դ [ZDCLK0043]  10��������  20�������  30�Է�';
comment on column CL_DD.fksj
  is '����ʱ��';
comment on column CL_DD.pjdj
  is '���۵ȼ�';
comment on column CL_DD.pjnr
  is '��������';
comment on column CL_DD.cjsj
  is '����ʱ��';
comment on column CL_DD.cjr
  is '������';
comment on column CL_DD.xgsj
  is '�޸�ʱ��';
comment on column CL_DD.xgr
  is '�޸���';
comment on column CL_DD.jgdm
  is '��������';
comment on column CL_DD.jgmc
  is '��������';
comment on column CL_DD.cl_id
  is '����id';
comment on column CL_DD.glf
  is '��·��';
comment on column CL_DD.sy
  is '����';
comment on column CL_DD.cllx
  is '�������� �ֵ��ZDCLK0019���������� 10��С�� 20���� 30��У��';
comment on column CL_DD.wf
  is '����    �������� [ZDCLK0042] 10���� 20����';
comment on column CL_DD.dzqrsj
  is '�ӳ�ȷ��ʱ��';
comment on column CL_DD.shsj
  is '���ʱ��';
comment on column CL_DD.dzxm
  is '�ӳ�����';
comment on column CL_DD.fkbz
  is '���ע';
comment on column CL_DD.sj_sx
  is '���ԣ��ֵ���(ZDCLK0039) 10:�ڲ�˾��������CL_JSY�� 11���ⲿ����������ʱ����';
comment on column CL_DD.dzbh
  is '�ӳ����';
comment on column CL_DD.cdbh
  is '���ӱ��';
comment on column CL_DD.zdbh
  is '�ն˱��';
comment on column CL_DD.bhyy
  is '����ԭ��';
comment on column CL_DD.ktcode
  is '������  �����ֵ� [ZDCLK0045]';
comment on column CL_DD.ck_cjl
  is '�˿ʹ����ˣ�����������ɳ˿ʹ���ʱ��������д�����������';
comment on column CL_DD.gqf
  is '���ŷ�';
comment on column CL_DD.jbsc
  is '�Ӱ�ʱ��';
alter table CL_DD
  add constraint PK_CL_DD primary key (ID);

prompt Creating CL_DDLSB...
create table CL_DDLSB
(
  id     VARCHAR2(32) not null,
  sjqrsj TIMESTAMP(6),
  hcdz   VARCHAR2(255),
  mdd    VARCHAR2(255),
  cph    VARCHAR2(10),
  sj     VARCHAR2(32),
  sjxm   VARCHAR2(32),
  zj     FLOAT,
  sc     NUMBER,
  dj     FLOAT,
  lc     FLOAT,
  scf    FLOAT,
  lcf    FLOAT,
  ck     VARCHAR2(16),
  ckzw   CHAR(2),
  cklxdh VARCHAR2(16),
  zws    NUMBER,
  cklx   CHAR(2),
  yysj   TIMESTAMP(6),
  ddzt   CHAR(2),
  fkzt   CHAR(2),
  fkfs   CHAR(2),
  fksj   TIMESTAMP(6),
  pjdj   NUMBER,
  pjnr   VARCHAR2(255),
  cjsj   TIMESTAMP(6),
  cjr    VARCHAR2(64),
  xgsj   TIMESTAMP(6),
  xgr    VARCHAR2(64),
  jgdm   VARCHAR2(20),
  jgmc   VARCHAR2(32),
  cl_id  VARCHAR2(32),
  glf    FLOAT,
  sy     VARCHAR2(255),
  cllx   CHAR(2),
  wf     CHAR(2),
  dzqrsj TIMESTAMP(6),
  shsj   TIMESTAMP(6),
  dzxm   VARCHAR2(32),
  fkbz   VARCHAR2(255),
  dd_id  VARCHAR2(32),
  xgcs   NUMBER,
  ddxgsj TIMESTAMP(6),
  ddcjsj TIMESTAMP(6),
  ddxgr  VARCHAR2(64)
)
;
comment on table CL_DDLSB
  is '������ʷ��(ԭʼ���ݱ�)';
comment on column CL_DDLSB.id
  is 'ID';
comment on column CL_DDLSB.sjqrsj
  is '˾��ȷ��ʱ��';
comment on column CL_DDLSB.hcdz
  is '�򳵵�ַ';
comment on column CL_DDLSB.mdd
  is 'Ŀ�ĵ�';
comment on column CL_DDLSB.cph
  is '���ƺ�';
comment on column CL_DDLSB.sj
  is '˾��';
comment on column CL_DDLSB.sjxm
  is '˾������';
comment on column CL_DDLSB.zj
  is '�ܼ�';
comment on column CL_DDLSB.sc
  is 'ʱ��';
comment on column CL_DDLSB.dj
  is '����';
comment on column CL_DDLSB.lc
  is '���';
comment on column CL_DDLSB.scf
  is 'ʱ����';
comment on column CL_DDLSB.lcf
  is '��̷�';
comment on column CL_DDLSB.ck
  is '�˿�����';
comment on column CL_DDLSB.ckzw
  is '�˿�ְ��';
comment on column CL_DDLSB.cklxdh
  is '�˿���ϵ�绰';
comment on column CL_DDLSB.zws
  is '��λ��';
comment on column CL_DDLSB.cklx
  is '�˿�����';
comment on column CL_DDLSB.yysj
  is 'ԤԼʱ��';
comment on column CL_DDLSB.ddzt
  is '����״̬';
comment on column CL_DDLSB.fkzt
  is '����״̬';
comment on column CL_DDLSB.fkfs
  is '���ʽ';
comment on column CL_DDLSB.fksj
  is '����ʱ��';
comment on column CL_DDLSB.pjdj
  is '���۵ȼ�';
comment on column CL_DDLSB.pjnr
  is '��������';
comment on column CL_DDLSB.cjsj
  is '����ʱ��';
comment on column CL_DDLSB.cjr
  is '������';
comment on column CL_DDLSB.xgsj
  is '�޸�ʱ��';
comment on column CL_DDLSB.xgr
  is '�޸���';
comment on column CL_DDLSB.jgdm
  is '��������';
comment on column CL_DDLSB.jgmc
  is '��������';
comment on column CL_DDLSB.cl_id
  is '����id';
comment on column CL_DDLSB.glf
  is '��·��';
comment on column CL_DDLSB.sy
  is '����';
comment on column CL_DDLSB.cllx
  is '��������';
comment on column CL_DDLSB.wf
  is '����';
comment on column CL_DDLSB.dzqrsj
  is '�ӳ�ȷ��ʱ��';
comment on column CL_DDLSB.shsj
  is '���ʱ��';
comment on column CL_DDLSB.dzxm
  is '�ӳ�����';
comment on column CL_DDLSB.fkbz
  is '���ע';
comment on column CL_DDLSB.dd_id
  is '����ID';
comment on column CL_DDLSB.xgcs
  is '�޸Ĵ���';
comment on column CL_DDLSB.ddxgsj
  is '�����޸�ʱ��';
comment on column CL_DDLSB.ddcjsj
  is '��������ʱ��';
comment on column CL_DDLSB.ddxgr
  is '�ٶ�������';
alter table CL_DDLSB
  add constraint PK_CL_DDLSB primary key (ID);

prompt Creating CL_DDRZ...
create table CL_DDRZ
(
  id    VARCHAR2(32) not null,
  dd_id VARCHAR2(32),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  bz    VARCHAR2(255),
  jgdm  VARCHAR2(20),
  jgmc  VARCHAR2(32),
  ddzt  CHAR(2)
)
;
comment on table CL_DDRZ
  is '�������ڱ�(����������־��)';
comment on column CL_DDRZ.id
  is 'ID';
comment on column CL_DDRZ.dd_id
  is '����id';
comment on column CL_DDRZ.cjsj
  is '����ʱ��';
comment on column CL_DDRZ.cjr
  is '������';
comment on column CL_DDRZ.bz
  is '��ע';
comment on column CL_DDRZ.jgdm
  is '��������';
comment on column CL_DDRZ.jgmc
  is '��������';
comment on column CL_DDRZ.ddzt
  is '����״̬';
alter table CL_DDRZ
  add constraint PK_CL_DDRZ primary key (ID);

prompt Creating CL_DZWL...
create table CL_DZWL
(
  id     VARCHAR2(32) not null,
  wlmc   VARCHAR2(32),
  jgdm   VARCHAR2(20),
  jgmc   VARCHAR2(32),
  mj     FLOAT default 1000,
  dlxxzb VARCHAR2(4000),
  ksjd   NUMBER(10,2),
  kswd   NUMBER(10,2),
  zt     CHAR(2),
  cjsj   TIMESTAMP(6),
  cjr    VARCHAR2(64),
  xgsj   TIMESTAMP(6),
  xgr    VARCHAR2(64)
)
;
comment on table CL_DZWL
  is '����Χ����';
comment on column CL_DZWL.id
  is 'ID';
comment on column CL_DZWL.wlmc
  is 'Χ������';
comment on column CL_DZWL.jgdm
  is '��������';
comment on column CL_DZWL.jgmc
  is '��������';
comment on column CL_DZWL.mj
  is '���';
comment on column CL_DZWL.dlxxzb
  is '������Ϣ����';
comment on column CL_DZWL.ksjd
  is '��ʼ����';
comment on column CL_DZWL.kswd
  is '��ʼγ��';
comment on column CL_DZWL.zt
  is '״̬';
comment on column CL_DZWL.cjsj
  is '����ʱ��';
comment on column CL_DZWL.cjr
  is '������';
comment on column CL_DZWL.xgsj
  is '�޸�ʱ��';
comment on column CL_DZWL.xgr
  is '�޸���';
alter table CL_DZWL
  add constraint PK_CL_DZWL primary key (ID);

prompt Creating CL_DZWL_CL...
create table CL_DZWL_CL
(
  id    VARCHAR2(32) not null,
  wl_id VARCHAR2(32),
  cl_id VARCHAR2(32),
  cph   VARCHAR2(10),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64)
)
;
comment on table CL_DZWL_CL
  is '����Χ��������';
comment on column CL_DZWL_CL.id
  is 'ID';
comment on column CL_DZWL_CL.wl_id
  is 'Χ��id';
comment on column CL_DZWL_CL.cl_id
  is '����id';
comment on column CL_DZWL_CL.cph
  is '���ƺ�';
comment on column CL_DZWL_CL.cjsj
  is '����ʱ��';
comment on column CL_DZWL_CL.cjr
  is '������';
alter table CL_DZWL_CL
  add constraint PK_DZWL_CL primary key (ID);

prompt Creating CL_GPS...
create table CL_GPS
(
  zdbh VARCHAR2(32) not null,
  lx   CHAR(5),
  jd   NUMBER,
  wd   NUMBER,
  ggjd NUMBER,
  ggwd NUMBER,
  bdjd NUMBER,
  bdwd NUMBER,
  gdjd NUMBER,
  gdwd NUMBER,
  cjsj TIMESTAMP(6),
  gxsj TIMESTAMP(6),
  dwjd NUMBER,
  fxj  NUMBER,
  yxsd VARCHAR2(32)
)
;
comment on column CL_GPS.zdbh
  is '�ն˱��';
comment on column CL_GPS.lx
  is '����';
comment on column CL_GPS.jd
  is '����';
comment on column CL_GPS.wd
  is 'γ��';
comment on column CL_GPS.ggjd
  is '�ȸ辭��';
comment on column CL_GPS.ggwd
  is '�ȸ�γ��';
comment on column CL_GPS.bdjd
  is '�ٶȾ���';
comment on column CL_GPS.bdwd
  is '�ٶ�γ��';
comment on column CL_GPS.gdjd
  is '�ߵ¾���';
comment on column CL_GPS.gdwd
  is '�ߵ�γ��';
comment on column CL_GPS.cjsj
  is '����ʱ��';
comment on column CL_GPS.gxsj
  is '����ʱ��';
comment on column CL_GPS.dwjd
  is '��λ����';
comment on column CL_GPS.fxj
  is '�����';
comment on column CL_GPS.yxsd
  is '�����ٶ�';
alter table CL_GPS
  add constraint PK_CL_GPS primary key (ZDBH);

prompt Creating CL_GPS_LS...
create table CL_GPS_LS
(
  id   VARCHAR2(32) not null,
  zdbh VARCHAR2(32),
  cjsj TIMESTAMP(8),
  jd   NUMBER(32,10),
  wd   NUMBER(32,10),
  ggjd NUMBER(32,10),
  ggwd NUMBER(32,10),
  bdjd NUMBER(32,10),
  bdwd NUMBER(32,10),
  gdjd NUMBER(32,10),
  gdwd NUMBER(32,10),
  lx   CHAR(22),
  dwjd NUMBER(32),
  fxj  NUMBER(32,1),
  yxsd VARCHAR2(32)
)
;
comment on column CL_GPS_LS.id
  is 'ID';
comment on column CL_GPS_LS.zdbh
  is '�ն˱��';
comment on column CL_GPS_LS.cjsj
  is '����ʱ��';
comment on column CL_GPS_LS.jd
  is '����';
comment on column CL_GPS_LS.wd
  is 'γ��';
comment on column CL_GPS_LS.ggjd
  is '�ȸ辭��';
comment on column CL_GPS_LS.ggwd
  is '�ȸ�γ��';
comment on column CL_GPS_LS.bdjd
  is '�ٶȾ���';
comment on column CL_GPS_LS.bdwd
  is '�ٶ�γ��';
comment on column CL_GPS_LS.gdjd
  is '�ߵ¾���';
comment on column CL_GPS_LS.gdwd
  is '�ߵ�γ��';
comment on column CL_GPS_LS.lx
  is '����';
comment on column CL_GPS_LS.dwjd
  is '��λ����';
comment on column CL_GPS_LS.fxj
  is '�����';
comment on column CL_GPS_LS.yxsd
  is '�����ٶ�';
alter table CL_GPS_LS
  add constraint PK_CL_GPS_LS primary key (ID);

prompt Creating CL_JSY...
create table CL_JSY
(
  sfzhm VARCHAR2(18) not null,
  xm    VARCHAR2(32),
  jgdm  VARCHAR2(20),
  xb    CHAR(1),
  nl    NUMBER,
  jl    NUMBER,
  zt    CHAR(2),
  tx    VARCHAR2(255),
  zjcx  CHAR(2),
  dj    CHAR(2),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  xgr   VARCHAR2(64),
  cdbh  VARCHAR2(32),
  dabh  VARCHAR2(32),
  clrq  TIMESTAMP(6),
  sjh   VARCHAR2(12),
  pwd   VARCHAR2(255),
  xx    VARCHAR2(2),
  jkzk  VARCHAR2(255),
  jzyxq VARCHAR2(20)
)
;
comment on column CL_JSY.sfzhm
  is '���֤����';
comment on column CL_JSY.xm
  is '����';
comment on column CL_JSY.jgdm
  is '��������';
comment on column CL_JSY.xb
  is '�Ա�';
comment on column CL_JSY.nl
  is '����';
comment on column CL_JSY.jl
  is '����';
comment on column CL_JSY.zt
  is '״̬ �ֵ��ZDCLK0018   (00���ڰ� 10����Ϣ)';
comment on column CL_JSY.tx
  is 'ͷ��';
comment on column CL_JSY.zjcx
  is '׼�ݳ���';
comment on column CL_JSY.dj
  is '�ȼ�';
comment on column CL_JSY.cjsj
  is '����ʱ��';
comment on column CL_JSY.cjr
  is '������';
comment on column CL_JSY.xgsj
  is '�޸�ʱ��';
comment on column CL_JSY.xgr
  is '�޸���';
comment on column CL_JSY.cdbh
  is '���ӱ��';
comment on column CL_JSY.dabh
  is '�������';
comment on column CL_JSY.clrq
  is '��������';
comment on column CL_JSY.sjh
  is '��ʻԱ�ֻ���';
comment on column CL_JSY.pwd
  is '΢�ŵ�½����';
comment on column CL_JSY.xx
  is 'Ѫ��';
comment on column CL_JSY.jkzk
  is '����״��';
comment on column CL_JSY.jzyxq
  is '������Ч��';
alter table CL_JSY
  add constraint PK_CL_JSY primary key (SFZHM);

prompt Creating CL_LSC...
create table CL_LSC
(
  id      VARCHAR2(32) not null,
  cph     VARCHAR2(10),
  cjr     VARCHAR2(64),
  djcs    NUMBER,
  lsdw_id VARCHAR2(32),
  lsdwmc  VARCHAR2(32),
  cllx    CHAR(2),
  zws     NUMBER,
  zt      CHAR(2),
  cl_id   VARCHAR2(32),
  cjsj    TIMESTAMP(6)
)
;
comment on column CL_LSC.id
  is 'ID';
comment on column CL_LSC.cph
  is '���ƺ�';
comment on column CL_LSC.cjr
  is '������';
comment on column CL_LSC.djcs
  is '�ǼǴ���';
comment on column CL_LSC.lsdw_id
  is '��ʱ��λid';
comment on column CL_LSC.lsdwmc
  is '��ʱ��λ����';
comment on column CL_LSC.cllx
  is '��������';
comment on column CL_LSC.zws
  is '��λ��';
comment on column CL_LSC.zt
  is '״̬';
comment on column CL_LSC.cl_id
  is '����id';
alter table CL_LSC
  add constraint PK_CL_LSC primary key (ID);

prompt Creating CL_LSDW...
create table CL_LSDW
(
  id   VARCHAR2(32) not null,
  dwmc VARCHAR2(32),
  cjr  VARCHAR2(64),
  cjsj TIMESTAMP(6),
  djcs NUMBER,
  xgr  VARCHAR2(64),
  xgsj TIMESTAMP(6),
  dwbh VARCHAR2(32),
  zt   CHAR(2),
  lxr  VARCHAR2(32),
  lxdh VARCHAR2(16)
)
;
comment on column CL_LSDW.id
  is 'ID';
comment on column CL_LSDW.dwmc
  is '��λ����';
comment on column CL_LSDW.cjr
  is '������';
comment on column CL_LSDW.cjsj
  is '����ʱ��';
comment on column CL_LSDW.djcs
  is '�ǼǴ���';
comment on column CL_LSDW.xgr
  is '�޸���';
comment on column CL_LSDW.xgsj
  is '�޸�ʱ��';
comment on column CL_LSDW.dwbh
  is '��λ���';
comment on column CL_LSDW.zt
  is '״̬';
comment on column CL_LSDW.lxr
  is '��ϵ��';
comment on column CL_LSDW.lxdh
  is '��ϵ�绰';
alter table CL_LSDW
  add constraint PK_CL_LSDW primary key (ID);

prompt Creating CL_PB...
create table CL_PB
(
  id    VARCHAR2(32) not null,
  cph   VARCHAR2(10),
  xl_id VARCHAR2(32),
  pbsj  TIMESTAMP(6),
  sj    VARCHAR2(32),
  sjxm  VARCHAR2(32),
  jgdm  VARCHAR2(20),
  jgmc  VARCHAR2(32),
  cl_id VARCHAR2(32),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgj   TIMESTAMP(6),
  xgr   VARCHAR2(64)
)
;
comment on column CL_PB.id
  is 'ID';
comment on column CL_PB.cph
  is '���ƺ�';
comment on column CL_PB.xl_id
  is '��·id';
comment on column CL_PB.pbsj
  is '�Ű�ʱ��';
comment on column CL_PB.sj
  is '˾��';
comment on column CL_PB.sjxm
  is '˾������';
comment on column CL_PB.jgdm
  is '��������';
comment on column CL_PB.jgmc
  is '��������';
comment on column CL_PB.cl_id
  is '����id';
comment on column CL_PB.cjsj
  is '����ʱ��';
comment on column CL_PB.cjr
  is '������';
comment on column CL_PB.xgj
  is '�޸�ʱ��';
comment on column CL_PB.xgr
  is '�޸���';
alter table CL_PB
  add constraint PK_CL_PB primary key (ID);

prompt Creating CL_SBYCSJJL...
create table CL_SBYCSJJL
(
  id   VARCHAR2(32) not null,
  zdbh VARCHAR2(32),
  lx   CHAR(2),
  cjsj TIMESTAMP(6),
  cjr  VARCHAR2(64),
  jd   NUMBER(3,10),
  wd   NUMBER(3,10)
)
;
comment on table CL_SBYCSJJL
  is '�豸�쳣�¼���¼��';
comment on column CL_SBYCSJJL.id
  is 'ID';
comment on column CL_SBYCSJJL.zdbh
  is '�ն˱��';
comment on column CL_SBYCSJJL.lx
  is '����';
comment on column CL_SBYCSJJL.cjsj
  is '����ʱ��';
comment on column CL_SBYCSJJL.cjr
  is '������';
comment on column CL_SBYCSJJL.jd
  is '����';
comment on column CL_SBYCSJJL.wd
  is 'γ��';
alter table CL_SBYCSJJL
  add constraint PK_CL_SBYCSJJL primary key (ID);

prompt Creating CL_SBYXSJJL...
create table CL_SBYXSJJL
(
  id   VARCHAR2(32) not null,
  zdbh VARCHAR2(32),
  sjjb CHAR(2),
  cjsj TIMESTAMP(6),
  jd   NUMBER,
  wd   NUMBER,
  jid  NUMBER,
  sjlx CHAR(2),
  yxfx FLOAT,
  bz   VARCHAR2(100),
  sjxm VARCHAR2(255),
  cph  VARCHAR2(255),
  cx   VARCHAR2(255)
)
;
comment on table CL_SBYXSJJL
  is '�豸�����¼���¼��';
comment on column CL_SBYXSJJL.id
  is 'ID';
comment on column CL_SBYXSJJL.zdbh
  is '�ն˱��';
comment on column CL_SBYXSJJL.sjjb
  is '�¼�����';
comment on column CL_SBYXSJJL.cjsj
  is '����ʱ��';
comment on column CL_SBYXSJJL.jd
  is '����';
comment on column CL_SBYXSJJL.wd
  is 'γ��';
comment on column CL_SBYXSJJL.jid
  is '����';
comment on column CL_SBYXSJJL.sjlx
  is '�¼�����';
comment on column CL_SBYXSJJL.yxfx
  is '���з���';
comment on column CL_SBYXSJJL.bz
  is '��ע';
comment on column CL_SBYXSJJL.sjxm
  is '˾������';
comment on column CL_SBYXSJJL.cph
  is '���ƺ�';
comment on column CL_SBYXSJJL.cx
  is '����';
alter table CL_SBYXSJJL
  add constraint PK_CL_SBYXSJJL primary key (ID);

prompt Creating CL_SG...
create table CL_SG
(
  id    VARCHAR2(32) not null,
  cph   VARCHAR2(10),
  cdbh  VARCHAR2(32),
  jgdm  VARCHAR2(20),
  sj    VARCHAR2(32),
  sjxm  VARCHAR2(32),
  sgdd  VARCHAR2(255),
  sgsj  TIMESTAMP(6),
  cljg  VARCHAR2(255),
  clr   VARCHAR2(64),
  sgms  VARCHAR2(500),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  xgr   VARCHAR2(64),
  cl_id VARCHAR2(32),
  clsj  TIMESTAMP(6),
  bz    VARCHAR2(255),
  lxdh  VARCHAR2(16)
)
;
comment on column CL_SG.id
  is 'ID';
comment on column CL_SG.cph
  is '���ƺ�';
comment on column CL_SG.cdbh
  is '���ӱ��';
comment on column CL_SG.jgdm
  is '��������';
comment on column CL_SG.sj
  is '˾��';
comment on column CL_SG.sjxm
  is '˾������';
comment on column CL_SG.sgdd
  is '�¹ʵص�';
comment on column CL_SG.sgsj
  is '�¹�ʱ��';
comment on column CL_SG.cljg
  is '������';
comment on column CL_SG.clr
  is '������';
comment on column CL_SG.sgms
  is '�¹�����';
comment on column CL_SG.cjsj
  is '����ʱ��';
comment on column CL_SG.cjr
  is '������';
comment on column CL_SG.xgsj
  is '�޸�ʱ��';
comment on column CL_SG.xgr
  is '�޸���';
comment on column CL_SG.cl_id
  is '����id';
comment on column CL_SG.clsj
  is '����ʱ��';
comment on column CL_SG.bz
  is '��ע';
comment on column CL_SG.lxdh
  is '��ϵ�绰';
alter table CL_SG
  add constraint PK_CL_SG primary key (ID);

prompt Creating CL_SGWJ...
create table CL_SGWJ
(
  id    VARCHAR2(32) not null,
  sg_id VARCHAR2(32),
  wjlx  VARCHAR2(16),
  fwqdz VARCHAR2(255),
  wldz  VARCHAR2(255),
  wjdx  FLOAT,
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64)
)
;
comment on column CL_SGWJ.id
  is 'ID';
comment on column CL_SGWJ.sg_id
  is '�¹�id';
comment on column CL_SGWJ.wjlx
  is '�ļ�����';
comment on column CL_SGWJ.fwqdz
  is '��������ַ';
comment on column CL_SGWJ.wldz
  is '�����ַ';
comment on column CL_SGWJ.wjdx
  is '�ļ���С';
comment on column CL_SGWJ.cjsj
  is '����ʱ��';
comment on column CL_SGWJ.cjr
  is '������';
alter table CL_SGWJ
  add constraint PK_SGWJ primary key (ID);

prompt Creating CL_SPK...
create table CL_SPK
(
  id    VARCHAR2(32) not null,
  wjm   VARCHAR2(32),
  dz    VARCHAR2(255),
  zdbh  VARCHAR2(32),
  kssj  TIMESTAMP(6),
  jssj  TIMESTAMP(6),
  sc    NUMBER,
  url   VARCHAR2(255),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  xgr   VARCHAR2(64),
  bj    VARCHAR2(32),
  sply  VARCHAR2(32),
  splx  CHAR(10),
  jgdm  VARCHAR2(20),
  jgmc  VARCHAR2(32),
  cph   VARCHAR2(48),
  cl_id VARCHAR2(32)
)
;
comment on column CL_SPK.id
  is 'ID';
comment on column CL_SPK.wjm
  is '�ļ���';
comment on column CL_SPK.dz
  is '��ַ';
comment on column CL_SPK.zdbh
  is '�ն˱��';
comment on column CL_SPK.kssj
  is '��ʼʱ��';
comment on column CL_SPK.jssj
  is '����ʱ��';
comment on column CL_SPK.sc
  is 'ʱ��';
comment on column CL_SPK.url
  is 'url';
comment on column CL_SPK.cjsj
  is '����ʱ��';
comment on column CL_SPK.cjr
  is '������';
comment on column CL_SPK.xgsj
  is '�޸�ʱ��';
comment on column CL_SPK.xgr
  is '�޸���';
comment on column CL_SPK.bj
  is '���';
comment on column CL_SPK.sply
  is '��Ƶ��Դ';
comment on column CL_SPK.splx
  is '��Ƶ����';
comment on column CL_SPK.jgdm
  is '��������';
comment on column CL_SPK.jgmc
  is '��������';
comment on column CL_SPK.cph
  is '���ƺ�';
comment on column CL_SPK.cl_id
  is '����id';
alter table CL_SPK
  add constraint PK_CL_SPK primary key (ID);

prompt Creating CL_XC...
create table CL_XC
(
  id           VARCHAR2(32) not null,
  xc_kssj      VARCHAR2(32),
  xc_jssj      VARCHAR2(32),
  cl_zdbh      VARCHAR2(32),
  xc_start_end VARCHAR2(64)
)
;
comment on table CL_XC
  is '�����г̱�';
comment on column CL_XC.xc_kssj
  is '�����г̿�ʼʱ��';
comment on column CL_XC.xc_jssj
  is '�г̽���ʱ��';
comment on column CL_XC.cl_zdbh
  is '�ն˱��';
comment on column CL_XC.xc_start_end
  is '�г̵Ŀ�ʼ�ͽ�����';
alter table CL_XC
  add constraint PK_BAIDU_YINGYAN_XC primary key (ID);

prompt Creating CL_XL...
create table CL_XL
(
  id     VARCHAR2(32) not null,
  xlmc   VARCHAR2(32),
  xlbh   VARCHAR2(6),
  cd     FLOAT,
  pjsj   FLOAT,
  yxkssj VARCHAR2(32),
  yxjssj VARCHAR2(32),
  cjr    VARCHAR2(64),
  cjsj   TIMESTAMP(6),
  xgr    VARCHAR2(64),
  xgsj   TIMESTAMP(6),
  jgdm   VARCHAR2(20),
  jgmc   VARCHAR2(32),
  zt     CHAR(2),
  bz     VARCHAR2(255),
  yxfs   CHAR(2),
  lx     CHAR(2)
)
;
comment on column CL_XL.id
  is 'ID';
comment on column CL_XL.xlmc
  is '��·����';
comment on column CL_XL.xlbh
  is '��·���';
comment on column CL_XL.cd
  is '����';
comment on column CL_XL.pjsj
  is 'ƽ��ʱ��';
comment on column CL_XL.cjr
  is '������';
comment on column CL_XL.cjsj
  is '����ʱ��';
comment on column CL_XL.xgr
  is '�޸���';
comment on column CL_XL.xgsj
  is '�޸�ʱ��';
comment on column CL_XL.jgdm
  is '��������';
comment on column CL_XL.jgmc
  is '��������';
comment on column CL_XL.zt
  is '״̬';
comment on column CL_XL.bz
  is '��ע';
comment on column CL_XL.yxfs
  is '���з�ʽ';
comment on column CL_XL.lx
  is '����';
alter table CL_XL
  add constraint PK_CL_XL primary key (ID);

prompt Creating CL_XLCL...
create table CL_XLCL
(
  id     VARCHAR2(32) not null,
  "��·id" VARCHAR2(32),
  ���ƺ�    VARCHAR2(10),
  ״̬     CHAR(2),
  ����ʱ��   TIMESTAMP(6),
  ������    VARCHAR2(64),
  �޸�ʱ��   TIMESTAMP(6),
  �޸���    VARCHAR2(64),
  "����id" VARCHAR2(32)
)
;
alter table CL_XLCL
  add constraint PK_CL_XLCL primary key (ID);

prompt Creating CL_XLZD...
create table CL_XLZD
(
  id     VARCHAR2(32) not null,
  xl_id  VARCHAR2(32),
  zd_id  VARCHAR2(32),
  zt     CHAR(2),
  cjr    VARCHAR2(64),
  cjsj   TIMESTAMP(6),
  xgr    VARCHAR2(64),
  xgsj   TIMESTAMP(6),
  xh     NUMBER,
  fx     CHAR(2),
  yjdzsj NUMBER
)
;
comment on column CL_XLZD.id
  is 'ID';
comment on column CL_XLZD.xl_id
  is '��·id';
comment on column CL_XLZD.zd_id
  is 'վ��id';
comment on column CL_XLZD.zt
  is '״̬';
comment on column CL_XLZD.cjr
  is '������';
comment on column CL_XLZD.cjsj
  is '����ʱ��';
comment on column CL_XLZD.xgr
  is '�޸���';
comment on column CL_XLZD.xgsj
  is '�޸�ʱ��';
comment on column CL_XLZD.xh
  is '���';
comment on column CL_XLZD.fx
  is '����';
comment on column CL_XLZD.yjdzsj
  is 'Ԥ�Ƶ�վʱ��';
alter table CL_XLZD
  add constraint PK_CL_XLZD primary key (ID);

prompt Creating CL_YY...
create table CL_YY
(
  id        VARCHAR2(32) not null,
  zdbh      VARCHAR2(32),
  longitude FLOAT,
  latitude  FLOAT,
  loctime   VARCHAR2(32),
  objectkey VARCHAR2(32),
  direction VARCHAR2(32),
  speed     FLOAT
)
;
comment on column CL_YY.id
  is '����';
comment on column CL_YY.zdbh
  is '�ն˱�Ŷ�Ӧ�İٶ�ӥ��entityname';
comment on column CL_YY.longitude
  is '����';
comment on column CL_YY.latitude
  is 'γ��';
comment on column CL_YY.loctime
  is '�ϴ��ĵ�λʱ��';
comment on column CL_YY.objectkey
  is 'Ԥ���ֶ�';
comment on column CL_YY.direction
  is '�����';
comment on column CL_YY.speed
  is '�ٶ�';
alter table CL_YY
  add constraint PK_BAIDU_YINGYAN primary key (ID);

prompt Creating CL_ZD...
create table CL_ZD
(
  id   VARCHAR2(32) not null,
  dz   VARCHAR2(255),
  mc   VARCHAR2(64),
  jd   FLOAT,
  wd   FLOAT,
  cjsj TIMESTAMP(6),
  cjr  VARCHAR2(64),
  xgsj TIMESTAMP(6),
  xgr  VARCHAR2(64),
  jgdm VARCHAR2(20),
  jgmc VARCHAR2(32),
  zt   CHAR(2),
  bz   VARCHAR2(255),
  yxfs CHAR(2),
  fw   NUMBER,
  lx   CHAR(2)
)
;
comment on column CL_ZD.id
  is 'id';
comment on column CL_ZD.dz
  is '��ַ';
comment on column CL_ZD.mc
  is '����';
comment on column CL_ZD.jd
  is '����';
comment on column CL_ZD.wd
  is 'γ��';
comment on column CL_ZD.cjsj
  is '����ʱ��';
comment on column CL_ZD.cjr
  is '������';
comment on column CL_ZD.xgsj
  is '�޸�ʱ��';
comment on column CL_ZD.xgr
  is '�޸���';
comment on column CL_ZD.jgdm
  is '��������';
comment on column CL_ZD.jgmc
  is '��������';
comment on column CL_ZD.zt
  is '״̬';
comment on column CL_ZD.bz
  is '��ע';
comment on column CL_ZD.yxfs
  is '���з�ʽ';
comment on column CL_ZD.fw
  is '��Χ';
comment on column CL_ZD.lx
  is '����';
alter table CL_ZD
  add constraint PK_CL_ZD primary key (ID);

prompt Creating CL_ZDGL...
create table CL_ZDGL
(
  zdbh    VARCHAR2(32) not null,
  xh      VARCHAR2(32),
  mc      VARCHAR2(32),
  cs      VARCHAR2(32),
  zt      CHAR(2),
  cjr     VARCHAR2(64),
  cjsj    TIMESTAMP(6),
  xgr     VARCHAR2(64),
  xgsj    TIMESTAMP(6),
  zxzt    CHAR(2),
  zxsj    TIMESTAMP(6),
  jslmd   VARCHAR2(255),
  scms    VARCHAR2(255),
  pzlmd   VARCHAR2(255),
  cmd     VARCHAR2(255),
  spscms  VARCHAR2(255),
  gpsxt   VARCHAR2(255),
  sfyy    VARCHAR2(255),
  jgdm    VARCHAR2(20),
  jgmc    VARCHAR2(20),
  version VARCHAR2(20)
)
;
comment on table CL_ZDGL
  is '�ն˹����';
comment on column CL_ZDGL.zdbh
  is '�ն˱��';
comment on column CL_ZDGL.xh
  is '�ͺ�';
comment on column CL_ZDGL.mc
  is '����';
comment on column CL_ZDGL.cs
  is '����';
comment on column CL_ZDGL.zt
  is '״̬';
comment on column CL_ZDGL.cjr
  is '������';
comment on column CL_ZDGL.cjsj
  is '����ʱ��';
comment on column CL_ZDGL.xgr
  is '�޸���';
comment on column CL_ZDGL.xgsj
  is '�޸�ʱ��';
comment on column CL_ZDGL.zxzt
  is '����״̬ ZDCLK0032 (00��� 10 Ϩ�� 20����)';
comment on column CL_ZDGL.zxsj
  is '����ʱ��';
comment on column CL_ZDGL.jslmd
  is '����������';
comment on column CL_ZDGL.scms
  is '�����ϴ�ģʽ';
comment on column CL_ZDGL.pzlmd
  is '��ײ������';
comment on column CL_ZDGL.cmd
  is '�ϱ���ַ';
comment on column CL_ZDGL.spscms
  is '�����ϴ�ģʽ';
comment on column CL_ZDGL.gpsxt
  is 'GPS����';
comment on column CL_ZDGL.sfyy
  is '�Ƿ��豸�ϴ���ӥ��';
comment on column CL_ZDGL.jgdm
  is '��������';
comment on column CL_ZDGL.jgmc
  is '��������';
comment on column CL_ZDGL.version
  is '�豸�汾��';
alter table CL_ZDGL
  add constraint PK_CL_ZDGL primary key (ZDBH);

prompt Creating CL_ZNZP...
create table CL_ZNZP
(
  zdbh  VARCHAR2(32) not null,
  mc    VARCHAR2(32),
  xh    VARCHAR2(32),
  cs    VARCHAR2(255),
  dz    VARCHAR2(255),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  xgr   VARCHAR2(64),
  zd_id VARCHAR2(32),
  tips  VARCHAR2(255),
  zxzt  VARCHAR2(4) default 10 not null
)
;
comment on column CL_ZNZP.zdbh
  is '�ն˱��';
comment on column CL_ZNZP.mc
  is '����';
comment on column CL_ZNZP.xh
  is '�ͺ�';
comment on column CL_ZNZP.cs
  is '����';
comment on column CL_ZNZP.dz
  is '��ַ';
comment on column CL_ZNZP.cjsj
  is '����ʱ��';
comment on column CL_ZNZP.cjr
  is '������';
comment on column CL_ZNZP.xgsj
  is '�޸�ʱ��';
comment on column CL_ZNZP.xgr
  is '�޸���';
comment on column CL_ZNZP.zd_id
  is 'վ��ID';
comment on column CL_ZNZP.tips
  is '��ע';
comment on column CL_ZNZP.zxzt
  is '����״̬ /* 00:���� 10������*/';
alter table CL_ZNZP
  add constraint PK_CL_ZNZP primary key (ZDBH);

prompt Creating CL_ZP_XL...
create table CL_ZP_XL
(
  id    VARCHAR2(32) not null,
  zp_id VARCHAR2(32),
  xl_id VARCHAR2(32),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64)
)
;
comment on column CL_ZP_XL.id
  is 'ID';
comment on column CL_ZP_XL.zp_id
  is 'վ��ID';
comment on column CL_ZP_XL.xl_id
  is '��·ID';
comment on column CL_ZP_XL.cjsj
  is '����ʱ��';
comment on column CL_ZP_XL.cjr
  is '������';
alter table CL_ZP_XL
  add constraint PK_CL_ZP_XL primary key (ID);

prompt Creating SYS_CLK_PTJS...
create table SYS_CLK_PTJS
(
  js_id VARCHAR2(32) not null,
  jsmc  VARCHAR2(32),
  jslx  VARCHAR2(32),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  zt    CHAR(2),
  jgdm  VARCHAR2(20),
  sm    VARCHAR2(1000)
)
;
comment on column SYS_CLK_PTJS.js_id
  is '��ɫid';
comment on column SYS_CLK_PTJS.jsmc
  is '��ɫ����';
comment on column SYS_CLK_PTJS.jslx
  is '��ɫ����';
comment on column SYS_CLK_PTJS.cjsj
  is '����ʱ��';
comment on column SYS_CLK_PTJS.cjr
  is '������';
comment on column SYS_CLK_PTJS.xgr
  is '�޸���';
comment on column SYS_CLK_PTJS.xgsj
  is '�޸�ʱ��';
comment on column SYS_CLK_PTJS.zt
  is '״̬';
comment on column SYS_CLK_PTJS.jgdm
  is '��������';
comment on column SYS_CLK_PTJS.sm
  is '˵��';
alter table SYS_CLK_PTJS
  add constraint PK_SYS_CLK_PTJS primary key (JS_ID);

prompt Creating SYS_CLK_PTYH...
create table SYS_CLK_PTYH
(
  yhid  VARCHAR2(32) not null,
  zh    VARCHAR2(32),
  mm    VARCHAR2(32),
  sjh   VARCHAR2(32),
  cjr   VARCHAR2(64),
  cjsj  TIMESTAMP(6),
  xgr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  zt    CHAR(2),
  jgdm  VARCHAR2(20),
  xm    VARCHAR2(32),
  lx    CHAR(2),
  xb    CHAR(1),
  zjhm  VARCHAR2(32),
  mmyxq TIMESTAMP(6),
  zw    VARCHAR2(32)
)
;
comment on table SYS_CLK_PTYH
  is 'ƽ̨�û���';
comment on column SYS_CLK_PTYH.yhid
  is '�û�id';
comment on column SYS_CLK_PTYH.zh
  is '�˺�';
comment on column SYS_CLK_PTYH.mm
  is '����';
comment on column SYS_CLK_PTYH.sjh
  is '�ֻ���';
comment on column SYS_CLK_PTYH.cjr
  is '������';
comment on column SYS_CLK_PTYH.cjsj
  is '����ʱ��';
comment on column SYS_CLK_PTYH.xgr
  is '�޸���';
comment on column SYS_CLK_PTYH.xgsj
  is '�޸�ʱ��';
comment on column SYS_CLK_PTYH.zt
  is '״̬';
comment on column SYS_CLK_PTYH.jgdm
  is '��������';
comment on column SYS_CLK_PTYH.xm
  is '����';
comment on column SYS_CLK_PTYH.lx
  is '����';
comment on column SYS_CLK_PTYH.xb
  is '�Ա�';
comment on column SYS_CLK_PTYH.zjhm
  is '֤������';
comment on column SYS_CLK_PTYH.mmyxq
  is '������Ч��';
comment on column SYS_CLK_PTYH.zw
  is 'ְ��';
alter table SYS_CLK_PTYH
  add constraint PK_SYS_CLK_PTYH primary key (YHID);

prompt Creating SYS_FWGN...
create table SYS_FWGN
(
  gndm   VARCHAR2(32) not null,
  gnmc   VARCHAR2(32),
  fwdm   VARCHAR2(32),
  cjsj   TIMESTAMP(6),
  cjr    VARCHAR2(64),
  xgsj   TIMESTAMP(6),
  xgr    VARCHAR2(64),
  zt     CHAR(2),
  bz     VARCHAR2(255),
  url    VARCHAR2(255),
  fjd    VARCHAR2(32),
  tzdz   VARCHAR2(32),
  tb     VARCHAR2(32),
  api_qz VARCHAR2(64),
  api_hz VARCHAR2(64),
  px     FLOAT default 0
)
;
comment on column SYS_FWGN.gndm
  is '���ܴ���';
comment on column SYS_FWGN.gnmc
  is '��������';
comment on column SYS_FWGN.fwdm
  is '�������';
comment on column SYS_FWGN.cjsj
  is '����ʱ��';
comment on column SYS_FWGN.cjr
  is '������';
comment on column SYS_FWGN.xgsj
  is '�޸�ʱ��';
comment on column SYS_FWGN.xgr
  is '�޸���';
comment on column SYS_FWGN.zt
  is '״̬';
comment on column SYS_FWGN.bz
  is '��ע';
comment on column SYS_FWGN.url
  is 'URL';
comment on column SYS_FWGN.fjd
  is '���ڵ�';
comment on column SYS_FWGN.tzdz
  is '��ת��ַ';
comment on column SYS_FWGN.tb
  is 'ͼ��';
comment on column SYS_FWGN.api_qz
  is 'APIǰ׺';
comment on column SYS_FWGN.api_hz
  is 'API��׺';
comment on column SYS_FWGN.px
  is '����';
alter table SYS_FWGN
  add constraint PK_FWGN primary key (GNDM);

prompt Creating SYS_HDYX...
create table SYS_HDYX
(
  hd_id VARCHAR2(32) not null,
  kssj  TIMESTAMP(6),
  jssj  TIMESTAMP(6),
  hdlx  VARCHAR2(32),
  jgdm  VARCHAR2(20),
  zt    CHAR(2),
  cjr   VARCHAR2(64),
  cjsj  TIMESTAMP(6),
  xgr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  hdbt  VARCHAR2(32),
  url   VARCHAR2(255),
  wjlx  VARCHAR2(32) default 00,
  wz    VARCHAR2(32) default 10
)
;
comment on table SYS_HDYX
  is '�Ӫ��';
comment on column SYS_HDYX.hd_id
  is '�id';
comment on column SYS_HDYX.kssj
  is '�ʱ�俪ʼ';
comment on column SYS_HDYX.jssj
  is '�ʱ�����';
comment on column SYS_HDYX.hdlx
  is '�����(00 ΢�š�10����վ��)';
comment on column SYS_HDYX.jgdm
  is '��������';
comment on column SYS_HDYX.zt
  is '״̬(00δ��ʼ 10 �ѿ�ʼ  20 �ѽ���)';
comment on column SYS_HDYX.cjr
  is '������';
comment on column SYS_HDYX.cjsj
  is '����ʱ��';
comment on column SYS_HDYX.xgr
  is '�޸���';
comment on column SYS_HDYX.xgsj
  is '�޸�ʱ��';
comment on column SYS_HDYX.hdbt
  is '�����';
comment on column SYS_HDYX.url
  is 'URL';
comment on column SYS_HDYX.wjlx
  is '�ļ�����,ͼƬ����Ƶ';
comment on column SYS_HDYX.wz
  is '����վ��λ��(10�� 20�� 30��)';
alter table SYS_HDYX
  add constraint PK_SYS_HDYX primary key (HD_ID);

prompt Creating SYS_HSGS...
create table SYS_HSGS
(
  id   VARCHAR2(32) not null,
  cx   CHAR(2),
  lx   CHAR(2),
  nr   VARCHAR2(32),
  je   NUMBER(10,2),
  cjr  VARCHAR2(64),
  cjsj TIMESTAMP(6),
  xgr  VARCHAR2(64),
  xgsj TIMESTAMP(6)
)
;
comment on table SYS_HSGS
  is '���㹫ʽ';
comment on column SYS_HSGS.id
  is 'ID';
comment on column SYS_HSGS.cx
  is '����';
comment on column SYS_HSGS.lx
  is '����';
comment on column SYS_HSGS.nr
  is '����';
comment on column SYS_HSGS.je
  is '���';
comment on column SYS_HSGS.cjr
  is '������';
comment on column SYS_HSGS.cjsj
  is '����ʱ��';
comment on column SYS_HSGS.xgr
  is '�޸���';
comment on column SYS_HSGS.xgsj
  is '�޸�ʱ��';
alter table SYS_HSGS
  add constraint PK_SYS_HSGS primary key (ID);

prompt Creating SYS_JGSQLB...
create table SYS_JGSQLB
(
  id   VARCHAR2(32) not null,
  jgdm VARCHAR2(20),
  fwdm VARCHAR2(32),
  gndm VARCHAR2(32),
  cjsj TIMESTAMP(6),
  cjr  VARCHAR2(64),
  xgsj TIMESTAMP(6),
  xgr  VARCHAR2(64),
  yxq  TIMESTAMP(6)
)
;
comment on column SYS_JGSQLB.id
  is 'ID';
comment on column SYS_JGSQLB.jgdm
  is '��������';
comment on column SYS_JGSQLB.fwdm
  is '�������';
comment on column SYS_JGSQLB.gndm
  is '���ܴ���';
comment on column SYS_JGSQLB.cjsj
  is '����ʱ��';
comment on column SYS_JGSQLB.cjr
  is '������';
comment on column SYS_JGSQLB.xgsj
  is '�޸�ʱ��';
comment on column SYS_JGSQLB.xgr
  is '�޸���';
comment on column SYS_JGSQLB.yxq
  is '��Ч��';

prompt Creating SYS_JS_FW...
create table SYS_JS_FW
(
  "id" VARCHAR2(32) not null,
  jsdm VARCHAR2(32),
  fwdm VARCHAR2(32),
  cjsj TIMESTAMP(6),
  cjr  VARCHAR2(64)
)
;
comment on column SYS_JS_FW."id"
  is 'id';
comment on column SYS_JS_FW.jsdm
  is '��ɫ����';
comment on column SYS_JS_FW.fwdm
  is '�������';
comment on column SYS_JS_FW.cjsj
  is '����ʱ��';
comment on column SYS_JS_FW.cjr
  is '������';

prompt Creating SYS_JS_GN...
create table SYS_JS_GN
(
  id    VARCHAR2(32) not null,
  jsdm  VARCHAR2(32),
  gndm  VARCHAR2(32),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  fwdm  VARCHAR2(32),
  fgndm VARCHAR2(32)
)
;
comment on column SYS_JS_GN.id
  is 'id';
comment on column SYS_JS_GN.jsdm
  is '��ɫ����';
comment on column SYS_JS_GN.gndm
  is '���ܴ���';
comment on column SYS_JS_GN.cjsj
  is '����ʱ��';
comment on column SYS_JS_GN.cjr
  is '������';
comment on column SYS_JS_GN.fwdm
  is '�������';
comment on column SYS_JS_GN.fgndm
  is '�����ܴ���';
alter table SYS_JS_GN
  add constraint PK_SYS_JS_GN primary key (ID);

prompt Creating SYS_JZGXX...
create table SYS_JZGXX
(
  id   VARCHAR2(32) not null,
  xm   VARCHAR2(32),
  xb   CHAR(2),
  zw   VARCHAR2(16),
  zjhm VARCHAR2(16),
  jgdm VARCHAR2(32),
  jdmc VARCHAR2(32),
  zglx CHAR(4),
  cjr  VARCHAR2(64),
  cjsj TIMESTAMP(6),
  xgr  VARCHAR2(64),
  xgsj TIMESTAMP(6),
  sjhm VARCHAR2(16)
)
;
comment on column SYS_JZGXX.id
  is 'ID';
comment on column SYS_JZGXX.xm
  is '����';
comment on column SYS_JZGXX.xb
  is '�Ա�';
comment on column SYS_JZGXX.zw
  is 'ְ��';
comment on column SYS_JZGXX.zjhm
  is '֤������';
comment on column SYS_JZGXX.jgdm
  is '��������';
comment on column SYS_JZGXX.jdmc
  is '��������';
comment on column SYS_JZGXX.zglx
  is 'ְ������';
comment on column SYS_JZGXX.cjr
  is '������';
comment on column SYS_JZGXX.cjsj
  is '����ʱ��';
comment on column SYS_JZGXX.xgr
  is '�޸���';
comment on column SYS_JZGXX.xgsj
  is '�޸�ʱ��';
comment on column SYS_JZGXX.sjhm
  is '�ֻ�����';
alter table SYS_JZGXX
  add constraint PK_SYS_JZGXX primary key (ID);

prompt Creating SYS_MESSAGE...
create table SYS_MESSAGE
(
  id            VARCHAR2(32),
  creation_time TIMESTAMP(6) default SYSDATE,
  message       VARCHAR2(1000),
  type          CHAR(3),
  title         VARCHAR2(200),
  status        CHAR(3) default 0,
  send_count    CHAR(3) default 0,
  send_time     TIMESTAMP(6) default SYSDATE,
  remark        VARCHAR2(2000),
  sendee_code   VARCHAR2(200),
  biz_id        VARCHAR2(32)
)
;
comment on table SYS_MESSAGE
  is '��Ϣ��-ƽ̨������Ϣ����';
comment on column SYS_MESSAGE.id
  is '����';
comment on column SYS_MESSAGE.creation_time
  is '����ʱ��';
comment on column SYS_MESSAGE.message
  is '���ű��⣬��ҵ��ͬ��Ҳ�������ģ�Ҳ����JSON����';
comment on column SYS_MESSAGE.type
  is '1������';
comment on column SYS_MESSAGE.title
  is '����';
comment on column SYS_MESSAGE.status
  is '0��δ���� 1���ѷ���  2������ʧ��';
comment on column SYS_MESSAGE.send_count
  is '���ʹ���';
comment on column SYS_MESSAGE.send_time
  is '����ʱ��';
comment on column SYS_MESSAGE.remark
  is '��ע';
comment on column SYS_MESSAGE.sendee_code
  is '���շ����(�������ֻ��š�΢����open_id)';
comment on column SYS_MESSAGE.biz_id
  is 'ҵ����(����ҵ��) (���ű���ģ��ID)';

prompt Creating SYS_PTFW...
create table SYS_PTFW
(
  fw_id  VARCHAR2(32) not null,
  fwmc   VARCHAR2(32),
  fwdm   VARCHAR2(32),
  cjsj   TIMESTAMP(6),
  cjr    VARCHAR2(64),
  xgsj   TIMESTAMP(6),
  xgr    VARCHAR2(64),
  zt     CHAR(2),
  api_qz VARCHAR2(64),
  tb     VARCHAR2(32)
)
;
comment on column SYS_PTFW.fw_id
  is '����id';
comment on column SYS_PTFW.fwmc
  is '��������';
comment on column SYS_PTFW.fwdm
  is '�������';
comment on column SYS_PTFW.cjsj
  is '����ʱ��';
comment on column SYS_PTFW.cjr
  is '������';
comment on column SYS_PTFW.xgsj
  is '�޸�ʱ��';
comment on column SYS_PTFW.xgr
  is '�޸���';
comment on column SYS_PTFW.zt
  is '״̬';
comment on column SYS_PTFW.api_qz
  is 'APIǰ׺';
comment on column SYS_PTFW.tb
  is 'ͼ��';
alter table SYS_PTFW
  add constraint PK_SYS_PTFW primary key (FW_ID);

prompt Creating SYS_PTJG...
create table SYS_PTJG
(
  jgdm  VARCHAR2(20) not null,
  jgmc  VARCHAR2(32),
  jglx  CHAR(2),
  zt    CHAR(2),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  gly   VARCHAR2(32),
  glyxm VARCHAR2(32),
  fjgdm VARCHAR2(32),
  bz    VARCHAR2(255),
  jgdj  NUMBER default 1,
  jgbm  VARCHAR2(64),
  jgsm  VARCHAR2(255)
)
;
comment on table SYS_PTJG
  is 'ƽ̨������';
comment on column SYS_PTJG.jgdm
  is '��������';
comment on column SYS_PTJG.jgmc
  is '��������';
comment on column SYS_PTJG.jglx
  is '��������';
comment on column SYS_PTJG.zt
  is '״̬';
comment on column SYS_PTJG.cjsj
  is '����ʱ��';
comment on column SYS_PTJG.cjr
  is '������';
comment on column SYS_PTJG.xgr
  is '�޸���';
comment on column SYS_PTJG.xgsj
  is '�޸�ʱ��';
comment on column SYS_PTJG.gly
  is '����Ա';
comment on column SYS_PTJG.glyxm
  is '����Ա����';
comment on column SYS_PTJG.fjgdm
  is '����������';
comment on column SYS_PTJG.bz
  is '��ע';
comment on column SYS_PTJG.jgdj
  is '�����ȼ�';
comment on column SYS_PTJG.jgbm
  is '��������';
comment on column SYS_PTJG.jgsm
  is '����˵��';
alter table SYS_PTJG
  add constraint PK_SYS_PTJG primary key (JGDM);

prompt Creating SYS_PTRZ...
create table SYS_PTRZ
(
  rz_id VARCHAR2(32) not null,
  czlx  VARCHAR2(32),
  czsj  TIMESTAMP(6),
  czr   VARCHAR2(64),
  dx_id VARCHAR2(32),
  dxlx  VARCHAR2(32),
  cs    VARCHAR2(1024),
  jg    VARCHAR2(1112),
  zxsj  NUMBER,
  sm    VARCHAR2(32),
  ff    VARCHAR2(64)
)
;
comment on table SYS_PTRZ
  is 'ƽ̨��־��';
comment on column SYS_PTRZ.rz_id
  is '��־id';
comment on column SYS_PTRZ.czlx
  is '��������';
comment on column SYS_PTRZ.czsj
  is '����ʱ��';
comment on column SYS_PTRZ.czr
  is '������';
comment on column SYS_PTRZ.dx_id
  is '����id';
comment on column SYS_PTRZ.dxlx
  is '��������';
comment on column SYS_PTRZ.cs
  is '����';
comment on column SYS_PTRZ.jg
  is '���';
comment on column SYS_PTRZ.zxsj
  is 'ִ��ʱ��';
comment on column SYS_PTRZ.sm
  is '˵��';
comment on column SYS_PTRZ.ff
  is '����';
alter table SYS_PTRZ
  add constraint PK_SYS_PTRZ primary key (RZ_ID);

prompt Creating SYS_RLB...
create table SYS_RLB
(
  rqdm VARCHAR2(8) not null,
  xq   NUMBER,
  zt   CHAR(2),
  rq   VARCHAR2(16)
)
;
comment on table SYS_RLB
  is '������';
comment on column SYS_RLB.rqdm
  is '���ڴ���';
comment on column SYS_RLB.xq
  is '����';
comment on column SYS_RLB.zt
  is '״̬';
comment on column SYS_RLB.rq
  is '����';
alter table SYS_RLB
  add constraint PK_SYS_RLB primary key (RQDM);

prompt Creating SYS_WXYH...
create table SYS_WXYH
(
  openid VARCHAR2(32) not null,
  xm     VARCHAR2(32),
  dh     VARCHAR2(16),
  yhlx   CHAR(2),
  xy     VARCHAR2(16),
  zjhm   VARCHAR2(16),
  zw     VARCHAR2(16),
  cjsj   TIMESTAMP(6),
  cjr    VARCHAR2(64),
  xgsj   TIMESTAMP(6),
  xgr    VARCHAR2(64),
  jgdm   VARCHAR2(32),
  jgmc   VARCHAR2(32)
)
;
comment on column SYS_WXYH.openid
  is 'OPENID';
comment on column SYS_WXYH.xm
  is '����';
comment on column SYS_WXYH.dh
  is '�绰';
comment on column SYS_WXYH.yhlx
  is '�û�����';
comment on column SYS_WXYH.xy
  is 'ѧԺ';
comment on column SYS_WXYH.zjhm
  is '֤������';
comment on column SYS_WXYH.zw
  is 'ְ��';
comment on column SYS_WXYH.cjsj
  is '����ʱ��';
comment on column SYS_WXYH.cjr
  is '������';
comment on column SYS_WXYH.xgsj
  is '�޸�ʱ��';
comment on column SYS_WXYH.xgr
  is '�޸���';
alter table SYS_WXYH
  add constraint PK_SYS_WXYH primary key (OPENID);

prompt Creating SYS_XXTS...
create table SYS_XXTS
(
  xx_id VARCHAR2(32) not null,
  xxnr  VARCHAR2(1000),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  xgr   VARCHAR2(64),
  kjfw  VARCHAR2(32),
  kssj  TIMESTAMP(6),
  jssj  TIMESTAMP(6)
)
;
comment on column SYS_XXTS.xx_id
  is '��Ϣid';
comment on column SYS_XXTS.xxnr
  is '��Ϣ����';
comment on column SYS_XXTS.cjsj
  is '����ʱ��';
comment on column SYS_XXTS.cjr
  is '������';
comment on column SYS_XXTS.xgsj
  is '�޸�ʱ��';
comment on column SYS_XXTS.xgr
  is '�޸���';
comment on column SYS_XXTS.kjfw
  is '�ɼ���Χ';
comment on column SYS_XXTS.kssj
  is '��ʼʱ��';
comment on column SYS_XXTS.jssj
  is '����ʱ��';
alter table SYS_XXTS
  add constraint PK_SYS_XXTS primary key (XX_ID);

prompt Creating SYS_YH_JS...
create table SYS_YH_JS
(
  yhjs_id VARCHAR2(32) not null,
  yh_id   VARCHAR2(32),
  js_id   VARCHAR2(32),
  cjr     VARCHAR2(64),
  cjsj    TIMESTAMP(6)
)
;
comment on column SYS_YH_JS.yhjs_id
  is '�û���ɫ������id';
comment on column SYS_YH_JS.yh_id
  is '�û�id';
comment on column SYS_YH_JS.js_id
  is '��ɫid';
comment on column SYS_YH_JS.cjr
  is '������';
comment on column SYS_YH_JS.cjsj
  is '����ʱ��';
alter table SYS_YH_JS
  add constraint PK_SYS_YH_JS primary key (YHJS_ID);

prompt Creating SYS_YH_XX...
create table SYS_YH_XX
(
  "id"  VARCHAR2(32) not null,
  yh_id VARCHAR2(32),
  xx_id VARCHAR2(32),
  cjsj  TIMESTAMP(6),
  zt    CHAR(2)
)
;
comment on column SYS_YH_XX."id"
  is 'id';
comment on column SYS_YH_XX.yh_id
  is '�û�id';
comment on column SYS_YH_XX.xx_id
  is '��Ϣid';
comment on column SYS_YH_XX.cjsj
  is '����ʱ��';
comment on column SYS_YH_XX.zt
  is '״̬';

prompt Creating SYS_YJFK...
create table SYS_YJFK
(
  yj_id VARCHAR2(32) not null,
  yh_id VARCHAR2(32),
  nr    VARCHAR2(1000),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  zt    CHAR(2),
  yjlx  CHAR(2),
  cljg  VARCHAR2(512),
  lxrxm VARCHAR2(32),
  lxfs  VARCHAR2(32),
  xgr   VARCHAR2(32),
  xgsj  TIMESTAMP(6)
)
;
comment on column SYS_YJFK.yj_id
  is '���id';
comment on column SYS_YJFK.yh_id
  is '�û�id';
comment on column SYS_YJFK.nr
  is '����';
comment on column SYS_YJFK.cjsj
  is '����ʱ��';
comment on column SYS_YJFK.cjr
  is '������';
comment on column SYS_YJFK.zt
  is '״̬';
comment on column SYS_YJFK.yjlx
  is '�������';
comment on column SYS_YJFK.cljg
  is '������';
comment on column SYS_YJFK.lxrxm
  is '��ϵ������';
comment on column SYS_YJFK.lxfs
  is '��ϵ��ʽ';
comment on column SYS_YJFK.xgr
  is '�޸���';
comment on column SYS_YJFK.xgsj
  is '�޸�ʱ��';
alter table SYS_YJFK
  add constraint PK_SYS_YJFK primary key (YJ_ID);

prompt Creating SYS_YXHDWJ...
create table SYS_YXHDWJ
(
  id    VARCHAR2(32) not null,
  hd_id VARCHAR2(32),
  wjlx  VARCHAR2(32),
  wjlj  VARCHAR2(255),
  wllj  VARCHAR2(255),
  cjsj  TIMESTAMP(6),
  cjr   VARCHAR2(64),
  xgsj  TIMESTAMP(6),
  xgr   VARCHAR2(64)
)
;
comment on column SYS_YXHDWJ.id
  is 'ID';
comment on column SYS_YXHDWJ.hd_id
  is '�id';
comment on column SYS_YXHDWJ.wjlx
  is '�ļ�����';
comment on column SYS_YXHDWJ.wjlj
  is '�ļ�·��';
comment on column SYS_YXHDWJ.wllj
  is '����·��';
comment on column SYS_YXHDWJ.cjsj
  is '����ʱ��';
comment on column SYS_YXHDWJ.cjr
  is '������';
comment on column SYS_YXHDWJ.xgsj
  is '�޸�ʱ��';
comment on column SYS_YXHDWJ.xgr
  is '�޸���';
alter table SYS_YXHDWJ
  add constraint PK_SYS_YXHDWJ primary key (ID);

prompt Creating SYS_ZDLM...
create table SYS_ZDLM
(
  lmdm VARCHAR2(32) not null,
  lmmc VARCHAR2(32),
  cjsj TIMESTAMP(6),
  cjr  VARCHAR2(64),
  xgsj TIMESTAMP(6),
  xgr  VARCHAR2(64),
  qz   FLOAT
)
;
comment on table SYS_ZDLM
  is 'ƽ̨�ֵ���Ŀ��(�ֵ����)';
comment on column SYS_ZDLM.lmdm
  is '��Ŀ����';
comment on column SYS_ZDLM.lmmc
  is '��Ŀ����';
comment on column SYS_ZDLM.cjsj
  is '����ʱ��';
comment on column SYS_ZDLM.cjr
  is '������';
comment on column SYS_ZDLM.xgsj
  is '�޸�ʱ��';
comment on column SYS_ZDLM.xgr
  is '�޸���';
comment on column SYS_ZDLM.qz
  is 'Ȩ��';
alter table SYS_ZDLM
  add constraint PK_ZDLM primary key (LMDM);

prompt Creating SYS_ZDXM...
create table SYS_ZDXM
(
  zd_id  VARCHAR2(32) not null,
  zdlmdm VARCHAR2(32),
  zddm   VARCHAR2(32),
  zdmc   VARCHAR2(32),
  qz     FLOAT(8),
  cjsj   TIMESTAMP(6),
  cjr    VARCHAR2(64),
  by1    VARCHAR2(32),
  by2    VARCHAR2(32),
  by3    VARCHAR2(32)
)
;
comment on table SYS_ZDXM
  is 'ƽ̨�ֵ��';
comment on column SYS_ZDXM.zd_id
  is '�ֵ�id';
comment on column SYS_ZDXM.zdlmdm
  is '�ֵ���Ŀ����';
comment on column SYS_ZDXM.zddm
  is '�ֵ����';
comment on column SYS_ZDXM.zdmc
  is '�ֵ�����';
comment on column SYS_ZDXM.qz
  is 'Ȩ��';
comment on column SYS_ZDXM.cjsj
  is '����ʱ��';
comment on column SYS_ZDXM.cjr
  is '������';
comment on column SYS_ZDXM.by1
  is '����1';
comment on column SYS_ZDXM.by2
  is '����2';
comment on column SYS_ZDXM.by3
  is '����3';
alter table SYS_ZDXM
  add constraint PK_SYS_ZDXM primary key (ZD_ID);

prompt Disabling triggers for CL_BXJZ...
alter table CL_BXJZ disable all triggers;
prompt Disabling triggers for CL_CD...
alter table CL_CD disable all triggers;
prompt Disabling triggers for CL_CL...
alter table CL_CL disable all triggers;
prompt Disabling triggers for CL_CLYXJL...
alter table CL_CLYXJL disable all triggers;
prompt Disabling triggers for CL_CSSD...
alter table CL_CSSD disable all triggers;
prompt Disabling triggers for CL_DD...
alter table CL_DD disable all triggers;
prompt Disabling triggers for CL_DDLSB...
alter table CL_DDLSB disable all triggers;
prompt Disabling triggers for CL_DDRZ...
alter table CL_DDRZ disable all triggers;
prompt Disabling triggers for CL_DZWL...
alter table CL_DZWL disable all triggers;
prompt Disabling triggers for CL_DZWL_CL...
alter table CL_DZWL_CL disable all triggers;
prompt Disabling triggers for CL_GPS...
alter table CL_GPS disable all triggers;
prompt Disabling triggers for CL_GPS_LS...
alter table CL_GPS_LS disable all triggers;
prompt Disabling triggers for CL_JSY...
alter table CL_JSY disable all triggers;
prompt Disabling triggers for CL_LSC...
alter table CL_LSC disable all triggers;
prompt Disabling triggers for CL_LSDW...
alter table CL_LSDW disable all triggers;
prompt Disabling triggers for CL_PB...
alter table CL_PB disable all triggers;
prompt Disabling triggers for CL_SBYCSJJL...
alter table CL_SBYCSJJL disable all triggers;
prompt Disabling triggers for CL_SBYXSJJL...
alter table CL_SBYXSJJL disable all triggers;
prompt Disabling triggers for CL_SG...
alter table CL_SG disable all triggers;
prompt Disabling triggers for CL_SGWJ...
alter table CL_SGWJ disable all triggers;
prompt Disabling triggers for CL_SPK...
alter table CL_SPK disable all triggers;
prompt Disabling triggers for CL_XC...
alter table CL_XC disable all triggers;
prompt Disabling triggers for CL_XL...
alter table CL_XL disable all triggers;
prompt Disabling triggers for CL_XLCL...
alter table CL_XLCL disable all triggers;
prompt Disabling triggers for CL_XLZD...
alter table CL_XLZD disable all triggers;
prompt Disabling triggers for CL_YY...
alter table CL_YY disable all triggers;
prompt Disabling triggers for CL_ZD...
alter table CL_ZD disable all triggers;
prompt Disabling triggers for CL_ZDGL...
alter table CL_ZDGL disable all triggers;
prompt Disabling triggers for CL_ZNZP...
alter table CL_ZNZP disable all triggers;
prompt Disabling triggers for CL_ZP_XL...
alter table CL_ZP_XL disable all triggers;
prompt Disabling triggers for SYS_CLK_PTJS...
alter table SYS_CLK_PTJS disable all triggers;
prompt Disabling triggers for SYS_CLK_PTYH...
alter table SYS_CLK_PTYH disable all triggers;
prompt Disabling triggers for SYS_FWGN...
alter table SYS_FWGN disable all triggers;
prompt Disabling triggers for SYS_HDYX...
alter table SYS_HDYX disable all triggers;
prompt Disabling triggers for SYS_HSGS...
alter table SYS_HSGS disable all triggers;
prompt Disabling triggers for SYS_JGSQLB...
alter table SYS_JGSQLB disable all triggers;
prompt Disabling triggers for SYS_JS_FW...
alter table SYS_JS_FW disable all triggers;
prompt Disabling triggers for SYS_JS_GN...
alter table SYS_JS_GN disable all triggers;
prompt Disabling triggers for SYS_JZGXX...
alter table SYS_JZGXX disable all triggers;
prompt Disabling triggers for SYS_MESSAGE...
alter table SYS_MESSAGE disable all triggers;
prompt Disabling triggers for SYS_PTFW...
alter table SYS_PTFW disable all triggers;
prompt Disabling triggers for SYS_PTJG...
alter table SYS_PTJG disable all triggers;
prompt Disabling triggers for SYS_PTRZ...
alter table SYS_PTRZ disable all triggers;
prompt Disabling triggers for SYS_RLB...
alter table SYS_RLB disable all triggers;
prompt Disabling triggers for SYS_WXYH...
alter table SYS_WXYH disable all triggers;
prompt Disabling triggers for SYS_XXTS...
alter table SYS_XXTS disable all triggers;
prompt Disabling triggers for SYS_YH_JS...
alter table SYS_YH_JS disable all triggers;
prompt Disabling triggers for SYS_YH_XX...
alter table SYS_YH_XX disable all triggers;
prompt Disabling triggers for SYS_YJFK...
alter table SYS_YJFK disable all triggers;
prompt Disabling triggers for SYS_YXHDWJ...
alter table SYS_YXHDWJ disable all triggers;
prompt Disabling triggers for SYS_ZDLM...
alter table SYS_ZDLM disable all triggers;
prompt Disabling triggers for SYS_ZDXM...
alter table SYS_ZDXM disable all triggers;
prompt Truncating SYS_ZDXM...
truncate table SYS_ZDXM;
prompt Truncating SYS_ZDLM...
truncate table SYS_ZDLM;
prompt Truncating SYS_YXHDWJ...
truncate table SYS_YXHDWJ;
prompt Truncating SYS_YJFK...
truncate table SYS_YJFK;
prompt Truncating SYS_YH_XX...
truncate table SYS_YH_XX;
prompt Truncating SYS_YH_JS...
truncate table SYS_YH_JS;
prompt Truncating SYS_XXTS...
truncate table SYS_XXTS;
prompt Truncating SYS_WXYH...
truncate table SYS_WXYH;
prompt Truncating SYS_RLB...
truncate table SYS_RLB;
prompt Truncating SYS_PTRZ...
truncate table SYS_PTRZ;
prompt Truncating SYS_PTJG...
truncate table SYS_PTJG;
prompt Truncating SYS_PTFW...
truncate table SYS_PTFW;
prompt Truncating SYS_MESSAGE...
truncate table SYS_MESSAGE;
prompt Truncating SYS_JZGXX...
truncate table SYS_JZGXX;
prompt Truncating SYS_JS_GN...
truncate table SYS_JS_GN;
prompt Truncating SYS_JS_FW...
truncate table SYS_JS_FW;
prompt Truncating SYS_JGSQLB...
truncate table SYS_JGSQLB;
prompt Truncating SYS_HSGS...
truncate table SYS_HSGS;
prompt Truncating SYS_HDYX...
truncate table SYS_HDYX;
prompt Truncating SYS_FWGN...
truncate table SYS_FWGN;
prompt Truncating SYS_CLK_PTYH...
truncate table SYS_CLK_PTYH;
prompt Truncating SYS_CLK_PTJS...
truncate table SYS_CLK_PTJS;
prompt Truncating CL_ZP_XL...
truncate table CL_ZP_XL;
prompt Truncating CL_ZNZP...
truncate table CL_ZNZP;
prompt Truncating CL_ZDGL...
truncate table CL_ZDGL;
prompt Truncating CL_ZD...
truncate table CL_ZD;
prompt Truncating CL_YY...
truncate table CL_YY;
prompt Truncating CL_XLZD...
truncate table CL_XLZD;
prompt Truncating CL_XLCL...
truncate table CL_XLCL;
prompt Truncating CL_XL...
truncate table CL_XL;
prompt Truncating CL_XC...
truncate table CL_XC;
prompt Truncating CL_SPK...
truncate table CL_SPK;
prompt Truncating CL_SGWJ...
truncate table CL_SGWJ;
prompt Truncating CL_SG...
truncate table CL_SG;
prompt Truncating CL_SBYXSJJL...
truncate table CL_SBYXSJJL;
prompt Truncating CL_SBYCSJJL...
truncate table CL_SBYCSJJL;
prompt Truncating CL_PB...
truncate table CL_PB;
prompt Truncating CL_LSDW...
truncate table CL_LSDW;
prompt Truncating CL_LSC...
truncate table CL_LSC;
prompt Truncating CL_JSY...
truncate table CL_JSY;
prompt Truncating CL_GPS_LS...
truncate table CL_GPS_LS;
prompt Truncating CL_GPS...
truncate table CL_GPS;
prompt Truncating CL_DZWL_CL...
truncate table CL_DZWL_CL;
prompt Truncating CL_DZWL...
truncate table CL_DZWL;
prompt Truncating CL_DDRZ...
truncate table CL_DDRZ;
prompt Truncating CL_DDLSB...
truncate table CL_DDLSB;
prompt Truncating CL_DD...
truncate table CL_DD;
prompt Truncating CL_CSSD...
truncate table CL_CSSD;
prompt Truncating CL_CLYXJL...
truncate table CL_CLYXJL;
prompt Truncating CL_CL...
truncate table CL_CL;
prompt Truncating CL_CD...
truncate table CL_CD;
prompt Truncating CL_BXJZ...
truncate table CL_BXJZ;
prompt Loading CL_BXJZ...
prompt Table is empty
prompt Loading CL_CD...
prompt Table is empty
prompt Loading CL_CL...
prompt Table is empty
prompt Loading CL_CLYXJL...
prompt Table is empty
prompt Loading CL_CSSD...
prompt Table is empty
prompt Loading CL_DD...
prompt Table is empty
prompt Loading CL_DDLSB...
prompt Table is empty
prompt Loading CL_DDRZ...
prompt Table is empty
prompt Loading CL_DZWL...
prompt Table is empty
prompt Loading CL_DZWL_CL...
prompt Table is empty
prompt Loading CL_GPS...
prompt Table is empty
prompt Loading CL_GPS_LS...
prompt Table is empty
prompt Loading CL_JSY...
prompt Table is empty
prompt Loading CL_LSC...
prompt Table is empty
prompt Loading CL_LSDW...
prompt Table is empty
prompt Loading CL_PB...
prompt Table is empty
prompt Loading CL_SBYCSJJL...
prompt Table is empty
prompt Loading CL_SBYXSJJL...
prompt Table is empty
prompt Loading CL_SG...
prompt Table is empty
prompt Loading CL_SGWJ...
prompt Table is empty
prompt Loading CL_SPK...
prompt Table is empty
prompt Loading CL_XC...
prompt Table is empty
prompt Loading CL_XL...
prompt Table is empty
prompt Loading CL_XLCL...
prompt Table is empty
prompt Loading CL_XLZD...
prompt Table is empty
prompt Loading CL_YY...
prompt Table is empty
prompt Loading CL_ZD...
prompt Table is empty
prompt Loading CL_ZDGL...
prompt Table is empty
prompt Loading CL_ZNZP...
prompt Table is empty
prompt Loading CL_ZP_XL...
prompt Table is empty
prompt Loading SYS_CLK_PTJS...
prompt Table is empty
prompt Loading SYS_CLK_PTYH...
insert into SYS_CLK_PTYH (yhid, zh, mm, sjh, cjr, cjsj, xgr, xgsj, zt, jgdm, xm, lx, xb, zjhm, mmyxq, zw)
values ('1', 'admini', '4DA3BB20330A34F4', '�ֻ���', 'cjr', to_timestamp('16-03-2018 06:52:07.810000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '01', '100', '��������Ա', 'su', '1', '֤������', null, '����Ա');
commit;
prompt 1 records loaded
prompt Loading SYS_FWGN...
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('VehicleMonitoring', '�������', '1', to_timestamp('12-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', to_timestamp('13-08-2018 15:38:09.403000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'VehicleMonitoring', 'OperationMonitoring', '/', 'ios-car', '/api/cl/,/api/clzd/,/api/clsbyxsjjl/,/api/xc/', '/', 2);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('mergeVideo', '�ϲ���Ƶ', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:44:24.226000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'mergeVideo', 'VehicleScheduling', '/', 'md-git-branch', '/api/spk/', null, 10);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('CloudPhoto', '��ͼƬ��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:40:50.564000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'CloudPhoto', 'VehicleScheduling', '/', 'ios-cloudy', '/api/spk/', null, 12);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('abnormal', '�¼���¼', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:40:16.892000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'abnormal', 'VehicleScheduling', '//', 'ios-create', '/api/clsbyxsjjl/', '/', 14);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('OrderManagement', '��������', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('11-06-2018 10:24:51.777000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'OrderManagement', 'VehicleScheduling', '/', 'ios-pulse', '/', null, 7);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Establish', '��������', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:30:50.888000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Establish', 'OrderManagement', '/', 'ios-clipboard', '/api/dd/', null, 1);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('DriverManagement', '��ʻԱ����', '1', to_timestamp('21-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', to_timestamp('13-08-2018 15:37:51.513000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', ' ', 'DriverManagement', 'VehicleScheduling', '/', 'md-person', '/api/jsy/', null, 3);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-role', '��ɫ����', '1', to_timestamp('23-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', to_timestamp('24-07-2018 10:37:34.901000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', ' ', 'system-role', 'system', '/', 'ios-people', '/api/js/,/api/gn/', null, 2);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system', 'ϵͳ����', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:39:23.735000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system', null, '/', 'md-menu', '/', null, 1);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-user', '�û�����', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:41:09.517000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system-user', 'system', '/', 'md-person', '/api/yh/,/api/jg/getCurrentOrgTree', null, 1);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-framework', '��֯����', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:42:39.252000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system-framework', 'system', '/', 'md-git-network', '/api/gn/,/api/jg/', null, 3);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('ToExamine', '�������', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:37:49.438000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'ToExamine', 'OrderManagement', '/', 'md-document', '/api/dd/', null, 2);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Consult', '��������', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:37:22.922000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Consult', 'OrderManagement', '/', 'md-eye', '/api/dd/', null, 8);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Assignment', 'С���ɵ�', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:40:38.595000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Assignment', 'OrderManagement', '/', 'ios-car', '/api/dd/', null, 3);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Confirm', '˾��ȷ��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:38:19.485000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Confirm', 'OrderManagement', '/', 'md-person', '/api/dd/', null, 5);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('ShuttleBus', '�೵����', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:29:07.497000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'ShuttleBus', 'VehicleScheduling', '/', 'md-bus', '/', null, 8);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('SiteMaintenance', 'վ��ά��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:40:42.995000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'SiteMaintenance', 'ShuttleBus', '/', 'logo-chrome', '/api/clzd/', null, 12);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('LineMaintenance', '��·ά��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:39:56.782000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'LineMaintenance', 'ShuttleBus', '/', 'md-analytics', '/', null, 12);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Scheduling', '�����Ű�', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 16:29:53.841000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Scheduling', 'ShuttleBus', '/', 'ios-car', '/', null, 12);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('dz_Confirm', '�ӳ�ȷ��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:44:56.366000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'dz_Confirm', 'OrderManagement', '/', 'md-person', '/api/dd/', null, 6);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Assignment_max', '���ɵ�', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:45:24.975000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Assignment_max', 'OrderManagement', '/', 'md-bus', '/api/dd/', null, 4);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('FinancialSettlement', '�������', '1', to_timestamp('08-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:37:11.013000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'FinancialSettlement', null, 'FinancialSettlement', 'logo-usd', '/', '/', 3);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Reimbursement', '��������', '1', to_timestamp('08-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:37:15.122000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Reimbursement', 'FinancialSettlement', 'Reimbursement', 'logo-usd', '/api/bxjz/', '/', 4);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('ReceivablesManagement', '�տ����', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:29:43.378000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'ReceivablesManagement', 'FinancialSettlement', 'ReceivablesManagement', 'logo-usd', '/', '/', 1);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-dictionary', '�ֵ����', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('28-04-2018 10:58:40.869000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system-dictionary', 'system', null, 'ios-keypad', '/api/zd/', null, 4);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-ITSM', '�������', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('28-04-2018 10:59:05.744000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system-ITSM', 'system', null, 'ios-briefcase', '/api/fw/', null, 5);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-function', '���ܹ���', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('28-04-2018 10:58:06.447000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system-function', 'system', null, 'ios-cog', '/api/gn/', null, 6);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-daily', '��־����', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:46:53.396000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system-daily', 'system', '/', 'ios-create', '/api/rz/', null, 7);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-suggestions', '�������', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:47:07.615000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system-suggestions', 'system', '/', 'ios-create', '/api/yj/', null, 8);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('system-advertising', '�����', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:47:29.396000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'system-advertising', 'system', '/', 'ios-aperture', '/api/hd/', null, 11);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('VehicleScheduling', '��������', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('04-05-2018 18:02:37.283000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'VehicleScheduling', null, null, 'ios-cog', null, null, 2);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('vehicle-management', '��������', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:47:48.583000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'vehicle-management', 'VehicleScheduling', '/', 'ios-car', '/api/cl/', null, 1);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('SafeDriving', '��ȫ��ʻ', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:34:57.530000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'SafeDriving', 'Echarts', 'SafeDriving', 'ios-car', '/', '/', 1);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('FleetManagement', '���ӹ���', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:48:03.614000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '10', '3213213', 'FleetManagement', 'VehicleScheduling', '/', 'ios-car', '/api/cd/', '1111', 4);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('ElectronicFence', '����Χ��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:48:33.786000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'ElectronicFence', 'VehicleScheduling', '/', 'ios-globe-outline', '/api/dzwlCl/,/api/dzwl/', null, 5);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('OverspeedLimit', '�����趨', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:49:07.160000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'OverspeedLimit', 'VehicleScheduling', '/', 'ios-compass', '/api/cssd/', null, 6);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('CloudVideo', '����Ƶ��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:49:25.582000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'CloudVideo', 'VehicleScheduling', '/', 'ios-cloudy', '/api/spk/', null, 13);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('ScManage', 'У�͹���', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:50:00.019000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'ScManage', 'VehicleScheduling', '/', 'md-bus', '/api/xl/', null, 9);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Sc_SiteMaintenance', 'վ��ά��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:51:49.127000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Sc_SiteMaintenance', 'ScManage', '/', 'logo-chrome', '/api/clzd/', null, 8);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Sc_LineMaintenance', '��·ά��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:50:30.581000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Sc_LineMaintenance', 'ScManage', '/', 'md-git-commit', '/api/xl/', null, 9);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Sc_Scheduling', 'У���Ű�', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:43:16.618000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Sc_Scheduling', 'ScManage', '/', 'md-bus', '/api/pb/', null, 10);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('OperationMonitoring', '��Ӫ���', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:52:46.985000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'OperationMonitoring', null, '/', 'ios-leaf', '/api/', null, 11);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('BusMonitor', 'У�ͼ��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:53:01.345000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'BusMonitor', 'OperationMonitoring', '/', 'md-bus', '/api/clsbyxsjjl/,/api/xl/,/api/clzd/,/api/cl/', '/', 12);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('OrderStatistics', '����ͳ��', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:32:35.845000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'OrderStatistics', 'Echarts', 'OrderStatistics', 'ios-podium', '/', '/', 2);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('BusStatistics', '�೵ͳ��', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:32:29.595000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'BusStatistics', 'Echarts', 'BusStatistics', 'ios-podium', '/', '/', 3);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('TrafficStatistics', '����ͳ��', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:35:09.530000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'TrafficStatistics', 'Echarts', 'TrafficStatistics', 'ios-podium', '/', '/', 4);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('TerminalAnomaly', '�ն��쳣', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:36:26.810000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'TerminalAnomaly', 'Echarts', 'TerminalAnomaly', 'ios-construct', '/', '/', 5);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('OverspeedStatistics', '����ͳ��', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('09-05-2018 15:11:55.149000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'OverspeedStatistics', 'Echarts', 'OverspeedStatistics', 'ios-timer-outline', '/', '/', 7);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('CollectionStatistics', '�տ�ͳ��', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('09-05-2018 13:09:19.967000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'CollectionStatistics', 'Echarts', 'CollectionStatistics', 'ios-pie-outline', '/', '/', 9);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('PaymentStatistics', '����ͳ��', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:36:48.498000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'PaymentStatistics', 'Echarts', 'PaymentStatistics', 'ios-podium', '/', '/', 10);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('ReimbursementStatistics', '����ͳ��', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 16:01:45.229000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'ReimbursementStatistics', 'Echarts', 'ReimbursementStatistics', 'logo-usd', '/', '/', 11);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('TemporaryCarManagement', '��ʱ������', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('11-06-2018 10:23:09.105000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'TemporaryCarManagement', 'VehicleScheduling', 'TemporaryCarManagement', 'ios-stopwatch-outline', '/', '/', 2);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('UnitManagement', '��λ����', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 16:36:41.734000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'UnitManagement', 'TemporaryCarManagement', 'UnitManagement', 'md-flag', '/', '/', 1);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('VehicleManagement', '��������', '1', to_timestamp('09-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 16:29:22.356000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'VehicleManagement', 'TemporaryCarManagement', 'VehicleManagement', 'ios-car', '/', '/', 2);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('terminal', '�ն˹���', '1', to_timestamp('27-07-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:27:46.380000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'terminal', null, '/', 'ios-home', '/', '/', 2);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('AccidentManagement', '�¹ʹ���', '1', to_timestamp('17-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:27:52.833000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'AccidentManagement', 'VehicleScheduling', 'AccidentManagement', 'ios-home', '/api/sg', '/', 16);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('terminal-station', '����վ��', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:53:44.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'terminal-station', 'terminal', '/', 'ios-funnel', '/api/znzp/', '/', 10);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('terminal-car', '�豸�ն�', '1', to_timestamp('09-04-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '��ʼ����', to_timestamp('13-08-2018 15:54:03.203000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'terminal-car', 'terminal', '/', 'logo-android', '/api/zdgl/', '/', 1);
insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('Echarts', 'ͳ�Ʒ���', '1', to_timestamp('07-08-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', to_timestamp('13-08-2018 15:21:54.009000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '00', null, 'Echarts', null, '/', 'ios-home', '/', '/', 8);
commit;
prompt 59 records loaded
prompt Loading SYS_HDYX...
insert into SYS_HDYX (hd_id, kssj, jssj, hdlx, jgdm, zt, cjr, cjsj, xgr, xgsj, hdbt, url, wjlx, wz)
values ('491556765465313280', to_timestamp('18-09-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('19-09-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '00', '100', '10', '1-��������Ա', to_timestamp('18-09-2018 10:31:05.570000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, '����', '10.113.4.70:9090/index.html', '00', '10');
commit;
prompt 1 records loaded
prompt Loading SYS_HSGS...
prompt Table is empty
prompt Loading SYS_JGSQLB...
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154467897344', '100043', null, 'system-advertising', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154467897345', '100043', null, 'FinancialSettlement', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154467897346', '100043', null, 'Reimbursement', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154467897347', '100043', null, 'ReceivablesManagement', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('472341516761694209', '100002', null, 'system-role', to_timestamp('27-07-2018 09:56:33.277000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('472341516761694210', '100002', null, 'BusMonitor', to_timestamp('27-07-2018 09:56:33.277000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('472341516761694211', '100002', null, 'Reimbursement', to_timestamp('27-07-2018 09:56:33.277000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('472368229168185344', '100', '1', 'terminal', to_timestamp('27-07-2018 11:42:42.011000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('474934400564330496', '100', '1', 'Echarts', to_timestamp('03-08-2018 13:39:44.949000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395182997110784', '100007', null, 'VehicleMonitoring', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183060025344', '100007', null, 'mergeVideo', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('472368229168185342', '100', '1', 'terminal-car', to_timestamp('27-07-2018 11:42:42.011000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395438581219328', '100045', null, 'terminal', to_timestamp('07-08-2018 14:25:23.557000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183060025345', '100007', null, 'CloudPhoto', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('472341516761694208', '100002', null, 'system', to_timestamp('27-07-2018 09:56:33.277000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183060025346', '100007', null, 'abnormal', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183060025347', '100007', null, 'OrderManagement', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183060025348', '100007', null, 'Establish', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183127134208', '100007', null, 'DriverManagement', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183127134209', '100007', null, 'ToExamine', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183127134210', '100007', null, 'Consult', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183127134211', '100007', null, 'Assignment', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183127134212', '100007', null, 'Confirm', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183127134213', '100007', null, 'ShuttleBus', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183190048768', '100007', null, 'SiteMaintenance', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183190048769', '100007', null, 'LineMaintenance', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183190048770', '100007', null, 'Scheduling', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183190048771', '100007', null, 'dz_Confirm', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183190048772', '100007', null, 'VehicleScheduling', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183190048773', '100007', null, 'vehicle-management', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183190048774', '100007', null, 'FleetManagement', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183257157632', '100007', null, 'ElectronicFence', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183257157633', '100007', null, 'OverspeedLimit', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183257157634', '100007', null, 'CloudVideo', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183257157635', '100007', null, 'ScManage', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183257157636', '100007', null, 'Sc_SiteMaintenance', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183257157637', '100007', null, 'Sc_LineMaintenance', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183257157638', '100007', null, 'Sc_Scheduling', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183324266496', '100007', null, 'OperationMonitoring', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183324266497', '100007', null, 'BusMonitor', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183324266498', '100007', null, 'TemporaryCarManagement', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183324266499', '100007', null, 'UnitManagement', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183324266500', '100007', null, 'VehicleManagement', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395183324266501', '100007', null, 'AccidentManagement', to_timestamp('07-08-2018 14:24:22.621000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('489809381471289344', '100014', null, 'VehicleMonitoring', to_timestamp('13-09-2018 14:47:36.745000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476395438581219329', '100045', null, 'terminal-car', to_timestamp('07-08-2018 14:25:23.557000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476438771357712384', '100045035', null, 'terminal-car', to_timestamp('07-08-2018 17:17:34.881000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476395819071700992-122', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476438792199208960', '100045034', null, 'terminal', to_timestamp('07-08-2018 17:17:39.865000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476395819071700992-122', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('489809381471289345', '100014', null, 'terminal', to_timestamp('13-09-2018 14:47:36.745000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476038005048475648', '100', '1', '1', to_timestamp('06-08-2018 14:45:04.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('472368229168185341', '100', '1', 'terminal-car', to_timestamp('27-07-2018 11:42:42.011000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476438771294797824', '100045035', null, 'terminal', to_timestamp('07-08-2018 17:17:34.881000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476395819071700992-122', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476514058221125632', '100', '1', 'Echarts', to_timestamp('07-08-2018 22:16:44.683000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476438792199208961', '100045034', null, 'terminal-car', to_timestamp('07-08-2018 17:17:39.865000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476395819071700992-122', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476687271119028224', '100044', null, 'OperationMonitoring', to_timestamp('08-08-2018 09:45:01.856000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476687271119028225', '100044', null, 'VehicleMonitoring', to_timestamp('08-08-2018 09:45:01.856000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476687271119028226', '100044', null, 'BusMonitor', to_timestamp('08-08-2018 09:45:01.856000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476687271119028227', '100044', null, 'terminal', to_timestamp('08-08-2018 09:45:01.856000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('476687271119028228', '100044', null, 'terminal-car', to_timestamp('08-08-2018 09:45:01.856000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('489809381404180480', '100014', null, 'OperationMonitoring', to_timestamp('13-09-2018 14:47:36.745000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('489809381471289346', '100014', null, 'terminal-car', to_timestamp('13-09-2018 14:47:36.745000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154404982789', '100043', null, 'system-propelling', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091727192948736', '100', '1', 'FleetManagement', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091727465578496', '100', '1', 'VehicleMonitoring', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091727771762688', '100', '1', 'abnormal', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091726173732864', '100', '1', 'system', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091727046148096', '100', '1', 'system-user', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091728732258304', '100', '1', 'system-role', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091728845504512', '100', '1', 'VehicleScheduling', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091728954556416', '100', '1', 'vehicle-management', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091729067802624', '100', '1', 'system-framework', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091729483038720', '100', '1', 'system-dictionary', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091729596284928', '100', '1', 'ElectronicFence', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091731169148928', '100', '1', 'DriverManagement', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091731290783744', '100', '1', 'system-ITSM', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091731399835648', '100', '1', 'OverspeedLimit', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091731504693248', '100', '1', 'mergeVideo', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091731609550848', '100', '1', 'CloudPhoto', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091731722797056', '100', '1', 'system-function', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091732045758464', '100', '1', 'CloudVideo', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091732456800256', '100', '1', 'OrderManagement', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091732561657856', '100', '1', 'Establish', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091732670709760', '100', '1', 'ToExamine', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091732796538880', '100', '1', 'Consult', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091732901396480', '100', '1', 'Assignment', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091733018836992', '100', '1', 'Confirm', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091734679781376', '100', '1', 'ShuttleBus', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091735225040896', '100', '1', 'dz_Confirm', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091736755961856', '100', '1', 'system-daily', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091737028591616', '100', '1', 'ScManage', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091737464799232', '100', '1', 'system-suggestions', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091737582239744', '100', '1', 'Sc_SiteMaintenance', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091737762594816', '100', '1', 'system-notice', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091738840530944', '100', '1', 'Sc_LineMaintenance', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091739083800576', '100', '1', 'system-propelling', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091739230601216', '100', '1', 'Sc_Scheduling', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091739465482240', '100', '1', 'system-advertising', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091739570339840', '100', '1', 'OperationMonitoring', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091739696168960', '100', '1', 'SiteMaintenance', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091740274982912', '100', '1', 'LineMaintenance', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
commit;
prompt 100 records committed...
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091740375646208', '100', '1', 'Scheduling', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443091740480503808', '100', '1', 'BusMonitor', to_timestamp('07-05-2018 16:48:19.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443161825626816512', '100', '11', '11', to_timestamp('07-05-2018 21:26:52.778000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443514721631469568', '100', '1', 'FinancialSettlement', to_timestamp('08-05-2018 20:49:09.727000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443515028474167296', '100', '1', 'Reimbursement', to_timestamp('08-05-2018 20:50:22.899000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443737871900213248', '100', '1', 'ReceivablesManagement', to_timestamp('09-05-2018 11:35:52.912000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443738321554767872', '100', '1', 'PaymentManagement', to_timestamp('09-05-2018 11:37:40.118000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443738576300015616', '100', '1', 'AccountingFormula', to_timestamp('09-05-2018 11:38:40.838000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443739121047830528', '100', '1', 'Echarts', to_timestamp('09-05-2018 11:40:50.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443739895245045760', '100', '1', 'SafeDriving', to_timestamp('09-05-2018 11:43:55.315000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443740102347194368', '100', '1', 'OrderStatistics', to_timestamp('09-05-2018 11:44:44.692000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443740291619356672', '100', '1', 'BusStatistics', to_timestamp('09-05-2018 11:45:29.818000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443740475124350976', '100', '1', 'TrafficStatistics', to_timestamp('09-05-2018 11:46:13.569000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443740677700845568', '100', '1', 'TerminalAnomaly', to_timestamp('09-05-2018 11:47:01.867000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443740845934379008', '100', '1', 'VehicleDetails', to_timestamp('09-05-2018 11:47:41.962000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443741009910693888', '100', '1', 'OverspeedStatistics', to_timestamp('09-05-2018 11:48:21.072000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443741217797177344', '100', '1', 'VehicleAccident', to_timestamp('09-05-2018 11:49:10.636000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443741714767675392', '100', '1', 'CollectionStatistics', to_timestamp('09-05-2018 11:51:09.123000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443741898012622848', '100', '1', 'PaymentStatistics', to_timestamp('09-05-2018 11:51:52.812000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443742210559574016', '100', '1', 'ReimbursementStatistics', to_timestamp('09-05-2018 11:53:07.314000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443783929988644864', '100', '1', 'TemporaryCarManagement', to_timestamp('09-05-2018 14:38:54.016000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443784412799172608', '100', '1', 'UnitManagement', to_timestamp('09-05-2018 14:40:49.127000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443784655611625472', '100', '1', 'VehicleManagement', to_timestamp('09-05-2018 14:41:47.003000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('443785382023135232', '100', '1', 'AccidentManagement', to_timestamp('09-05-2018 14:44:40.192000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('446624800085901312', '100', '1', 'AccidentManagement', to_timestamp('17-05-2018 10:47:30.228000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167198777344', '100038', null, 'system-framework', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167198777345', '100038', null, 'system-suggestions', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167198777346', '100038', null, 'system-notice', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167198777347', '100038', null, 'system-propelling', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167198777348', '100038', null, 'system-advertising', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167198777349', '100038', null, 'VehicleScheduling', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167265886208', '100038', null, 'mergeVideo', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167265886209', '100038', null, 'CloudPhoto', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167265886210', '100038', null, 'abnormal', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167265886211', '100038', null, 'OrderManagement', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167265886212', '100038', null, 'Establish', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543127261184', '100038029', null, 'BusMonitor', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543223730176', '100038029', null, 'VehicleMonitoring', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543316004864', '100038029', null, 'BusMonitor', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543412473856', '100038029', null, 'VehicleScheduling', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543513137152', '100038029', null, 'DriverManagement', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543605411840', '100038029', null, 'OrderManagement', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543697686528', '100038029', null, 'dz_Confirm', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691542938517504', '100038029', null, 'OperationMonitoring', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543030792192', '100038029', null, 'VehicleMonitoring', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543789961216', '100038029', null, 'dz_Confirm', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543882235904', '100038029', null, 'DriverManagement', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691543978704896', '100038029', null, 'OrderManagement', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691544070979584', '100038029', null, 'dz_Confirm', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455691544171642880', '100038029', null, 'dz_Confirm', to_timestamp('11-06-2018 11:15:30.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167135862784', '100038', null, 'system-role', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167135862785', '100038', null, 'system-user', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167265886213', '100038', null, 'ToExamine', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167265886214', '100038', null, 'Consult', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167332995072', '100038', null, 'Assignment', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167332995073', '100038', null, 'Confirm', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167332995074', '100038', null, 'dz_Confirm', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167332995075', '100038', null, 'DriverManagement', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167332995076', '100038', null, 'ShuttleBus', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167332995077', '100038', null, 'SiteMaintenance', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167332995078', '100038', null, 'LineMaintenance', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167395909632', '100038', null, 'Scheduling', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167395909633', '100038', null, 'vehicle-management', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167395909634', '100038', null, 'FleetManagement', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167395909635', '100038', null, 'ElectronicFence', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167395909636', '100038', null, 'OverspeedLimit', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167395909637', '100038', null, 'CloudVideo', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167395909638', '100038', null, 'ScManage', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167463018496', '100038', null, 'Sc_SiteMaintenance', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167463018497', '100038', null, 'Sc_LineMaintenance', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167463018498', '100038', null, 'Sc_Scheduling', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167463018499', '100038', null, 'TemporaryCarManagement', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167463018500', '100038', null, 'UnitManagement', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167463018501', '100038', null, 'VehicleManagement', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167463018502', '100038', null, 'AccidentManagement', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167530127360', '100038', null, 'OperationMonitoring', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167530127361', '100038', null, 'VehicleMonitoring', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('456498167530127362', '100038', null, 'BusMonitor', to_timestamp('13-06-2018 16:40:44.371000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958650429538304', '100041', null, 'system', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958650530201600', '100041', null, 'system-role', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958650626670592', '100041', null, 'system-user', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958650723139584', '100041', null, 'system-framework', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958650815414272', '100041', null, 'system-dictionary', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958650937049088', '100041', null, 'system-ITSM', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651054489600', '100041', null, 'system-function', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651150958592', '100041', null, 'system-daily', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651247427584', '100041', null, 'system-suggestions', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651348090880', '100041', null, 'system-notice', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651436171264', '100041', null, 'system-propelling', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651528445952', '100041', null, 'system-advertising', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651620720640', '100041', null, 'FinancialSettlement', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651712995328', '100041', null, 'Reimbursement', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470958651805270016', '100041', null, 'ReceivablesManagement', to_timestamp('23-07-2018 14:21:32.251000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967491997204480', '100041031', null, 'system', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492114644992', '100041031', null, 'system-role', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492227891200', '100041031', null, 'system-user', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492324360192', '100041031', null, 'system-framework', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492416634880', '100041031', null, 'system-dictionary', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492504715264', '100041031', null, 'system-ITSM', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492605378560', '100041031', null, 'system-function', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
commit;
prompt 200 records committed...
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492693458944', '100041031', null, 'system-daily', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492827676672', '100041031', null, 'system-suggestions', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967492915757056', '100041031', null, 'system-notice', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967493054169088', '100041031', null, 'system-propelling', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967493146443776', '100041031', null, 'system-advertising', to_timestamp('23-07-2018 14:56:40.245000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967550675517440', '100041032', null, 'system-role', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967550776180736', '100041032', null, 'system-user', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967550872649728', '100041032', null, 'system-framework', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967550985895936', '100041032', null, 'system-dictionary', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967551086559232', '100041032', null, 'system-ITSM', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967551187222528', '100041032', null, 'system-function', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967551279497216', '100041032', null, 'system-daily', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967551367577600', '100041032', null, 'system-notice', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967551459852288', '100041032', null, 'system-propelling', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470967551552126976', '100041032', null, 'system-advertising', to_timestamp('23-07-2018 14:56:54.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118111850496', '100042', null, 'system', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118220902400', '100042', null, 'system-role', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118321565696', '100042', null, 'system-user', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118418034688', '100042', null, 'system-framework', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118510309376', '100042', null, 'system-dictionary', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118602584064', '100042', null, 'system-ITSM', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118703247360', '100042', null, 'system-function', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118803910656', '100042', null, 'system-daily', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995118908768256', '100042', null, 'system-suggestions', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995119001042944', '100042', null, 'system-notice', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995119101706240', '100042', null, 'system-propelling', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995119198175232', '100042', null, 'system-advertising', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995119294644224', '100042', null, 'FinancialSettlement', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995119391113216', '100042', null, 'Reimbursement', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('470995119487582208', '100042', null, 'ReceivablesManagement', to_timestamp('23-07-2018 16:46:26.824000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019421578559488', '100042033', null, 'system', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019421679222784', '100042033', null, 'system-role', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019421771497472', '100042033', null, 'system-user', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019421867966464', '100042033', null, 'system-framework', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019421972824064', '100042033', null, 'system-dictionary', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019422111236096', '100042033', null, 'system-ITSM', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019422224482304', '100042033', null, 'system-function', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019422325145600', '100042033', null, 'system-daily', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019422425808896', '100042033', null, 'system-suggestions', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019422526472192', '100042033', null, 'system-notice', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019422622941184', '100042033', null, 'system-propelling', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471019422719410176', '100042033', null, 'system-advertising', to_timestamp('23-07-2018 18:23:01.221000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154140741632', '100043', null, 'system', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154337873920', '100043', null, 'system-role', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154337873921', '100043', null, 'system-user', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154337873922', '100043', null, 'system-framework', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154337873923', '100043', null, 'system-dictionary', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154404982784', '100043', null, 'system-ITSM', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154404982785', '100043', null, 'system-function', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154404982786', '100043', null, 'system-daily', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154404982787', '100043', null, 'system-suggestions', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('471615154404982788', '100043', null, 'system-notice', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455334786915893248', '100002001', null, 'VehicleScheduling', to_timestamp('10-06-2018 11:37:52.912000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('455334787054305280', '100002001', null, 'mergeVideo', to_timestamp('10-06-2018 11:37:52.912000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
commit;
prompt 254 records loaded
prompt Loading SYS_JS_FW...
prompt Table is empty
prompt Loading SYS_JS_GN...
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476395819331747841', '476395819071700993', 'system-user', to_timestamp('07-08-2018 14:26:54.273000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476344777340616705', '476034372646993920', 'OperationMonitoring', to_timestamp('07-08-2018 11:04:04.976000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476344777340616706', '476034372646993920', 'VehicleMonitoring', to_timestamp('07-08-2018 11:04:04.976000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476395819268833280', '476395819071700993', 'system-framework', to_timestamp('07-08-2018 14:26:54.273000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476687486798528512', '476687486668505089', 'system-framework', to_timestamp('08-08-2018 09:45:53.247000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476687486798528513', '476687486668505089', 'system-role', to_timestamp('08-08-2018 09:45:53.247000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476687486798528514', '476687486668505089', 'system-user', to_timestamp('08-08-2018 09:45:53.247000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476687561968844800', '111', 'VehicleMonitoring', to_timestamp('08-08-2018 09:46:11.200000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476687486668505088-admin111', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476687561968844801', '111', 'OperationMonitoring', to_timestamp('08-08-2018 09:46:11.200000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476687486668505088-admin111', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476687561968844802', '111', 'BusMonitor', to_timestamp('08-08-2018 09:46:11.200000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476687486668505088-admin111', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476687561968844803', '111', 'terminal', to_timestamp('08-08-2018 09:46:11.200000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476687486668505088-admin111', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476687561968844804', '111', 'terminal-car', to_timestamp('08-08-2018 09:46:11.200000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476687486668505088-admin111', '1', 'terminal');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824961', 'sly123', 'CloudPhoto', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824962', 'sly123', 'abnormal', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824963', 'sly123', 'OrderManagement', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824964', 'sly123', 'Establish', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824965', 'sly123', 'ToExamine', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824966', 'sly123', 'Consult', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824967', 'sly123', 'Assignment', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824968', 'sly123', 'Confirm', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824969', 'sly123', 'dz_Confirm', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824970', 'sly123', 'FinancialSettlement', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824971', 'sly123', 'Reimbursement', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'FinancialSettlement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824972', 'sly123', 'ReceivablesManagement', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'FinancialSettlement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824973', 'sly123', 'VehicleScheduling', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438974978588672', '1233', 'terminal', to_timestamp('07-08-2018 17:18:23.443000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476395819071700992-122', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438974978588673', '1233', 'terminal-car', to_timestamp('07-08-2018 17:18:23.443000', 'dd-mm-yyyy hh24:mi:ss.ff'), '476395819071700992-122', '1', 'terminal');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018240', 'eee', 'system-role', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018241', 'eee', 'system', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018242', 'eee', 'system-user', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018243', 'eee', 'system-framework', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018244', 'eee', 'system-dictionary', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018245', 'eee', 'system-ITSM', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018246', 'eee', 'system-function', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018247', 'eee', 'system-daily', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018248', 'eee', 'system-suggestions', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018249', 'eee', 'system-notice', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018250', 'eee', 'system-propelling', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018251', 'eee', 'system-advertising', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018252', 'eee', 'VehicleScheduling', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018253', 'eee', 'vehicle-management', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018254', 'eee', 'DriverManagement', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018255', 'eee', 'FleetManagement', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018256', 'eee', 'ElectronicFence', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018257', 'eee', 'OverspeedLimit', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018258', 'eee', 'CloudVideo', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018259', 'eee', 'ScManage', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018260', 'eee', 'Sc_SiteMaintenance', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018261', 'eee', 'Sc_LineMaintenance', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('434305549195018262', 'eee', 'Sc_Scheduling', to_timestamp('13-04-2018 10:55:11.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665216', '1231', 'system-role', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665217', '1231', 'system', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665218', '1231', 'system-user', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665219', '1231', 'system-framework', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665220', '1231', 'system-dictionary', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665221', '1231', 'system-ITSM', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665222', '1231', 'system-function', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665223', '1231', 'system-daily', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665224', '1231', 'system-suggestions', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665225', '1231', 'system-notice', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665226', '1231', 'system-propelling', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435778198454665227', '1231', 'system-advertising', to_timestamp('17-04-2018 12:26:58.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286656', '1221', 'system-role', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286657', '1221', 'system', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286658', '1221', 'system-user', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286659', '1221', 'system-framework', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286660', '1221', 'system-dictionary', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286661', '1221', 'system-ITSM', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286662', '1221', 'system-function', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286663', '1221', 'system-daily', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286664', '1221', 'system-suggestions', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286665', '1221', 'system-notice', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286666', '1221', 'system-propelling', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('435908246457286667', '1221', 'system-advertising', to_timestamp('17-04-2018 21:03:44.739000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404935', '0000', 'system-propelling', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404936', '0000', 'system-advertising', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728064', '111222', 'system-role', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728065', '111222', 'system', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728066', '111222', 'system-user', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404928', '0000', 'system-role', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741504', '1212', 'system-role', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741505', '1212', 'system', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741506', '1212', 'system-user', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741507', '1212', 'system-framework', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741508', '1212', 'system-dictionary', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741509', '1212', 'system-ITSM', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741510', '1212', 'system-function', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741511', '1212', 'system-daily', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741512', '1212', 'system-suggestions', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741513', '1212', 'system-notice', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741514', '1212', 'system-propelling', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436255386190741515', '1212', 'system-advertising', to_timestamp('18-04-2018 20:03:09.301000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116352', '23123', 'system-role', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116353', '23123', 'system', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116354', '23123', 'system-user', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116355', '23123', 'system-framework', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116356', '23123', 'system-dictionary', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116357', '23123', 'system-ITSM', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116358', '23123', 'system-function', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116359', '23123', 'system-daily', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
commit;
prompt 100 records committed...
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116360', '23123', 'system-suggestions', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116361', '23123', 'system-notice', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116362', '23123', 'system-propelling', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436288327990116363', '23123', 'system-advertising', to_timestamp('18-04-2018 22:14:03.238000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561920', '12', 'system-role', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561921', '12', 'system', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561922', '12', 'system-user', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561923', '12', 'system-framework', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561924', '12', 'system-dictionary', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561925', '12', 'system-ITSM', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561926', '12', 'system-function', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561927', '12', 'system-daily', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561928', '12', 'system-suggestions', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561929', '12', 'system-notice', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561930', '12', 'system-propelling', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436284395796561931', '12', 'system-advertising', to_timestamp('18-04-2018 21:58:25.730000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404929', '0000', 'system-framework', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404930', '0000', 'system-dictionary', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404931', '0000', 'system-ITSM', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404932', '0000', 'system-function', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404933', '0000', 'system-suggestions', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436463544385404934', '0000', 'system-notice', to_timestamp('19-04-2018 09:50:18.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728067', '111222', 'system-framework', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728068', '111222', 'system-dictionary', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728069', '111222', 'system-ITSM', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728070', '111222', 'system-function', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728071', '111222', 'system-daily', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728072', '111222', 'system-suggestions', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728073', '111222', 'system-notice', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728074', '111222', 'system-propelling', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('436471966480728075', '111222', 'system-advertising', to_timestamp('19-04-2018 10:23:46.066000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311424', 'asdgrgftnA', 'system-role', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311425', 'asdgrgftnA', 'system', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311426', 'asdgrgftnA', 'system-user', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('438104660780253184', '123', 'VehicleMonitoring', to_timestamp('23-04-2018 22:31:30.721000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('438104660780253185', '123', 'OperationMonitoring', to_timestamp('23-04-2018 22:31:30.721000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('438104660780253186', '123', 'BusMonitor', to_timestamp('23-04-2018 22:31:30.721000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026084', '000000', 'Sc_SiteMaintenance', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026085', '000000', 'Sc_LineMaintenance', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026086', '000000', 'Sc_Scheduling', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026087', '000000', 'OperationMonitoring', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026088', '000000', 'BusMonitor', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311427', 'asdgrgftnA', 'system-framework', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311428', 'asdgrgftnA', 'system-dictionary', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311429', 'asdgrgftnA', 'system-ITSM', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311430', 'asdgrgftnA', 'system-function', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311431', 'asdgrgftnA', 'system-daily', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311432', 'asdgrgftnA', 'system-suggestions', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311433', 'asdgrgftnA', 'system-notice', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311434', 'asdgrgftnA', 'system-propelling', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('439154967182311435', 'asdgrgftnA', 'system-advertising', to_timestamp('26-04-2018 20:05:03.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), '438108538678542336-����001', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026048', '000000', 'VehicleMonitoring', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026049', '000000', 'mergeVideo', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026050', '000000', 'CloudPhoto', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026051', '000000', 'abnormal', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026052', '000000', 'OrderManagement', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026053', '000000', 'Establish', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026054', '000000', 'DriverManagement', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026055', '000000', 'system-role', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026056', '000000', 'system', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026057', '000000', 'system-user', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026058', '000000', 'system-framework', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026059', '000000', 'ToExamine', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026060', '000000', 'Consult', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026061', '000000', 'Assignment', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026062', '000000', 'Confirm', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026063', '000000', 'ShuttleBus', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026064', '000000', 'SiteMaintenance', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026065', '000000', 'LineMaintenance', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026066', '000000', 'Scheduling', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026067', '000000', 'dz_Confirm', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026068', '000000', 'Assignment_max', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026069', '000000', 'system-dictionary', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026070', '000000', 'system-ITSM', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026071', '000000', 'system-function', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026072', '000000', 'system-daily', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026073', '000000', 'system-suggestions', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026074', '000000', 'system-notice', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026075', '000000', 'system-propelling', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026076', '000000', 'system-advertising', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026077', '000000', 'VehicleScheduling', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026078', '000000', 'vehicle-management', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026079', '000000', 'FleetManagement', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026080', '000000', 'ElectronicFence', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026081', '000000', 'OverspeedLimit', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026082', '000000', 'CloudVideo', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('442809282145026083', '000000', 'ScManage', to_timestamp('06-05-2018 22:05:59.862000', 'dd-mm-yyyy hh24:mi:ss.ff'), '442806265194741760-��������B', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455696480204750848', '10012', 'system-role', to_timestamp('11-06-2018 11:35:07.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414236', 'wdgly', 'CloudVideo', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414237', 'wdgly', 'ScManage', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414238', 'wdgly', 'Sc_SiteMaintenance', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414239', 'wdgly', 'Sc_LineMaintenance', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414240', 'wdgly', 'Sc_Scheduling', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414241', 'wdgly', 'OperationMonitoring', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414242', 'wdgly', 'BusMonitor', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414243', 'wdgly', 'TemporaryCarManagement', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414244', 'wdgly', 'UnitManagement', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'TemporaryCarManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414245', 'wdgly', 'VehicleManagement', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'TemporaryCarManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414246', 'wdgly', 'AccidentManagement', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455696480204750849', '10012', 'FinancialSettlement', to_timestamp('11-06-2018 11:35:07.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
commit;
prompt 200 records committed...
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455696480204750850', '10012', 'Reimbursement', to_timestamp('11-06-2018 11:35:07.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'FinancialSettlement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455696480204750851', '10012', 'ReceivablesManagement', to_timestamp('11-06-2018 11:35:07.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'FinancialSettlement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455696480204750852', '10012', 'PaymentManagement', to_timestamp('11-06-2018 11:35:07.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'FinancialSettlement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455691444028440576', '0009222', 'VehicleMonitoring', to_timestamp('11-06-2018 11:15:06.594000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455691444028440577', '0009222', 'DriverManagement', to_timestamp('11-06-2018 11:15:06.594000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455691444028440578', '0009222', 'dz_Confirm', to_timestamp('11-06-2018 11:15:06.594000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455691444028440579', '0009222', 'OperationMonitoring', to_timestamp('11-06-2018 11:15:06.594000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455691444028440580', '0009222', 'BusMonitor', to_timestamp('11-06-2018 11:15:06.594000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455696480204750853', '10012', 'AccountingFormula', to_timestamp('11-06-2018 11:35:07.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'FinancialSettlement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455730829021151232', 'test', 'Establish', to_timestamp('11-06-2018 13:51:36.707000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('455730829021151233', 'test', 'system-notice', to_timestamp('11-06-2018 13:51:36.707000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('469608060520759296', '469608059669315584', 'system-role', to_timestamp('19-07-2018 20:54:46.321000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('469608060701114368', '469608059669315584', 'system-user', to_timestamp('19-07-2018 20:54:46.321000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('470995206137708544', '470995205504368640', 'system-role', to_timestamp('23-07-2018 16:46:47.660000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('470995206376783872', '470995205504368640', 'system-user', to_timestamp('23-07-2018 16:46:47.660000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('470995483767078912', '470995483234402304', 'system-role', to_timestamp('23-07-2018 16:47:53.876000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('470995483901296640', '470995483234402304', 'system-user', to_timestamp('23-07-2018 16:47:53.876000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('471324534411100160', '1', 'ReceivablesManagement', to_timestamp('24-07-2018 14:35:21.325000', 'dd-mm-yyyy hh24:mi:ss.ff'), '470995483104378880-22', '1', 'FinancialSettlement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('471615626197073920', '471615625937027072', 'system-user', to_timestamp('25-07-2018 09:52:07.418000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('472096660831338496', 'zdgl', 'system-notice', to_timestamp('26-07-2018 17:43:35.074000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('471615626134159360', '471615625937027072', 'system-role', to_timestamp('25-07-2018 09:52:07.418000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('472096660831338497', 'zdgl', 'system-propelling', to_timestamp('26-07-2018 17:43:35.074000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414208', 'wdgly', 'VehicleMonitoring', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414209', 'wdgly', 'mergeVideo', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414210', 'wdgly', 'CloudPhoto', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414211', 'wdgly', 'abnormal', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414212', 'wdgly', 'OrderManagement', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414213', 'wdgly', 'Establish', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414214', 'wdgly', 'DriverManagement', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414215', 'wdgly', 'system-role', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414216', 'wdgly', 'system-user', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414217', 'wdgly', 'system-framework', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414218', 'wdgly', 'ToExamine', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414219', 'wdgly', 'Consult', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414220', 'wdgly', 'Assignment', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414221', 'wdgly', 'Confirm', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414222', 'wdgly', 'ShuttleBus', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414223', 'wdgly', 'SiteMaintenance', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414224', 'wdgly', 'LineMaintenance', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414225', 'wdgly', 'Scheduling', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414226', 'wdgly', 'dz_Confirm', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414227', 'wdgly', 'system-suggestions', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414228', 'wdgly', 'system-notice', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414229', 'wdgly', 'system-propelling', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414230', 'wdgly', 'system-advertising', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'system');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414231', 'wdgly', 'VehicleScheduling', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414232', 'wdgly', 'vehicle-management', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414233', 'wdgly', 'FleetManagement', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414234', 'wdgly', 'ElectronicFence', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('461852907256414235', 'wdgly', 'OverspeedLimit', to_timestamp('28-06-2018 11:18:33.902000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476438245366824960', 'sly123', 'mergeVideo', to_timestamp('07-08-2018 17:15:29.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614784', '11', 'CloudPhoto', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614785', '11', 'abnormal', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614786', '11', 'OrderManagement', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614787', '11', 'Establish', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614788', '11', 'DriverManagement', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614789', '11', 'ToExamine', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614790', '11', 'Consult', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614791', '11', 'Assignment', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614792', '11', 'Confirm', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614793', '11', 'ShuttleBus', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614794', '11', 'SiteMaintenance', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614795', '11', 'LineMaintenance', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614796', '11', 'Scheduling', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ShuttleBus');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614797', '11', 'dz_Confirm', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OrderManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614798', '11', 'VehicleScheduling', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', null);
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614799', '11', 'vehicle-management', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614800', '11', 'FleetManagement', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614801', '11', 'ElectronicFence', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614802', '11', 'OverspeedLimit', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614803', '11', 'CloudVideo', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614804', '11', 'ScManage', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614805', '11', 'Sc_SiteMaintenance', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614806', '11', 'Sc_LineMaintenance', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614807', '11', 'Sc_Scheduling', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'ScManage');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614808', '11', 'TemporaryCarManagement', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614809', '11', 'UnitManagement', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'TemporaryCarManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614810', '11', 'VehicleManagement', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'TemporaryCarManagement');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('474989059882614811', '11', 'AccidentManagement', to_timestamp('03-08-2018 17:16:56.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'VehicleScheduling');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476344777340616704', '476034372646993920', 'BusMonitor', to_timestamp('07-08-2018 11:04:04.976000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', '1', 'OperationMonitoring');
insert into SYS_JS_GN (id, jsdm, gndm, cjsj, cjr, fwdm, fgndm)
values ('476395819331747840', '476395819071700993', 'system-role', to_timestamp('07-08-2018 14:26:54.273000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admini', '1', 'system');
commit;
prompt 281 records loaded
prompt Loading SYS_JZGXX...
prompt Table is empty
prompt Loading SYS_MESSAGE...
prompt Table is empty
prompt Loading SYS_PTFW...
prompt Table is empty
prompt Loading SYS_PTJG...
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100', '�人��ѧ', '10', '10', to_timestamp('15-03-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '������', 'w�޸���', to_timestamp('15-03-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, 't����Ա����', null, null, 1, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002', '��ѧѧԺ', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002001', '��ѧϵ', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002002', '�ڽ�ѧϵ', '10', '10', to_timestamp('06-05-2018 16:09:16.768000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002010', 'ŷ���ڽ��Ļ��о���', '10', '10', to_timestamp('06-05-2018 18:35:41.216000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'ŷ���ڽ��Ļ��о���', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002003', '����ѧϵ', '10', '10', to_timestamp('06-05-2018 16:09:45.752000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����ѧϵ', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002004', '�����칫��', '10', '10', to_timestamp('06-05-2018 16:10:16.392000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����칫��', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002005', 'ѧ�������칫��', '10', '10', to_timestamp('06-05-2018 16:19:26.322000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'ѧ�������칫��', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002006', '������', '10', '10', to_timestamp('06-05-2018 16:20:00.806000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '������', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100014', '��ѧ��ͳ��ѧԺ', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ѧ��ͳ��ѧԺ', '100', null, 2, '1', '1');
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100004', '���������ѧѧԺ', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '���������ѧѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100005', '�����봫��ѧԺ', '10', '10', to_timestamp('06-05-2018 16:21:40.492000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����봫��ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100006', '��ʷѧԺ', '10', '10', to_timestamp('06-05-2018 16:22:03.336000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ʷѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100007', '����ϵ', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100008', '���������ѧԺ', '10', '10', to_timestamp('06-05-2018 16:30:27.080000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '���������ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100009', '��ѧԺ', '10', '10', to_timestamp('06-05-2018 17:24:52.013000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100010', '�����빫������ѧԺ', '10', '10', to_timestamp('06-05-2018 17:25:15.232000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����빫������ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100012', '���ѧϵ', '10', '10', to_timestamp('06-05-2018 17:34:35.623000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '���ѧϵ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100011', '���˼����ѧԺ', '10', '10', to_timestamp('06-05-2018 17:33:16.919000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '���˼����ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100013', '��Ϣ����ѧԺ', '10', '10', to_timestamp('06-05-2018 17:34:58.201000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��Ϣ����ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100015', '��ѧ��ͳ��ѧԺ', '10', '10', to_timestamp('06-05-2018 17:35:54.607000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ѧ��ͳ��ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100016', '�����ѧ�뼼��ѧԺ', '10', '10', to_timestamp('06-05-2018 17:36:06.560000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����ѧ�뼼��ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100017', '��ѧ����ӿ�ѧѧԺ', '10', '10', to_timestamp('06-05-2018 17:36:14.997000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ѧ����ӿ�ѧѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100018', '������ѧѧԺ', '10', '10', to_timestamp('06-05-2018 17:36:26.154000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '������ѧѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100019', '��Դ�뻷����ѧѧԺ', '10', '10', to_timestamp('06-05-2018 17:36:34.263000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��Դ�뻷����ѧѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100020', '�������еѧԺ', '10', '10', to_timestamp('06-05-2018 17:36:42.716000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�������еѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100021', '��������ѧԺ', '10', '10', to_timestamp('06-05-2018 17:36:56.669000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��������ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100022', '��ľ��������ѧԺ', '10', '10', to_timestamp('06-05-2018 17:37:05.966000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ľ��������ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100023', 'ˮ������ѧԺ', '10', '10', to_timestamp('06-05-2018 17:37:14.544000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'ˮ������ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100024', '�������ѧԺ', '10', '10', to_timestamp('06-05-2018 17:38:11.654000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�������ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100025', '������ϢѧԺ', '10', '10', to_timestamp('06-05-2018 17:38:22.373000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '������ϢѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100026', '�����ѧԺ', '10', '10', to_timestamp('06-05-2018 17:38:33.513000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100027', '�������ѧԺ', '10', '10', to_timestamp('06-05-2018 17:38:41.685000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�������ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100028', 'ң����Ϣ����ѧԺ', '10', '10', to_timestamp('06-05-2018 17:38:53.310000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'ң����Ϣ����ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100030', 'ӡˢ���װϵ', '10', '10', to_timestamp('06-05-2018 17:39:15.841000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'ӡˢ���װϵ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100031', '����ҽѧԺ', '10', '10', to_timestamp('06-05-2018 17:39:27.560000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����ҽѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100032', '����ѧԺ', '10', '10', to_timestamp('06-05-2018 17:39:38.451000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100033', 'ҩѧԺ', '10', '10', to_timestamp('06-05-2018 17:42:50.357000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'ҩѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100034', 'HOPE����ѧԺ', '10', '10', to_timestamp('06-05-2018 17:43:01.263000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'HOPE����ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100035', '��һ�ٴ�ѧԺ', '10', '10', to_timestamp('06-05-2018 17:43:14.185000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��һ�ٴ�ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100036', '�ڶ��ٴ�ѧԺ', '10', '10', to_timestamp('06-05-2018 17:43:23.935000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�ڶ��ٴ�ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100037', '��ǻҽѧԺ', '10', '10', to_timestamp('06-05-2018 17:43:34.263000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ǻҽѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002007', '����ѧʵ����', '10', '10', to_timestamp('06-05-2018 18:05:58.701000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����ѧʵ����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002008', '�����Ƚ���ѧ�о�����', '10', '10', to_timestamp('06-05-2018 18:35:04.888000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����Ƚ���ѧ�о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002009', '�����ڽ��������ڽ��Ļ��о�����', '10', '10', to_timestamp('06-05-2018 18:35:23.591000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����ڽ��������ڽ��Ļ��о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002011', '�й���ѧ���������о�����', '10', '10', to_timestamp('06-05-2018 18:35:56.872000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�й���ѧ���������о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002014', 'Ƥ��ʿ�о�����', '10', '10', to_timestamp('06-05-2018 18:37:14.794000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'Ƥ��ʿ�о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002012', '��������о�����', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��������о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002013', '�����о�����', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002015', '��������ѧ�о�����', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��������ѧ�о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002016', '��ѧ�о���', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ѧ�о���', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002017', '���˼������ѧ�о���', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '���˼������ѧ�о���', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002018', '�������˼������ѧ�о���', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�������˼������ѧ�о���', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002019', '�й���ѧ�о���', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�й���ѧ�о���', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002020', 'ë��˼���о���', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, 'ë��˼���о���', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002021', '�ִ�����ѧ�о�����', '10', '10', to_timestamp('06-05-2018 18:39:30.982000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�ִ�����ѧ�о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002022', '���״����о���', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '���״����о���', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002023', '�人��ѧ�����о�����', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�人��ѧ�����о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002024', '�人��ѧ���������о�����', '10', '10', to_timestamp('06-05-2018 18:40:21.044000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�人��ѧ���������о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002025', '�й�Ů���о�����', '10', '10', to_timestamp('06-05-2018 18:40:35.904000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�й�Ů���о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002026', '����ְҵ�����о�����', '10', '10', to_timestamp('06-05-2018 18:40:54.138000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '����ְҵ�����о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002027', '�����Ļ��滮�о�����', '10', '10', to_timestamp('06-05-2018 18:41:08.701000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�����Ļ��滮�о�����', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100002028', '�¹���ѧ�о���', '10', '10', to_timestamp('06-05-2018 18:41:23.779000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�¹���ѧ�о���', '100002', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100038', '��ѧԺ', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '��ѧԺ', '100', null, 2, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100038029', '�й��ֵ�����ѧ������', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�й��ֵ�����ѧ������', '100038', null, 3, null, null);
insert into SYS_PTJG (jgdm, jgmc, jglx, zt, cjsj, cjr, xgr, xgsj, gly, glyxm, fjgdm, bz, jgdj, jgbm, jgsm)
values ('100038030', '�й��Ŵ���ѧ������', '10', '10', to_timestamp('06-05-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null, '�й��Ŵ���ѧ������', '100038', null, 3, null, null);
commit;
prompt 66 records loaded
prompt Loading SYS_PTRZ...
insert into SYS_PTRZ (rz_id, czlx, czsj, czr, dx_id, dxlx, cs, jg, zxsj, sm, ff)
values ('491557270568566784', null, to_timestamp('18-09-2018 10:33:05.994000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, '{"jgdm":"100045035001","jgdj":4,"jgmc":"123","jglx":"10","zt":"10","cjsj":1537237985943,"cjr":"1-��������Ա","gly":"","fjgdm":"100045035","jgbm":"123","jgsm":"123","children":[],"title":"123","level":4}', '{"code":200,"message":"����ɹ���","result":"100045035001","success":true}', 53, null, 'JgController.save');
insert into SYS_PTRZ (rz_id, czlx, czsj, czr, dx_id, dxlx, cs, jg, zxsj, sm, ff)
values ('491557309328130048', null, to_timestamp('18-09-2018 10:33:15.237000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, '["100045035001"]', '{"code":200,"message":"����ɾ���ɹ���","success":true}', 14, null, 'JgController.remove');
insert into SYS_PTRZ (rz_id, czlx, czsj, czr, dx_id, dxlx, cs, jg, zxsj, sm, ff)
values ('491556766090264576', null, to_timestamp('18-09-2018 10:31:05.716000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, '{"hdId":"491556765465313280","kssj":1537200000000,"jssj":1537286400000,"hdlx":"00","jgdm":"100","zt":"10","cjr":"1-��������Ա","cjsj":1537237865570,"hdbt":"����","url":"10.113.4.70:9090/index.html","wjlx":"00","wz":"10","filePaths":"hdFile/f9fa435211dc496b8fea5cf4c8a3a85a.jpg,"}', '{"code":200,"message":"�����ɹ�","success":true}', 161, null, 'HdController.save');
commit;
prompt 3 records loaded
prompt Loading SYS_RLB...
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1390', 4, '1 ', '2018-09-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1391', 5, '1 ', '2018-09-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1392', 6, '4 ', '2018-09-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1393', 7, '4 ', '2018-09-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1394', 1, '3 ', '2018-10-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1395', 2, '3 ', '2018-10-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1396', 3, '3 ', '2018-10-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1397', 4, '3 ', '2018-10-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1398', 5, '3 ', '2018-10-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1399', 6, '3 ', '2018-10-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1400', 7, '3 ', '2018-10-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1401', 1, '1 ', '2018-10-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1402', 2, '1 ', '2018-10-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1403', 3, '1 ', '2018-10-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1404', 4, '1 ', '2018-10-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1405', 5, '1 ', '2018-10-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1406', 6, '2 ', '2018-10-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1407', 7, '2 ', '2018-10-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1408', 1, '1 ', '2018-10-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1409', 2, '1 ', '2018-10-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1410', 3, '1 ', '2018-10-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1411', 4, '1 ', '2018-10-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1412', 5, '1 ', '2018-10-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1413', 6, '2 ', '2018-10-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1414', 7, '2 ', '2018-10-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1415', 1, '1 ', '2018-10-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1416', 2, '1 ', '2018-10-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1417', 3, '1 ', '2018-10-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1418', 4, '1 ', '2018-10-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1419', 5, '1 ', '2018-10-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1420', 6, '2 ', '2018-10-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1421', 7, '2 ', '2018-10-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1422', 1, '1 ', '2018-10-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1423', 2, '1 ', '2018-10-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1424', 3, '1 ', '2018-10-31');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1425', 4, '1 ', '2018-11-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1426', 5, '1 ', '2018-11-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1427', 6, '2 ', '2018-11-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1428', 7, '2 ', '2018-11-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1429', 1, '1 ', '2018-11-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1430', 2, '1 ', '2018-11-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1431', 3, '1 ', '2018-11-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1432', 4, '1 ', '2018-11-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1433', 5, '1 ', '2018-11-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1434', 6, '2 ', '2018-11-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1435', 7, '2 ', '2018-11-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1436', 1, '1 ', '2018-11-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1437', 2, '1 ', '2018-11-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1438', 3, '1 ', '2018-11-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1439', 4, '1 ', '2018-11-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1440', 5, '1 ', '2018-11-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1441', 6, '2 ', '2018-11-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1442', 7, '2 ', '2018-11-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1443', 1, '1 ', '2018-11-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1444', 2, '1 ', '2018-11-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1445', 3, '1 ', '2018-11-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1446', 4, '1 ', '2018-11-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1447', 5, '1 ', '2018-11-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1448', 6, '2 ', '2018-11-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1449', 7, '2 ', '2018-11-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1450', 1, '1 ', '2018-11-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1451', 2, '1 ', '2018-11-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1452', 3, '1 ', '2018-11-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1453', 4, '1 ', '2018-11-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1454', 5, '1 ', '2018-11-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1455', 6, '2 ', '2018-12-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1456', 7, '2 ', '2018-12-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1457', 1, '1 ', '2018-12-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1458', 2, '1 ', '2018-12-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1459', 3, '1 ', '2018-12-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1460', 4, '1 ', '2018-12-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1461', 5, '1 ', '2018-12-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1462', 6, '2 ', '2018-12-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1463', 7, '2 ', '2018-12-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1464', 1, '1 ', '2018-12-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1465', 2, '1 ', '2018-12-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1466', 3, '1 ', '2018-12-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1467', 4, '1 ', '2018-12-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1468', 5, '1 ', '2018-12-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1469', 6, '2 ', '2018-12-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1470', 7, '2 ', '2018-12-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1471', 1, '1 ', '2018-12-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1472', 2, '1 ', '2018-12-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1473', 3, '1 ', '2018-12-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1474', 4, '1 ', '2018-12-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1475', 5, '1 ', '2018-12-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1476', 6, '2 ', '2018-12-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1477', 7, '2 ', '2018-12-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1478', 1, '1 ', '2018-12-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1479', 2, '1 ', '2018-12-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1480', 3, '1 ', '2018-12-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1481', 4, '1 ', '2018-12-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1482', 5, '1 ', '2018-12-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1483', 6, '2 ', '2018-12-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1484', 7, '2 ', '2018-12-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1485', 1, '1 ', '2018-12-31');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1377', 5, '1 ', '2018-09-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1378', 6, '2 ', '2018-09-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1379', 7, '2 ', '2018-09-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1380', 1, '1 ', '2018-09-17');
commit;
prompt 100 records committed...
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1381', 2, '1 ', '2018-09-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1382', 3, '1 ', '2018-09-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1383', 4, '1 ', '2018-09-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1384', 5, '1 ', '2018-09-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1385', 6, '3 ', '2018-09-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1386', 7, '3 ', '2018-09-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1387', 1, '3 ', '2018-09-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1388', 2, '1 ', '2018-09-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1389', 3, '1 ', '2018-09-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1122', 2, '1 ', '2018-01-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1123', 3, '1 ', '2018-01-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1124', 4, '1 ', '2018-01-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1125', 5, '1 ', '2018-01-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1126', 6, '2 ', '2018-01-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1127', 7, '2 ', '2018-01-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1128', 1, '1 ', '2018-01-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1129', 2, '1 ', '2018-01-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1130', 3, '1 ', '2018-01-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1131', 4, '1 ', '2018-01-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1132', 5, '1 ', '2018-01-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1133', 6, '2 ', '2018-01-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1134', 7, '2 ', '2018-01-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1135', 1, '1 ', '2018-01-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1136', 2, '1 ', '2018-01-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1137', 3, '1 ', '2018-01-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1138', 4, '1 ', '2018-01-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1139', 5, '1 ', '2018-01-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1140', 6, '2 ', '2018-01-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1141', 7, '2 ', '2018-01-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1142', 1, '1 ', '2018-01-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1143', 2, '1 ', '2018-01-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1144', 3, '1 ', '2018-01-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1145', 4, '1 ', '2018-01-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1146', 5, '1 ', '2018-01-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1147', 6, '2 ', '2018-01-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1148', 7, '2 ', '2018-01-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1149', 1, '1 ', '2018-01-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1150', 2, '1 ', '2018-01-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1151', 3, '1 ', '2018-01-31');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1152', 4, '1 ', '2018-02-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1153', 5, '1 ', '2018-02-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1154', 6, '2 ', '2018-02-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1155', 7, '2 ', '2018-02-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1156', 1, '1 ', '2018-02-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1157', 2, '1 ', '2018-02-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1158', 3, '1 ', '2018-02-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1159', 4, '1 ', '2018-02-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1160', 5, '1 ', '2018-02-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1161', 6, '2 ', '2018-02-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1162', 7, '4 ', '2018-02-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1163', 1, '1 ', '2018-02-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1164', 2, '1 ', '2018-02-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1165', 3, '1 ', '2018-02-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1166', 4, '3 ', '2018-02-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1167', 5, '3 ', '2018-02-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1168', 6, '3 ', '2018-02-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1169', 7, '3 ', '2018-02-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1170', 1, '3 ', '2018-02-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1171', 2, '3 ', '2018-02-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1172', 3, '3 ', '2018-02-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1173', 4, '1 ', '2018-02-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1174', 5, '1 ', '2018-02-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1175', 6, '1 ', '2018-02-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1176', 7, '2 ', '2018-02-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1177', 1, '1 ', '2018-02-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1178', 2, '1 ', '2018-02-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1179', 3, '1 ', '2018-02-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1180', 4, '1 ', '2018-03-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1181', 5, '1 ', '2018-03-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1182', 6, '2 ', '2018-03-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1183', 7, '2 ', '2018-03-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1184', 1, '1 ', '2018-03-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1185', 2, '1 ', '2018-03-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1186', 3, '1 ', '2018-03-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1187', 4, '1 ', '2018-03-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1188', 5, '1 ', '2018-03-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1189', 6, '2 ', '2018-03-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1190', 7, '2 ', '2018-03-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1191', 1, '1 ', '2018-03-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1192', 2, '1 ', '2018-03-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1193', 3, '1 ', '2018-03-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1194', 4, '1 ', '2018-03-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1195', 5, '1 ', '2018-03-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1196', 6, '2 ', '2018-03-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1197', 7, '2 ', '2018-03-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1198', 1, '1 ', '2018-03-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1199', 2, '1 ', '2018-03-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1200', 3, '1 ', '2018-03-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1201', 4, '1 ', '2018-03-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1202', 5, '1 ', '2018-03-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1203', 6, '2 ', '2018-03-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1204', 7, '2 ', '2018-03-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1205', 1, '1 ', '2018-03-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1206', 2, '1 ', '2018-03-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1207', 3, '1 ', '2018-03-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1208', 4, '1 ', '2018-03-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1209', 5, '1 ', '2018-03-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1210', 6, '2 ', '2018-03-31');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1211', 7, '2 ', '2018-04-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1212', 1, '1 ', '2018-04-02');
commit;
prompt 200 records committed...
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1213', 2, '1 ', '2018-04-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1214', 3, '1 ', '2018-04-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1215', 4, '3 ', '2018-04-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1216', 5, '3 ', '2018-04-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1217', 6, '3 ', '2018-04-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1218', 7, '4 ', '2018-04-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1219', 1, '1 ', '2018-04-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1220', 2, '1 ', '2018-04-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1221', 3, '1 ', '2018-04-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1222', 4, '1 ', '2018-04-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1223', 5, '1 ', '2018-04-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1224', 6, '2 ', '2018-04-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1225', 7, '2 ', '2018-04-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1226', 1, '1 ', '2018-04-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1227', 2, '1 ', '2018-04-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1228', 3, '1 ', '2018-04-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1229', 4, '1 ', '2018-04-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1230', 5, '1 ', '2018-04-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1231', 6, '2 ', '2018-04-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1232', 7, '2 ', '2018-04-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1233', 1, '1 ', '2018-04-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1234', 2, '1 ', '2018-04-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1235', 3, '1 ', '2018-04-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1236', 4, '1 ', '2018-04-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1237', 5, '1 ', '2018-04-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1238', 6, '4 ', '2018-04-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1239', 7, '3 ', '2018-04-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1240', 1, '3 ', '2018-04-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1241', 2, '3 ', '2018-05-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1242', 3, '1 ', '2018-05-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1243', 4, '1 ', '2018-05-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1244', 5, '1 ', '2018-05-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1245', 6, '2 ', '2018-05-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1246', 7, '2 ', '2018-05-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1247', 1, '1 ', '2018-05-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1248', 2, '1 ', '2018-05-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1249', 3, '1 ', '2018-05-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1250', 4, '1 ', '2018-05-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1251', 5, '1 ', '2018-05-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1252', 6, '2 ', '2018-05-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1253', 7, '2 ', '2018-05-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1254', 1, '1 ', '2018-05-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1255', 2, '1 ', '2018-05-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1256', 3, '1 ', '2018-05-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1257', 4, '1 ', '2018-05-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1258', 5, '1 ', '2018-05-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1259', 6, '2 ', '2018-05-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1260', 7, '2 ', '2018-05-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1261', 1, '1 ', '2018-05-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1262', 2, '1 ', '2018-05-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1263', 3, '1 ', '2018-05-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1264', 4, '1 ', '2018-05-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1265', 5, '1 ', '2018-05-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1266', 6, '2 ', '2018-05-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1267', 7, '2 ', '2018-05-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1268', 1, '1 ', '2018-05-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1269', 2, '1 ', '2018-05-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1270', 3, '1 ', '2018-05-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1271', 4, '1 ', '2018-05-31');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1272', 5, '1 ', '2018-06-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1273', 6, '2 ', '2018-06-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1274', 7, '2 ', '2018-06-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1275', 1, '1 ', '2018-06-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1276', 2, '1 ', '2018-06-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1277', 3, '1 ', '2018-06-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1278', 4, '1 ', '2018-06-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1279', 5, '1 ', '2018-06-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1280', 6, '2 ', '2018-06-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1281', 7, '2 ', '2018-06-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1282', 1, '1 ', '2018-06-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1283', 2, '1 ', '2018-06-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1284', 3, '1 ', '2018-06-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1285', 4, '1 ', '2018-06-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1286', 5, '1 ', '2018-06-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1287', 6, '3 ', '2018-06-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1288', 7, '3 ', '2018-06-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1289', 1, '3 ', '2018-06-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1290', 2, '1 ', '2018-06-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1291', 3, '1 ', '2018-06-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1292', 4, '1 ', '2018-06-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1293', 5, '1 ', '2018-06-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1294', 6, '2 ', '2018-06-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1295', 7, '2 ', '2018-06-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1296', 1, '1 ', '2018-06-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1297', 2, '1 ', '2018-06-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1298', 3, '1 ', '2018-06-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1299', 4, '1 ', '2018-06-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1300', 5, '1 ', '2018-06-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1301', 6, '2 ', '2018-06-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1302', 7, '2 ', '2018-07-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1303', 1, '1 ', '2018-07-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1304', 2, '1 ', '2018-07-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1305', 3, '1 ', '2018-07-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1306', 4, '1 ', '2018-07-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1307', 5, '1 ', '2018-07-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1308', 6, '2 ', '2018-07-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1309', 7, '2 ', '2018-07-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1310', 1, '1 ', '2018-07-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1311', 2, '1 ', '2018-07-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1312', 3, '1 ', '2018-07-11');
commit;
prompt 300 records committed...
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1313', 4, '1 ', '2018-07-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1314', 5, '1 ', '2018-07-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1315', 6, '2 ', '2018-07-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1316', 7, '2 ', '2018-07-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1317', 1, '1 ', '2018-07-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1318', 2, '1 ', '2018-07-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1319', 3, '1 ', '2018-07-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1320', 4, '1 ', '2018-07-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1321', 5, '1 ', '2018-07-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1322', 6, '2 ', '2018-07-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1323', 7, '2 ', '2018-07-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1324', 1, '1 ', '2018-07-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1325', 2, '1 ', '2018-07-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1326', 3, '1 ', '2018-07-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1327', 4, '1 ', '2018-07-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1328', 5, '1 ', '2018-07-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1329', 6, '2 ', '2018-07-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1330', 7, '2 ', '2018-07-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1331', 1, '1 ', '2018-07-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1332', 2, '1 ', '2018-07-31');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1333', 3, '1 ', '2018-08-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1334', 4, '1 ', '2018-08-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1335', 5, '1 ', '2018-08-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1336', 6, '2 ', '2018-08-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1337', 7, '2 ', '2018-08-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1338', 1, '1 ', '2018-08-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1339', 2, '1 ', '2018-08-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1340', 3, '1 ', '2018-08-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1341', 4, '1 ', '2018-08-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1342', 5, '1 ', '2018-08-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1343', 6, '2 ', '2018-08-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1344', 7, '2 ', '2018-08-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1345', 1, '1 ', '2018-08-13');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1346', 2, '1 ', '2018-08-14');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1347', 3, '1 ', '2018-08-15');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1348', 4, '1 ', '2018-08-16');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1349', 5, '1 ', '2018-08-17');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1350', 6, '2 ', '2018-08-18');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1351', 7, '2 ', '2018-08-19');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1352', 1, '1 ', '2018-08-20');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1353', 2, '1 ', '2018-08-21');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1354', 3, '1 ', '2018-08-22');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1355', 4, '1 ', '2018-08-23');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1356', 5, '1 ', '2018-08-24');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1357', 6, '2 ', '2018-08-25');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1358', 7, '2 ', '2018-08-26');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1359', 1, '1 ', '2018-08-27');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1360', 2, '1 ', '2018-08-28');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1361', 3, '1 ', '2018-08-29');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1362', 4, '1 ', '2018-08-30');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1363', 5, '1 ', '2018-08-31');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1364', 6, '2 ', '2018-09-01');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1365', 7, '2 ', '2018-09-02');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1366', 1, '1 ', '2018-09-03');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1367', 2, '1 ', '2018-09-04');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1368', 3, '1 ', '2018-09-05');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1369', 4, '1 ', '2018-09-06');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1370', 5, '1 ', '2018-09-07');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1371', 6, '2 ', '2018-09-08');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1372', 7, '2 ', '2018-09-09');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1373', 1, '1 ', '2018-09-10');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1374', 2, '1 ', '2018-09-11');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1375', 3, '1 ', '2018-09-12');
insert into SYS_RLB (rqdm, xq, zt, rq)
values ('1376', 4, '1 ', '2018-09-13');
commit;
prompt 364 records loaded
prompt Loading SYS_WXYH...
prompt Table is empty
prompt Loading SYS_XXTS...
prompt Table is empty
prompt Loading SYS_YH_JS...
prompt Table is empty
prompt Loading SYS_YH_XX...
prompt Table is empty
prompt Loading SYS_YJFK...
prompt Table is empty
prompt Loading SYS_YXHDWJ...
insert into SYS_YXHDWJ (id, hd_id, wjlx, wjlj, wllj, cjsj, cjr, xgsj, xgr)
values ('491556765477896192', '491556765465313280', 'jpg', 'hdFile/f9fa435211dc496b8fea5cf4c8a3a85a.jpg', 'hdFile/f9fa435211dc496b8fea5cf4c8a3a85a.jpg', to_timestamp('18-09-2018 10:31:05.573000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null);
commit;
prompt 1 records loaded
prompt Loading SYS_ZDLM...
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0045', '�����ֵ�', to_timestamp('06-05-2018 16:01:10.227000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, 1);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0041', '�ؿ���', to_timestamp('18-04-2018 14:22:49.135000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, 2);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0002', '�û�״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 2);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0042', '��������', to_timestamp('06-05-2018 19:32:26.527000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0001', '��������', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 1);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0003', '�û�����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 3);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0004', '��ɫ����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 4);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0005', '��ɫ״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 5);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0006', '����״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 6);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0007', '����״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 7);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0008', '����״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 8);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0009', '�������', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 9);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0010', '���״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 10);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0011', '��־����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 11);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0012', '�û���Ϣ״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 12);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0013', '�״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 13);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0014', '�ļ�����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 14);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0015', '�ӿ�״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 15);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0016', '����״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 16);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0017', '�Ա�', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 17);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0018', '��ʻԱ״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 18);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0019', '��������', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 19);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0020', '����״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 20);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0021', '����״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 21);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0022', '��·״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 22);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0023', '��·����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 23);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0024', '���з�ʽ', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 24);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0025', 'վ��״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 25);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0026', '��ʱ��״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 26);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0027', '��ʱ��λ״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 27);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0028', '��·վ��״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 28);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0029', '��Ƶ����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 29);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0030', '�ļ�����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 30);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0031', '�ն�״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 31);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0032', '�ն�����״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 32);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0033', 'Χ��״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 33);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0034', 'gps����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 34);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0035', '����״̬', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 35);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0036', '��������', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 36);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0037', '�¼�����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 37);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0038', '�¼�����', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 38);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0039', '˾�����ԣ�10:�ڲ�˾�� 11���ⲿ��', to_timestamp('16-03-2018 09:09:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', null, null, 38);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0040', '׼�ݳ���', to_timestamp('17-04-2018 13:35:09.944000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK0043', '������Դ', to_timestamp('06-05-2018 20:29:40.626000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('ZDCLK9991', 'ҵ����(SYS_MESSAGE��BIZ_ID)', to_timestamp('09-05-2018 10:33:40.300000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('scspms', '�ϴ�����ģʽ', to_timestamp('10-06-2018 14:19:35.898000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDLM (lmdm, lmmc, cjsj, cjr, xgsj, xgr, qz)
values ('pzlmd', '��ײ������', to_timestamp('10-06-2018 14:18:46.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
commit;
prompt 47 records loaded
prompt Loading SYS_ZDXM...
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('435795455742312448', 'ZDCLK0040', 'ZDCLK00400001', 'C1', null, to_timestamp('17-04-2018 13:35:33.337000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('435795529461399552', 'ZDCLK0040', 'ZDCLK00400003', 'C3', null, to_timestamp('17-04-2018 13:35:50.913000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('435795499505680384', 'ZDCLK0040', 'ZDCLK00400002', 'C2', null, to_timestamp('17-04-2018 13:35:43.771000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('4', '2', '1', '�ֵ���21', 1, to_timestamp('16-03-2018 09:10:12.964000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00010001', 'ZDCLK0001', '01', '��������', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('435795579797241856', 'ZDCLK0040', 'ZDCLK00400004', 'A1', null, to_timestamp('17-04-2018 13:36:02.914000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('425339298829565952', 'newDic', 'qq', 'qq', null, to_timestamp('19-03-2018 17:06:31.263000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('425339366739542016', 'newDic', 'w', 'wwww', null, to_timestamp('19-03-2018 17:06:47.454000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('435795644376940544', 'ZDCLK0040', 'ZDCLK00400005', 'A2', null, to_timestamp('17-04-2018 13:36:18.311000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00010002', 'ZDCLK0001', '02', 'С������', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00030002', 'ZDCLK0003', '10', '��ͨ�û�', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00020001', 'ZDCLK0002', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00030001', 'ZDCLK0003', '00', '����Ա', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00040001', 'ZDCLK0004', '00', '����Ա', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00040002', 'ZDCLK0004', '10', '��ͨ�û�', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00050001', 'ZDCLK0005', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00050002', 'ZDCLK0005', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00060001', 'ZDCLK0006', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00060002', 'ZDCLK0006', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00070001', 'ZDCLK0007', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00070002', 'ZDCLK0007', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00080001', 'ZDCLK0008', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00080002', 'ZDCLK0008', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00090001', 'ZDCLK0009', '00', '���', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00090002', 'ZDCLK0009', '10', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00100001', 'ZDCLK0010', '00', '������', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00100002', 'ZDCLK0010', '10', '�Ѵ���', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00110001', 'ZDCLK0011', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00110002', 'ZDCLK0011', '10', '�޸�', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00110003', 'ZDCLK0011', '20', 'ɾ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00120001', 'ZDCLK0012', '10', '�Ѷ�', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00120002', 'ZDCLK0012', '00', 'δ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00130001', 'ZDCLK0013', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00130002', 'ZDCLK0013', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00140001', 'ZDCLK0014', '00', '��Ƶ', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00140002', 'ZDCLK0014', '10', 'ͼƬ', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00150001', 'ZDCLK0015', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00150002', 'ZDCLK0015', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00160001', 'ZDCLK0016', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00160002', 'ZDCLK0016', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00170001', 'ZDCLK0017', '1', '��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00170002', 'ZDCLK0017', '0', 'Ů', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00180001', 'ZDCLK0018', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('441300707388162048', 'ZDCLK0041', '11', '11', null, to_timestamp('02-05-2018 18:11:27.612000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00190001', 'ZDCLK0019', '10', 'С��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00190002', 'ZDCLK0019', '20', '��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442101998775435264', 'ZDCLK0020', '10', '��������', null, to_timestamp('04-05-2018 23:15:30.366000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442102057562800128', 'ZDCLK0020', '11', '���ͨ��', null, to_timestamp('04-05-2018 23:15:44.382000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442102100030128128', 'ZDCLK0020', '12', '��˲���', null, to_timestamp('04-05-2018 23:15:54.507000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442102145903230976', 'ZDCLK0020', '13', '���ɵ�', null, to_timestamp('04-05-2018 23:16:05.444000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442102217206398976', 'ZDCLK0020', '20', '˾����ȷ��', null, to_timestamp('04-05-2018 23:16:22.444000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00210001', 'ZDCLK0021', '00', 'δ����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00210002', 'ZDCLK0021', '10', '�Ѹ���', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00220001', 'ZDCLK0022', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00220002', 'ZDCLK0022', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00230001', 'ZDCLK0023', '10', 'У��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00230002', 'ZDCLK0023', '20', '�೵', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00240001', 'ZDCLK0024', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00240002', 'ZDCLK0024', '10', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00240003', 'ZDCLK0024', '20', '˫��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00250001', 'ZDCLK0025', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00250002', 'ZDCLK0025', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00260001', 'ZDCLK0026', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00260002', 'ZDCLK0026', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00270001', 'ZDCLK0027', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00270002', 'ZDCLK0027', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00280001', 'ZDCLK0028', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00280002', 'ZDCLK0028', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00290001', 'ZDCLK0029', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00290002', 'ZDCLK0029', '10', '�¹�', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00290003', 'ZDCLK0029', '20', '������', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00290004', 'ZDCLK0029', '30', '������', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00290005', 'ZDCLK0029', '40', '��ת��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00300001', 'ZDCLK0030', '00', '��Ƶ', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00300002', 'ZDCLK0030', '10', 'ͼƬ', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00310001', 'ZDCLK0031', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00310002', 'ZDCLK0031', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00320001', 'ZDCLK0032', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('438068280763088896', 'ZDCLK0032', '10', 'Ϩ��', null, to_timestamp('23-04-2018 20:06:57.049000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00330001', 'ZDCLK0033', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00330002', 'ZDCLK0033', '10', 'ͣ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00340001', 'ZDCLK0034', '00', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00340002', 'ZDCLK0034', '10', '�¹�', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00340003', 'ZDCLK0034', '20', '������', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00340004', 'ZDCLK0034', '30', '������', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00340005', 'ZDCLK0034', '40', '��ת��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00350001', 'ZDCLK0035', '10', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00350002', 'ZDCLK0035', '20', '�ż�', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00360001', 'ZDCLK0036', '10', '���', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00360002', 'ZDCLK0036', '20', '�Ӱ�', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00360003', 'ZDCLK0036', '30', '�ڼ���', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00370001', 'ZDCLK0037', '10', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00370002', 'ZDCLK0037', '20', '�쳣', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00380001', 'ZDCLK0038', '10', '������', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00380002', 'ZDCLK0038', '20', '��ɲ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00380003', 'ZDCLK0038', '30', '��ת��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00380004', 'ZDCLK0038', '40', '����', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00390001', 'ZDCLK0039', '10', '�ڲ�˾��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00390002', 'ZDCLK0039', '11', '�ⲿ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('436557909279637504', '����', ' ��', '�ǵ� ', null, to_timestamp('19-04-2018 16:05:16.426000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
commit;
prompt 100 records committed...
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('438093378169602048', 'ZDCLK0018', '10', '��Ϣ', null, to_timestamp('23-04-2018 21:46:40.737000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('436174602624827392', 'ZDCLK0019', '30', 'У��', null, to_timestamp('18-04-2018 14:42:08.998000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('438068345904824320', 'ZDCLK0032', '20', '����', null, to_timestamp('23-04-2018 20:07:12.580000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442102303651004416', 'ZDCLK0020', '30', '�ӳ�ȷ��', null, to_timestamp('04-05-2018 23:16:43.054000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442098297344098304', 'ZDCLK0004', '20', '��ʻԱ', null, to_timestamp('04-05-2018 23:00:47.876000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('439439373541310464', 'ZDCLK0038', '00', '����', null, to_timestamp('27-04-2018 14:55:11.041000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442098412624543744', 'ZDCLK0004', '30', '�ӳ�', null, to_timestamp('04-05-2018 23:01:15.361000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('441243943649148928', '111', '22', '22', null, to_timestamp('02-05-2018 14:25:54.082000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442717667883745280', 'ZDCLK0045', '001', '����һ', null, to_timestamp('06-05-2018 16:01:57.320000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('441245993615228928', 'ZDCLK0041', '10', '10', null, to_timestamp('02-05-2018 14:34:02.832000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442722575617884160', 'ZDCLK0045', '002', '�����', null, to_timestamp('06-05-2018 16:21:27.415000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442730327245324288', 'ZDCLK0045', '3', 'ss', null, to_timestamp('06-05-2018 16:52:15.547000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442730722424258560', 'ZDCLK0045', '23', '3', null, to_timestamp('06-05-2018 16:53:49.765000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442731288189730816', 'ZDCLK0045', '9', '90', null, to_timestamp('06-05-2018 16:56:04.654000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442731379742998528', 'ZDCLK0045', '0', '9', null, to_timestamp('06-05-2018 16:56:26.482000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442731723411685376', 'ZDCLK0045', '8', '9', null, to_timestamp('06-05-2018 16:57:48.419000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442731816932081664', 'ZDCLK0045', '5', '6', null, to_timestamp('06-05-2018 16:58:10.716000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442750875849457664', 'ZDCLK0045', '2', '3', null, to_timestamp('06-05-2018 18:13:54.716000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442770714836074496', 'ZDCLK0042', '10', '����', null, to_timestamp('06-05-2018 19:32:44.699000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442770753503363072', 'ZDCLK0042', '20', '����', null, to_timestamp('06-05-2018 19:32:53.918000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442785118096982016', 'ZDCLK0043', '10', '��������', null, to_timestamp('06-05-2018 20:29:58.704000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442785162200088576', 'ZDCLK0043', '20', '�������', null, to_timestamp('06-05-2018 20:30:09.219000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442785190905905152', 'ZDCLK0043', '30', '�Է�', null, to_timestamp('06-05-2018 20:30:16.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442792710143737856', 'ZDCLK0045', '31', '3', null, to_timestamp('06-05-2018 21:00:08.789000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442806707630899200', 'ZDCLK0041', '3', '3', null, to_timestamp('06-05-2018 21:55:46.050000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442807003719401472', 'ZDCLK0041', '30', '30', null, to_timestamp('06-05-2018 21:56:56.643000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('442807082559733760', 'ZDCLK0041', '20', '20', null, to_timestamp('06-05-2018 21:57:15.440000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('443468458953277440', 'ZDCLK0041', '7', '7', null, to_timestamp('08-05-2018 17:45:19.860000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('443722393542721536', 'ZDCLK9991', 'BIZ_10', '�����ɵ�ҵ��', null, to_timestamp('09-05-2018 10:34:22.584000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('446639397769576448', 'ZDCLK0041', '12', '12', null, to_timestamp('17-05-2018 11:45:30.587000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('446639620004773889', 'ZDCLK0041', '2', '2', null, to_timestamp('17-05-2018 11:46:23.572000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('448088891363164160', 'ZDCLK0038', '50', '���', null, to_timestamp('21-05-2018 11:45:16.790000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('448088915744653312', 'ZDCLK0038', '60', 'Ϩ��', null, to_timestamp('21-05-2018 11:45:22.603000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('451505912880824320', 'ZDCLK0045', '1', '1', null, to_timestamp('30-05-2018 22:03:18.205000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('451508182225780736', 'ZDCLK0045', '11', '11', null, to_timestamp('30-05-2018 22:12:19.259000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('451510409623502848', 'ZDCLK0045', '12', '12', null, to_timestamp('30-05-2018 22:21:10.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('451510899249774592', 'ZDCLK0045', '13', '13', null, to_timestamp('30-05-2018 22:23:07.048000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('454593861239439360', 'ZDCLK0045', '002009', '����', null, to_timestamp('08-06-2018 10:33:42.465000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('455375340668190720', 'pzlmd', '00', '��', null, to_timestamp('10-06-2018 14:19:01.680000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('455375373157269504', 'pzlmd', '10', '��', null, to_timestamp('10-06-2018 14:19:09.426000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('455375414345334784', 'pzlmd', '20', '��', null, to_timestamp('10-06-2018 14:19:19.246000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('455375516367585280', 'scspms', '10', 'WIFI���ϴ���ͨ��Ƶ', null, to_timestamp('10-06-2018 14:19:43.570000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('455375547430600704', 'scspms', '20', 'WIFI�²��ϴ���ͨ��Ƶ', null, to_timestamp('10-06-2018 14:19:50.976000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('455375596625592320', 'scspms', '30', 'WIFI/4G���ϴ�', null, to_timestamp('10-06-2018 14:20:02.705000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('455672525012074496', 'ZDCLK0038', '70', '���ڵ���Χ����', null, to_timestamp('11-06-2018 09:59:55.949000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('468469466708049920', 'ZDCLK0041', '5', '5', null, to_timestamp('16-07-2018 17:30:24.605000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('471376854171779072', 'ZDCLK0041', '1', '1', null, to_timestamp('24-07-2018 18:03:19.793000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-��������Ա', null, null, null);
insert into SYS_ZDXM (zd_id, zdlmdm, zddm, zdmc, qz, cjsj, cjr, by1, by2, by3)
values ('ZDCLK00090003', 'ZDCLK0009', '20', 'Ͷ��', null, to_timestamp('19-03-2018 17:12:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, null, null, null);
commit;
prompt 148 records loaded
prompt Enabling triggers for CL_BXJZ...
alter table CL_BXJZ enable all triggers;
prompt Enabling triggers for CL_CD...
alter table CL_CD enable all triggers;
prompt Enabling triggers for CL_CL...
alter table CL_CL enable all triggers;
prompt Enabling triggers for CL_CLYXJL...
alter table CL_CLYXJL enable all triggers;
prompt Enabling triggers for CL_CSSD...
alter table CL_CSSD enable all triggers;
prompt Enabling triggers for CL_DD...
alter table CL_DD enable all triggers;
prompt Enabling triggers for CL_DDLSB...
alter table CL_DDLSB enable all triggers;
prompt Enabling triggers for CL_DDRZ...
alter table CL_DDRZ enable all triggers;
prompt Enabling triggers for CL_DZWL...
alter table CL_DZWL enable all triggers;
prompt Enabling triggers for CL_DZWL_CL...
alter table CL_DZWL_CL enable all triggers;
prompt Enabling triggers for CL_GPS...
alter table CL_GPS enable all triggers;
prompt Enabling triggers for CL_GPS_LS...
alter table CL_GPS_LS enable all triggers;
prompt Enabling triggers for CL_JSY...
alter table CL_JSY enable all triggers;
prompt Enabling triggers for CL_LSC...
alter table CL_LSC enable all triggers;
prompt Enabling triggers for CL_LSDW...
alter table CL_LSDW enable all triggers;
prompt Enabling triggers for CL_PB...
alter table CL_PB enable all triggers;
prompt Enabling triggers for CL_SBYCSJJL...
alter table CL_SBYCSJJL enable all triggers;
prompt Enabling triggers for CL_SBYXSJJL...
alter table CL_SBYXSJJL enable all triggers;
prompt Enabling triggers for CL_SG...
alter table CL_SG enable all triggers;
prompt Enabling triggers for CL_SGWJ...
alter table CL_SGWJ enable all triggers;
prompt Enabling triggers for CL_SPK...
alter table CL_SPK enable all triggers;
prompt Enabling triggers for CL_XC...
alter table CL_XC enable all triggers;
prompt Enabling triggers for CL_XL...
alter table CL_XL enable all triggers;
prompt Enabling triggers for CL_XLCL...
alter table CL_XLCL enable all triggers;
prompt Enabling triggers for CL_XLZD...
alter table CL_XLZD enable all triggers;
prompt Enabling triggers for CL_YY...
alter table CL_YY enable all triggers;
prompt Enabling triggers for CL_ZD...
alter table CL_ZD enable all triggers;
prompt Enabling triggers for CL_ZDGL...
alter table CL_ZDGL enable all triggers;
prompt Enabling triggers for CL_ZNZP...
alter table CL_ZNZP enable all triggers;
prompt Enabling triggers for CL_ZP_XL...
alter table CL_ZP_XL enable all triggers;
prompt Enabling triggers for SYS_CLK_PTJS...
alter table SYS_CLK_PTJS enable all triggers;
prompt Enabling triggers for SYS_CLK_PTYH...
alter table SYS_CLK_PTYH enable all triggers;
prompt Enabling triggers for SYS_FWGN...
alter table SYS_FWGN enable all triggers;
prompt Enabling triggers for SYS_HDYX...
alter table SYS_HDYX enable all triggers;
prompt Enabling triggers for SYS_HSGS...
alter table SYS_HSGS enable all triggers;
prompt Enabling triggers for SYS_JGSQLB...
alter table SYS_JGSQLB enable all triggers;
prompt Enabling triggers for SYS_JS_FW...
alter table SYS_JS_FW enable all triggers;
prompt Enabling triggers for SYS_JS_GN...
alter table SYS_JS_GN enable all triggers;
prompt Enabling triggers for SYS_JZGXX...
alter table SYS_JZGXX enable all triggers;
prompt Enabling triggers for SYS_MESSAGE...
alter table SYS_MESSAGE enable all triggers;
prompt Enabling triggers for SYS_PTFW...
alter table SYS_PTFW enable all triggers;
prompt Enabling triggers for SYS_PTJG...
alter table SYS_PTJG enable all triggers;
prompt Enabling triggers for SYS_PTRZ...
alter table SYS_PTRZ enable all triggers;
prompt Enabling triggers for SYS_RLB...
alter table SYS_RLB enable all triggers;
prompt Enabling triggers for SYS_WXYH...
alter table SYS_WXYH enable all triggers;
prompt Enabling triggers for SYS_XXTS...
alter table SYS_XXTS enable all triggers;
prompt Enabling triggers for SYS_YH_JS...
alter table SYS_YH_JS enable all triggers;
prompt Enabling triggers for SYS_YH_XX...
alter table SYS_YH_XX enable all triggers;
prompt Enabling triggers for SYS_YJFK...
alter table SYS_YJFK enable all triggers;
prompt Enabling triggers for SYS_YXHDWJ...
alter table SYS_YXHDWJ enable all triggers;
prompt Enabling triggers for SYS_ZDLM...
alter table SYS_ZDLM enable all triggers;
prompt Enabling triggers for SYS_ZDXM...
alter table SYS_ZDXM enable all triggers;
set feedback on
set define on
prompt Done.
