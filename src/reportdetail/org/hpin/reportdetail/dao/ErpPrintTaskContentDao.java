package org.hpin.reportdetail.dao;

import org.hpin.base.customerrelationship.entity.CustomerRelationShipPro;
import org.hpin.common.core.orm.BaseDao;
import org.hpin.events.entity.ErpCustomer;
import org.hpin.events.entity.ErpEvents;
import org.hpin.reportdetail.entity.ErpPrintTaskContent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public class ErpPrintTaskContentDao extends BaseDao {


    public List<ErpCustomer> getCustomerInfoByCode(String code) {
        String sql = "from ErpCustomer where is_deleted=0 and code=?";
        return this.getHibernateTemplate().find(sql,code);
    }

    public CustomerRelationShipPro getProjectCodeByEvent(String eventsNo) {
        String sql1 = "from ErpEvents where is_deleted=0 and events_no=?";
        List<ErpEvents> eventList = this.getHibernateTemplate().find(sql1,eventsNo);
        CustomerRelationShipPro shipProList = null;
        if(eventList.size()!=0){
            shipProList =  this.getHibernateTemplate().get(CustomerRelationShipPro.class,eventList.get(0).getCustomerRelationShipProId());
        }
        return shipProList;
    }

    public List<ErpPrintTaskContent> getContentByPdfId(String pdfId) {
        String sql = "from ErpPrintTaskContent where pdfcontentid=?";
        return this.getHibernateTemplate().find(sql,pdfId);
    }

}
