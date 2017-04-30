package org.hpin.foreign.service;

import org.apache.commons.lang.StringUtils;
import org.hpin.common.core.orm.BaseService;
import org.hpin.foreign.dao.ErpReportResultsJYDao;
import org.hpin.foreign.entity.ErpReportOrgJY;
import org.hpin.foreign.entity.ErpReportResultsJY;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报告详情里(ErpReportOrgJy)的results字段(金域报告)Service
 * @author Damian
 * @since 2017-02-23
 */
@Transactional
@Service
public class ErpReportResultsJYService extends BaseService{

    @Autowired
    private ErpReportResultsJYDao dao;

    /**
     * 根据条件查询
     * @param params 传入的Map键值对
     * @return List
     * @author Damian
     * @since 2017-02-23
     */
    public List<ErpReportResultsJY> listByProps(Map<String,String> params) throws Exception {
       return dao.listByProps(params);
    }

    /**
     * Erp_Report_Org_JY表中的results JSON字符串转换成ErpReportResultsJY对象
     * @param org ErpReportOrgJY
     * @return ErpReportResultsJY
     * @throws Exception
     */
    public List<ErpReportResultsJY> orgToObject(ErpReportOrgJY org) throws Exception{
        List<ErpReportResultsJY> list = null;
        if (org!=null) {
            String results = org.getResults();
            if (StringUtils.isNotEmpty(results)) {
                list = new ArrayList<ErpReportResultsJY>();
                ErpReportResultsJY obj = new ErpReportResultsJY();
                JSONObject json = new JSONObject(results);
                if (json != null) {


                    //保存 Erp_Report_Org_JY 表的关联字段
                    obj.setOrgId(org.getId());
                    obj.setReportId(org.getReportId());
                    obj.setBarCode(org.getBarcode());

                    obj.setItemGroupId(json.getString("itemGroupCode"));

                }
            }
        }
        return list;
    }
}
