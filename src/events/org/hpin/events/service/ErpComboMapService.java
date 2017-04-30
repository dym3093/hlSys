package org.hpin.events.service;

import org.hpin.common.core.orm.BaseService;
import org.hpin.events.dao.ErpComboMapDao;
import org.hpin.events.entity.ErpComboMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/11/9.
 */
@Service("org.hpin.events.service.ErpComboMapService")
@Transactional
public class ErpComboMapService extends BaseService{

    @Autowired
    private ErpComboMapDao dao;

    public List<ErpComboMap> listByProps(Map<String,String> params) throws Exception{
        return dao.listByProps(params);
    }

}
