/**
 * 
 */
package org.hpin.events.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hpin.base.usermanager.entity.User;
import org.hpin.common.core.orm.BaseService;
import org.hpin.common.util.HttpTool;
import org.hpin.common.util.ReflectionUtils;
import org.hpin.common.widget.pagination.Page;
import org.hpin.events.dao.ErpConferenceDao;
import org.hpin.events.dao.ErpCustomerDao;
import org.hpin.events.entity.ErpConference;
import org.hpin.events.entity.ErpEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 公司名称: 远盟康健(北京)科技有限公司
* 
* author: tangxing 
* createDate: 2016-3-29 下午12:26:44
*/

@Service(value = "org.hpin.events.service.ErpConferenceService")
@Transactional()
public class ErpConferenceService extends BaseService {
    
    @Autowired
    private ErpConferenceDao dao;

    @Autowired
    private ErpCustomerDao cDao;
    
    /**
     * 分页获取用户
     * 
     * @param page
     * @param searchMap
     * @return
     */
    public List findByPage(Page page, Map searchMap) {
        return dao.findByPage(page, searchMap);
    }
    
    public List getAllInputNumsList(String userName){
        List list=dao.getAllInputNumsList(userName);
        return list;
    }
    
    /**
     * 保存
     * @param conference
     */
    public void save(ErpConference conference){
        dao.save(conference);
    }
    
    /*删除或修改*/
    public void delete(List<ErpConference> list) {
        for(ErpConference conference:list){
            String conferenceNo = conference.getConferenceNo();
            String sql = " delete from ERP_CUSTOMER as c where c.CONFERENCE_NO ='"+conferenceNo+"';";
            cDao.getJdbcTemplate().update(sql);
            dao.update(conference);
        }
    }
    
    public void modify(ErpConference conference){
        
        ErpConference oldErpConference=get(conference.getId());
        //拷贝属性 复制属性
        ReflectionUtils.copyPropertiesForHasValue(oldErpConference, conference);
        dao.saveOrUpdate(oldErpConference);
    }
    
    public ErpConference get(String id){
        return dao.get(id);
    }
    
    public Integer getCustomerNums(String conferenceNo){
        Integer count=dao.getCustomerNums(conferenceNo);
        return count;
    }
    
    public String isNotRepeatNoByDateAndBannyCompany(String compannyName,String ownedCompany,Date conferneceDate, String address){
        return dao.isNotRepeatBycompanyIdAndDate(compannyName,ownedCompany,conferneceDate,address);
    }
    
    public String maxNo(String date){
        return dao.maxNo(date);
    }
    
    /**
     * 根据会议号号获取人数
     * 
     * @param eventsNo
     * @return
     */
    public int getNowHeadcountByConferenceNo(String conferenceNo){
        return cDao.getNowHeadcountByConferenceNo(conferenceNo);
    }
    
	/**
	 * 根据条件查询相关场次
	 * @param params 传入的条件
	 * @return List
	 * @throws Exception
	 * @author DengYouming
	 * @since 2016-5-18 上午11:32:03
	 */
	public List<ErpConference> listConferenceByProps(Map<String,Object> params) throws Exception{
		List<ErpConference> list = null;
		if(params!=null&&params.keySet().size()>0){
			list = dao.listConferenceByProps(params);
		}
		return list;
	}

	/**
	 * @param costId 费用id
	 * @param name 姓名
	 * @param conferenceId 会议id
	 * @param price 合计价格
	 * @throws Exception
	 */
	public void removeRow(String costId, String name,String conferenceId,String price) throws Exception{
		ErpConference conference = (ErpConference) this.findById(conferenceId);
		String sql1 = "delete from erp_conference_staff_cost where id='"+costId+"'";
		String sql2 = "delete from erp_conference_cost_detail where conferenceId='"+conferenceId+"' and name='"+name+"'";
		dao.getJdbcTemplate().execute(sql1);
		dao.getJdbcTemplate().execute(sql2);
		
		User user = (User)HttpTool.getSession().getAttribute("currentUser");
		conference.setProduceCost(conference.getProduceCost()-Double.parseDouble(price));
		conference.setSettNumbers(conference.getSettNumbers()-1);
		conference.setUpdateTime(new Date());
		conference.setUpdateUserName(user.getAccountName());
		dao.update(conference);
	}

	/**
	 * @param conferenceId
	 * @return 总费用
	 */
	public BigDecimal getProduceCostSum(String conferenceId) {
		String sql = "select sum(ecsc.amount) producecostsum from erp_conference_staff_cost ecsc where ecsc.conference_id=?";
		return (BigDecimal)dao.getJdbcTemplate().queryForMap(sql, conferenceId).get("PRODUCECOSTSUM");
	}
    
}
