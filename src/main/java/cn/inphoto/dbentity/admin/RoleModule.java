package cn.inphoto.dbentity.admin;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by root on 17-7-11.
 */
@Entity
@Table(name = "admin_role_module", schema = "IN_Photo", catalog = "")
public class RoleModule {
    private int roleId;
    private int moduleId;

    @Basic
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
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
