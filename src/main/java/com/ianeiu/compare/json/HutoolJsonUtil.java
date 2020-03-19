package com.ianeiu.compare.json;

import cn.hutool.json.JSONUtil;

/**
 * @Description: hutool
 * @author: wm
 */
public class HutoolJsonUtil extends TemplateJsonParse {

	private static final String NAME = "hutool";

	@Override
	public String utilName() {
		return NAME;
	}

	@Override
	public void parseObj(String json) {
		JSONUtil.parseObj(json);
	}
}
