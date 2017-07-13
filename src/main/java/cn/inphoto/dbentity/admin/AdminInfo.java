package cn.inphoto.dbentity.admin;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ming on 17-7-13.
 */
@Entity
@Table(name = "admin_info", schema = "IN_Photo", catalog = "")
public class AdminInfo {
    private int adminId;
    private String adminName;
    private String password;
    private String phone;
    private String email;
    private Timestamp createTime;
    private String adminStatu;

    @Id
    @Column(name = "admin_id")
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "admin_name")
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "admin_statu")
    public String getAdminStatu() {
        return adminStatu;
    }

    public void setAdminStatu(String adminStatu) {
        this.adminStatu = adminStatu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminInfo adminInfo = (AdminInfo) o;

        if (adminId != adminInfo.adminId) return false;
        if (adminName != null ? !adminName.equals(adminInfo.adminName) : adminInfo.adminName != null) return false;
        if (password != null ? !password.equals(adminInfo.password) : adminInfo.password != null) return false;
        if (phone != null ? !phone.equals(adminInfo.phone) : adminInfo.phone != null) return false;
        if (email != null ? !email.equals(adminInfo.email) : adminInfo.email != null) return false;
        if (createTime != null ? !createTime.equals(adminInfo.createTime) : adminInfo.createTime != null) return false;
        if (adminStatu != null ? !adminStatu.equals(adminInfo.adminStatu) : adminInfo.adminStatu != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adminId;
        result = 31 * result + (adminName != null ? adminName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (adminStatu != null ? adminStatu.hashCode() : 0);
        return result;
    }
}
