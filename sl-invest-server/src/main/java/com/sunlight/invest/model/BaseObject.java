package com.sunlight.invest.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author gaoguang
 */
@Data
public class BaseObject {

	private List<Integer> ids;
	/**
	 * mysql 分页查询 limit pageSize offset start;
	 */
	private Integer start;
	private Integer pageSize;
	private Date startTime;
	private Date endTime;
	private String dateStr;
	private List<String> codes;
	private String orderBy;
	private String orderColumn;

	// PolicySelHighLowNotify 专用
	private Integer notifyDays;
}
