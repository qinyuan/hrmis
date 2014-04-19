package qinyuan.hrmis.domain.att;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.att.manager.AccdType;

public class AccdTypeTest {

	@Test
	public void testGetInstances() {
		AccdType[] ats=AccdType.getInstances();
		assertNotNull(ats);
		assertTrue(ats.length>0);
	}
	
	@Test
	public void testGetInstance(){
		AccdType at=AccdType.getInstance(1);
		assertNotNull(at);
		assertEquals(1,(int)at.getId());
		assertEquals("忘带卡", at.getDesc());
	}
}
