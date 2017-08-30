package cn.inphoto.dbentity.user;

import cn.inphoto.dbentity.admin.AdminInfo;
import cn.inphoto.dbentity.admin.ModuleInfo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "category", schema = "IN_Photo", catalog = "")
public class Category {
    private int categoryId;
    private String categoryCode;
    private String categoryName;
    private byte madeGif;
    private byte gifTransparency;
    private String categoryNote;
    private Set<AdminInfo> adminInfoSet;

    /*套餐映射*/
    @ManyToMany(mappedBy = "categorySet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<AdminInfo> getAdminInfoSet() {
        return adminInfoSet;
    }

    public void setAdminInfoSet(Set<AdminInfo> adminInfoSet) {
        this.adminInfoSet = adminInfoSet;
    }

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_code")
    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Basic
    @Column(name = "category_name")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (categoryId != category.categoryId) return false;
        if (madeGif != category.madeGif) return false;
        if (gifTransparency != category.gifTransparency) return false;
        if (categoryCode != null ? !categoryCode.equals(category.categoryCode) : category.categoryCode != null)
            return false;
        if (categoryName != null ? !categoryName.equals(category.categoryName) : category.categoryName != null)
            return false;
        if (categoryNote != null ? !categoryNote.equals(category.categoryNote) : category.categoryNote != null)
            return false;
        return adminInfoSet != null ? adminInfoSet.equals(category.adminInfoSet) : category.adminInfoSet == null;
    }

    @Override
    public int hashCode() {
        int result = categoryId;
        result = 31 * result + (categoryCode != null ? categoryCode.hashCode() : 0);
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (int) madeGif;
        result = 31 * result + (int) gifTransparency;
        result = 31 * result + (categoryNote != null ? categoryNote.hashCode() : 0);
        result = 31 * result + (adminInfoSet != null ? adminInfoSet.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "made_gif")
    public byte getMadeGif() {
        return madeGif;
    }

    public void setMadeGif(byte madeGif) {
        this.madeGif = madeGif;
    }

    @Basic
    @Column(name = "gif_transparency")
    public byte getGifTransparency() {
        return gifTransparency;
    }

    public void setGifTransparency(byte gifTransparency) {
        this.gifTransparency = gifTransparency;
    }

    @Basic
    @Column(name = "category_note")
    public String getCategoryNote() {
        return categoryNote;
    }

    public void setCategoryNote(String categoryNote) {
        this.categoryNote = categoryNote;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryCode='" + categoryCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", madeGif=" + madeGif +
                ", gifTransparency=" + gifTransparency +
                ", categoryNote='" + categoryNote + '\'' +
                '}';
    }
}
