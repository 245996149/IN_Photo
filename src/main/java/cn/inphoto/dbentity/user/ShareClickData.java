package cn.inphoto.dbentity.user;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "share_click_data", schema = "IN_Photo")
public class ShareClickData {

    private long shareClickDataId;
    private long userId;
    private int categoryId;
    private long mediaId;
    private Timestamp clickTime;
    private OSType osType;//系统类型
    private String screenResolution;//屏幕分辨率
    private String osVersion;//系统版本
    private BrowserType browserType;//浏览器类型
    private String brand;//品牌
    private String model;

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Id
    @Column(name = "share_click_data_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getShareClickDataId() {
        return shareClickDataId;
    }

    public void setShareClickDataId(long shareClickDataId) {
        this.shareClickDataId = shareClickDataId;
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
    @Column(name = "media_id")
    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    @Basic
    @Column(name = "click_time")
    @CreationTimestamp
    public Timestamp getClickTime() {
        return clickTime;
    }

    public void setClickTime(Timestamp clickTime) {
        this.clickTime = clickTime;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "os_type")
    public OSType getOsType() {
        return osType;
    }

    public void setOsType(OSType osType) {
        this.osType = osType;
    }

    @Basic
    @Column(name = "screen_resolution")
    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    @Basic
    @Column(name = "os_version")
    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @Basic
    @Column(name = "browser_type")
    @Enumerated(EnumType.STRING)
    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShareClickData that = (ShareClickData) o;

        if (shareClickDataId != that.shareClickDataId) return false;
        if (userId != that.userId) return false;
        if (categoryId != that.categoryId) return false;
        if (mediaId != that.mediaId) return false;
        if (clickTime != null ? !clickTime.equals(that.clickTime) : that.clickTime != null) return false;
        if (osType != that.osType) return false;
        if (screenResolution != null ? !screenResolution.equals(that.screenResolution) : that.screenResolution != null)
            return false;
        if (osVersion != null ? !osVersion.equals(that.osVersion) : that.osVersion != null) return false;
        if (browserType != that.browserType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (shareClickDataId ^ (shareClickDataId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + categoryId;
        result = 31 * result + (int) (mediaId ^ (mediaId >>> 32));
        result = 31 * result + (clickTime != null ? clickTime.hashCode() : 0);
        result = 31 * result + (osType != null ? osType.hashCode() : 0);
        result = 31 * result + (screenResolution != null ? screenResolution.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (browserType != null ? browserType.hashCode() : 0);
        return result;
    }

    public enum OSType {
        ios,
        android,
        windows,
        otherOs
    }

    public enum BrowserType {
        wechat,
        weibo,
        otherBrowser
    }

    @Override
    public String toString() {
        return "ShareClickData{" +
                "shareClickDataId=" + shareClickDataId +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", mediaId=" + mediaId +
                ", clickTime=" + clickTime +
                ", osType=" + osType +
                ", screenResolution='" + screenResolution + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", browserType=" + browserType +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
