package com.ldz.util.bean;

/**
 * 鹰眼设备 entity
 */
public class YyEntity {

    /**
     * 用户的ak   （必填）
     */
    private String ak;

    /**
     * service的ID，service 的唯一标识  （必填）
     * 在轨迹管理台创建鹰眼服务时，系统返回的 service_id
     */
    private String service_id;

    /**
     * entity名称，作为其唯一标识 String(128)  （必填）
     *
     * 同一service服务中entity_name不可重复。一旦创建，entity_name 不可更新。
     * 命名规则：仅支持中文、英文大小字母、英文下划线"_"、英文横线"-"和数字。 entity_name 和 entity_desc 支持联合模糊检索。
     */
    private String entity_name;

    /**
     * entity 的可读性描述 String(128)
     * 命名规则：仅支持中文、英文大小字母、英文下划线"_"、英文横线"-"和数字。entity_name 和 entity_desc 支持联合模糊检索。
     */
    private String entity_desc;
    /**
     * city  和 district 为自定义字段
     */
    private String city;

    private String district;

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getEntity_desc() {
        return entity_desc;
    }

    public void setEntity_desc(String entity_desc) {
        this.entity_desc = entity_desc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
