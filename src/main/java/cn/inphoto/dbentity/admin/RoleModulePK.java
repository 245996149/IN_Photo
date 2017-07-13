package cn.inphoto.dbentity.admin;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by ming on 17-7-13.
 */
public class RoleModulePK implements Serializable {
    private int roleId;
    private int moduleId;

    @Column(name = "role_id")
    @Id
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name = "module_id")
    @Id
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

        RoleModulePK that = (RoleModulePK) o;

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
