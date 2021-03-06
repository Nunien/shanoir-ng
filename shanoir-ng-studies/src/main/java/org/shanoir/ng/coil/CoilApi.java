package org.shanoir.ng.coil;

import java.util.List;

import javax.validation.Valid;

import org.shanoir.ng.shared.exception.ErrorModel;
import org.shanoir.ng.shared.exception.RestServiceException;
import org.springframework.http.ResponseEntity;
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

@Api(value = "coil", description = "the coil API")
@RequestMapping("/coils")
public interface CoilApi {

	@ApiOperation(value = "", notes = "Deletes a coil", response = Void.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 204, message = "coil deleted", response = Void.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 404, message = "no coil found", response = Void.class),
			@ApiResponse(code = 500, message = "unexpected error", response = ErrorModel.class) })
	@RequestMapping(value = "/{coilId}", produces = { "application/json" }, method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT')")
	ResponseEntity<Void> deleteCoil(
			@ApiParam(value = "id of the coil", required = true) @PathVariable("coilId") Long coilId)
			throws RestServiceException;

	@ApiOperation(value = "", notes = "If exists, returns the coil corresponding to the given id", response = CoilDTO.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "found coil", response = CoilDTO.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 404, message = "no coil found", response = Void.class),
			@ApiResponse(code = 500, message = "unexpected error", response = ErrorModel.class) })
	@RequestMapping(value = "/{coilId}", produces = { "application/json" }, method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'USER')")
	ResponseEntity<CoilDTO> findCoilById(
			@ApiParam(value = "id of the coil", required = true) @PathVariable("coilId") Long coilId);

	@ApiOperation(value = "", notes = "Returns all the coils", response = CoilDTO.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "found coils", response = CoilDTO.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "no coil found", response = Void.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 500, message = "unexpected error", response = ErrorModel.class) })
	@RequestMapping(value = "", produces = { "application/json" }, method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'USER')")
	ResponseEntity<List<CoilDTO>> findCoils();

	@ApiOperation(value = "", notes = "Saves a new coil", response = CoilDTO.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "created coil", response = CoilDTO.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 422, message = "bad parameters", response = ErrorModel.class),
			@ApiResponse(code = 500, message = "unexpected error", response = ErrorModel.class) })
	@RequestMapping(value = "", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT')")
	ResponseEntity<CoilDTO> saveNewCoil(@ApiParam(value = "coil to create", required = true) @Valid @RequestBody Coil coil,
			BindingResult result) throws RestServiceException;

	@ApiOperation(value = "", notes = "Updates a coil", response = Void.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 204, message = "coil updated", response = Void.class),
			@ApiResponse(code = 401, message = "unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "forbidden", response = Void.class),
			@ApiResponse(code = 422, message = "bad parameters", response = ErrorModel.class),
			@ApiResponse(code = 500, message = "unexpected error", response = ErrorModel.class) })
	@RequestMapping(value = "/{coilId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('ADMIN', 'EXPERT')")
	ResponseEntity<Void> updateCoil(
			@ApiParam(value = "id of the coil", required = true) @PathVariable("coilId") Long coilId,
			@ApiParam(value = "coil to update", required = true) @Valid @RequestBody Coil coil, BindingResult result)
			throws RestServiceException;

}
