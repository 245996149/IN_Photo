package cn.inphoto.util;

import org.junit.Test;

import java.util.Random;

public class PasswordUtil {

    private static char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 随机生成指定位数大小写数字的密码
     *
     * @param num 位数
     * @return 密码
     */
    public static String getRandomPassword(int num) {
        final int maxNum = 62;
        int i; // 生成的随机数

        StringBuilder pwd = null;

        boolean a = false;
        while (!a) {
            pwd = new StringBuilder();
            int count = 0; // 生成的密码的长度
            Random r = new Random();
            while (count < num) {
                // 生成随机数，取绝对值，防止生成负数，
                i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
                if (i >= 0 && i < str.length) {
                    pwd.append(str[i]);
                    count++;
                }
            }

            a = pwd.toString().matches("^[a-zA-Z]\\w{5,17}$");
        }

        return pwd.toString();
    }

    @Test
    public void test() {
        for (int i = 0; i < 50; i++) {
            System.out.println(i + "     " + getRandomPassword(10));
        }
    }

}
