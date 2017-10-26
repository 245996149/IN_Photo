package cn.inphoto.dbentity.page;

import cn.inphoto.dbentity.user.MediaData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-3-31.
 */
public class TablePage extends Page {

    private Long user_id;

    private int category_id;

    private List<MediaData.MediaState> media_state_list;

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

    public List<MediaData.MediaState> getMedia_state_list() {
        return media_state_list;
    }

    public void setMedia_state_list(List<MediaData.MediaState> media_state_list) {
        this.media_state_list = media_state_list;
    }

    public void setMedia_state_list(MediaData.MediaState media_state) {
        List<MediaData.MediaState> media_state_list = new ArrayList<>();
        media_state_list.add(media_state);
        this.media_state_list = media_state_list;
    }

    @Override
    public String toString() {
        return "TablePage{" +
                "user_id=" + user_id +
                ", category_id=" + category_id +
                ", media_state_list=" + media_state_list +
                '}';
    }
}
