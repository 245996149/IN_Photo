package cn.inphoto.dbentity.user;

import javax.persistence.*;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "share_info", schema = "IN_Photo", catalog = "")
public class ShareInfoEntity {
    private long shareInfoId;
    private long userId;
    private int categoryId;
    private String shareMomentsTitle;
    private String shareMomentsIcon;
    private String shareChatsTitle;
    private String shareChatsText;
    private String shareChatsIcon;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "share_info_id")
    public long getShareInfoId() {
        return shareInfoId;
    }

    public void setShareInfoId(long shareInfoId) {
        this.shareInfoId = shareInfoId;
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
    @Column(name = "share_moments_title")
    public String getShareMomentsTitle() {
        return shareMomentsTitle;
    }

    public void setShareMomentsTitle(String shareMomentsTitle) {
        this.shareMomentsTitle = shareMomentsTitle;
    }

    @Basic
    @Column(name = "share_moments_icon")
    public String getShareMomentsIcon() {
        return shareMomentsIcon;
    }

    public void setShareMomentsIcon(String shareMomentsIcon) {
        this.shareMomentsIcon = shareMomentsIcon;
    }

    @Basic
    @Column(name = "share_chats_title")
    public String getShareChatsTitle() {
        return shareChatsTitle;
    }

    public void setShareChatsTitle(String shareChatsTitle) {
        this.shareChatsTitle = shareChatsTitle;
    }

    @Basic
    @Column(name = "share_chats_text")
    public String getShareChatsText() {
        return shareChatsText;
    }

    public void setShareChatsText(String shareChatsText) {
        this.shareChatsText = shareChatsText;
    }

    @Basic
    @Column(name = "share_chats_icon")
    public String getShareChatsIcon() {
        return shareChatsIcon;
    }

    public void setShareChatsIcon(String shareChatsIcon) {
        this.shareChatsIcon = shareChatsIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShareInfoEntity that = (ShareInfoEntity) o;

        if (shareInfoId != that.shareInfoId) return false;
        if (userId != that.userId) return false;
        if (categoryId != that.categoryId) return false;
        if (shareMomentsTitle != null ? !shareMomentsTitle.equals(that.shareMomentsTitle) : that.shareMomentsTitle != null)
            return false;
        if (shareMomentsIcon != null ? !shareMomentsIcon.equals(that.shareMomentsIcon) : that.shareMomentsIcon != null)
            return false;
        if (shareChatsTitle != null ? !shareChatsTitle.equals(that.shareChatsTitle) : that.shareChatsTitle != null)
            return false;
        if (shareChatsText != null ? !shareChatsText.equals(that.shareChatsText) : that.shareChatsText != null)
            return false;
        if (shareChatsIcon != null ? !shareChatsIcon.equals(that.shareChatsIcon) : that.shareChatsIcon != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (shareInfoId ^ (shareInfoId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + categoryId;
        result = 31 * result + (shareMomentsTitle != null ? shareMomentsTitle.hashCode() : 0);
        result = 31 * result + (shareMomentsIcon != null ? shareMomentsIcon.hashCode() : 0);
        result = 31 * result + (shareChatsTitle != null ? shareChatsTitle.hashCode() : 0);
        result = 31 * result + (shareChatsText != null ? shareChatsText.hashCode() : 0);
        result = 31 * result + (shareChatsIcon != null ? shareChatsIcon.hashCode() : 0);
        return result;
    }
}
