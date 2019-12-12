package com.ianeiu.compare.json;

public class Main {
	public static void main(String[] args) {

		String json = "{\"string_0_1572405383519\":\"23231\",\"string_1_1572405383519\":\"34534534\",\"string_2_1572405383519\":\"423423423423\",\"string_1572418396818\":\"23423423\",\"string_1572418399075\":\"2342342\",\"string_1572418402012\":\"23423423\",\"string_1572418411352\":\"1234567\",\"string_1572418413130\":\"广州市天河区\"}";
		String json2 = "{\"month_1574752412096\":\"2019-11\",\"time_1574752417048\":\"16:24:03\",\"daterange_1574752413576\":[\"2019-11-27\",\"2019-11-27\"],\"boolean_1574752399248\":true,\"radio_1574752401264\":\"2\",\"textarea_1574752392202\":\"SFDASDF\",\"range_1574752418880\":37,\"rating_1574752432007\":4,\"select_1574752406584\":\"1\",\"date_1574752408283\":\"2019-11-27\",\"number_1574752397831\":3,\"nation_1574752433971\":\"汉族\",\"password_1574752430562\":\"23234\",\"checkbox_1574752404024\":[\"2\"],\"region_1574752435832\":[\"河北省\",\"邯郸市\",\"峰峰矿区\"],\"key\":\"1\"}";

		TemplateJsonParse hutoolJsonUtil = new HutoolJsonUtil();
		TemplateJsonParse fastJsonUtil = new FastJsonUtil();

		hutoolJsonUtil.parseObjAndPrint(json);
		fastJsonUtil.parseObjAndPrint(json);

		hutoolJsonUtil.parseObjAndPrint(json2);
		fastJsonUtil.parseObjAndPrint(json2);

		/*
		hutool执行parseObj开始：
		耗时 66 毫秒
		----------------------------
		fastjson执行parseObj开始：
		耗时 505 毫秒
		----------------------------
		hutool执行parseObj开始：
		耗时 17 毫秒
		----------------------------
		fastjson执行parseObj开始：
		耗时 1 毫秒
		----------------------------
		*/
	}

}
