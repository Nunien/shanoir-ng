package org.shanoir.ng.examination;

import java.util.Date;
import java.util.List;

import org.shanoir.ng.datasetacquisition.ExaminationDatasetAcquisitionDTO;

/**
 * Simple examination DTO with information for subject.
 * 
 * @author msimon
 *
 */
public class SubjectExaminationDTO {

	private String comment;

	private List<ExaminationDatasetAcquisitionDTO> datasetAcquisitions;

	private Date examinationDate;

	private Long id;

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the datasetAcquisitions
	 */
	public List<ExaminationDatasetAcquisitionDTO> getDatasetAcquisitions() {
		return datasetAcquisitions;
	}

	/**
	 * @param datasetAcquisitions
	 *            the datasetAcquisitions to set
	 */
	public void setDatasetAcquisitions(List<ExaminationDatasetAcquisitionDTO> datasetAcquisitions) {
		this.datasetAcquisitions = datasetAcquisitions;
	}

	/**
	 * @return the examinationDate
	 */
	public Date getExaminationDate() {
		return examinationDate;
	}

	/**
	 * @param examinationDate
	 *            the examinationDate to set
	 */
	public void setExaminationDate(Date examinationDate) {
		this.examinationDate = examinationDate;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
