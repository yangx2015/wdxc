# ldz
#安装oracle驱动至本地maven仓库
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.5.0 -Dpackaging=jar -Dfile=/develop/workspace/ldz/ojdbc7.jar


-----------
redis 标签KEY管理

以下的KEY都是智能站牌（TIC_ZNZP）中使用。需要BIZ项目在修改时间要做清除： 
这些KEY会在当天23.59.59分失效
- ZNZP_CL_{{终端编号}}   BIZ 车辆修改、删除时，需要先查询一次车辆，获取取终端编号。    `ZdglServiceImpl `
- ZNZP_PB_{{DateUtils.getToday("yyyyMMdd")}}_{{cl_id}}   BIZ在取消排班时，一定要清除。 `delPbList`  `deleteByXlAndCl`
- ZNZP_XL_{{xlId}}           BIZ 修改、删除线路时后，需要清除     `xlserviceimp.updateEntity`  
- ZNZP_XLZD_{{xlId}}         BIZ 修改、删除线路时后，需要清除     `xlserviceimp.updateEntity`  
- ZNZP_LISTXL_{{xlId}}   智能站牌在修改时，会先删除线路，这里需要在删除前，先查询一下所有的线路，将所有线路的redis做移除后，再做下面的操作。


---------
清除一个终端号的历史线路 SQL 
SELECT * FROM CL_XC where CL_ZDBH='865923030009978' ORDER BY ID DESC;
select * from CL_GPS where ZDBH='865923030009978';
select * from CL_GPS_LS where ZDBH='865923030009978' order by id desc ;
SELECT * FROM CL_YY WHERE ZDBH ='865923030009978';

delete from CL_XC where CL_ZDBH='865923030009978';
delete from CL_GPS where ZDBH='865923030009978';
delete from CL_GPS_LS where ZDBH='865923030009978';
delete FROM CL_YY WHERE ZDBH ='865923030009978';