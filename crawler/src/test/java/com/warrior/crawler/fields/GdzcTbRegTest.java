package com.warrior.crawler.fields;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.warrior.crawler.target.FieldFilter;
import com.warrior.crawler.target.tjcn.filter.GdzcTbFilter;

import cn.hutool.core.lang.Tuple;

/**
 * 正则表达式测试
 * 
 * @author warrior
 * 2018年7月12日
 */
public class GdzcTbRegTest {

	public static List<Tuple> conds = null;

	@BeforeClass
	public static void intConfig() {
		// 增加值
		conds = new ArrayList<>();
		conds.add(new Tuple("工业投资3352.34亿元，增长3.8%。", "3.8"));
		conds.add(new Tuple("分行业看，工业投资915.89亿元，增长27.5%", "27.5"));
		conds.add(new Tuple("其中工业投资9181.2亿元，增长12.5%", "12.5"));
		conds.add(new Tuple("全市工业投资2537.8亿元，增长9.9%", "9.9"));
		conds.add(new Tuple("其中，工业投资1690.71亿元，增长15.5%", "15.5"));
		conds.add(new Tuple("工业投资1356.5亿元，下降6.5%", "-6.5"));
		conds.add(new Tuple("其中工业投资157.41亿元，下降61.19%", "-61.19"));
	}

	@Test
	public void testReg() {
		FieldFilter filter = new GdzcTbFilter();
		for (Tuple cond : conds) {
			String content = cond.get(0);
			String expect = cond.get(1);
			Assert.assertEquals("数值提取不正确--" + content, expect, filter.extractValue(content));
		}
	}

}
