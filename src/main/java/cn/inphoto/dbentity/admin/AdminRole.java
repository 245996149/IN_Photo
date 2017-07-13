package cn.inphoto.dbentity.admin;

import javax.persistence.*;

/**
 * Created by ming on 17-7-13.
 */
@Entity
@Table(name = "admin_role", schema = "IN_Photo", catalog = "")
@IdClass(AdminRolePK.class)
public class AdminRole {
    private int adminId;
    private int roleId;

    @Id
    @Column(name = "admin_id")
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Id
    @Column(name = "role_id")
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

        AdminRole adminRole = (AdminRole) o;

        if (adminId != adminRole.adminId) return false;
        if (roleId != adminRole.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adminId;
        result = 31 * result + roleId;
        return result;
    }
}
