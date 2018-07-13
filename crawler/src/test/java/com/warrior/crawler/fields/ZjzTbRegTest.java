package com.warrior.crawler.fields;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.warrior.crawler.target.FieldFilter;
import com.warrior.crawler.target.tjcn.filter.ZjzTbFilter;

import cn.hutool.core.lang.Tuple;

/**
 * 正则表达式测试
 * 
 * @author warrior
 * 2018年7月12日
 */
public class ZjzTbRegTest {

	public static List<Tuple> conds = null;

	@BeforeClass
	public static void intConfig() {
		conds = new ArrayList<>();
		conds.add(new Tuple("全年规模以上工业实现增加值70.9亿元，同比下降5.0%。", "-5.0"));
		conds.add(new Tuple("规模以上工业增加值比上年增长11.2%。", "11.2"));
		conds.add(new Tuple("全年规模以上工业增加值1276.4亿元，比上年增长2.8%。", "2.8"));
		conds.add(new Tuple("全年规模以上工业（以下工业数据均为此口径）增加值195.3亿元，按可比价格计算，比上年增长10.0%。", "10.0"));
		conds.add(new Tuple("全市规模以上工业增加值按可比价格计算，比上年上升0.2%。", "0.2"));
		conds.add(new Tuple("全年规模以上工业增加值351.8亿元，按可比价计算，比上年增长4.0%。", "4.0"));
		conds.add(new Tuple("全年规模以上工业[3]增加值按可比价格计算比上年增长6.9%。", "6.9"));
		conds.add(new Tuple("全年规模以上工业[5]增加值比上年增长4.4%。", "4.4"));
		conds.add(new Tuple("全年规模以上工业增加值比上年增长6.6%。", "6.6"));
		conds.add(new Tuple("全年规模以上工业增加值增长9.1%。", "9.1"));
		conds.add(new Tuple("2017年，全市规模以上工业增加值按可比价格计算，同比增长9.0%，较上年提高0.6个百分点。", "9.0"));
		conds.add(new Tuple("全年全部工业增加值474.09亿元，比上年增长8.4%，占生产总值比重为44.3%。其中，规模以上工业增加值增长8.8%。", "8.8"));
		conds.add(new Tuple("规模以上工业实现增加值322.73亿元，增长9.0%。", "9.0"));
		conds.add(new Tuple("年末全市共有1163家规模以上工业企业，实现增加值760.8亿元，增长9.4%，实现总产值3317.66亿元，增长10.3%。", "9.4"));
		conds.add(new Tuple("规模以上工业增加值276.38（不含军工涉密企业）亿元，增长8.5%。", "8.5"));
		conds.add(new Tuple("全年全部工业完成增加值492.79亿元，比上年增长8.1%，占生产总值比重为49.4%。其中，规模以上工业增加值436.27亿元，增长8.5%。", "8.5"));
		conds.add(new Tuple("全市1304家规模以上工业企业实现增加值增长5.7%。", "5.7"));
		conds.add(new Tuple("规模以上工业增加值增长7.6%。", "7.6"));
		conds.add(new Tuple("全市规模以上工业增加值按可比价格计算比上年增长9.1%。", "9.1"));
		conds.add(new Tuple("年末全市规模以上工业企业数量达到955家，规模以上工业增加值增长11.4%。", "11.4"));
		conds.add(new Tuple("其中，规模以上工业增加值增长5.6%。", "5.6"));
		conds.add(new Tuple("全年全市规模以上工业增加值2355.8亿元，同比增长3.6%。", "3.6"));
		conds.add(new Tuple("规模以上工业企业实现增加值1412.8亿元，比上年增长5.1%。", "5.1"));
		conds.add(new Tuple("全部工业增加值比上年增长1.6%，其中规模以上工业增加值增长1.8%。", "1.8"));
		conds.add(new Tuple("全年全部工业增加值3772.6亿元，比上年增长4.5%，其中规模以上工业增加值3518.9亿元，增长4.7%。", "4.7"));
		conds.add(new Tuple("规模以上工业（以下口径相同）增加值增长8.8%。", "8.8"));
		conds.add(new Tuple("规模以上工业增加值增长7.4%。", "7.4"));
		conds.add(new Tuple("全年完成规模以上工业增加值2654.6亿元，比上年增长9%。", "9"));
		conds.add(new Tuple("全年全市规模以上工业增加值增长7.5%。", "7.5"));
		conds.add(new Tuple("2017年全市规模以上工业实现增加值3266.7亿元，比上年增长9.6%。", "9.6"));
		conds.add(new Tuple("全市规模以上工业增加值比上年增长0.4%", "0.4"));
		conds.add(new Tuple("其中规模以上工业增加值3205亿元，增长7.0%", "7.0"));
		conds.add(new Tuple("全年规模以上工业增加值同比增长9.2%", "9.2"));
		conds.add(new Tuple("其中，规模以上工业增加值【4】增长9.7%。", "9.7"));
		conds.add(new Tuple("全年规模以上工业（3）增加值比上年增长9.7%。", "9.7"));
		conds.add(new Tuple("全市2693家规模以上工业企业完成总产值9371.10亿元，增长13.9%，工业增加值增长8.0%。", "8.0"));
		conds.add(new Tuple("全年年主营业务收入2000万元及以上工业企业（以下简称规模以上工业）实现增加值比上年增长9.0%。", "9.0"));
		conds.add(new Tuple("规模以上工业增加值比上年增长8.7%", "8.7"));
		conds.add(new Tuple("全市全部工业增加值4101.47亿元，比上年增长8.1%，其中规模以上工业实现增加值3533.26亿元，增长8.5%；工业增加值占GDP的比重为38.9%。", "8.5"));

	}

	@Test
	public void testReg() {
		FieldFilter filter = new ZjzTbFilter();
		for (Tuple cond : conds) {
			String content = cond.get(0);
			String expect = cond.get(1);
			Assert.assertEquals("增加值提取不正确--" + content, expect, filter.extractValue(content));
		}
	}

}
