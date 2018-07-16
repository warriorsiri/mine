package com.warrior.crawler.fields;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.warrior.crawler.target.FieldFilter;
import com.warrior.crawler.target.tjcn.filter.LrzeFilter;

import cn.hutool.core.lang.Tuple;

/**
 * 正则表达式测试
 * 
 * @author warrior
 * 2018年7月12日
 */
public class LrzeRegTest {

	public static List<Tuple> conds = null;

	@BeforeClass
	public static void intConfig() {
		// 增加值
		conds = new ArrayList<>();
		conds.add(new Tuple("全年规模以上工业企业实现利润总额3200.10亿元，比上年增长10.5%，实现税金总额2087.36亿元，增长6.7%。", "3200.10"));
		conds.add(new Tuple("全年规模以上工业企业主营业务收入29114.74亿元，比上年增长9.4%；实现利润总额2024.21亿元，增长13.6%", "2024.21"));
		conds.add(new Tuple("全年规模以上工业企业实现利润755.6亿元，增长15.2%；主营业务收入增长18.2%；", "755.6"));
		conds.add(new Tuple("全年规模以上工业主营业务收入完成12472.6亿元，比上年增长23.5%。实现利润707.7亿元，增长70.7%", "707.7"));
		conds.add(new Tuple(
				"全年规模以上工业企业产品销售率98.58%，比上年提高0.66个百分点。主营业务收入5194.4亿元，比上年下降4.2%；利税总额631.1亿元，下降9%；利润总额350.9亿元，增长15.8%。",
				"350.9"));
		conds.add(new Tuple("全市1607户规模以上工业企业实现主营业务收入5603.36亿元，增长11.9%；实现利润474.26亿元，增长25.8%。", "474.26"));
		conds.add(new Tuple(
				"全年规模以上工业企业实现利润总额511.97亿元，比上年增长17.6%。规模以上工业企业资产负债率55.88%，每百元主营业务收入中的成本为87.75元，下降0.06元，主营业务收入利润率为5.9%。",
				"511.97"));
		conds.add(new Tuple(
				"全年规模以上工业企业实现销售产值2026.29亿元，比上年增长20.7%。工业企业主营业务收入1905.31亿元，比上年增长12.5%；实现利润总额70.28亿元，增长4.8%。利润总额66.10亿元，增长42.7%。",
				"70.28"));
		conds.add(new Tuple("全市规模以上工业企业实现主营业务收入5129.2亿元，增长11.3%。实现利税386.2亿元，下降0.2%，其中利润总额264.6亿元，增长0.5%。", "264.6"));
		conds.add(new Tuple("全市规模以上工业企业主营业务收入3280.56亿元，比上年增长8.51%；利润211.2亿元，增长4.15%。", "211.2"));
		conds.add(new Tuple(
				"全年规模以上工业企业743个，比上年增长9.4%。主营业务收入2750.11亿元，比上年增长9.7%；实现利税总额402.35亿元,比上年增长3.1%；实现利润总额206.94亿元，比上年增长1.5%。",
				"206.94"));
		conds.add(new Tuple("规模以上工业企业实现主营业务收入8876.94亿元，增长11.9%；实现利润507.25亿元，增长2.5%。", "507.25"));
		conds.add(new Tuple("规模以上工业企业实现利润总额173.8亿元,增长10.9%", "173.8"));
		conds.add(new Tuple("全年规模以上工业实现主营业务收入6166.39亿元，比上年增长12.9%；利润总额296.33亿元，利税总额526.23亿元，分别比上年增长3.2%和8.3%。",
				"296.33"));
		conds.add(new Tuple("规模以上工业企业主营业务收入5166.00亿元，增长7.3%。实现利润总额333.00亿元，增长9.1%。", "333.00"));
		conds.add(new Tuple("全年一般公共预算收入2252.4亿元，比上年增长3.0%。其中税收收入1476.3亿元，增长7.3%。全年规模以上工业企业利润比上年增长19.4%。", null));
	}

	@Test
	public void testReg() {
		FieldFilter filter = new LrzeFilter();
		for (Tuple cond : conds) {
			String content = cond.get(0);
			String expect = cond.get(1);
			Assert.assertEquals("数值提取不正确--" + content, expect, filter.extractValue(content));
		}
	}

}
