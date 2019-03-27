package org.shanoir.ng.acquisitionequipment.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.shanoir.ng.center.model.Center;
import org.shanoir.ng.manufacturermodel.model.ManufacturerModel;
import org.shanoir.ng.shared.hateoas.HalEntity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Acquisition equipment.
 * 
 * @author msimon
 *
 */
@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "manufacturer_model_id", "serialNumber" }, name = "model_number_idx") })
@JsonPropertyOrder({ "_links", "id", "serialNumber" })
@GenericGenerator(name = "IdOrGenerate", strategy = "increment")
public class AcquisitionEquipment extends HalEntity {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7137351748882747602L;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "center_id")
	private Center center;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "manufacturer_model_id")
	private ManufacturerModel manufacturerModel;

	private String serialNumber;

	/**
	 * @return the center
	 */
	public Center getCenter() {
		return center;
	}

	/**
	 * @param center
	 *            the center to set
	 */
	public void setCenter(Center center) {
		this.center = center;
	}

	/**
	 * @return the manufacturerModel
	 */
	public ManufacturerModel getManufacturerModel() {
		return manufacturerModel;
	}

	/**
	 * @param manufacturerModel
	 *            the manufacturerModel to set
	 */
	public void setManufacturerModel(ManufacturerModel manufacturerModel) {
		this.manufacturerModel = manufacturerModel;
	}

	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber
	 *            the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}
