package cn.bidostar.ticserver.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by admins on 2018/3/1.
 */
@Table(name="localfiles")
public class LocalFilesModel implements Serializable {
    @Column(name="id",isId = true)
    public int id;
    @Column(name="filename")
    public String fileName;//文件名称
    @Column(name="addtime")
    public String addTime;//记录时间
    @Column(name="localpath")
    public String localPath;//文件本地路径
    @Column(name="flagremove")
    public int flagRemove;//是否删除（逻辑删除之后使用），物理删除记录即被销毁 0 正常  1 逻辑删除
    @Column(name="flagupload")
    public String flagUpload;//是否已经上传到服务器 0 未上传  1 已上传
    @Column(name="filepex")
    public String filePex;//文件后缀
    @Column(name="filetype")
    public int fileType;//文件类型  1 图片  2 视频  3 语音 4 文本
    @Column(name="filesize")
    public String fileSize;//文件大小
    @Column(name="jltype")
    public String jltype;//记录类型  1 普通记录 2 意外记录  3 手动记录

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public int getFlagRemove() {
        return flagRemove;
    }

    public void setFlagRemove(int flagRemove) {
        this.flagRemove = flagRemove;
    }

    public String getFlagUpload() {
        return flagUpload;
    }

    public void setFlagUpload(String flagUpload) {
        this.flagUpload = flagUpload;
    }

    public String getFilePex() {
        return filePex;
    }

    public void setFilePex(String filePex) {
        this.filePex = filePex;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getJltype() {
        return jltype;
    }

    public void setJltype(String jltype) {
        this.jltype = jltype;
    }
}
