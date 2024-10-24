package io.github.wittyprince.commons.cmm1.dal.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * base DO
 *
 * @author WangChen
 * Created on 2023/9/25
 * @since 1.0
 */
public class BaseDO implements Serializable {

    /**
     * pk
     */
    private Long id;

    /**
     * 实体类唯一标识
     */
    private String bid;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String createBy;
    private String updateBy;

    /**
     * 0-未删除: 默认
     * 1-删除
     */
    private Boolean deleted;

    /**
     * 各实体类继承时:
     * 1. 可以重写该方法, 用以产生identity
     * 2. 也可以不重写，使用默认的生成方式
     */
    public String identityGenerateCode() {
        return "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "BaseDO{" +
                "id=" + id +
                ", bid='" + bid + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
