package com.ldz.sys.bean;

import com.ldz.sys.model.SysGn;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Menu {
    private String icon;
    private String path;
    private String name;
    private String title;
    private List<Menu> children;
    public Menu(SysGn function){
        this.icon = function.getTb();
        this.path = function.getUrl();
        this.name = function.getUrl();
        this.title = function.getGnmc();
    }
}
