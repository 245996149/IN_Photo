package cn.inphoto.dbentity.user;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "media_data", schema = "IN_Photo", catalog = "")
public class MediaData {

    private long mediaId;
    private String mediaName;
    private String mediaKey;
    private long userId;
    private int categoryId;
    private Timestamp createTime;
    private MediaState mediaState;
    private Timestamp deleteTime;
    private Timestamp overTime;
    private MediaType mediaType = MediaType.MediaData;
    private Long videoPicMedia;

    @Basic
    @Column(name = "video_pic_media_id")
    public Long getVideoPicMedia() {
        return videoPicMedia;
    }

    public void setVideoPicMedia(Long videoPicMedia) {
        this.videoPicMedia = videoPicMedia;
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
    @Column(name = "media_key")
    public String getMediaKey() {
        return mediaKey;
    }

    public void setMediaKey(String mediaKey) {
        this.mediaKey = mediaKey;
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
    @Enumerated(EnumType.ORDINAL)
    public MediaState getMediaState() {
        return mediaState;
    }

    public void setMediaState(MediaState mediaState) {
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

    @Basic
    @Column(name = "media_type")
    @Enumerated(EnumType.ORDINAL)
    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaData that = (MediaData) o;

        if (mediaId != that.mediaId) return false;
        if (userId != that.userId) return false;
        if (categoryId != that.categoryId) return false;
        if (mediaName != null ? !mediaName.equals(that.mediaName) : that.mediaName != null) return false;
        if (mediaKey != null ? !mediaKey.equals(that.mediaKey) : that.mediaKey != null) return false;
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
        result = 31 * result + (mediaKey != null ? mediaKey.hashCode() : 0);
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
        return "MediaData{" +
                "mediaId=" + mediaId +
                ", mediaName='" + mediaName + '\'' +
                ", mediaKey='" + mediaKey + '\'' +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", createTime=" + createTime +
                ", mediaState=" + mediaState +
                ", deleteTime=" + deleteTime +
                ", overTime=" + overTime +
                ", mediaType=" + mediaType +
                ", videoPicMedia=" + videoPicMedia +
                '}';
    }


    public enum MediaState {
        /*媒体数据正常*/
        Normal,
        /*媒体数据已经移入回收站中*/
        Recycle,
        /*媒体数据已删除*/
        Delete,
        /*套餐过期，媒体数据待移动到回收站中*/
        WillDelete
    }

    public enum MediaType {
        /*媒体数据*/
        MediaData,
        /*用户设置数据*/
        SettingsData,
        /*视频缩略图*/
        VideoPicData
    }
}
