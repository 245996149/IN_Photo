package cn.inphoto.dbentity.user;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "share_data", schema = "IN_Photo", catalog = "")
public class ShareData {

    /*微信分享给好友*/
    public static final String SHARE_TYPE_WECHAT_SHARE_CHATS = "1";
    /*微信分享到朋友圈*/
    public static final String SHARE_TYPE_WECHAT_SHARE_MOMENTS = "2";
    /*二次点击*/
    public static final String SHARE_TYPE_WEB_CLICK_AGE = "3";

    private long shareDataId;
    private long userId;
    private int categoryId;
    private long mediaId;
    private Timestamp shareTime;
    private String shareType;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "share_data_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getShareDataId() {
        return shareDataId;
    }

    public void setShareDataId(long shareDataId) {
        this.shareDataId = shareDataId;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "category_id")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "media_id")
    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    @Basic
    @Column(name = "share_time")
    @CreationTimestamp
    public Timestamp getShareTime() {
        return shareTime;
    }

    public void setShareTime(Timestamp shareTime) {
        this.shareTime = shareTime;
    }

    @Basic
    @Column(name = "share_type")
    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShareData that = (ShareData) o;

        if (shareDataId != that.shareDataId) return false;
        if (userId != that.userId) return false;
        if (categoryId != that.categoryId) return false;
        if (mediaId != that.mediaId) return false;
        if (shareTime != null ? !shareTime.equals(that.shareTime) : that.shareTime != null) return false;
        if (shareType != null ? !shareType.equals(that.shareType) : that.shareType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (shareDataId ^ (shareDataId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + categoryId;
        result = 31 * result + (int) (mediaId ^ (mediaId >>> 32));
        result = 31 * result + (shareTime != null ? shareTime.hashCode() : 0);
        result = 31 * result + (shareType != null ? shareType.hashCode() : 0);
        return result;
    }
}
