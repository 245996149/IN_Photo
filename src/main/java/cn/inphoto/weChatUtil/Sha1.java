/**
 * 
 */
package cn.inphoto.user.weChatUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * @author ming.C
 * @date 2016年7月1日 上午11:26:49
 */
public class Sha1 {

	public static String getSha1(String str) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(str.getBytes());
			outStr = byteToString(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return outStr;
	}

	/**
	 * @param digest
	 * @return
	 */
	private static String byteToString(byte[] digest) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < digest.length; i++) {
			String tempStr = Integer.toHexString(digest[i] & 0xff);
			if (tempStr.length() == 1) {
				buf.append("0").append(tempStr);
			} else {
				buf.append(tempStr);
			}
		}
		return buf.toString().toLowerCase();
	}

	public static String getMD5(String input) {
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(input.getBytes());
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < md.length; i++) {
				String shaHex = Integer.toHexString(md[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Test
	public void testSha1() {
		String str = "jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VL7OZvcWB3UXYlsbwIBJXLR7LtjOfX3M6eWVuGFsr-A9aKwDYNQqXz-_"
				+ "-eZ7crkkYQ&noncestr=76d89b6e-5753-4eff-9199-23dda1832e11&timestamp=1476418553&"
				+ "url=http://app.in-show.com.cn/WeChatPayTest/view/toPay.do";
		System.out.println(getMD5(str));
		System.out.println(getSha1(str));
	}

	// public static void main(String[] args) {
	// String str = "abcde";
	// System.out.println(sha1(str));
	// }

}
