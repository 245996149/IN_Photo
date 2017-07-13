package cn.inphoto.dbentity.admin;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by ming on 17-7-13.
 */
public class AdminCategoryPK implements Serializable {
    private int adminId;
    private int categoryId;

    @Column(name = "admin_id")
    @Id
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Column(name = "category_id")
    @Id
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

        AdminCategoryPK that = (AdminCategoryPK) o;

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
