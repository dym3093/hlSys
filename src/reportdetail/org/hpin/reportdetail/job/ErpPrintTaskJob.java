package org.hpin.reportdetail.job;/**
 * Created by admin on 2016/12/16.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hpin.common.core.SpringTool;
import org.hpin.reportdetail.service.ErpPrintTaskService;

import java.io.IOException;

/**
 * 打印任务的定时任务
 *
 * @author YoumingDeng
 * @since 2016-12-16 18:55
 */
public class ErpPrintTaskJob {

    Logger log = Logger.getLogger(ErpPrintTaskJob.class);

    /**
     * @description 发送超时打印任务的邮件提醒
     * @param timeLimit 超时时限,单位小时,可以在application.xml中配置时间
     * @author YoumingDeng
     * @since: 2016/12/16 19:10
     */
    public void sendMailAttention(String timeLimit){
        log.info("开始执行发送提示邮件的任务...");
        Integer limit = Integer.valueOf(timeLimit);
        ErpPrintTaskService service = (ErpPrintTaskService) SpringTool.getBean(ErpPrintTaskService.class);
        String msg = null;
        try {
            msg = service.tigger4SendMail(limit);
        } catch (IOException e) {
            log.info(e);
        }
        if(StringUtils.isNotEmpty(msg)){
            log.info("邮件["+msg+"]已发送！！！");
        }else {
            log.info("无邮件发送...");
        }

    }
}
