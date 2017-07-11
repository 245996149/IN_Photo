package cn.inphoto.dbentity.user;

import javax.persistence.*;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "category", schema = "in_photo", catalog = "")
public class CategoryEntity {
    private int categoryId;
    private String categoryCode;
    private String categoryName;
    private byte madeGif;
    private byte gifTransparency;
    private String categoryNote;

    @Id
    @Column(name = "category_id")
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

        CategoryEntity that = (CategoryEntity) o;

        if (categoryId != that.categoryId) return false;
        if (categoryCode != null ? !categoryCode.equals(that.categoryCode) : that.categoryCode != null) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryId;
        result = 31 * result + (categoryCode != null ? categoryCode.hashCode() : 0);
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
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
}
