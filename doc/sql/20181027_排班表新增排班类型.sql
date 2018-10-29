-- 生产环境还没有更新 ，需要联系肖老师，更新数据库 20181027 yx

-- Add/modify columns
alter table CL_PB add CLASSES VARCHAR2(5);
alter table CL_PB add START_TIME VARCHAR2(12);
alter table CL_PB add END_TIME VARCHAR2(12);
-- Add comments to the table
comment on table CL_PB
  is '排班表';
-- Add comments to the columns
comment on column CL_PB.CLASSES
  is '排班_班次([ZDCLK0054]1 早班、2 午班、4、下午、7 全天 )';
comment on column CL_PB.START_TIME
  is '班次开始时间  格式为  HH:mm:ss';
comment on column CL_PB.END_TIME
  is '班次结束时间  格式为  HH:mm:ss';

alter table CL_PB modify CLASSES number(2);


INSERT INTO SYS_ZDLM (LMDM, LMMC, CJSJ, CJR, XGSJ, XGR, QZ) VALUES ('ZDCLK0054', '排班_班次', TO_TIMESTAMP('2018-10-27 22:01:41', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);

INSERT INTO SYS_ZDXM (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3) VALUES ('505864562214436864', 'ZDCLK0054', '4', '下午', null, TO_TIMESTAMP('2018-10-27 22:05:10', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);
INSERT INTO SYS_ZDXM (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3) VALUES ('505864532854308864', 'ZDCLK0054', '2', '午班', null, TO_TIMESTAMP('2018-10-27 22:05:03', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);
INSERT INTO SYS_ZDXM (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3) VALUES ('505864505461309440', 'ZDCLK0054', '1', '早班', null, TO_TIMESTAMP('2018-10-27 22:04:56', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);
INSERT INTO SYS_ZDXM (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3) VALUES ('505864437631025152', 'ZDCLK0054', '7', '全天', null, TO_TIMESTAMP('2018-10-27 22:04:40', 'YYYY-MM-DD HH24:MI:SS'), '1-超级管理员', null, null, null);

