package cn.inphoto.user.dbentity.page;

/**
 * Created by root on 17-3-31.
 */
public class RecyclePage extends Page {

    private Integer user_id;

    private String state;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return super.toString()+";RecyclePage{" +
                "user_id=" + user_id +
                ", state='" + state + '\'' +
                '}';
    }
}
