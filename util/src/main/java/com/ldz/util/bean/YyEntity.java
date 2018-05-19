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
     *
     */
    private String modify_time;

    private String create_time;

    private LatestLocation latest_location;

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public LatestLocation getLatest_location() {
        return latest_location;
    }

    public void setLatest_location(LatestLocation latest_location) {
        this.latest_location = latest_location;
    }

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

    
}
