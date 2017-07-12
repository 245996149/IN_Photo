/**
 * 
 */
package cn.inphoto.weChatEntity;

/**
 * @author Ming.c
 * @date 2016年7月12日下午3:35:41
 */
public class JsapiTicket {
	// JS接口临时票据
	private String ticket;
	// 票据有效时间
	private int expiresIn;
	// 获取到的时间
	private long time;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "JsapiTicket [ticket=" + ticket + ", expiresIn=" + expiresIn
				+ ", time=" + time + "]";
	}

}
