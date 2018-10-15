-- 生产环境还没有更新 ，需要联系肖老师，更新数据库 20181012 yx
CREATE TABLE CL_WF
(
    id VARCHAR2(32),
    wf_type VARCHAR2(10),
    wf_cl VARCHAR2(32),
    wf_cph VARCHAR2(32),
    wf_jsz VARCHAR2(32),
    wf_name VARCHAR2(32),
    wf_date VARCHAR2(32),
    wf_site VARCHAR2(600),
    wf_scoring VARCHAR2(10),
    wf_money VARCHAR2(20),
    wf_status INT
);
COMMENT ON COLUMN CL_WF.id IS 'PKID';
COMMENT ON COLUMN CL_WF.wf_type IS '违法类型 [ZDCLK0052] （10 车辆违法、20驾驶员违法）';
COMMENT ON COLUMN CL_WF.wf_cl IS '违法车辆ID 关联cl_cl表';
COMMENT ON COLUMN CL_WF.wf_cph IS '违法车牌号';
COMMENT ON COLUMN CL_WF.wf_jsz IS '违法驾证号';
COMMENT ON COLUMN CL_WF.wf_name IS '违法人姓名';
COMMENT ON COLUMN CL_WF.wf_date IS '违法时间';
COMMENT ON COLUMN CL_WF.wf_site IS '违法地点';
COMMENT ON COLUMN CL_WF.wf_scoring IS '违法记分';
COMMENT ON COLUMN CL_WF.wf_money IS '违法金额';
COMMENT ON COLUMN CL_WF.wf_status IS '违法状态  ZDCLK0053 (0未处理 1已处理)';
COMMENT ON TABLE CL_WF IS '违法表';

ALTER TABLE CL_WF ADD CONSTRAINT "CL_WF_ID_pk" PRIMARY KEY (ID);
ALTER TABLE C##WDXC_ADMIN.SYS_JZGXX ADD PWD VARCHAR2(32) NULL;
COMMENT ON COLUMN C##WDXC_ADMIN.SYS_JZGXX.PWD IS '登录密码';
COMMENT ON TABLE C##WDXC_ADMIN.SYS_JZGXX IS '教职工表';

COMMENT ON COLUMN SYS_JZGXX.ZJHM IS '证件号码-工号';
 

INSERT INTO SYS_ZDLM (LMDM, LMMC, CJSJ, CJR, XGSJ, XGR, QZ) VALUES ('ZDCLK0053', '违法状态', TO_TIMESTAMP('2018-10-12 16:42:21', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);
INSERT INTO SYS_ZDLM (LMDM, LMMC, CJSJ, CJR, XGSJ, XGR, QZ) VALUES ('ZDCLK0052', '违法类型 [ZDCLK0052]', TO_TIMESTAMP('2018-10-12 16:35:25', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);

INSERT INTO SYS_ZDXM (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3) VALUES ('500347586197061632', 'ZDCLK0053', '1', '已处理', null, TO_TIMESTAMP('2018-10-12 16:42:40', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);
INSERT INTO SYS_ZDXM (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3) VALUES ('500347558669844480', 'ZDCLK0053', '0', '未处理', null, TO_TIMESTAMP('2018-10-12 16:42:33', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);
INSERT INTO SYS_ZDXM (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3) VALUES ('500345845363769344', 'ZDCLK0052', '20', '驾驶员违法', null, TO_TIMESTAMP('2018-10-12 16:35:45', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);
INSERT INTO SYS_ZDXM (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3) VALUES ('500345807942189056', 'ZDCLK0052', '10', '车辆违法', null, TO_TIMESTAMP('2018-10-12 16:35:36', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);




insert into SYS_FWGN (gndm, gnmc, fwdm, cjsj, cjr, xgsj, xgr, zt, bz, url, fjd, tzdz, tb, api_qz, api_hz, px)
values ('WFXXGL', '违法管理', '1', to_timestamp('13-10-2018 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-超级管理员', to_timestamp('13-10-2018 14:46:35.857000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-超级管理员', '00', null, 'wfxx', 'VehicleScheduling', '/', 'ios-clipboard', '/api/wfxx', '/', 2);

insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('500623006314266624', '100', '1', 'WFXXGL', to_timestamp('13-10-2018 10:57:05.800000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-超级管理员', null, null, null);

insert into SYS_JGSQLB (id, jgdm, fwdm, gndm, cjsj, cjr, xgsj, xgr, yxq)
values ('1', '100', null, 'terminal-station', to_timestamp('25-07-2018 09:50:14.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1-超级管理员', null, null, null);

