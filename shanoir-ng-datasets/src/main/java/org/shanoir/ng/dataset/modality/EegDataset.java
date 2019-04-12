package org.shanoir.ng.dataset.modality;

import javax.persistence.Entity;

import org.shanoir.ng.dataset.model.Dataset;

/**
 * EEG dataset.
 * 
 * @author msimon
 *
 */
@Entity
public class EegDataset extends Dataset {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -7618433089837302003L;

	@Override
	public String getType() {
		return "Eeg";
	}

}
