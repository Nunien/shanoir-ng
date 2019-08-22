package org.shanoir.uploader.action;

import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;

import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;
import org.shanoir.dicom.importer.Serie;
import org.shanoir.dicom.importer.UploadJob;
import org.shanoir.uploader.ShUpConfig;
import org.shanoir.uploader.gui.ImportDialog;
import org.shanoir.uploader.gui.MainWindow;
import org.shanoir.uploader.model.Study;
import org.shanoir.uploader.model.StudyCard;
import org.shanoir.uploader.model.dto.ExaminationDTO;
import org.shanoir.uploader.model.dto.SubjectDTO;
import org.shanoir.uploader.service.wsdl.ShanoirUploaderServiceClientNG;

/**
 * This class implements the logic when the start import button is clicked.
 * 
 * @author mkain
 * 
 */
public class ImportDialogOpenerNG {

	private static Logger logger = Logger.getLogger(ImportDialogOpenerNG.class);

	private MainWindow mainWindow;

	private ImportDialog importDialog;

	private ResourceBundle resourceBundle;

	private ShanoirUploaderServiceClientNG shanoirUploaderServiceClientNG;

	public ImportDialogOpenerNG(final MainWindow mainWindow,
			final ShanoirUploaderServiceClientNG shanoirUploaderServiceClientNG) {
		this.mainWindow = mainWindow;
		this.resourceBundle = mainWindow.resourceBundle;
		this.shanoirUploaderServiceClientNG = shanoirUploaderServiceClientNG;
	}

	public void openImportDialog(UploadJob uploadJob, File uploadFolder) {
		// login again, in case session has been expired
//		if (shanoirUploaderServiceClient.login()) {
			try {
				SubjectDTO subjectDTO = getSubject(uploadJob);
				ImportStudyAndStudyCardCBItemListener importStudyAndStudyCardCBIL = new ImportStudyAndStudyCardCBItemListener(this.mainWindow);
				ImportFinishActionListener importFinishAL = new ImportFinishActionListener(this.mainWindow, uploadJob, uploadFolder, subjectDTO);
				importDialog = new ImportDialog(this.mainWindow,
						ShUpConfig.resourceBundle.getString("shanoir.uploader.preImportDialog.title"), true, resourceBundle,
						importStudyAndStudyCardCBIL, importFinishAL);
				updateImportDialogForSubject(subjectDTO);
				List<Study> studiesWithStudyCards = getStudiesWithStudyCards(uploadJob);
				updateImportDialogForStudyAndStudyCard(studiesWithStudyCards);
				List<ExaminationDTO> examinationDTOs = getExaminations(subjectDTO);
				updateImportDialogForExaminations(examinationDTOs, uploadJob);
				updateImportDialogForMRICenter(uploadJob);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return;
			}
			importDialog.setVisible(true);
//		} else {
//			return;
//		}
	}

	/**
	 * @param uploadJob
	 */
	private void updateImportDialogForMRICenter(final UploadJob uploadJob) {
		Serie firstSerie = uploadJob.getSeries().iterator().next();
		String institutionName = firstSerie.getMriInformation().getInstitutionName();
		String institutionAddress = firstSerie.getMriInformation().getInstitutionAddress();
		String stationName = firstSerie.getMriInformation().getStationName();
		String manufacturer = firstSerie.getMriInformation().getManufacturer();
		String manufacturersModelName = firstSerie.getMriInformation().getManufacturersModelName();
		String deviceSerialNumber = firstSerie.getMriInformation().getDeviceSerialNumber();
		importDialog.mriCenterText.setText(institutionName);
		importDialog.mriCenterAddressText.setText(institutionAddress);
		importDialog.mriStationNameText.setText(stationName);
		importDialog.mriManufacturerText.setText(manufacturer);
		importDialog.mriManufacturersModelNameText.setText(manufacturersModelName);
		importDialog.mriDeviceSerialNumberText.setText(deviceSerialNumber);
	}

	/**
	 * This method calls the backend service and transforms DTO into model objects.
	 * Maybe this step could be unified.
	 * 
	 * @param dicomData
	 * @param equipmentDicom
	 */
	private List<Study> getStudiesWithStudyCards(final UploadJob uploadJob) {
		Serie firstSerie = uploadJob.getSeries().iterator().next();
		String manufacturer = firstSerie.getMriInformation().getManufacturer();
		String manufacturerModelName = firstSerie.getMriInformation().getManufacturersModelName();
		String deviceSerialNumber = firstSerie.getMriInformation().getDeviceSerialNumber();
		List<Study> studies = shanoirUploaderServiceClientNG.findStudiesWithStudyCards();
		return studies;
	}

	/**
	 * @param studiesWithStudyCards
	 */
	private void updateImportDialogForStudyAndStudyCard(List<Study> studiesWithStudyCards) {
		if (!studiesWithStudyCards.isEmpty()) {
			importDialog.studyCB.removeAllItems();
			boolean firstCompatibleStudyFound = false;
			for (Study study : studiesWithStudyCards) {
				importDialog.studyCB.addItem(study);
				if (study.getCompatible() != null && !firstCompatibleStudyFound) {
					importDialog.studyCB.setSelectedItem(study);
					importDialog.studyCardCB.removeAllItems();
					boolean firstCompatibleStudyCardFound = false;
					for (StudyCard studyCard : study.getStudyCards()) {
						importDialog.studyCardCB.addItem(studyCard);
						if (studyCard.getCompatible() && !firstCompatibleStudyCardFound) {
							importDialog.studyCardCB.setSelectedItem(studyCard);
							firstCompatibleStudyCardFound = true;
						}
					}
					firstCompatibleStudyFound = true;
				}
			}
		} else {
			importDialog.studyCB.removeAllItems();
			importDialog.studyCardCB.removeAllItems();
		}
		importDialog.studyCB.setValueSet(false);
	}

	/**
	 * @param dicomData
	 * @throws Exception
	 */
	private SubjectDTO getSubject(final UploadJob uploadJob) throws Exception {
		SubjectDTO foundSubject = null;
		if (uploadJob.getSubjectIdentifier() != null) {
			foundSubject = shanoirUploaderServiceClientNG
					.findSubjectBySubjectIdentifier(uploadJob.getSubjectIdentifier());
		}
		return foundSubject;
	}

	/**
	 * @param subjectDTO
	 */
	private void updateImportDialogForSubject(SubjectDTO subjectDTO) {
		// Existing subject found with identifier:
		if (subjectDTO != null) {
			// Common name
			importDialog.subjectTextField.setText(subjectDTO.getName());
			importDialog.subjectTextField.setBackground(Color.LIGHT_GRAY);
			importDialog.subjectTextField.setEnabled(false);
			importDialog.subjectTextField.setEditable(false);
			importDialog.subjectTextField.setValueSet(true);

			importDialog.subjectImageObjectCategoryCB.setSelectedItem(subjectDTO.getImagedObjectCategory());
			importDialog.subjectImageObjectCategoryCB.setEnabled(false);

			importDialog.subjectLanguageHemisphericDominanceCB
					.setSelectedItem(subjectDTO.getLanguageHemisphericDominance());
			importDialog.subjectLanguageHemisphericDominanceCB.setEnabled(false);
			importDialog.subjectManualHemisphericDominanceCB
					.setSelectedItem(subjectDTO.getManualHemisphericDominance());
			importDialog.subjectManualHemisphericDominanceCB.setEnabled(false);

			importDialog.subjectPersonalCommentTextArea.setBackground(Color.LIGHT_GRAY);
			importDialog.subjectPersonalCommentTextArea.setEditable(false);
			// No existing subject found with identifier:
		} else {
			// Common name
			if (ShUpConfig.isModeSubjectCommonNameManual()) {
				importDialog.subjectTextField.setText("");
				importDialog.subjectTextField.setBackground(Color.WHITE);
				importDialog.subjectTextField.setEnabled(true);
				importDialog.subjectTextField.setEditable(true);
			} else if (ShUpConfig.isModeSubjectCommonNameAutoIncrement()) {
				importDialog.subjectTextField
						.setText(resourceBundle.getString("shanoir.uploader.import.subject.autofill"));
				importDialog.subjectTextField.setBackground(Color.LIGHT_GRAY);
				importDialog.subjectTextField.setEnabled(false);
				importDialog.subjectTextField.setEditable(false);
			}
			importDialog.subjectTextField.setValueSet(false);

			importDialog.subjectImageObjectCategoryCB.setEnabled(true);
			importDialog.subjectImageObjectCategoryCB.setSelectedItem(importDialog.imageObjectCategories[1]);

			importDialog.subjectLanguageHemisphericDominanceCB.setEnabled(true);
			importDialog.subjectLanguageHemisphericDominanceCB.setSelectedItem(importDialog.leftOrRightLanguage[0]);
			importDialog.subjectManualHemisphericDominanceCB.setEnabled(true);
			importDialog.subjectManualHemisphericDominanceCB.setSelectedItem(importDialog.leftOrRightManual[0]);

			importDialog.subjectPersonalCommentTextArea.setText("");
			importDialog.subjectPersonalCommentTextArea.setBackground(Color.WHITE);
			importDialog.subjectPersonalCommentTextArea.setEditable(true);
		}
		importDialog.subjectIsPhysicallyInvolvedCB.setSelected(true);
		importDialog.subjectTypeCB.setSelectedItem(importDialog.subjectTypeValues[1]);
	}

	private List<ExaminationDTO> getExaminations(SubjectDTO subjectDTO) throws Exception {
		if (subjectDTO != null) {
			List<ExaminationDTO> examinationList = shanoirUploaderServiceClientNG
					.findExaminationsBySubjectId(subjectDTO.getId());
			return examinationList;
		}
		return null;
	}

	private void updateImportDialogForExaminations(List<ExaminationDTO> examinationDTOs, UploadJob uploadJob)
			throws ParseException {
		importDialog.mrExaminationExistingExamCB.removeAllItems();
		if (examinationDTOs != null && !examinationDTOs.isEmpty()) {
			for (Iterator iterator = examinationDTOs.iterator(); iterator.hasNext();) {
				ExaminationDTO examinationDTO = (ExaminationDTO) iterator.next();
				importDialog.mrExaminationExistingExamCB.addItem(examinationDTO);
			}
			importDialog.mrExaminationExistingExamCB.setEnabled(true);
			importDialog.mrExaminationNewExamCB.setEnabled(true);
			importDialog.mrExaminationNewExamCB.setSelected(false);
			disableExaminationNew();
		} else {
			if (importDialog.studyCardCB.getItemCount() > 0) {
				importDialog.mrExaminationNewExamCB.setEnabled(true);
				importDialog.mrExaminationNewExamCB.setSelected(true);
			}
		}
		Date studyDate = ShUpConfig.formatter.parse(uploadJob.getStudyDate());
		importDialog.mrExaminationNewDateModel.setValue(studyDate);
		importDialog.mrExaminationCommentTF.setText(uploadJob.getStudyDescription());
	}

	private void disableExaminationNew() {
		importDialog.mrExaminationExamExecutiveCB.setEnabled(false);
		importDialog.mrExaminationCenterCB.setEnabled(false);
		((Container) importDialog.mrExaminationDateDP).getComponent(1).setEnabled(false);
		JFormattedTextField mrExaminationDateDPTF = ((JDatePickerImpl) importDialog.mrExaminationDateDP)
				.getJFormattedTextField();
		mrExaminationDateDPTF.setBackground(Color.LIGHT_GRAY);
		importDialog.mrExaminationCommentTF.setEnabled(false);
	}

}
