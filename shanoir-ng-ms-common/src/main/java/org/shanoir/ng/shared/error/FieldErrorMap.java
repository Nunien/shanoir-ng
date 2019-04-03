package org.shanoir.ng.shared.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Field error map.
 * 
 * @author jlouis
 *
 */
public class FieldErrorMap extends HashMap<String, List<FieldError>> {

	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructor
	 */
	public FieldErrorMap() {
		super();
	}


	/**
	 * Constructor
	 *
	 * @param {@link
	 * 			BindingResult}
	 * @return {@link FieldErrorMap}
	 */
	public FieldErrorMap(BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError objectError : result.getAllErrors()) {
				org.springframework.validation.FieldError fieldError = (org.springframework.validation.FieldError) objectError;
				if (!this.containsKey(fieldError.getField())) {
					this.put(fieldError.getField(), new ArrayList<org.shanoir.ng.shared.error.FieldError>());
				}
				this.get(fieldError.getField()).add(new org.shanoir.ng.shared.error.FieldError(fieldError.getCode(),
						fieldError.getDefaultMessage(), fieldError.getRejectedValue()));
			}
		}
	}

	/**
	 * Merge errors properly
	 *
	 * @param maps
	 */
	public FieldErrorMap add(FieldErrorMap map) {
		for (String fieldName : map.keySet()) {
			List<FieldError> error = map.get(fieldName);
			if (!this.containsKey(fieldName)) {
				this.put(fieldName, error);
			} else {
				this.get(fieldName).addAll(error);
			}
		}
		return this;
	}


}
