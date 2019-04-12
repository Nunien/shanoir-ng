package org.shanoir.ng.datasetacquisition.model.ct;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.shanoir.ng.datasetacquisition.model.DatasetAcquisition;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * CT dataset acquisition.
 * 
 * @author msimon
 *
 */
@Entity
@JsonTypeName("Ct")
public class CtDatasetAcquisition extends DatasetAcquisition {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -8511002756058790037L;

	/** MR protocol. */
	@OneToOne(cascade = CascadeType.ALL)
	private CtProtocol ctProtocol;

	/**
	 * @return the ctProtocol
	 */
	public CtProtocol getCtProtocol() {
		return ctProtocol;
	}

	/**
	 * @param ctProtocol
	 *            the ctProtocol to set
	 */
	public void setCtProtocol(CtProtocol ctProtocol) {
		this.ctProtocol = ctProtocol;
	}

	@Override
	public String getType() {
		return "Ct";
	}

}
