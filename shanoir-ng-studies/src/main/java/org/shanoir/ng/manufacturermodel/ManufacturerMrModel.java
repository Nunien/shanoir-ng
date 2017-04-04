package org.shanoir.ng.manufacturermodel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Manufacturer MR model.
 * 
 * @author msimon
 *
 */
@Entity
@DiscriminatorValue(DatasetModalityType.Values.MR_DATASET)
public class ManufacturerMrModel extends ManufacturerModel {

	@NotNull
	private double magneticField;

	/**
	 * @return the magneticField
	 */
	public double getMagneticField() {
		return magneticField;
	}

	/**
	 * @param magneticField
	 *            the magneticField to set
	 */
	public void setMagneticField(double magneticField) {
		this.magneticField = magneticField;
	}

}
