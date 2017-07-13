package cn.inphoto.dbentity.admin;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by ming on 17-7-13.
 */
public class AdminRolePK implements Serializable {
    private int adminId;
    private int roleId;

    @Column(name = "admin_id")
    @Id
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Column(name = "role_id")
    @Id
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminRolePK that = (AdminRolePK) o;

        if (adminId != that.adminId) return false;
        if (roleId != that.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adminId;
        result = 31 * result + roleId;
        return result;
    }
}
