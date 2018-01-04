package cn.inphoto.weibo.entity;

public class JsapiTicket {

    private String jsTicket;
    // 票据有效时间
    private int expiresTime;
    // 获取到的时间
    private long time;

    public String getJsTicket() {
        return jsTicket;
    }

    public void setJsTicket(String jsTicket) {
        this.jsTicket = jsTicket;
    }

    public int getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(int expiresTime) {
        this.expiresTime = expiresTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "JsapiTicket{" +
                "jsTicket='" + jsTicket + '\'' +
                ", expiresTime=" + expiresTime +
                ", time=" + time +
                '}';
    }
}
