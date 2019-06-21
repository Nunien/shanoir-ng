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

package org.shanoir.ng.coil;

import java.util.List;

import javax.validation.Valid;

import org.shanoir.ng.shared.error.FieldErrorMap;
import org.shanoir.ng.shared.exception.ErrorDetails;
import org.shanoir.ng.shared.exception.ErrorModel;
import org.shanoir.ng.shared.exception.RestServiceException;
import org.shanoir.ng.shared.exception.ShanoirStudiesException;
import org.shanoir.ng.shared.exception.StudiesErrorModelCode;
import org.shanoir.ng.shared.validation.EditableOnlyByValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiParam;

@Controller
public class CoilApiController implements CoilApi {

	private static final Logger LOG = LoggerFactory.getLogger(CoilApiController.class);

	@Autowired
	private CoilMapper coilMapper;

	@Autowired
	private CoilService coilService;

	public ResponseEntity<Void> deleteCoil(
			@ApiParam(value = "id of the coil", required = true) @PathVariable("coilId") Long coilId)
			throws RestServiceException {
		if (coilService.findById(coilId) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
			coilService.deleteById(coilId);
		} catch (ShanoirStudiesException e) {
			if (StudiesErrorModelCode.COIL_NOT_FOUND.equals(e.getErrorCode())) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else if (e.getErrorMap() != null) {
				throw new RestServiceException(new ErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Forbidden",
						new ErrorDetails(e.getErrorMap())));
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<CoilDTO> findCoilById(
			@ApiParam(value = "id of the coil", required = true) @PathVariable("coilId") Long coilId) {
		final Coil coil = coilService.findById(coilId);
		if (coil == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(coilMapper.coilToCoilDTO(coil), HttpStatus.OK);
	}

	public ResponseEntity<List<CoilDTO>> findCoils() {
		final List<Coil> coils = coilService.findAll();
		if (coils.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coilMapper.coilsToCoilDTOs(coils), HttpStatus.OK);
	}

	public ResponseEntity<CoilDTO> saveNewCoil(
			@ApiParam(value = "coil to create", required = true) @Valid @RequestBody Coil coil,
			final BindingResult result) throws RestServiceException {
		/* Validation */
		// A basic coil can only update certain fields, check that
		final FieldErrorMap accessErrors = this.getCreationRightsErrors(coil);
		// Check hibernate validation
		final FieldErrorMap hibernateErrors = new FieldErrorMap(result);
		/* Merge errors. */
		final FieldErrorMap errors = new FieldErrorMap(accessErrors, hibernateErrors);
		if (!errors.isEmpty()) {
			throw new RestServiceException(
					new ErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Bad arguments", new ErrorDetails(errors)));
		}

		// Guarantees it is a creation, not an update
		coil.setId(null);

		/* Save coil in db. */
		try {
			final Coil createdCoil = coilService.save(coil);
			return new ResponseEntity<>(coilMapper.coilToCoilDTO(createdCoil), HttpStatus.OK);
		} catch (ShanoirStudiesException e) {
			throw new RestServiceException(
					new ErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Bad arguments", null));
		}
	}

	public ResponseEntity<Void> updateCoil(
			@ApiParam(value = "id of the coil", required = true) @PathVariable("coilId") Long coilId,
			@ApiParam(value = "coil to update", required = true) @Valid @RequestBody Coil coil,
			final BindingResult result) throws RestServiceException {

		// IMPORTANT : avoid any confusion that could lead to security breach
		coil.setId(coilId);

		// A basic coil can only update certain fields, check that
		final FieldErrorMap accessErrors = this.getUpdateRightsErrors(coil);
		// Check hibernate validation
		final FieldErrorMap hibernateErrors = new FieldErrorMap(result);
		/* Merge errors. */
		final FieldErrorMap errors = new FieldErrorMap(accessErrors, hibernateErrors);
		if (!errors.isEmpty()) {
			throw new RestServiceException(
					new ErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Bad arguments", new ErrorDetails(errors)));
		}

		/* Update coil in db. */
		try {
			coilService.update(coil);
		} catch (ShanoirStudiesException e) {
			LOG.error("Error while trying to update coil " + coilId + " : ", e);
			throw new RestServiceException(
					new ErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Bad arguments", null));
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/*
	 * Get access rights errors.
	 *
	 * @param coil coil.
	 * 
	 * @return an error map.
	 */
	private FieldErrorMap getUpdateRightsErrors(final Coil coil) {
		final Coil previousStateCoil = coilService.findById(coil.getId());
		final FieldErrorMap accessErrors = new EditableOnlyByValidator<Coil>().validate(previousStateCoil, coil);
		return accessErrors;
	}

	/*
	 * Get access rights errors.
	 *
	 * @param coil coil.
	 * 
	 * @return an error map.
	 */
	private FieldErrorMap getCreationRightsErrors(final Coil coil) {
		return new EditableOnlyByValidator<Coil>().validate(coil);
	}

}
