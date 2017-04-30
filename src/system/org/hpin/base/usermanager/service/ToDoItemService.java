package org.hpin.base.usermanager.service;

import org.hpin.base.usermanager.dao.ToDoItemDao;
import org.hpin.common.core.orm.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 代办事项
 * 
 * @author thinkpad
 * 
 */
@Service(value = "org.hpin.base.usermanager.service.ToDoItemService")
@Transactional()
public class ToDoItemService extends BaseService {
	@Autowired
	private ToDoItemDao toDoItemDao = null;

}
