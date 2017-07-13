package cn.inphoto.dbentity.admin;

import javax.persistence.*;

/**
 * Created by ming on 17-7-13.
 */
@Entity
@Table(name = "admin_role_module", schema = "IN_Photo", catalog = "")
@IdClass(RoleModulePK.class)
public class RoleModule {
    private int roleId;
    private int moduleId;

    @Id
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "module_id")
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleModule that = (RoleModule) o;

        if (roleId != that.roleId) return false;
        if (moduleId != that.moduleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + moduleId;
        return result;
    }
}
