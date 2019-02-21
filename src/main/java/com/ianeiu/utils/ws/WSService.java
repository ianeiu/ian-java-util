package com.ianeiu.utils.ws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class WSService<T> {
	
	private Class<T> serviceEndpointInterface;
	private Service service;
	
	public static <T> T createService(String spec, String namespaceURI, String localPart, Class<T> serviceEndpointInterface) {
		return new WSService<>(spec, namespaceURI, localPart, serviceEndpointInterface).getService();
	}
	
	public static <T> WSService<T> newService(String spec, String namespaceURI, String localPart, Class<T> serviceEndpointInterface) {
		return new WSService<>(spec, namespaceURI, localPart, serviceEndpointInterface);
	}

	@Deprecated
	private WSService(String spec, String namespaceURI, String localPart, Class<T> serviceEndpointInterface) {
		try {
			this.serviceEndpointInterface = serviceEndpointInterface;
			QName qName = new QName(namespaceURI, localPart);
			this.service = Service.create(new URL(spec), qName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Deprecated
	public WSService<T> soapHeader() {
		return this;
	}
	
	@Deprecated
	public T getService() {
		return service.getPort(serviceEndpointInterface);
	}
	
}
