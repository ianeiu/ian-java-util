package com.ianeiu.utils.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

public class WSSecService {

	@SuppressWarnings("unchecked")
	public static <T> T getService(String address, String identifier, String token, Class<T> serviceEndpointInterface) {
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, identifier);
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new WSCallbackHandler(identifier, token));
		ArrayList<Interceptor<? extends Message>> list = new ArrayList<>();
		list.add(new SAAJOutInterceptor());
		list.add(new WSS4JOutInterceptor(outProps));
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(serviceEndpointInterface);
		factory.setAddress(address);
		factory.getOutInterceptors().addAll(list);
		return (T) factory.create();
	}
	
}