package com.ianeiu.compare.json;

import com.alibaba.fastjson.JSON;

/**
 * @Description: fastjson
 * @author: wm
 */
public class FastJsonUtil extends TemplateJsonParse {

	private static final String NAME = "fastjson";

	@Override
	public String utilName() {
		return NAME;
	}

	@Override
	public void parseObj(String json) {
		JSON.parseObject(json);
	}
}
