package org.hpin.settlementManagement.Thread;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.SpringTool;
import org.hpin.common.core.orm.daoWrapper.DaoSupport;

/**
 * 基因公司结算线程(业务逻辑存储过程实现)
 * @author ybc
 * @since 2016-08-18
 */
public class ErpSettleTaskJYPceThread implements Runnable {
	
	private Logger log = Logger.getLogger(ErpSettleTaskJYPceThread.class);
	
	private String settleId;
	
	private User currentUser;
	
	private DaoSupport dao = null;
	
	private String taskPce = "{Call JY_SETTLEMENT_TASK(?)}";
	
	private String saveTaskPce = "{Call JY_SETTEMENT_SAVETASK(?,?,?)}";
	
	private String exceTaskPce = "{Call JY_SETTLEMENT_CREATE_EXCETASK(?,?,?,?)}";
	
	private String updateSql = "update ERP_SETTLEMENT_TASK_JY set status=? where id=?";
	
	public ErpSettleTaskJYPceThread(String settleId,User currentUser){
		this.settleId = settleId;
		this.currentUser = currentUser;
		if (null == dao) {
			dao = (DaoSupport) SpringTool.getBean("DaoSupport");
		}
	}
	
	@Override
	public void run() {
		log.info("ErpSettleTaskJYPceThread begin run , settleId = "+settleId + "current user = " + currentUser.getAccountName());
		boolean a = false;
		boolean b = false;
		try{
			doTaskPce();
			a = doSaveTaskPce();
			if(a){
				b = doExceTaskPce();
			}
			if(a&&b){
				//成功，状态改为2（可结算）
				dao.getJdbcTemplate().update(updateSql,new Object[]{"2",settleId});
				log.info("ErpSettleTaskJYPceThread update ERP_SETTLEMENT_TASK_JY state : 2");
			}else{
				//回滚状态为新增
				dao.getJdbcTemplate().update(updateSql,new Object[]{"0",settleId});
				log.info("ErpSettleTaskJYPceThread update ERP_SETTLEMENT_TASK_JY state : 0");
			}
		}catch(Exception e){
			log.error("ErpSettleTaskJYPceThread error , settleId = "+settleId,e);
		}
		
		
	}
	
	//调用比对数据的存储过程
	private void doTaskPce() throws Exception{
		Connection connection =dao.getJdbcTemplate().getDataSource().getConnection();
		CallableStatement cs = connection.prepareCall(taskPce);
		cs.setString(1,settleId); //第一个参数
		cs.execute();
		cs.close();
		connection.close();
	}
	
	//调用更新Task数据存储过程
	private boolean doSaveTaskPce() throws Exception{
		Connection connection =dao.getJdbcTemplate().getDataSource().getConnection();
		CallableStatement cs = connection.prepareCall(saveTaskPce);
		cs.setString(1, settleId);//第一个参数
		cs.setString(2, currentUser.getUserName());//第二个参数
		cs.registerOutParameter(3, java.sql.Types.VARCHAR);//第三个参数（输出）
		cs.execute();
		log.info("saveSettleTaskByCustomer--"+cs.getString(3));
		if(("fail").equals(cs.getString(3))){
			return false;
		}
		cs.close();
		connection.close();
		return true;
	}
	
	//调用异常数据存储过程
	private boolean doExceTaskPce() throws Exception{
		String s = "JSYC";
		String defTaskNum = defTaskNum();
		int ranNum = ranNum();
		String exceSettleTaskNo = s+defTaskNum+ranNum;//异常结算任务号
		//到新增页面时生成ID
		String id = UUID.randomUUID().toString().replace("-", "");
		Connection connection =dao.getJdbcTemplate().getDataSource().getConnection();
		CallableStatement cs = connection.prepareCall(exceTaskPce);
		cs.setString(1, settleId);	//第一个参数
		cs.setString(2, exceSettleTaskNo);	//第二个参数
		cs.setString(3, id);	//第三个参数
		cs.registerOutParameter(4, java.sql.Types.VARCHAR);//第四个参数（输出）
		cs.execute();
		log.info("createExceSettleTask--"+cs.getString(4));
		if(("fail").equals(cs.getString(4))){
			return false;
		}
		cs.close();
		connection.close();
		return true;
	}
	
	//默认的任务号
	private String defTaskNum(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String time = dateFormat.format(date);
		return time;
	}
	
	//随机生成三位数
	public int ranNum(){
		Random randomNumber = new Random();
		int i=randomNumber.nextInt(900)+100;	//三位数随机数
		return i;
	}
}
