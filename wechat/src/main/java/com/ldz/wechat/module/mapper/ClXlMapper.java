package com.ldz.wechat.module.mapper;

import com.ldz.wechat.module.bean.DdClModel;
import com.ldz.wechat.module.model.ClXl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ClXlMapper extends Mapper<ClXl> {

    @Select("SELECT Z.FW AS FW,Z.ID AS ID,Z.MC AS MC,(SELECT COUNT(1) FROM CL_CLYXJL JL WHERE JL.ZDBH=Z.ID) AS CLSL FROM CL_XLZD T,CL_ZD Z WHERE T.ZD_ID=Z.ID AND T.ZT='00' AND Z.ZT ='00' AND  T.XL_ID= #{id} ORDER BY T.XH ASC")
    @Results({
            @Result(property = "zdId", column = "ID"),
            @Result(property = "zdName", column = "MC"),
            @Result(property = "vehicleCount", column = "CLSL"),
            @Result(property = "vehicleScope", column = "FW")
    })
    List<DdClModel> getBySiteVehicleList(@Param("id") String id);
}
