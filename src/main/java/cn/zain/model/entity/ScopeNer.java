package cn.zain.model.entity;

import java.io.Serializable;
import java.util.Date;

public class ScopeNer implements Serializable {
    private Long id;

    private String scopeType;

    private String scopeValue;

    private String nerKey;

    private String nerValue;

    private String remark;

    private Date createTime;

    private String isValid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getScopeValue() {
        return scopeValue;
    }

    public void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue;
    }

    public String getNerKey() {
        return nerKey;
    }

    public void setNerKey(String nerKey) {
        this.nerKey = nerKey;
    }

    public String getNerValue() {
        return nerValue;
    }

    public void setNerValue(String nerValue) {
        this.nerValue = nerValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", scopeType=").append(scopeType);
        sb.append(", scopeValue=").append(scopeValue);
        sb.append(", nerKey=").append(nerKey);
        sb.append(", nerValue=").append(nerValue);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", isValid=").append(isValid);
        sb.append("]");
        return sb.toString();
    }
}