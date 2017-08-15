package cn.inphoto.util;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class MailUtil {

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    private static String myEmailAccount = "chen.ming@in-show.com.cn";
    private static String myEmailPassword = "chen245996149";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
    private static String myEmailSMTPHost = "smtp.exmail.qq.com";

    @Value("#{properties['EmailAccount']}")
    public static void setMyEmailAccount(String myEmailAccount) {
        MailUtil.myEmailAccount = myEmailAccount;
    }

    @Value("#{properties['EmailPassword']}")
    public static void setMyEmailPassword(String myEmailPassword) {
        MailUtil.myEmailPassword = myEmailPassword;
    }

    @Value("#{properties['EmailSMTPHost']}")
    public static void setMyEmailSMTPHost(String myEmailSMTPHost) {
        MailUtil.myEmailSMTPHost = myEmailSMTPHost;
    }

    public static void sendMail(String receiveMailAccount, String total, String text) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // 开启 SSL 连接, 以及更详细的发送步骤请看上一篇: 基于 JavaMail 的 Java 邮件发送：简单邮件发送

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount, total, text);

        // 也可以保持到本地查看
        // message.writeTo(file_out_put_stream);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        //    这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String total, String text) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "赢秀科技", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "尊敬的用户", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject(total, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(text, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    @Test
    public void a() throws Exception {

        sendMail("245996149@qq.com", "IN Photo注册邮件",
                "<div>尊敬的245996149@qq.com您好！ 感谢您成功注册IN Photo的会员。</div>" +
                        "<div><includetail><p>我们将为您提供最贴心的服务，祝您购物愉快！</p>" +
                        "<p>您在IN Photo管理中心的登录帐号：</p><p>帐号：245996149@qq.com</p>" +
                        "<p>密码：syvdu3</p><p>请您及时登录系统更改密码。</p>" +
                        "<p><a href='http://www.baidu.com'>点击前往IN Photo管理中心</a></p>" +
                        "<p>此邮件为系统自动发送，请勿直接回复该邮件</p></includetail></div>");

    }

}
