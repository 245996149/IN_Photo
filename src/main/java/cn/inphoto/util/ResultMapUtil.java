package cn.inphoto.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取、解析返回结果工具类
 */
public class ResultMapUtil {

    /**
     * 获取一个返回结果
     *
     * @param success 是否成功
     * @param message 返回信息
     * @return Map
     */
    public static Map<String, Object> createResult(boolean success, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);
        return result;
    }

    /**
     * 解析是否成功
     *
     * @param map 解析的map
     * @return 是否成功
     */
    public static boolean getSuccess(Map<String, Object> map) {
        if (map.isEmpty()) {
            return false;
        } else if (!map.containsKey("success")) {
            return false;
        } else {
            return (boolean) map.get("success");
        }
    }

    /**
     * 解析返回信息
     *
     * @param map 解析的map
     * @return 返回信息
     */
    public static String getMessage(Map<String, Object> map) {
        if (map.isEmpty()) {
            return null;
        } else if (!map.containsKey("message")) {
            return null;
        } else {
            return (String) map.get("message");
        }
    }

}
