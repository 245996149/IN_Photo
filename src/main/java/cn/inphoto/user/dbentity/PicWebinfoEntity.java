package cn.inphoto.user.dbentity;

import javax.persistence.*;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "pic_webinfo", schema = "IN_Photo", catalog = "")
public class PicWebinfoEntity {

    /*正常生效*/
    public static final String PIC_WEB_INFO_STATE_NORMAL = "0";
    /*预览状态*/
    public static final String PIC_WEB_INFO_STATE_PREVIEW = "1";

    private long picWebinfoId;
    private int userId;
    private int categoryId;
    private String pageTitle;
    private String background;
    private Double pictureTop;
    private Double pictureLeft;
    private Double pictureRight;
    private Double pictureBottom;
    private String picWebinfoState;

    @Id
    @Column(name = "pic_webinfo_id")
    public long getPicWebinfoId() {
        return picWebinfoId;
    }

    public void setPicWebinfoId(long picWebinfoId) {
        this.picWebinfoId = picWebinfoId;
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

    @Basic
    @Column(name = "page_title")
    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Basic
    @Column(name = "background")
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Basic
    @Column(name = "picture_top")
    public Double getPictureTop() {
        return pictureTop;
    }

    public void setPictureTop(Double pictureTop) {
        this.pictureTop = pictureTop;
    }

    @Basic
    @Column(name = "picture_left")
    public Double getPictureLeft() {
        return pictureLeft;
    }

    public void setPictureLeft(Double pictureLeft) {
        this.pictureLeft = pictureLeft;
    }

    @Basic
    @Column(name = "picture_right")
    public Double getPictureRight() {
        return pictureRight;
    }

    public void setPictureRight(Double pictureRight) {
        this.pictureRight = pictureRight;
    }

    @Basic
    @Column(name = "picture_bottom")
    public Double getPictureBottom() {
        return pictureBottom;
    }

    public void setPictureBottom(Double pictureBottom) {
        this.pictureBottom = pictureBottom;
    }

    @Basic
    @Column(name = "pic_webinfo_state")
    public String getPicWebinfoState() {
        return picWebinfoState;
    }

    public void setPicWebinfoState(String picWebinfoState) {
        this.picWebinfoState = picWebinfoState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PicWebinfoEntity that = (PicWebinfoEntity) o;

        if (picWebinfoId != that.picWebinfoId) return false;
        if (userId != that.userId) return false;
        if (categoryId != that.categoryId) return false;
        if (pageTitle != null ? !pageTitle.equals(that.pageTitle) : that.pageTitle != null) return false;
        if (background != null ? !background.equals(that.background) : that.background != null) return false;
        if (pictureTop != null ? !pictureTop.equals(that.pictureTop) : that.pictureTop != null) return false;
        if (pictureLeft != null ? !pictureLeft.equals(that.pictureLeft) : that.pictureLeft != null) return false;
        if (pictureRight != null ? !pictureRight.equals(that.pictureRight) : that.pictureRight != null) return false;
        if (pictureBottom != null ? !pictureBottom.equals(that.pictureBottom) : that.pictureBottom != null)
            return false;
        if (picWebinfoState != null ? !picWebinfoState.equals(that.picWebinfoState) : that.picWebinfoState != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (picWebinfoId ^ (picWebinfoId >>> 32));
        result = 31 * result + userId;
        result = 31 * result + categoryId;
        result = 31 * result + (pageTitle != null ? pageTitle.hashCode() : 0);
        result = 31 * result + (background != null ? background.hashCode() : 0);
        result = 31 * result + (pictureTop != null ? pictureTop.hashCode() : 0);
        result = 31 * result + (pictureLeft != null ? pictureLeft.hashCode() : 0);
        result = 31 * result + (pictureRight != null ? pictureRight.hashCode() : 0);
        result = 31 * result + (pictureBottom != null ? pictureBottom.hashCode() : 0);
        result = 31 * result + (picWebinfoState != null ? picWebinfoState.hashCode() : 0);
        return result;
    }
}
