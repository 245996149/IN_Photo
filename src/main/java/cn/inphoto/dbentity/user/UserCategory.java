package cn.inphoto.dbentity.user;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "user_category", schema = "IN_Photo", catalog = "")
public class UserCategory implements Serializable {

    /*系统正常*/
    public static final String USER_CATEGORY_STATE_NORMAL = "0";
    /*系统已过期*/
    public static final String USER_CATEGORY_STATE_OVER = "1";
    /*系统未开始*/
    public static final String USER_CATEGORY_STATE_NOT_START = "2";

    private long userCategoryId;
    private long userId;
    private Timestamp payTime;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Integer mediaNumber;
    private Integer categoryId;
    private String userCategoryState;


    @Id
    @Column(name = "user_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getUserCategoryId() {
        return userCategoryId;
    }

    public void setUserCategoryId(long userCategoryId) {
        this.userCategoryId = userCategoryId;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "pay_time")
    @CreationTimestamp
    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    @Basic
    @Column(name = "begin_time")
    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "media_number")
    public Integer getMediaNumber() {
        return mediaNumber;
    }

    public void setMediaNumber(Integer mediaNumber) {
        this.mediaNumber = mediaNumber;
    }

    @Basic
    @Column(name = "category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "user_category_state")
    public String getUserCategoryState() {
        return userCategoryState;
    }

    public void setUserCategoryState(String userCategoryState) {
        this.userCategoryState = userCategoryState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCategory that = (UserCategory) o;

        if (userCategoryId != that.userCategoryId) return false;
        if (userId != that.userId) return false;
        if (payTime != null ? !payTime.equals(that.payTime) : that.payTime != null) return false;
        if (beginTime != null ? !beginTime.equals(that.beginTime) : that.beginTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (mediaNumber != null ? !mediaNumber.equals(that.mediaNumber) : that.mediaNumber != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (userCategoryState != null ? !userCategoryState.equals(that.userCategoryState) : that.userCategoryState != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userCategoryId ^ (userCategoryId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (payTime != null ? payTime.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (mediaNumber != null ? mediaNumber.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (userCategoryState != null ? userCategoryState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserCategory{" +
                "userCategoryId=" + userCategoryId +
                ", userId=" + userId +
                ", payTime=" + payTime +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", mediaNumber=" + mediaNumber +
                ", categoryId=" + categoryId +
                ", userCategoryState='" + userCategoryState + '\'' +
                '}';
    }
}
