package cn.inphoto.dbentity.admin;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by ming on 17-7-13.
 */
@Entity
@Table(name = "admin_module_info", schema = "IN_Photo", catalog = "")
public class ModuleInfo {
    private int moduleId;
    private String name;
    private Set<RoleInfo> roleInfoSet;

    /*模块映射*/
    @ManyToMany(mappedBy = "moduleInfoSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<RoleInfo> getRoleInfoSet() {
        return roleInfoSet;
    }

    public void setRoleInfoSet(Set<RoleInfo> roleInfoSet) {
        this.roleInfoSet = roleInfoSet;
    }

    @Id
    @Column(name = "module_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModuleInfo that = (ModuleInfo) o;

        if (moduleId != that.moduleId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moduleId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ModuleInfo{" +
                "moduleId=" + moduleId +
                ", name='" + name + '\'' +
//                ", roleInfoSet=" + roleInfoSet +
                '}';
    }
}
