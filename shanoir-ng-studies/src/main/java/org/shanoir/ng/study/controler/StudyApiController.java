package org.shanoir.ng.study.controler;

import java.util.ArrayList;
import java.util.List;

import org.shanoir.ng.shared.core.model.IdName;
import org.shanoir.ng.shared.error.FieldErrorMap;
import org.shanoir.ng.shared.exception.EntityNotFoundException;
import org.shanoir.ng.shared.exception.ErrorDetails;
import org.shanoir.ng.shared.exception.ErrorModel;
import org.shanoir.ng.shared.exception.RestServiceException;
import org.shanoir.ng.study.dto.IdNameCenterStudyDTO;
import org.shanoir.ng.study.dto.StudyDTO;
import org.shanoir.ng.study.dto.mapper.StudyMapper;
import org.shanoir.ng.study.model.Study;
import org.shanoir.ng.study.security.StudyFieldEditionSecurityManager;
import org.shanoir.ng.study.service.StudyService;
import org.shanoir.ng.study.service.StudyUniqueConstraintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class StudyApiController implements StudyApi {

	@Autowired
	private StudyService studyService;

	@Autowired
	private StudyMapper studyMapper;
	
	@Autowired
	private StudyFieldEditionSecurityManager fieldEditionSecurityManager;
	
	@Autowired
	private StudyUniqueConstraintManager uniqueConstraintManager;

	@Override
	public ResponseEntity<Void> deleteStudy(@PathVariable("studyId") Long studyId) {
		try {
			studyService.deleteById(studyId);		
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	public ResponseEntity<List<StudyDTO>> findStudies() {
		List<Study> studies = studyService.findAll();
		if (studies.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else return new ResponseEntity<>(studyMapper.studiesToStudyDTOs(studies), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<IdName>> findStudiesNames() throws RestServiceException {
		List<IdName> studiesDTO = new ArrayList<>(); 
		final List<Study> studies = studyService.findAll();
		if (studies.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		for (Study study : studies) studiesDTO.add(studyMapper.studyToIdNameDTO(study));			
		return new ResponseEntity<>(studiesDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<IdNameCenterStudyDTO>> findStudiesNamesAndCenters() throws RestServiceException {
		List<IdNameCenterStudyDTO> studiesDTO = new ArrayList<>(); 
		final List<Study> studies = studyService.findAll();
		if (studies.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		for (Study study : studies) studiesDTO.add(studyMapper.studyToExtendedIdNameDTO(study));			
		return new ResponseEntity<>(studiesDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StudyDTO> findStudyById(@PathVariable("studyId") final Long studyId) {
		Study study = studyService.findById(studyId);			
		if (study == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else return new ResponseEntity<>(studyMapper.studyToStudyDTO(study), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<StudyDTO> saveNewStudy(@RequestBody final Study study, final BindingResult result)
			throws RestServiceException {

		validate(study, result);

		final Study createdStudy = studyService.create(study);
		return new ResponseEntity<>(studyMapper.studyToStudyDTO(createdStudy), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> updateStudy(@PathVariable("studyId") final Long studyId, @RequestBody final Study study,
			final BindingResult result) throws RestServiceException {

		validate(study, result);
		
		try {
			studyService.update(study);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	private void validate(Study study, BindingResult result) throws RestServiceException {
		final FieldErrorMap errors = new FieldErrorMap()
				.add(fieldEditionSecurityManager.validate(study))
				.add(new FieldErrorMap(result))
				.add(uniqueConstraintManager.validate(study));
		if (!errors.isEmpty()) {
			ErrorModel error = new ErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Bad arguments", new ErrorDetails(errors));
			throw new RestServiceException(error);
		} 
	}
	
}