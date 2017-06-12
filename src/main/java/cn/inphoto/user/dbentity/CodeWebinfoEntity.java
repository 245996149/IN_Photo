package cn.inphoto.user.dbentity;

import javax.persistence.*;

/**
 * Created by kaxia on 2017/6/5.
 */
@Entity
@Table(name = "code_webinfo", schema = "IN_Photo", catalog = "")
public class CodeWebinfoEntity {

    /*正常生效*/
    public static final String CODE_WEB_INFO_STATE_NORMAL = "0";
    /*预览状态*/
    public static final String CODE_WEB_INFO_STATE_PREVIEW = "1";

    private long codeWebinfoId;
    private long userId;
    private int categoryId;
    private String pageTitle;
    private String background;
    private Double inputTop;
    private Double inputLeft;
    private Double inputRight;
    private Double inputBottom;
    private String inputBgColor;
    private String inputBorderColor;
    private String inputTextColor;
    private Double buttonTop;
    private Double buttonLeft;
    private Double buttonRight;
    private Double buttonBottom;
    private String buttonPic;
    private String codeWebinfoState;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "code_webinfo_id")
    public long getCodeWebinfoId() {
        return codeWebinfoId;
    }

    public void setCodeWebinfoId(long codeWebinfoId) {
        this.codeWebinfoId = codeWebinfoId;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "category_id")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "page_title")
    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Basic
    @Column(name = "background")
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Basic
    @Column(name = "input_top")
    public Double getInputTop() {
        return inputTop;
    }

    public void setInputTop(Double inputTop) {
        this.inputTop = inputTop;
    }

    @Basic
    @Column(name = "input_left")
    public Double getInputLeft() {
        return inputLeft;
    }

    public void setInputLeft(Double inputLeft) {
        this.inputLeft = inputLeft;
    }

    @Basic
    @Column(name = "input_right")
    public Double getInputRight() {
        return inputRight;
    }

    public void setInputRight(Double inputRight) {
        this.inputRight = inputRight;
    }

    @Basic
    @Column(name = "input_bottom")
    public Double getInputBottom() {
        return inputBottom;
    }

    public void setInputBottom(Double inputBottom) {
        this.inputBottom = inputBottom;
    }

    @Basic
    @Column(name = "input_bg_color")
    public String getInputBgColor() {
        return inputBgColor;
    }

    public void setInputBgColor(String inputBgColor) {
        this.inputBgColor = inputBgColor;
    }

    @Basic
    @Column(name = "input_border_color")
    public String getInputBorderColor() {
        return inputBorderColor;
    }

    public void setInputBorderColor(String inputBorderColor) {
        this.inputBorderColor = inputBorderColor;
    }

    @Basic
    @Column(name = "input_text_color")
    public String getInputTextColor() {
        return inputTextColor;
    }

    public void setInputTextColor(String inputTextColor) {
        this.inputTextColor = inputTextColor;
    }

    @Basic
    @Column(name = "button_top")
    public Double getButtonTop() {
        return buttonTop;
    }

    public void setButtonTop(Double buttonTop) {
        this.buttonTop = buttonTop;
    }

    @Basic
    @Column(name = "button_left")
    public Double getButtonLeft() {
        return buttonLeft;
    }

    public void setButtonLeft(Double buttonLeft) {
        this.buttonLeft = buttonLeft;
    }

    @Basic
    @Column(name = "button_right")
    public Double getButtonRight() {
        return buttonRight;
    }

    public void setButtonRight(Double buttonRight) {
        this.buttonRight = buttonRight;
    }

    @Basic
    @Column(name = "button_bottom")
    public Double getButtonBottom() {
        return buttonBottom;
    }

    public void setButtonBottom(Double buttonBottom) {
        this.buttonBottom = buttonBottom;
    }

    @Basic
    @Column(name = "button_pic")
    public String getButtonPic() {
        return buttonPic;
    }

    public void setButtonPic(String buttonPic) {
        this.buttonPic = buttonPic;
    }

    @Basic
    @Column(name = "code_webinfo_state")
    public String getCodeWebinfoState() {
        return codeWebinfoState;
    }

    public void setCodeWebinfoState(String codeWebinfoState) {
        this.codeWebinfoState = codeWebinfoState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeWebinfoEntity that = (CodeWebinfoEntity) o;

        if (codeWebinfoId != that.codeWebinfoId) return false;
        if (userId != that.userId) return false;
        if (categoryId != that.categoryId) return false;
        if (pageTitle != null ? !pageTitle.equals(that.pageTitle) : that.pageTitle != null) return false;
        if (background != null ? !background.equals(that.background) : that.background != null) return false;
        if (inputTop != null ? !inputTop.equals(that.inputTop) : that.inputTop != null) return false;
        if (inputLeft != null ? !inputLeft.equals(that.inputLeft) : that.inputLeft != null) return false;
        if (inputRight != null ? !inputRight.equals(that.inputRight) : that.inputRight != null) return false;
        if (inputBottom != null ? !inputBottom.equals(that.inputBottom) : that.inputBottom != null) return false;
        if (inputBgColor != null ? !inputBgColor.equals(that.inputBgColor) : that.inputBgColor != null) return false;
        if (inputBorderColor != null ? !inputBorderColor.equals(that.inputBorderColor) : that.inputBorderColor != null)
            return false;
        if (inputTextColor != null ? !inputTextColor.equals(that.inputTextColor) : that.inputTextColor != null)
            return false;
        if (buttonTop != null ? !buttonTop.equals(that.buttonTop) : that.buttonTop != null) return false;
        if (buttonLeft != null ? !buttonLeft.equals(that.buttonLeft) : that.buttonLeft != null) return false;
        if (buttonRight != null ? !buttonRight.equals(that.buttonRight) : that.buttonRight != null) return false;
        if (buttonBottom != null ? !buttonBottom.equals(that.buttonBottom) : that.buttonBottom != null) return false;
        if (buttonPic != null ? !buttonPic.equals(that.buttonPic) : that.buttonPic != null) return false;
        if (codeWebinfoState != null ? !codeWebinfoState.equals(that.codeWebinfoState) : that.codeWebinfoState != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (codeWebinfoId ^ (codeWebinfoId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + categoryId;
        result = 31 * result + (pageTitle != null ? pageTitle.hashCode() : 0);
        result = 31 * result + (background != null ? background.hashCode() : 0);
        result = 31 * result + (inputTop != null ? inputTop.hashCode() : 0);
        result = 31 * result + (inputLeft != null ? inputLeft.hashCode() : 0);
        result = 31 * result + (inputRight != null ? inputRight.hashCode() : 0);
        result = 31 * result + (inputBottom != null ? inputBottom.hashCode() : 0);
        result = 31 * result + (inputBgColor != null ? inputBgColor.hashCode() : 0);
        result = 31 * result + (inputBorderColor != null ? inputBorderColor.hashCode() : 0);
        result = 31 * result + (inputTextColor != null ? inputTextColor.hashCode() : 0);
        result = 31 * result + (buttonTop != null ? buttonTop.hashCode() : 0);
        result = 31 * result + (buttonLeft != null ? buttonLeft.hashCode() : 0);
        result = 31 * result + (buttonRight != null ? buttonRight.hashCode() : 0);
        result = 31 * result + (buttonBottom != null ? buttonBottom.hashCode() : 0);
        result = 31 * result + (buttonPic != null ? buttonPic.hashCode() : 0);
        result = 31 * result + (codeWebinfoState != null ? codeWebinfoState.hashCode() : 0);
        return result;
    }
}
