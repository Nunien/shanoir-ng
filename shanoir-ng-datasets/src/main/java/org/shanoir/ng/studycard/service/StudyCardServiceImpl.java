package org.shanoir.ng.studycard.service;

import java.util.List;

import org.shanoir.ng.shared.exception.EntityNotFoundException;
import org.shanoir.ng.shared.exception.MicroServiceCommunicationException;
import org.shanoir.ng.studycard.dto.StudyStudyCardDTO;
import org.shanoir.ng.studycard.model.StudyCard;
import org.shanoir.ng.studycard.repository.StudyCardRepository;
import org.shanoir.ng.utils.Utils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Study Card service implementation.
 *
 * @author msimon
 *
 */
@Service
public class StudyCardServiceImpl implements StudyCardService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private StudyCardRepository studyCardRepository;

	@Override
	public void deleteById(final Long id) throws EntityNotFoundException, MicroServiceCommunicationException {
		final StudyCard studyCard = studyCardRepository.findOne(id);
		if (studyCard == null) {
			throw new EntityNotFoundException(StudyCard.class, id);
		}
		studyCardRepository.delete(id);

		// Delete study card on MS studies
		final StudyStudyCardDTO studyCardDTO = new StudyStudyCardDTO(studyCard.getId(), null, studyCard.getStudyId());
		updateMsStudies(studyCardDTO);
	}

	@Override
	public List<StudyCard> findAll() {
		return Utils.toList(studyCardRepository.findAll());
	}

	@Override
	public StudyCard findById(final Long id) {
		return studyCardRepository.findOne(id);
	}

	@Override
	public StudyCard save(final StudyCard studyCard) throws MicroServiceCommunicationException {
		StudyCard savedStudyCard = studyCardRepository.save(studyCard);
		final StudyStudyCardDTO studyCardDTO = new StudyStudyCardDTO(studyCard.getId(), studyCard.getStudyId(), null);
		updateMsStudies(studyCardDTO);
		return savedStudyCard;
	}

	@Override
	public List<StudyCard> search(final List<Long> studyIdList) {
		return studyCardRepository.findByStudyIdIn(studyIdList);
	}

	@Override
	public StudyCard update(final StudyCard studyCard) throws EntityNotFoundException, MicroServiceCommunicationException {
		final StudyCard studyCardDb = studyCardRepository.findOne(studyCard.getId());
		if (studyCardDb == null) throw new EntityNotFoundException(StudyCard.class, studyCard.getId());
		final Long oldStudyId = studyCardDb.getStudyId();
		updateStudyCardValues(studyCardDb, studyCard);
		studyCardRepository.save(studyCardDb);
		final StudyStudyCardDTO studyCardDTO = new StudyStudyCardDTO(studyCard.getId(), studyCard.getStudyId(), oldStudyId);
		updateMsStudies(studyCardDTO);
		return studyCardDb;
	}

	/**
	 * Update MS studies to link study to current study card.
	 *
	 * @param StudyStudyCardDTO DTO with link between study card and study.
	 * @return false if it fails, true if it succeed.
	 * @throws MicroServiceCommunicationException 
	 */
	private boolean updateMsStudies(final StudyStudyCardDTO StudyStudyCardDTO) throws MicroServiceCommunicationException {
//		try {
//			rabbitTemplate.convertAndSend(RabbitMqConfiguration.queueToStudy().getName(),
//					new ObjectMapper().writeValueAsString(StudyStudyCardDTO));
//			return true;
//		} catch (AmqpException | JsonProcessingException e) {
//			throw new MicroServiceCommunicationException("Error while communicating with Study MS.");
//		} 
		return true;
	}

	/**
	 * Update some values of template to save them in database.
	 *
	 * @param templateDb template found in database.
	 * @param template template with new values.
	 * @return database template with new values.
	 */
	private StudyCard updateStudyCardValues(final StudyCard studyCardDb, final StudyCard studyCard) {
		studyCardDb.setName(studyCard.getName());
		studyCardDb.setDisabled(studyCard.isDisabled());
		return studyCardDb;
	}

	@Override
	public List<StudyCard> findStudyCardsOfStudy(Long studyId) {
		return null;
	}

	@Override
	public Long searchCenterId(Long studyCardId) {
		StudyCard studyCard = studyCardRepository.findOne(studyCardId);
		return studyCard.getCenterId();
	}

}
