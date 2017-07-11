/**
 * 
 */
package cn.inphoto.user.weChatEntity;

/**
 * @author Ming.C
 * @date 2017年1月20日上午10:56:29
 */
public class Ticket {

	private String ticket;

	private Integer expire_seconds;

	private String url;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Integer getExpire_seconds() {
		return expire_seconds;
	}

	public void setExpire_seconds(Integer expire_seconds) {
		this.expire_seconds = expire_seconds;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
