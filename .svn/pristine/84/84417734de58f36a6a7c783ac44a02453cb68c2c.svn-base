package org.hpin.events.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hpin.common.core.orm.BaseService;
import org.hpin.events.dao.ErpCustomerExceptionDao;
import org.hpin.events.entity.ErpCustomerTesting;
import org.hpin.events.util.MailEntity;
import org.hpin.events.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.hpin.events.service.ErpCustomerExceptionService")
@Transactional()
public class ErpCustomerExceptionService extends BaseService {
	private Logger log = Logger.getLogger(ErpCustomerExceptionService.class);
	
	@Autowired
	ErpCustomerExceptionDao dao;
	
	//2016-12-22 add by machuan
	@Value("#{setting['mail.account']}")
	private String sendMailAccount; //发件人账户;
	@Value("#{setting['mail.password']}")
	private String sendMailPassword; //发件人密码;
	
	/**
	 * 基因公司套餐费用维护情况检测  当导入时没有异常数据则去判断
	 * @param eventsNo
	 * @author machuan
	 * @date  2016年12月22日
	 */
	public void testingPrice(String eventsNo) {
		log.info("testingPrice---eventsNo==="+eventsNo);
		// 捕获异常 不抛出   不影响正常的客户信息导入业务
		try{
			String sql = "select t.genecompany,t.comboname,t.price from(select p.genecompany,p.comboname,p.price from erp_customer c "
						+" left join erp_combo_price p on c.setmeal_name=p.comboname where c.is_deleted='0' "
						+" and p.isdelete='0' and c.events_no='"
						+eventsNo
						+"')t group by t.genecompany,t.comboname,t.price";
			List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql);
			//拼接正文
			Set<String> set = new HashSet<String>();
			for(Map<String, Object> map : list){
				if(!(map.get("price")!=null&&((BigDecimal)map.get("price")).intValue()>0)){
					StringBuilder sb = new StringBuilder();
					sb.append("目前 （");
					sb.append(map.get("genecompany"));
					sb.append("） 基因公司的 （");
					sb.append(map.get("comboname"));
					sb.append("） 套餐没有维护价格，请处理，谢谢！<br>");
					set.add(sb.toString());
				};
			}
			ErpCustomerTesting customerTesting;
			//当有基因公司价格未维护则进行邮件通知和保存场次号进ERP_CUSTOMER_TESTING表
			if(set.size()>0){
				//保存场次号进ERP_CUSTOMER_TESTING表
				customerTesting = (ErpCustomerTesting) dao.findById(ErpCustomerTesting.class, eventsNo);
				if(customerTesting==null){
					customerTesting = new ErpCustomerTesting();
					customerTesting.setId(eventsNo);
					customerTesting.setStatus("0");
					customerTesting.setCreateTime(new Date());
					dao.save(customerTesting);
				}else{
					customerTesting.setStatus("0");
					customerTesting.setUpdateTime(new Date());
				}
				String msg = "您好： <br>";
				for(String str : set){
					msg+=str;
				}
				msg+="IT部 <br>";
				//发送邮件
				log.info("------基因公司套餐费用维护情况检测--邮件发送开始。-------");
				MailEntity mail = new MailEntity();
				//设置服务器地址
				mail.setHost("smtp.exmail.qq.com");
				//设置账号
				mail.setUsername(sendMailAccount);
				//发件人的邮箱
				mail.setSender(sendMailAccount);
				//发件人的密码
				mail.setPassword(sendMailPassword);
				//收件人的邮箱
				List<String> receiver = new ArrayList<String>();
				//TODO 正式环境添加 收件人邮箱huilin@healthlink.cn
//				receiver.add("3180974@qq.com");
				receiver.add("huilin@healthlink.cn");
				mail.setReceiver(receiver);
				//主题
				mail.setSubject("基因公司套餐价格未维护明细");
				//正文信息
				mail.setMessage(msg);
				log.info("基因公司套餐费用维护情况检测--正文信息："+msg);
				mail.setName("IT部");
				MailUtil.send(mail);
				log.info("------基因公司套餐费用维护情况检测--邮件发送结束。-------");
			}else{
				//当该场次号下面的基因公司套餐都已经维护  则删除ERP_CUSTOMER_TESTING下的该场次号
				customerTesting = (ErpCustomerTesting) dao.findById(ErpCustomerTesting.class, eventsNo);
				if(customerTesting!=null){
					customerTesting.setStatus("1");
				}
				log.info("testingPrice--count=0!");
			}
		}catch(Exception e){
			log.error("testingPrice--error: ", e);
		}
		
	}

	/**
	 * 查询该场次号是否有异常数据  true表示没有
	 * @param eventsNo
	 * @return
	 * @author machuan
	 * @date  2016年12月22日
	 */
	public boolean isHaveException(String eventsNo) {
		String sql = "select count(*) from erp_customer_exception e where e.events_no='"
				+ eventsNo.trim()
				+ "'";
		return dao.getJdbcTemplate().queryForInt(sql)==0;
	}
	
	/**
	 * 从数据库erp_customer_testing表中查询所有状态为0的场次号
	 * @return
	 * @author machuan
	 * @date  2016年12月26日
	 */
	public List<ErpCustomerTesting> getAllEventsNo(){
		String hql = "from ErpCustomerTesting where status='0'";
		return dao.getHibernateTemplate().find(hql);
	}

}

















