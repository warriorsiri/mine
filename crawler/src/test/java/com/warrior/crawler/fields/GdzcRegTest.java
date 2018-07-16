package com.warrior.crawler.fields;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.warrior.crawler.target.FieldFilter;
import com.warrior.crawler.target.tjcn.filter.GdzcFilter;

import cn.hutool.core.lang.Tuple;

/**
 * 正则表达式测试
 * 
 * @author warrior
 * 2018年7月12日
 */
public class GdzcRegTest {

	public static List<Tuple> conds = null;

	@BeforeClass
	public static void intConfig() {
		// 增加值
		conds = new ArrayList<>();
		conds.add(new Tuple("工业投资3352.34亿元，增长3.8%。", "3352.34"));
		conds.add(new Tuple("分行业看，工业投资915.89亿元，增长27.5%", "915.89"));
		conds.add(new Tuple("其中工业投资9181.2亿元，增长12.5%", "9181.2"));
		conds.add(new Tuple("全市工业投资2537.8亿元，增长9.9%", "2537.8"));
		conds.add(new Tuple("其中，工业投资1690.71亿元，增长15.5%", "1690.71"));
		conds.add(new Tuple("工业投资1356.5亿元，下降6.5%", "1356.5"));
	}

	@Test
	public void testReg() {
		FieldFilter filter = new GdzcFilter();
		for (Tuple cond : conds) {
			String content = cond.get(0);
			String expect = cond.get(1);
			Assert.assertEquals("数值提取不正确--" + content, expect, filter.extractValue(content));
		}
	}

}
