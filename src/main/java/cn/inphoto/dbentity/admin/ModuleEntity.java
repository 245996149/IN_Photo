package cn.inphoto.dbentity.admin;

import javax.persistence.*;

/**
 * Created by root on 17-7-11.
 */
@Entity
@Table(name = "admin_module_info", schema = "IN_Photo", catalog = "")
public class ModuleEntity {
    private int moduleId;
    private String name;

    @Id
    @Column(name = "module_id")
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

        ModuleEntity that = (ModuleEntity) o;

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
}
