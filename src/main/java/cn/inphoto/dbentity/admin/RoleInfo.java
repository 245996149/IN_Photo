package cn.inphoto.dbentity.admin;

import cn.inphoto.dao.ModuleDao;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ming on 17-7-13.
 */
@Entity
@Table(name = "admin_role_info", schema = "IN_Photo", catalog = "")
public class RoleInfo {
    private int roleId;
    private String name;
    private Set<AdminInfo> adminInfoSet;
    private Set<ModuleInfo> moduleInfoSet;
    /*用于获取页面上的传参*/
    private List<Integer> moduleIds;

    @Transient
    public List<Integer> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Integer> moduleIds) {
        this.moduleIds = moduleIds;
    }

    /*角色映射*/
    @ManyToMany(mappedBy = "roleInfoSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<AdminInfo> getAdminInfoSet() {
        return adminInfoSet;
    }

    public void setAdminInfoSet(Set<AdminInfo> adminInfoSet) {
        this.adminInfoSet = adminInfoSet;
    }

    /*模块主映射*/
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "admin_role_module",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    public Set<ModuleInfo> getModuleInfoSet() {
        return moduleInfoSet;
    }

    public void setModuleInfoSet(Set<ModuleInfo> moduleInfoSet) {
        this.moduleInfoSet = moduleInfoSet;
    }

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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

        RoleInfo that = (RoleInfo) o;

        if (roleId != that.roleId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder moduleInfoStr = new StringBuilder();
        if (moduleInfoSet != null && !moduleInfoSet.isEmpty()) {
            for (ModuleInfo m : moduleInfoSet
                    ) {
                moduleInfoStr.append(m.toString());
            }
        }
        return "RoleInfo{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", moduleInfoSet=" + moduleInfoStr +
                '}';
    }

}
