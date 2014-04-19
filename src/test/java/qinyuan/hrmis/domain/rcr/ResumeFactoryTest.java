package qinyuan.hrmis.domain.rcr;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ResumeFactoryTest {

	private ResumeFactory rf;

	@Before
	public void initialize() {
		rf = new ResumeFactory();
	}

	@Test
	public void testGetResumeCount() {
		int count = rf.getResumeCount();
		assertTrue(count >= 0);
	}

	@Test
	public void testGetPageCount() {
		int pageCount = rf.getPageCount();
		assertTrue(pageCount >= 0);
	}

	@Test
	public void testGetResumes() {
		//List<Resume> resumes = rf.getResumes();
		//assertEquals(ResumeFactory.PAGE_SIZE, resumes.size());
	}
}
