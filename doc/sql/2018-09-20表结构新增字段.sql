alter table CL_CLYXJL add SZDBH VARCHAR2(32);
comment on column CL_CLYXJL.SZDBH
  is '设备终端编号';


-- 测试环境数据库已经部了，武大内网的数据库还没有部署