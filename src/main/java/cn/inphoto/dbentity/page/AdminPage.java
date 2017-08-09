package cn.inphoto.dbentity.page;

import java.sql.Timestamp;

/**
 * Created by root on 17-3-31.
 */
public class AdminPage extends Page {

    private int adminId;
    private String adminName;
    private String phone;
    private String email;
    private String adminStatu;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminStatu() {
        return adminStatu;
    }

    public void setAdminStatu(String adminStatu) {
        this.adminStatu = adminStatu;
    }

    @Override
    public String toString() {
        return "AdminPage{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", adminStatu='" + adminStatu + '\'' +
                '}' + super.toString();
    }
}
