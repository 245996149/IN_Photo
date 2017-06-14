package cn.inphoto.user.dbentity.page;

/**
 * Created by root on 17-3-31.
 */
public class TablePage extends Page {

    private Long user_id;

    private int category_id;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "TablePage{" +
                "user_id=" + user_id +
                ", category_id=" + category_id +
                "};" + super.toString();
    }
}
