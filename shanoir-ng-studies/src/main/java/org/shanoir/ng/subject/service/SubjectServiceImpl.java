package org.shanoir.ng.subject.service;

import java.util.ArrayList;
import java.util.List;

import org.shanoir.ng.shared.exception.EntityNotFoundException;
import org.shanoir.ng.study.repository.StudyRepository;
import org.shanoir.ng.subject.dto.SimpleSubjectDTO;
import org.shanoir.ng.subject.model.Subject;
import org.shanoir.ng.subject.repository.SubjectRepository;
import org.shanoir.ng.subject.repository.SubjectRepositoryImpl;
import org.shanoir.ng.subjectstudy.dto.mapper.SubjectStudyDecorator;
import org.shanoir.ng.subjectstudy.model.SubjectStudy;
import org.shanoir.ng.subjectstudy.repository.SubjectStudyRepository;
import org.shanoir.ng.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Subject service implementation.
 *
 * @author msimon
 *
 */
@Service
public class SubjectServiceImpl implements SubjectService {

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SubjectServiceImpl.class);

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private StudyRepository studyRepository;

	@Autowired
	private SubjectStudyRepository subjectStudyRepository;
	
	@Autowired
	private SubjectStudyDecorator subjectStudyMapper;

	@Override
	public void deleteById(final Long id) throws EntityNotFoundException {
		if (subjectRepository.findOne(id) == null) throw new EntityNotFoundException(Subject.class, id);
		subjectRepository.delete(id);
	}

	@Override
	public List<Subject> findAll() {
		return Utils.toList(subjectRepository.findAll());
	}

	@Override
	public List<Subject> findBy(final String fieldName, final Object value) {
		return subjectRepository.findBy(fieldName, value);
	}

	@Override
	public Subject findByData(final String name) {
		return subjectRepository.findByName(name);
	}

	@Override
	public Subject findById(final Long id) {
		return subjectRepository.findOne(id);
	}

	@Override
	public Subject findByIdWithSubjecStudies(final Long id) {
		return subjectRepository.findSubjectWithSubjectStudyById(id);
	}
	
	@Override
	public Subject create(final Subject subject) {
		if (subject.getSubjectStudyList() != null) {
			for (final SubjectStudy subjectStudy : subject.getSubjectStudyList()) {
				subjectStudy.setSubject(subject);
			}			
		}
		return subjectRepository.save(subject);
	}

	@Override
	public Subject update(final Subject subject) throws EntityNotFoundException {
		final Subject subjectDb = subjectRepository.findOne(subject.getId());
		if (subjectDb == null) throw new EntityNotFoundException(Subject.class, subject.getId());
		updateSubjectValues(subjectDb, subject);
		subjectRepository.save(subjectDb);
		return subjectDb;
	}
	
	

	/*
	 * Update some values of template to save them in database.
	 *
	 * @param templateDb template found in database.
	 * @param template template with new values.
	 * @return database template with new values.
	 */
	private Subject updateSubjectValues(final Subject subjectDb, final Subject subject) {

		subjectDb.setName(subject.getName());
		//subjectDb.setBirthDate(subject.getBirthDate());
		subjectDb.setIdentifier(subject.getIdentifier());
		subjectDb.setPseudonymusHashValues(subject.getPseudonymusHashValues());
		subjectDb.setSex(subject.getSex());
		subjectDb.setSubjectStudyList(subject.getSubjectStudyList());
		subjectDb.setManualHemisphericDominance(subject.getManualHemisphericDominance());
		subjectDb.setLanguageHemisphericDominance(subject.getLanguageHemisphericDominance());
		subjectDb.setImagedObjectCategory(subject.getImagedObjectCategory());
		subjectDb.setUserPersonalCommentList(subject.getUserPersonalCommentList());
		
		// Copy list of database links subject/study
		final List<SubjectStudy> subjectStudyList = subject.getSubjectStudyList();
		if (subjectStudyList != null) {
			for (final SubjectStudy subjectStudy : subject.getSubjectStudyList()) {
				if (subjectStudy.getId() == null) {
					// Add link subject/study
					subjectStudy.setSubject(subjectDb);
					subjectDb.getSubjectStudyList().add(subjectStudy);
				}
			}			
		}
		final List<SubjectStudy> subjectStudyDbList = subjectDb.getSubjectStudyList();
		if (subjectStudyDbList != null) {
			for (final SubjectStudy subjectStudyDb : subjectStudyDbList) {
				boolean keepSubjectStudy = false;
				for (final SubjectStudy subjectStudy : subject.getSubjectStudyList()) {
					if (subjectStudyDb.getId().equals(subjectStudy.getId())) {
						keepSubjectStudy = true;
						break;
					}
				}
				if (!keepSubjectStudy) {
					// Move link subject/study
					subjectDb.getSubjectStudyList().remove(subjectStudyDb);
					subjectStudyRepository.delete(subjectStudyDb.getId());
				}
			}			
		}
		return subjectDb;
	}
	
	

	@Override
	public List<SimpleSubjectDTO> findAllSubjectsOfStudy(final Long studyId) {
		List<SimpleSubjectDTO> simpleSubjectDTOList = new ArrayList<SimpleSubjectDTO>();
		List<SubjectStudy> opt = subjectStudyRepository.findByStudy(studyRepository.findOne(studyId));
		if (opt != null) {
			for (SubjectStudy rel : opt) {
				SimpleSubjectDTO simpleSubjectDTO = new SimpleSubjectDTO();
				if (rel.getStudy().getId() == studyId) {
					Subject sub = rel.getSubject();
					simpleSubjectDTO.setId(sub.getId());
					simpleSubjectDTO.setName(sub.getName());
					simpleSubjectDTO.setIdentifier(sub.getIdentifier());
					simpleSubjectDTO.setSubjectStudy(subjectStudyMapper.subjectStudyToSubjectStudyDTO(rel));
					simpleSubjectDTOList.add(simpleSubjectDTO);
				}
			}
		} 
		return simpleSubjectDTOList;
	}
	

	@Override
	public Subject findByIdentifier(String identifier) {
		return subjectRepository.findByIdentifier(identifier);
	}

	
	@Override
	public Subject findSubjectFromCenterCode(final String centerCode) {
		if (centerCode == null || "".equals(centerCode)) {
			return null;
		}
		return subjectRepository.findFromCenterCode(centerCode);
	}

}
