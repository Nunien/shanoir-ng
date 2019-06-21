/**
 * Shanoir NG - Import, manage and share neuroimaging data
 * Copyright (C) 2009-2019 Inria - https://www.inria.fr/
 * Contact us on https://project.inria.fr/shanoir/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/gpl-3.0.html
 */

<<<<<<< HEAD:shanoir-ng-studies/src/main/java/org/shanoir/ng/subject/controler/SubjectApi.java
package org.shanoir.ng.subject.controler;
=======
package org.shanoir.ng.subject;
>>>>>>> upstream/develop:shanoir-ng-studies/src/main/java/org/shanoir/ng/subject/SubjectApi.java

import java.util.List;

import org.shanoir.ng.shared.core.model.IdName;
import org.shanoir.ng.shared.exception.ErrorModel;
import org.shanoir.ng.shared.exception.RestServiceException;
import org.shanoir.ng.subject.dto.SimpleSubjectDTO;
import org.shanoir.ng.subject.dto.SubjectDTO;
import org.shanoir.ng.subject.dto.SubjectFromShupDTO;
import org.shanoir.ng.subject.dto.SubjectStudyCardIdDTO;
import org.shanoir.ng.subject.model.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "subject", description = "the subject API")
@RequestMapping("/subjects")
public interface SubjectApi {

	@ApiOperation(value = "", notes = "Deletes a subject", response = Void.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "subject deleted", response = Void.class),
			@ApiResponse(code = 204, message = "no subject found", response = Void.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Void.class) })
	@RequestMapping(value = "/{subjectId}", produces = { "application/json" }, method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN') or (hasRole('EXPERT') and @studySecurityService.hasRightOnSubjectForEveryStudy(#subjectId, 'CAN_ADMINISTRATE'))")
	ResponseEntity<Void> deleteSubject(
			@ApiParam(value = "id of the subject", required = true) @PathVariable("subjectId") Long subjectId);

	@ApiOperation(value = "", notes = "Returns all the subjects", response = Subject.class, responseContainer = "List", tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "found subjects", response = Subject.class),
			@ApiResponse(code = 204, message = "no subject found", response = Subject.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Subject.class),
			@ApiResponse(code = 403, message = "forbidden", response = Subject.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Subject.class) })
	@RequestMapping(value = "", produces = { "application/json" }, method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'USER')")
	@PostAuthorize("hasRole('ADMIN') or @studySecurityService.filterSubjectDTOsHasRightInOneStudy(returnObject.getBody(), 'CAN_SEE_ALL')")
	ResponseEntity<List<SubjectDTO>> findSubjects();
	
	@ApiOperation(value = "", notes = "Returns id and name for all the subjects", response = IdName.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "found subjects", response = IdName.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "no subject found", response = Void.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 500, message = "unexpected error", response = ErrorModel.class) })
	@RequestMapping(value = "/names", produces = { "application/json" }, method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'USER')")
	@PostAuthorize("hasAnyRole('ADMIN', 'EXPERT') or @studySecurityService.filterSubjectIdNamesDTOsHasRightInOneStudy(returnObject.getBody(), 'CAN_SEE_ALL')")
	ResponseEntity<List<IdName>> findSubjectsNames();	

	@ApiOperation(value = "", notes = "If exists, returns the subject corresponding to the given id", response = Subject.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "found bubject", response = Subject.class),
			@ApiResponse(code = 204, message = "no subject found", response = Subject.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Subject.class),
			@ApiResponse(code = 403, message = "forbidden", response = Subject.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Subject.class) })
	@RequestMapping(value = "/{subjectId}", produces = { "application/json" }, method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'USER')")
	@PostAuthorize("@studySecurityService.hasRightOnSubjectForOneStudy(returnObject.getBody().getId(), 'CAN_SEE_ALL')")
	ResponseEntity<SubjectDTO> findSubjectById(
			@ApiParam(value = "id of the subject", required = true) @PathVariable("subjectId") Long subjectId);

	@ApiOperation(value = "", notes = "Saves a new subject", response = Subject.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "created subject", response = Subject.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Subject.class),
			@ApiResponse(code = 403, message = "forbidden", response = Subject.class),
			@ApiResponse(code = 422, message = "bad parameters", response = Subject.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Subject.class) })
	@RequestMapping(value = "", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN') or (hasAnyRole('EXPERT', 'USER') and @studySecurityService.checkRightOnEverySubjectStudyList(#subject.getSubjectStudyList(), 'CAN_IMPORT'))")
	ResponseEntity<SubjectDTO> saveNewSubject(
			@ApiParam(value = "subject to create", required = true) @RequestBody Subject subject,
			final BindingResult result) throws RestServiceException;

	@ApiOperation(value = "", notes = "Saves a new subject with auto generated common name", response = Subject.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "created subject", response = Subject.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Subject.class),
			@ApiResponse(code = 403, message = "forbidden", response = Subject.class),
			@ApiResponse(code = 422, message = "bad parameters", response = Subject.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Subject.class) })
	@RequestMapping(value = "/OFSEP/", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN') or (hasAnyRole('EXPERT', 'USER') and @studySecurityService.checkRightOnEverySubjectStudyList(#subjectStudyCardIdDTO.getSubject().getSubjectStudyList(), 'CAN_IMPORT'))")
	ResponseEntity<Subject> saveNewOFSEPSubject(
			@ApiParam(value = "subject to create and the id of the study card", required = true) @RequestBody SubjectStudyCardIdDTO subjectStudyCardIdDTO,
			final BindingResult result) throws RestServiceException;
	
	@ApiOperation(value = "", notes = "Saves a new subject with auto generated common name", response = Subject.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "created subject", response = Subject.class),
			@ApiResponse(code = 302, message = "found already existing subject", response = Void.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Subject.class),
			@ApiResponse(code = 403, message = "forbidden", response = Subject.class),
			@ApiResponse(code = 422, message = "bad parameters", response = Subject.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Subject.class) })
	@RequestMapping(value = "/OFSEP/SHUP", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN') or (hasAnyRole('EXPERT', 'USER') and #subjectFromShupDTO.getStudyId() != null and @studySecurityService.hasRightOnStudy(#subjectFromShupDTO.getStudyId(), 'CAN_IMPORT'))")
	ResponseEntity<Long> saveNewOFSEPSubjectFromShup(
			@ApiParam(value = "subject to create and the id of the study card", required = true) @RequestBody SubjectFromShupDTO subjectFromShupDTO,
			final BindingResult result) throws RestServiceException;

	@ApiOperation(value = "", notes = "Updates a subject", response = Void.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 204, message = "subject updated", response = Void.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 422, message = "bad parameters", response = Void.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Void.class) })
	@RequestMapping(value = "/{subjectId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<Void> updateSubject(
			@ApiParam(value = "id of the subject", required = true) @PathVariable("subjectId") Long subjectId,
			@ApiParam(value = "subject to update", required = true) @RequestBody Subject subject,
			final BindingResult result) throws RestServiceException;
	
	@ApiOperation(value = "", notes = "Updates a subject", response = Void.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 204, message = "subject updated", response = Void.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 422, message = "bad parameters", response = Void.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Void.class) })
	@RequestMapping(value = "/SHUP/{subjectId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<Long> updateSubjectFromShup(
			@ApiParam(value = "id of the subject", required = true) @PathVariable("subjectId") Long subjectId,
			@ApiParam(value = "subject to update", required = true) @RequestBody SubjectFromShupDTO subjectFromShupDTO,
			final BindingResult result) throws RestServiceException;

	@ApiOperation(value = "", notes = "If exists, returns the subjects of a study", response = Subject.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "found subjects", response = Subject.class),
			@ApiResponse(code = 204, message = "no subject found", response = Subject.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Subject.class),
			@ApiResponse(code = 403, message = "forbidden", response = Subject.class),
			@ApiResponse(code = 500, message = "unexpected error", response = Subject.class) })
	@RequestMapping(value = "/{studyId}/allSubjects", produces = {
			"application/json" }, method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'USER')")
	@PostAuthorize("hasRole('ADMIN') or @studySecurityService.filterSimpleSubjectDTOsHasRightInOneStudy(returnObject.getBody(), 'CAN_SEE_ALL')")
	ResponseEntity<List<SimpleSubjectDTO>> findSubjectsByStudyId(
			@ApiParam(value = "id of the study", required = true) @PathVariable("studyId") Long studyId);

	@ApiOperation(value = "", notes = "If exists, returns the subject corresponding to the given identifier", response = Subject.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "found subject", response = SubjectDTO.class),
			@ApiResponse(code = 204, message = "no subject found", response = SubjectDTO.class),
			@ApiResponse(code = 401, message = "unauthorized", response = SubjectDTO.class),
			@ApiResponse(code = 403, message = "forbidden", response = SubjectDTO.class),
			@ApiResponse(code = 500, message = "unexpected error", response = SubjectDTO.class) })
	@RequestMapping(value = "/findByIdentifier/{subjectIdentifier}", produces = {
			"application/json" }, method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'USER')")
	@PostAuthorize("@studySecurityService.filterSubjectDTOsHasRightInOneStudy(returnObject.getBody(), 'CAN_SEE_ALL')")
	ResponseEntity<SubjectDTO> findSubjectByIdentifier(
			@ApiParam(value = "identifier of the subject", required = true) @PathVariable("subjectIdentifier") String subjectIdentifier);

}
