package org.shanoir.ng.studyuser;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository for relations between a study and an user.
 *
 * @author msimon
 */
public interface StudyUserRepository extends CrudRepository<StudyUser, Long> {

	List<StudyUser> findByUserId(Long userId);
	
}
