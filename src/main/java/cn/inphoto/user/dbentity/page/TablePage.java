package cn.inphoto.user.dbentity.page;

/**
 * Created by root on 17-3-31.
 */
public class TablePage extends Page {

    private Integer user_id;

    private Long category_id;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "TablePage{" +
                "user_id=" + user_id +
                ", category_id=" + category_id +
                '}';
    }
}
