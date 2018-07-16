package com.warrior.crawler.fields;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.warrior.crawler.target.FieldFilter;
import com.warrior.crawler.target.tjcn.filter.LszeFilter;

import cn.hutool.core.lang.Tuple;

/**
 * 正则表达式测试
 * 
 * @author warrior
 * 2018年7月12日
 */
public class LszeRegTest {

	public static List<Tuple> conds = null;

	@BeforeClass
	public static void intConfig() {

		conds = new ArrayList<>();
		conds.add(new Tuple("全年规模以上工业企业实现利润总额3200.10亿元，比上年增长10.5%，实现税金总额2087.36亿元，增长6.7%。", "2087.36"));
		conds.add(new Tuple("规模以上工业企业实现主营业务收入4188.2亿元，同比增长30.9%；利税总额839.2亿元，同比增长129.3%。", "839.2"));
		conds.add(new Tuple(
				"全年规模以上工业企业产品销售率98.58%，比上年提高0.66个百分点。主营业务收入5194.4亿元，比上年下降4.2%；利税总额631.1亿元，下降9%；利润总额350.9亿元，增长15.8%。",
				"631.1"));
		conds.add(new Tuple("全市规模以上工业企业实现主营业务收入5129.2亿元，增长11.3%。实现利税386.2亿元，下降0.2%，其中利润总额264.6亿元，增长0.5%。", "386.2"));
		conds.add(new Tuple(
				"全年规模以上工业企业743个，比上年增长9.4%。主营业务收入2750.11亿元，比上年增长9.7%；实现利税总额402.35亿元,比上年增长3.1%；实现利润总额206.94亿元，比上年增长1.5%。",
				"402.35"));
		conds.add(new Tuple("全年规模以上工业实现主营业务收入6166.39亿元，比上年增长12.9%；利润总额296.33亿元，利税总额526.23亿元，分别比上年增长3.2%和8.3%。",
				"526.23"));

	}

	@Test
	public void testReg() {
		FieldFilter filter = new LszeFilter();
		for (Tuple cond : conds) {
			String content = cond.get(0);
			String expect = cond.get(1);
			Assert.assertEquals("数值提取不正确--" + content, expect, filter.extractValue(content));
		}
	}

}
