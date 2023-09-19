package com.imooc.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Filestore {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;


    /**
     * 文件全位置
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 创建时间 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间 更新
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取文件名称
     *
     * @return filename - 文件名称
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 设置文件名称
     *
     * @param filename 文件名称
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 获取文件路径
     *
     * @return file_path - 文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取文件类型
     *
     * @return file_type - 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型
     *
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取创建时间 创建时间
     *
     * @return create_time - 创建时间 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间 创建时间
     *
     * @param createTime 创建时间 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间 更新
     *
     * @return update_time - 更新时间 更新
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间 更新
     *
     * @param updateTime 更新时间 更新
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}