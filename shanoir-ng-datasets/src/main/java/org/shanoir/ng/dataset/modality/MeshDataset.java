package org.shanoir.ng.dataset.modality;

import javax.persistence.Entity;

import org.shanoir.ng.dataset.model.Dataset;

/**
 * MESH dataset.
 * 
 * @author msimon
 *
 */
@Entity
public class MeshDataset extends Dataset {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 5177847059488327065L;

	@Override
	public String getType() {
		return "Mesh";
	}

}
