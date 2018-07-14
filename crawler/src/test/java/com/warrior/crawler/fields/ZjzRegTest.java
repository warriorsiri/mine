package com.warrior.crawler.fields;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.warrior.crawler.target.FieldFilter;
import com.warrior.crawler.target.tjcn.filter.ZjzFilter;

import cn.hutool.core.lang.Tuple;

/**
 * 正则表达式测试
 * 
 * @author warrior
 * 2018年7月12日
 */
public class ZjzRegTest {

	public static List<Tuple> conds = null;

	@BeforeClass
	public static void intConfig() {
		// 增加值
		conds = new ArrayList<>();
		conds.add(new Tuple("全年规模以上工业实现增加值70.9亿元，同比下降5.0%。", "70.9"));
		conds.add(new Tuple("全年规模以上工业增加值1276.4亿元，比上年增长2.8%。", "1276.4"));
		conds.add(new Tuple("全年规模以上工业（以下工业数据均为此口径）增加值195.3亿元，按可比价格计算，比上年增长10.0%。", "195.3"));
		conds.add(new Tuple("全年规模以上工业增加值351.8亿元，按可比价计算，比上年增长4.0%。", "351.8"));
		conds.add(new Tuple("规模以上工业实现增加值322.73亿元，增长9.0%。", "322.73"));
		conds.add(new Tuple("年末全市共有1163家规模以上工业企业，实现增加值760.8亿元，增长9.4%，实现总产值3317.66亿元，增长10.3%。", "760.8"));
		conds.add(new Tuple("规模以上工业增加值276.38（不含军工涉密企业）亿元，增长8.5%。", "276.38"));
		conds.add(new Tuple("全年全部工业完成增加值492.79亿元，比上年增长8.1%，占生产总值比重为49.4%。其中，规模以上工业增加值436.27亿元，增长8.5%。", "436.27"));
		conds.add(new Tuple("全年全市规模以上工业增加值2355.8亿元，同比增长3.6%。", "2355.8"));
		conds.add(new Tuple("规模以上工业企业实现增加值1412.8亿元，比上年增长5.1%。", "1412.8"));
		conds.add(new Tuple("全年全部工业增加值3772.6亿元，比上年增长4.5%，其中规模以上工业增加值3518.9亿元，增长4.7%。", "3518.9"));
		conds.add(new Tuple("全年完成规模以上工业增加值2654.6亿元，比上年增长9%。", "2654.6"));
		conds.add(new Tuple("2017年全市规模以上工业实现增加值3266.7亿元，比上年增长9.6%。", "3266.7"));
		conds.add(new Tuple("其中规模以上工业增加值3205亿元，增长7.0%", "3205"));
		conds.add(
				new Tuple("全市全部工业增加值4101.47亿元，比上年增长8.1%，其中规模以上工业实现增加值3533.26亿元，增长8.5%；工业增加值占GDP的比重为38.9%。", "3533.26"));
	}

	@Test
	public void testReg() {
		FieldFilter filter = new ZjzFilter();
		for (Tuple cond : conds) {
			String content = cond.get(0);
			String expect = cond.get(1);
			Assert.assertEquals("数值提取不正确--" + content, expect, filter.extractValue(content));
		}
	}

}
