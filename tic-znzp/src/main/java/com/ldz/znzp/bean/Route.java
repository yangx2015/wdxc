package com.ldz.znzp.bean;

import com.ldz.znzp.model.ClXl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Route {
    private String routeId;
    private String routeName;
    private String startTime;
    private String endTime;
    private String routeType;
    private String runTime;
    private List<Station> stations;

    public Route(ClXl xl){
        this.routeId = xl.getId();
        this.routeName = xl.getXlmc();
        this.startTime = getTime(xl.getYxkssj());
        this.endTime = getTime(xl.getYxjssj());
        this.routeType = xl.getLx();
        this.runTime = String.valueOf(xl.getPjsj());
    }


    private String getTime(short t){
        String s = "";
        if (t < 10)s = "0";
        return s+t+":00";
    }
}
