package cn.inphoto.user.dbentity;

import javax.persistence.*;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "media_code", schema = "IN_Photo", catalog = "")
public class MediaCodeEntity {

    private long mediaCodeId;
    private String mediaCode;
    private long mediaId;
    private int userId;
    private int categoryId;

    @Id
    @Column(name = "media_code_id")
    public long getMediaCodeId() {
        return mediaCodeId;
    }

    public void setMediaCodeId(long mediaCodeId) {
        this.mediaCodeId = mediaCodeId;
    }

    @Basic
    @Column(name = "media_code")
    public String getMediaCode() {
        return mediaCode;
    }

    public void setMediaCode(String mediaCode) {
        this.mediaCode = mediaCode;
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
    @Column(name = "user_id")
    public int getUserId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaCodeEntity that = (MediaCodeEntity) o;

        if (mediaCodeId != that.mediaCodeId) return false;
        if (mediaId != that.mediaId) return false;
        if (userId != that.userId) return false;
        if (categoryId != that.categoryId) return false;
        if (mediaCode != null ? !mediaCode.equals(that.mediaCode) : that.mediaCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (mediaCodeId ^ (mediaCodeId >>> 32));
        result = 31 * result + (mediaCode != null ? mediaCode.hashCode() : 0);
        result = 31 * result + (int) (mediaId ^ (mediaId >>> 32));
        result = 31 * result + userId;
        result = 31 * result + categoryId;
        return result;
    }
}
