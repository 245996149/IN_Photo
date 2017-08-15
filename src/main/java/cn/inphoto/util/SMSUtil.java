package cn.inphoto.util;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static cn.inphoto.util.DirUtil.getConfigInfo;


/**
 * Created by root on 17-4-12.
 */
@Component
public class SMSUtil {

    private static Logger logger = Logger.getLogger(SMSUtil.class);

    private static String aliyunMNSEndpoint;

    private static String aliyunAccessId;

    private static String aliyunAccessKey;

    @Value("#{properties['aliyunMNSEndpoint']}")
    public static void setAliyunMNSEndpoint(String aliyunMNSEndpoint) {
        SMSUtil.aliyunMNSEndpoint = aliyunMNSEndpoint;
    }

    @Value("#{properties['aliyunAccessId']}")
    public static void setAliyunAccessId(String aliyunAccessId) {
        SMSUtil.aliyunAccessId = aliyunAccessId;
    }

    @Value("#{properties['aliyunAccessKey']}")
    public static void setAliyunAccessKey(String aliyunAccessKey) {
        SMSUtil.aliyunAccessKey = aliyunAccessKey;
    }

    /**
     * 阿里云发送验证码短信
     *
     * @param phone        手机号
     * @param code         验证码
     * @param product      发送的其他信息
     * @param templateCode 短信模板
     * @return 是否发送成功
     */
    public static boolean sendSMS(String phone, String code, String product, String templateCode) {
        boolean flag = false;
        /**
         * Step 1. get topic reference
         */
        CloudAccount account = new CloudAccount(aliyunAccessId, aliyunAccessKey, aliyunMNSEndpoint);
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef("sms.topic-cn-hangzhou");
        /**
         * Step 2. set SMS message body ( required )
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody("sms-message");
        /**
         * Step 3. generate SMS message attributes
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 set SMS message sign name
        batchSmsAttributes.setFreeSignName("上海赢秀");
        // 3.2 set SMS message template code
        batchSmsAttributes.setTemplateCode(templateCode);
        // 3.3 set SMS message receiver param (defined in SMS message template)
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        smsReceiverParams.setParam("code", code);
        smsReceiverParams.setParam("product", product);
        // 3.4 add phone number of receiver (200 receivers at most)
        batchSmsAttributes.addSmsReceiver(phone, smsReceiverParams);
        //batchSmsAttributes.addSmsReceiver("$YourReceiverPhoneNumber2", smsReceiverParams);
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        try {
            /**
             * Step 4. publish SMS message
             */
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
            logger.info("发送短信，回执MessageId: " + ret.getMessageId());
            logger.info("发送短信，回执MessageMD5: " + ret.getMessageBodyMD5());
            flag = true;
        } catch (ServiceException se) {
            logger.info(se.getErrorCode() + se.getRequestId());
            logger.info(se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
        return flag;
    }
}