package cn.inphoto.dbentity.admin;

import javax.persistence.*;

/**
 * Created by ming on 17-7-13.
 */
@Entity
@Table(name = "admin_category", schema = "IN_Photo", catalog = "")
@IdClass(AdminCategoryPK.class)
public class AdminCategory {
    private int adminId;
    private int categoryId;

    @Id
    @Column(name = "admin_id")
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Id
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

        AdminCategory that = (AdminCategory) o;

        if (adminId != that.adminId) return false;
        if (categoryId != that.categoryId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adminId;
        result = 31 * result + categoryId;
        return result;
    }

}
