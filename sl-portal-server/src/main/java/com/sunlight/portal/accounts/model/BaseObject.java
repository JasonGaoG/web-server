package com.sunlight.portal.accounts.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author gaoguang
 */
@Data
public class BaseObject {

	private List<String> ids;
	
	private String orderBy;
	
	private String orderColumn;
	
	private Date fromTime;

	private Date toTime;

	/**
	 * mysql 分页查询 limit pageSize offset start;
	 */
	private Integer start;

	private Integer pageSize;
	
	private String searchKeyWords;
	
	private String searchIp;
	
	private List<String> searchTypes;
	
	private List<String> searchStatus;

	public Integer getStart() {
		return start;
	}

}
