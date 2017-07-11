/**
 * 
 */
package cn.inphoto.user.weChatEntity;

/**
 * 描述: 凭证
 * 
 * @see WeChatToken
 * @author Ming.C
 * @date 2015-11-27
 * @version V1.0
 */
public class WeChatToken {
	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;
	// 获取到的时间
	private long time;

	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "Token [accessToken=" + accessToken + ", expiresIn="
				+ expiresIn + ", time=" + time + "]";
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
