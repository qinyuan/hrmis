package qinyuan.hrmis.domain.att.emp;

import static org.junit.Assert.*;

import org.junit.Test;

public class EmployeeTest {

	@Test
	public void testNewInstance() {
		Employee emp = Employee.newInstance(16);
		assertNotNull(emp);
		assertEquals(16, (int) emp.getId());
		assertEquals("曹建坤", emp.getName());
	}

}
