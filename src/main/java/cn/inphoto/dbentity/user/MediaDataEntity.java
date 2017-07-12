package cn.inphoto.dbentity.user;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "media_data", schema = "IN_Photo", catalog = "")
public class MediaDataEntity {

    /*媒体数据正常*/
    public static final String MEDIA_STATE_NORMAL = "0";
    /*媒体数据已经移入回收站中*/
    public static final String MEDIA_STATE_RECYCLE = "1";
    /*媒体数据已删除*/
    public static final String MEDIA_STATE_DELETE = "2";

    private long mediaId;
    private String mediaName;
    private String filePath;
    private long userId;
    private int categoryId;
    private Timestamp createTime;
    private String mediaState;
    private Timestamp deleteTime;
    private Timestamp overTime;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "media_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    @Basic
    @Column(name = "media_name")
    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    @Basic
    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
    @Column(name = "create_time")
    @CreationTimestamp
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "media_state")
    public String getMediaState() {
        return mediaState;
    }

    public void setMediaState(String mediaState) {
        this.mediaState = mediaState;
    }

    @Basic
    @Column(name = "delete_time")
    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Basic
    @Column(name = "over_time")
    public Timestamp getOverTime() {
        return overTime;
    }

    public void setOverTime(Timestamp overTime) {
        this.overTime = overTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaDataEntity that = (MediaDataEntity) o;

        if (mediaId != that.mediaId) return false;
        if (userId != that.userId) return false;
        if (categoryId != that.categoryId) return false;
        if (mediaName != null ? !mediaName.equals(that.mediaName) : that.mediaName != null) return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (mediaState != null ? !mediaState.equals(that.mediaState) : that.mediaState != null) return false;
        if (deleteTime != null ? !deleteTime.equals(that.deleteTime) : that.deleteTime != null) return false;
        if (overTime != null ? !overTime.equals(that.overTime) : that.overTime != null) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = (int) (mediaId ^ (mediaId >>> 32));
        result = 31 * result + (mediaName != null ? mediaName.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + categoryId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (mediaState != null ? mediaState.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        result = 31 * result + (overTime != null ? overTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MediaDataEntity{" +
                "mediaId=" + mediaId +
                ", mediaName='" + mediaName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", createTime=" + createTime +
                ", mediaState='" + mediaState + '\'' +
                ", deleteTime=" + deleteTime +
                ", overTime=" + overTime +
                '}';
    }
}
