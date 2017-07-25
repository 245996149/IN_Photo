package cn.inphoto.dbentity.page;

/**
 * Created by root on 17-3-31.
 */
public class UserPage extends Page {

    private Long user_id;

    private String userName;

    private int adminId;

    private String phone;

    private String email;

    private String userState;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    @Override
    public String toString() {
        return "UserPage{" +
                "user_id=" + user_id +
                ", userName='" + userName + '\'' +
                ", adminId=" + adminId +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", userState='" + userState + '\'' +
                '}';
    }
}
