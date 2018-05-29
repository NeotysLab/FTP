package com.neotys.ftp.FtpAction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FtpSendStreamActionTest {
	@Test
	public void shouldReturnType() {
		final FtpSendStreamAction action = new FtpSendStreamAction();
		assertEquals("FtpSendStream", action.getType());
	}

}
