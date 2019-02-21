package com.ianeiu.utils.ws;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class WSCallbackHandler implements CallbackHandler {
	
	private String identifier;
	private String token;
	
	public WSCallbackHandler(String identifier, String token) {
		this.identifier = identifier;
		this.token = token;
	}
	
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		if (pc.getIdentifier().equals(identifier)) {
			pc.setPassword(token);
		} else {
			throw new UnsupportedCallbackException(pc, "check failed");
		}
	}

}
