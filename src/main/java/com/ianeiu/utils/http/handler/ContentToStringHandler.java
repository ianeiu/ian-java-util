package com.ianeiu.utils.http.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;

public class ContentToStringHandler extends AbstractHttpResultHandler<String> {

	@Override
	public String doHandle(HttpEntity entity) throws IOException {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			if(bufferedReader!=null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					
				}
			}
		}
	}

}
