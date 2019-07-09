package com.ianeiu.utils.http;

import com.google.common.collect.Lists;
import com.ianeiu.utils.http.handler.AbstractHttpResultHandler;
import com.ianeiu.utils.http.handler.ContentToStringHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

	private static final CloseableHttpClient httpclient;
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).build();
		httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	/**
	 * getStringResult
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String, String> paramMap) throws Exception {
		return doGet(url,paramMap,new ContentToStringHandler());
	}
	
	/**
	 * getStringResult
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> paramMap) throws Exception {
		return doPost(url,paramMap,new ContentToStringHandler());
	}
	
	public static <T> T doGet(String url, Map<String, String> paramPairs, AbstractHttpResultHandler<T> httpResultHandler) throws Exception {
		CloseableHttpResponse response = null;
		try {
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			StringBuilder sb = new StringBuilder(url).append("?");
			for (Map.Entry<String, String> entry : paramPairs.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				sb.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
			HttpGet get = new HttpGet(sb.deleteCharAt(sb.length() - 1).toString());
			response = httpclient.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				return httpResultHandler.doHandle(response.getEntity());
			}
			throw new RuntimeException("网络异常，状态码：" + statusCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e);
		} finally {
			if(response != null) {
				response.close();
			}
		}
	}

	public static <T> T doPost(String url, Map<String, String> paramPairs, AbstractHttpResultHandler<T> httpResultHandler) throws Exception {
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> parameters = Lists.newArrayList();
			for(Map.Entry<String, String> me : paramPairs.entrySet()) {
				parameters.add(new BasicNameValuePair(me.getKey(), me.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return httpResultHandler.doHandle(entity);
			}
			throw new RuntimeException("网络异常，状态码：" + statusCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e);
		} finally {
			if(response != null) {
				response.close();
			}
		}
	}
	
}
