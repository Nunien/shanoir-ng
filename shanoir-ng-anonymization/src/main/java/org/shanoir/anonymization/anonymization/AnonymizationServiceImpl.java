package org.shanoir.anonymization.anonymization;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.shanoir.ng.anonymization.uid.generation.UIDException;
import org.shanoir.ng.anonymization.uid.generation.UIDGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Anonymization serviceImpl.
 * 
 * @author ifakhfakh
 * 
 */
public class AnonymizationServiceImpl implements AnonymizationService {

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AnonymizationServiceImpl.class);

	private static final String ANONYMIZATION_FILE_PATH = "anonymization.xlsx";
	private static final String xTagsColumn = "0xTag";
	private final String privateTags = "0xggggeeee";
	private final String curveDataTags = "0x50xxxxxx";
	private final String overlayCommentsTags = "0x60xx4000";
	private final String overlayDataTags = "0x60xx3000";
	private Map<String, String> anonymizationMAP;
	private List<String> tagsToKeep = new ArrayList< String>();


	private String patientBirthDate;

	private String mediaStorageSOPInstanceUID;

	Map<String, String> seriesInstanceUIDs = new HashMap<String, String>();

	Map<String, String> studyInstanceUIDs = new HashMap<String, String>();

	Map<String, String> studyIds = new HashMap<String, String>();

	public void anonymize(ArrayList<File> dicomFiles, String profile) {
		anonymizationMAP = AnonymizationRulesSingleton.getInstance().getAnonymizationMAP();
		tagsToKeep = AnonymizationRulesSingleton.getInstance().getTagsToKeep();
		final int totalAmount = dicomFiles.size();
		LOG.debug("anonymize : totalAmount=" + totalAmount);
		int current = 0;
		for (int i = 0; i < dicomFiles.size(); ++i) {
			final File file = dicomFiles.get(i);
			// Perform the anonymization
			performAnonymization(file, profile, false, "", "");
			current++;
			final int currentPercent = (int) (current * 100 / totalAmount);
			LOG.debug("anonymize : anonymization current percent= " + currentPercent + " %");
		}
	}
	
	public void anonymizeForShanoir(ArrayList<File> dicomFiles, String profile, String patientLastName, String patientFirstName, String patientID) {
		String patientName = patientLastName + "^" + patientFirstName + "^^^";
		anonymizeForShanoir(dicomFiles, profile, patientName, patientID);
	}

	public void anonymizeForShanoir(ArrayList<File> dicomFiles, String profile, String patientName, String patientID) {
		this.anonymizationMAP = AnonymizationRulesSingleton.getInstance().getAnonymizationMAP();
		this.tagsToKeep = AnonymizationRulesSingleton.getInstance().getTagsToKeep();
		final int totalAmount = dicomFiles.size();
		LOG.debug("anonymize : totalAmount=" + totalAmount);
		int current = 0;
		for (int i = 0; i < dicomFiles.size(); ++i) {
			final File file = dicomFiles.get(i);
			// Perform the anonymization
			performAnonymization(file, profile, true, patientName, patientID);
			current++;
			final int currentPercent = (int) (current * 100 / totalAmount);
			LOG.debug("anonymize : anonymization current percent= " + currentPercent + " %");

		}
	}

	private void anonymizePatientMetaData(Attributes attributes, String patientName, String patientID) {
		anonymizeTagAccordingToVR(attributes, Tag.PatientName, patientName);
		anonymizeTagAccordingToVR(attributes, Tag.PatientID, patientID);
		// patient birth date
		if (patientBirthDate != null && patientBirthDate.length() != 0) {
			String newDate = patientBirthDate.substring(0, 4) + "01" + "01";
			anonymizeTagAccordingToVR(attributes, Tag.PatientBirthDate, newDate);
		}
	}

	/**
	 * Perform the anonymization for an image according to choosed profile
	 * 
	 * @param dicomFile
	 *            the image path
	 * @param profile
	 *            anonymization profile
	 */
	public void performAnonymization(final File dicomFile, String profile, boolean isShanoirAnonymization,
			String patientName, String patientID) {
		try {
			DicomInputStream din = new DicomInputStream(dicomFile);
			// read metadata
			Attributes metaInformationAttributes = din.readFileMetaInformation();
			for (int tagInt : metaInformationAttributes.tags()) {
				String tagString = String.format("0x%08x", Integer.valueOf(tagInt));
				if (anonymizationMAP.containsKey(tagString)) {
					final String basicProfile = anonymizationMAP.get(tagString);
					anonymizeTag(tagInt, basicProfile, metaInformationAttributes);
				}
			}

			// read the other tags
			Attributes datasetAttributes = din.readDataset(-1, -1);
			
			// save the patient birth date if isShanoirAnonymization
			if (isShanoirAnonymization)
				patientBirthDate = datasetAttributes.getString(Tag.PatientBirthDate);

			// anonymize DICOM files according to selected profile
			for (int tagInt : datasetAttributes.tags()) {
				String tagString = String.format("0x%08X", Integer.valueOf(tagInt));

				String gggg = tagString.substring(2, 6);
				Integer intgggg = Integer.decode("0x" + gggg);
				if (intgggg % 2 == 1) {
					final String basicProfile = anonymizationMAP.get(privateTags);
					
					if(!this.tagsToKeep.contains(tagString)) {
						anonymizeTag(tagInt, basicProfile, datasetAttributes);
					}
				} else if (anonymizationMAP.containsKey(tagString)) {
					if (tagInt == Tag.SOPInstanceUID) {
						anonymizeSOPInstanceUID(tagInt, datasetAttributes);
					} else if (tagInt == Tag.SeriesInstanceUID) {
						anonymizeSeriesInstanceUID(tagInt, datasetAttributes);
					} else if (tagInt == Tag.StudyInstanceUID) {
						anonymizeStudyInstanceUID(tagInt, datasetAttributes);
					} else if (tagInt == Tag.StudyID) {
						anonymizeStudyId(tagInt, datasetAttributes);
					}

					else {
						final String basicProfile = anonymizationMAP.get(tagString);
						anonymizeTag(tagInt, basicProfile, datasetAttributes);
					}
				} else {
					if ((0x50000000 <= tagInt) && (tagInt <= 0x50FFFFFF)) {
						final String basicProfile = anonymizationMAP.get(curveDataTags);
						anonymizeTag(tagInt, basicProfile, datasetAttributes);
					} else if ((0x60004000 <= tagInt) && (tagInt <= 0x60FF4000)) {
						final String basicProfile = anonymizationMAP.get(overlayCommentsTags);
						anonymizeTag(tagInt, basicProfile, datasetAttributes);
					} else if ((0x60003000 <= tagInt) && (tagInt <= 0x60FF3000)) {
						final String basicProfile = anonymizationMAP.get(overlayDataTags);
						anonymizeTag(tagInt, basicProfile, datasetAttributes);
					}
				}
			}
			// Special anonymization of patient data if isShanoirAnonymization
			if (isShanoirAnonymization) {
				anonymizePatientMetaData(datasetAttributes, patientName, patientID);
			}
			LOG.debug("finish anonymization: begin storage");

			final DicomOutputStream dos = new DicomOutputStream(dicomFile);
			dos.writeDataset(metaInformationAttributes, datasetAttributes);
			dos.close();
			LOG.debug("finish anonymization: end storage");
		} catch (final IOException exc) {
			LOG.error("performAnonymization : error while anonimizing file " + dicomFile.toString() + " : ", exc);
		}

	}
//	
//	/**
//	 * Read the excel document containing the list of private tags to keep 
//	 * 
//	 * @param profile
//	 * @return
//	 */
//	public List<String> readPrivateTagToKeepInAnonymizationFile() {
//		List<String> tagsToKeep = new ArrayList< String>();
//
//
//		try {
//			ClassLoader classLoader = getClass().getClassLoader();
//			InputStream in = classLoader.getResourceAsStream(ANONYMIZATION_FILE_PATH);
//
//			XSSFWorkbook myWorkBook = new XSSFWorkbook(in);
//
//			// Return first sheet from the XLSX workbook
//			XSSFSheet mySheet = myWorkBook.getSheetAt(1);
//
//			// Get iterator to all the rows in current sheet
//			Iterator<Row> rowIterator = mySheet.iterator();
//
//			// Traversing over each row of XLSX file
//			while (rowIterator.hasNext()) {
//				Row row = rowIterator.next();
//				Cell cell = row.getCell(0);
//				tagsToKeep.add(cell.getStringCellValue());
//			}
//		} catch (IOException e) {
//			LOG.error("Unable to read anonymization file: " + e);
//		}
//		return tagsToKeep;
//	}

//	/**
//	 * Read the excel document containing the list of tags to anonymize
//	 * 
//	 * @param profile
//	 * @return
//	 */
//	public Map<String, String> readAnonymizationFile(final String profile) {
//		Map<String, String> anonymizationMAP = new HashMap<String, String>();
//		List<String> tagsToKeep = new ArrayList< String>();
//		Integer xtagColumn = null;
//		Integer profileColumn = null;
//
//		try {
//			ClassLoader classLoader = getClass().getClassLoader();
//			InputStream in = classLoader.getResourceAsStream(ANONYMIZATION_FILE_PATH);
//
//			XSSFWorkbook myWorkBook = new XSSFWorkbook(in);
//
//			// Return first sheet from the XLSX workbook
//			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
//
//			// Get iterator to all the rows in current sheet
//			Iterator<Row> rowIterator = mySheet.iterator();
//
//			// Traversing over each row of XLSX file
//			while (rowIterator.hasNext()) {
//				Row row = rowIterator.next();
//				int rowNumber = row.getRowNum();
//				if (rowNumber == 0) {
//					Iterator<Cell> cellIterator = row.cellIterator();
//					while (cellIterator.hasNext() && (xtagColumn == null || profileColumn == null)) {
//
//						Cell cell = cellIterator.next();
//
//						if (cell.getStringCellValue().equals(xTagsColumn)) {
//							xtagColumn = cell.getColumnIndex();
//							LOG.debug("Tags column : " + xtagColumn);
//						} else if (cell.getStringCellValue().equals(profile)) {
//							profileColumn = cell.getColumnIndex();
//						}
//
//					}
//
//				}
//				if (xtagColumn != null && profileColumn != null) {
//					Cell xtagCell = row.getCell(xtagColumn);
//					if (xtagCell != null) {
//						String tagString = xtagCell.getStringCellValue();
//						if (tagString != null && tagString.length() != 0 && !tagString.equals("0xTag")) {
//							Cell basicProfileCell = row.getCell(profileColumn);
//							LOG.debug("The basic profile of tag " + tagString + " = "
//									+ basicProfileCell.getStringCellValue());
//							anonymizationMAP.put(tagString, basicProfileCell.getStringCellValue());
//						}
//					}
//				} else {
//					LOG.error("Unable to read anonymization tags or/and anonymization profile ");
//				}
//			}
//			
//			// Return second sheet from the XLSX workbook
//			XSSFSheet mySheet2 = myWorkBook.getSheetAt(1);
//
//			// Get iterator to all the rows in current sheet
//			Iterator<Row> rowIterator2 = mySheet2.iterator();
//
//			// Traversing over each row of XLSX file
//			while (rowIterator2.hasNext()) {
//				Row row = rowIterator2.next();
//				Cell cell = row.getCell(0);
//				tagsToKeep.add(cell.getStringCellValue());
//			}
//			this.tagsToKeep = tagsToKeep;
//			
//		} catch (IOException e) {
//			LOG.error("Unable to read anonymization file: " + e);
//		}
//
//		return anonymizationMAP;
//	}

	/**
	 * Tag Anonymization
	 * 
	 * @param tagInt
	 *            : the tag to anonymize
	 * @param basicProfile
	 *            : the tag's basic profile
	 * @param attributes
	 *            : the list of dicom attributes to modify
	 */
	private void anonymizeTag(Integer tagInt, String basicProfile, Attributes attributes) {
		String value = getFinalValueForTag(tagInt, basicProfile);
		if (value == null) {
			attributes.remove(tagInt);
		} else if (value == "KEEP") {
			// do nothing
		} else {
			anonymizeTagAccordingToVR(attributes, tagInt, value);
		}
	}

	private void anonymizeSOPInstanceUID(int tagInt, Attributes attributes) {
		String value;
		if (mediaStorageSOPInstanceUID != null) {
			value = mediaStorageSOPInstanceUID;
		} else {
			UIDGeneration generator = new UIDGeneration();
			String newUID = null;
			try {
				newUID = generator.getNewUID();
			} catch (UIDException e) {
				LOG.error(e.getMessage());
			}
			value = newUID;
		}
		anonymizeTagAccordingToVR(attributes, tagInt, value);
	}

	private void anonymizeSeriesInstanceUID(int tagInt, Attributes attributes) {
		String value;
		if (seriesInstanceUIDs != null && seriesInstanceUIDs.size() != 0
				&& seriesInstanceUIDs.get(attributes.getString(tagInt)) != null) {
			value = seriesInstanceUIDs.get(attributes.getString(tagInt));
		} else {
			UIDGeneration generator = new UIDGeneration();
			String newUID = null;
			try {
				newUID = generator.getNewUID();
			} catch (UIDException e) {
				LOG.error(e.getMessage());
			}
			value = newUID;
			seriesInstanceUIDs.put(attributes.getString(tagInt), value);
		}
		anonymizeTagAccordingToVR(attributes, tagInt, value);
	}

	private void anonymizeStudyInstanceUID(int tagInt, Attributes attributes) {
		String value;
		if (studyInstanceUIDs != null && studyInstanceUIDs.size() != 0
				&& studyInstanceUIDs.get(attributes.getString(tagInt)) != null) {
			value = studyInstanceUIDs.get(attributes.getString(tagInt));
		} else {
			UIDGeneration generator = new UIDGeneration();
			String newUID = null;
			try {
				newUID = generator.getNewUID();
			} catch (UIDException e) {
				LOG.error(e.getMessage());
			}
			value = newUID;
			studyInstanceUIDs.put(attributes.getString(tagInt), value);
		}
		anonymizeTagAccordingToVR(attributes, tagInt, value);
	}

	private void anonymizeStudyId(int tagInt, Attributes attributes) {
		String value;
		if (studyIds != null && studyIds.size() != 0 && studyIds.get(attributes.getString(tagInt)) != null) {
			value = studyIds.get(attributes.getString(tagInt));
		} else {
			char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 10; i++) {
				char c = chars[random.nextInt(chars.length)];
				sb.append(c);
			}
			String output = sb.toString();
			value = output.toString();
			studyIds.put(attributes.getString(tagInt), value);
		}
		anonymizeTagAccordingToVR(attributes, tagInt, value);
	}

	/**
	 * Get the anonymized value of the tag
	 * 
	 * @param tag
	 *            : the tag to anonymize
	 * @param basicProfie
	 *            : the tag's basic profile
	 * @return
	 */
	private String getFinalValueForTag(final int tag, final String basicProfie) {
		String result = "";
		if (basicProfie != null) {
			if (basicProfie.equals("X")) {
				result = null;
			} else if (basicProfie.equals("Z")) {
				result = "";
			} else if (basicProfie.equals("D")) {
				SecureRandom random = new SecureRandom();
				result = new BigInteger(130, random).toString(32);
			} else if (basicProfie.equals("U")) {
				UIDGeneration generator = new UIDGeneration();
				String newUID = null;
				try {
					newUID = generator.getNewUID();
				} catch (UIDException e) {
					LOG.error(e.getMessage());
				}
				if (tag == Tag.MediaStorageSOPInstanceUID)
					mediaStorageSOPInstanceUID = newUID;
				result = newUID;
			} else if (basicProfie.equals("K")) {
				result = "KEEP";
			}
		}
		return result;
	}

	/**
	 * anonymize Tag According To its VR
	 * 
	 * @param attributes
	 *            : the list of dicom attributes to modify
	 * @param tag
	 *            : the tag to anonymize
	 * @param value
	 *            : the new value of the tag after anonymization
	 */
	private void anonymizeTagAccordingToVR(Attributes attributes, int tag, String value) {
		VR vr = attributes.getVR(tag);
		// VR.AT = Attribute Tag
		// VR.SL = Signed Long || VR.UL = Unsigned Long
		// VR.SS = Signed Short || VR.US = Unsigned Short
		if (vr.equals(VR.SL) || vr.equals(VR.UL) || vr.equals(VR.AT) || vr.equals(VR.SS) || vr.equals(VR.US)) {
			Integer i_value = Integer.decode(value);
			attributes.setInt(tag, vr, i_value);
		}

		// VR.FD = Floating Point Double
		else if (vr.equals(VR.FD)) {
			Double d_value = Double.valueOf(value);
			attributes.setDouble(tag, vr, d_value);
		}

		// VR.FL = Floating Point Single
		else if (vr.equals(VR.FL)) {
			Float f_value = Float.valueOf(value);
			attributes.setFloat(tag, vr, f_value);
		}

		// VR.OB = Other Byte String
		else if (vr.equals(VR.OB)) {
			byte[] b = new byte[1];
			attributes.setBytes(tag, vr, b);
		}

		// VR.SQ = Sequence of Items || VR.UN = Unknown
		else if (vr.equals(VR.SQ) || vr.equals(VR.UN)) {
			// attributes.setSequence(tag);
			attributes.setNull(tag, vr);
		}

		// Unlimited string:
		// VR.AE = Age String
		// VR.AS = Application Entity
		// VR.CS = Code String
		// VR.DA = Date
		// VR.DS = Date Time
		// VR.DT = Decimal String
		// VR.IS = Integer String
		// VR.LO = Long String
		// VR.LT = Long Text
		// VR.OF = Other Float String
		// VR.OW = Other Word String
		// VR.PN = Person Name
		// VR.SH = Short String
		// VR.ST = Short Text
		// VR.TM = Time
		// VR.UI = Unique Identifier (UID)
		// VR.UT = Unlimited Text
		else if (vr.equals(VR.AE) || vr.equals(VR.AS) || vr.equals(VR.CS) || vr.equals(VR.DA) || vr.equals(VR.DS)
				|| vr.equals(VR.DT) || vr.equals(VR.IS) || vr.equals(VR.LO) || vr.equals(VR.LT) || vr.equals(VR.OW)
				|| vr.equals(VR.PN) || vr.equals(VR.SH) || vr.equals(VR.ST) || vr.equals(VR.TM) || vr.equals(VR.UI)
				|| vr.equals(VR.UT) || vr.equals(VR.OF)) {
			attributes.setString(tag, vr, value);
		}

		else {
			attributes.setString(tag, vr, value);
		}

		// N.B.: Doesn't exist in the library:
		// VR.UR = Universal Resource Identifier or Universal
		// Resource Locator (URI/URL)
		// VR.OD = Other Double String
	}

}
