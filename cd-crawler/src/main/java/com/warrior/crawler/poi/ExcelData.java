package com.warrior.crawler.poi;

import java.io.Serializable;
import java.util.List;

/**
 * Excel数据model
 * 
 * @author warrior
 * 2018年8月17日
 */
public class ExcelData implements Serializable {

	private static final long serialVersionUID = 3134751300196843193L;

	private String fullFileName;

	private List<List<String>> data;

	private String desc;

	public String getFullFileName() {
		return fullFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public List<List<String>> getData() {
		return data;
	}

	public void setData(List<List<String>> data) {
		this.data = data;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
