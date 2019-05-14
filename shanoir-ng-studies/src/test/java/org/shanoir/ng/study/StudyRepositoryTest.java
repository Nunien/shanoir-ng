package org.shanoir.ng.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shanoir.ng.center.model.Center;
import org.shanoir.ng.shared.dto.IdNameDTO;
import org.shanoir.ng.study.model.Study;
import org.shanoir.ng.study.model.StudyStatus;
import org.shanoir.ng.study.repository.StudyRepository;
import org.shanoir.ng.study.repository.StudyUserRepository;
import org.shanoir.ng.studycenter.StudyCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@DataJpaTest
@ActiveProfiles("test")
public class StudyRepositoryTest {

	private static final Long STUDY_TEST_1_ID = 1L;

	/*
	 * Mocks used to avoid unsatisfied dependency exceptions.
	 */
	@MockBean
	private AuthenticationManager authenticationManager;
	@MockBean
	private DocumentationPluginsBootstrapper documentationPluginsBootstrapper;
	@MockBean
	private WebMvcRequestHandlerProvider webMvcRequestHandlerProvider;

	@Autowired
	private StudyRepository studyRepository;
	
	@Autowired
	private StudyUserRepository studyUserRepository;

	@Test
	public void create() {
		final Study study = new Study();
		study.setName("StudyTest");
		StudyCenter studyCenter = new StudyCenter();
		studyCenter.setStudy(study);
		studyCenter.setCenter(new Center());
		List<StudyCenter> studyCenters = new ArrayList<StudyCenter>();
		studyCenters.add(studyCenter);
		study.setStudyCenterList(studyCenters);
		study.setStudyStatus(StudyStatus.IN_PROGRESS);
		final Study newStudy = studyRepository.save(study);
		assertEquals("StudyTest", newStudy.getName());
	}

	@Test
	public void delete() {
		studyRepository.delete(3L);

		final List<Study> studyList = (List<Study>) studyRepository.findAll();
		assertEquals(2, studyList.size());
	}

	@Test
	public void findIdsAndNamesTest() throws Exception {
		List<IdNameDTO> studiesDb = studyRepository.findIdsAndNames();
		assertNotNull(studiesDb);
		assertThat(studiesDb.size()).isEqualTo(3);
	}
	
	@Test
	public void findByStudyUsers_UserIdTest() {
		final List<Study> studyList = (List<Study>) studyRepository.findByStudyUserList_UserIdOrderByNameAsc(STUDY_TEST_1_ID);
		assertNotNull(studyList);
		assertEquals(3, studyList.size());
	}

	@Test
	public void findAll() {
		final List<Study> studyList = (List<Study>) studyRepository.findAll();
		assertEquals("shanoirStudy1", studyList.get(0).getName());
	}

	@Test
	public void findOneTest() {
		final Study s = studyRepository.findOne(1L);
		assertEquals("shanoirStudy1", s.getName());
	}

	@Test
	public void update() {
		final Study study = new Study();
		study.setName("StudyTest");
		study.setId(3L);

		final Study studyDb = studyRepository.findOne(study.getId());
		studyDb.setName(study.getName());
		studyDb.setEndDate(study.getEndDate());
		studyDb.setClinical(study.isClinical());
		studyDb.setWithExamination(study.isWithExamination());
		studyDb.setVisibleByDefault(study.isVisibleByDefault());
		studyDb.setDownloadableByDefault(study.isDownloadableByDefault());
		studyDb.setStudyStatus(study.getStudyStatus());

		studyRepository.save(studyDb);

		final Study studyFound = studyRepository.findOne(Long.valueOf(3));

		assertEquals("StudyTest", studyFound.getName());
	}
	
	@Test
	public void testRights() {
		Study studyFound = studyRepository.findOne(1L);
		assertEquals(2, studyFound.getStudyUserList().size());
		assertEquals(2, studyFound.getStudyUserList().get(1).getStudyUserRights().size());
	}
	
//	@Test
//	public void testUpdateStudyUsers() {
//		int indexToDelete = 1;
//		Study studyFound = studyRepository.findOne(1L);
//		assertEquals(2, studyFound.getStudyUserList().size());
//		Long deletedId = studyFound.getStudyUserList().get(indexToDelete).getId();
//		
//		assertNotNull(studyUserRepository.findOne(deletedId));
//		studyFound.getStudyUserList().remove(indexToDelete);
//		studyRepository.save(studyFound);
//		assertNull(studyUserRepository.findOne(deletedId));
//	}

}
