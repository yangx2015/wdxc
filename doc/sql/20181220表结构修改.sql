-- 2019-01-07yx导入生产环境

ALTER TABLE "CL_DD"
MODIFY ("JGDM" VARCHAR2(64 BYTE) )
MODIFY ("JGMC" VARCHAR2(128 BYTE) )

ALTER TABLE "CL_DDRZ"
MODIFY ("JGDM" VARCHAR2(64 BYTE) )
MODIFY ("JGMC" VARCHAR2(128 BYTE) )

ALTER TABLE "CL_DDLSB"
MODIFY ("JGDM" VARCHAR2(64 BYTE) )
MODIFY ("JGMC" VARCHAR2(128 BYTE) )

ALTER TABLE "CL_DZWL"
MODIFY ("JGDM" VARCHAR2(64 BYTE) )
MODIFY ("JGMC" VARCHAR2(128 BYTE) )