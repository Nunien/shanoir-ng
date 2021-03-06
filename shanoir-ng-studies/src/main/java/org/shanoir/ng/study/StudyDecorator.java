package org.shanoir.ng.study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shanoir.ng.acquisitionequipment.AcquisitionEquipmentDTO;
import org.shanoir.ng.groupofsubjects.ExperimentalGroupOfSubjectsMapper;
import org.shanoir.ng.shared.dto.IdListDTO;
import org.shanoir.ng.shared.dto.IdNameDTO;
import org.shanoir.ng.shared.exception.ShanoirException;
import org.shanoir.ng.shared.service.MicroserviceRequestsService;
import org.shanoir.ng.studycenter.StudyCenterDTO;
import org.shanoir.ng.studycenter.StudyCenterMapper;
import org.shanoir.ng.studyuser.StudyUser;
import org.shanoir.ng.studyuser.StudyUserType;
import org.shanoir.ng.subjectstudy.SubjectStudyMapper;
import org.shanoir.ng.utils.KeycloakUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Decorator for study.
 * 
 * @author msimon
 *
 */
public abstract class StudyDecorator implements StudyMapper {

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(StudyDecorator.class);

	@Autowired
	private ExperimentalGroupOfSubjectsMapper experimentalGroupOfSubjectsMapper;

	@Autowired
	private StudyMapper delegate;

	@Autowired
	private MicroserviceRequestsService microservicesRequestsService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StudyCenterMapper studyCenterMapper;

	@Autowired
	private SubjectStudyMapper subjectStudyMapper;

	@Override
	public List<StudyDTO> studiesToStudyDTOs(final List<Study> studies) {
		final List<StudyDTO> studyDTOs = new ArrayList<>();
		for (Study study : studies) {
			final StudyDTO studyDTO = convertStudyToStudyDTO(study, false);
			if (study.getSubjectStudyList() != null) {
				studyDTO.setNbSujects(study.getSubjectStudyList().size());
			}
			if (study.getExaminationIds() != null) {
				studyDTO.setNbExaminations(study.getExaminationIds().size());
			}
			studyDTOs.add(studyDTO);
		}
		return studyDTOs;
	}

	@Override
	public StudyDTO studyToStudyDTO(final Study study) {
		return convertStudyToStudyDTO(study, true);
	}

	/*
	 * Map a @Study to a @StudyDTO.
	 * 
	 * @param study study to map.
	 * 
	 * @param withData study with data?
	 * 
	 * @return study DTO.
	 */
	private StudyDTO convertStudyToStudyDTO(final Study study, final boolean withData) {
		final StudyDTO studyDTO = delegate.studyToStudyDTO(study);
		if (withData) {
			studyDTO.setStudyCenterList(
					studyCenterMapper.studyCenterListToStudyCenterDTOList(study.getStudyCenterList()));
			studyDTO.setSubjectStudyList(subjectStudyMapper.subjectStudyListToSubjectStudyDTOList(study.getSubjectStudyList()));
			studyDTO.setExperimentalGroupsOfSubjects(experimentalGroupOfSubjectsMapper
					.experimentalGroupOfSubjectsToIdNameDTOs(study.getExperimentalGroupsOfSubjects()));
			getStudyCards(studyDTO);
			if (study.getStudyUserList() != null && !study.getStudyUserList().isEmpty()) {
				prepareMembersCategories(study, studyDTO);
			}
		}
		return studyDTO;
	}

	private void prepareMembersCategories(final Study study, final StudyDTO studyDTO) {
		// Sort members by category (studyUserType)
		final Map<StudyUserType, List<IdNameDTO>> membersMap = new HashMap<>();
		for (StudyUser studyUser : study.getStudyUserList()) {
			final IdNameDTO member = new IdNameDTO(studyUser.getUserId(), studyUser.getUserName());
			if (membersMap.containsKey(studyUser.getStudyUserType())) {
				membersMap.get(studyUser.getStudyUserType()).add(member);
			} else {
				final List<IdNameDTO> studyUsers = new ArrayList<>();
				studyUsers.add(member);
				membersMap.put(studyUser.getStudyUserType(), studyUsers);
			}
		}
		// Transform map into list
		studyDTO.setMembersCategories(new ArrayList<>());
		for (StudyUserType type : membersMap.keySet()) {
			studyDTO.getMembersCategories().add(new MembersCategoryDTO(type, membersMap.get(type)));
		}
		// Sort categories by importance
		Collections.sort(studyDTO.getMembersCategories(), new MembersCategoryComparator());
	}

	/*
	 * Get list of study cards of a study from study card microservice.
	 * 
	 * @param studyDTO study.
	 */
	private void getStudyCards(final StudyDTO studyDTO) {
		final IdListDTO studyIds = new IdListDTO();
		studyIds.getIdList().add(studyDTO.getId());
		HttpEntity<IdListDTO> entity = null;
		try {
			entity = new HttpEntity<>(studyIds, KeycloakUtil.getKeycloakHeader());
		} catch (ShanoirException e) {
			LOG.error("Error while getting study cards for study " + studyDTO.getId(), e);
			return;
		}

		// Request to studycard MS to get study cards for current study
		ResponseEntity<List<StudyCardDTO>> studyCardResponse = null;
		try {
			studyCardResponse = restTemplate.exchange(
					microservicesRequestsService.getStudycardsMsUrl() + MicroserviceRequestsService.SEARCH,
					HttpMethod.POST, entity, new ParameterizedTypeReference<List<StudyCardDTO>>() {
					});
		} catch (RestClientException e) {
			LOG.error("Error while getting study cards for study " + studyDTO.getId(), e);
			return;
		}

		if (HttpStatus.OK.equals(studyCardResponse.getStatusCode())
				|| HttpStatus.NO_CONTENT.equals(studyCardResponse.getStatusCode())) {
			final List<StudyCardDTO> studyCardDTOs = studyCardResponse.getBody();
			if (studyCardDTOs != null) {
				List<IdNameDTO> studycards = new ArrayList<>();
				for (StudyCardDTO studyCardDTO : studyCardDTOs) {
					studycards.add(new IdNameDTO(studyCardDTO.getId(), studyCardDTO.getName()));
					linkStudyCardToAcqEqpt(studyCardDTO, studyDTO.getStudyCenterList());
				}
				studyDTO.setStudyCards(studycards);
			}
		} else {
			LOG.error("Error on study card microservice response. Status is " + studyCardResponse.getStatusCode());
		}
	}

	/*
	 * Link a study card to its acquisition equipment.
	 * 
	 * @param studyCardDTO study card.
	 * 
	 * @param studyCenterDTOs list of centers of current study.
	 */
	private void linkStudyCardToAcqEqpt(final StudyCardDTO studyCardDTO, final List<StudyCenterDTO> studyCenterDTOs) {
		for (StudyCenterDTO studyCenter : studyCenterDTOs) {
			for (AcquisitionEquipmentDTO equipment : studyCenter.getCenter().getAcquisitionEquipments()) {
				if (equipment.getId().equals(studyCardDTO.getAcquisitionEquipmentId())) {
					equipment.getStudyCards().add(new IdNameDTO(studyCardDTO.getId(), studyCardDTO.getName()));
					return;
				}
			}
		}
	}

}
