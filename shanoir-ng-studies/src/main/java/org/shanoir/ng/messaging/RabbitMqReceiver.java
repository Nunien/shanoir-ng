//package org.shanoir.ng.messaging;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//import org.shanoir.ng.study.repository.StudyUserRepository;
//import org.shanoir.ng.study.rights.AbstractStudyUser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * RabbitMQ message receiver.
// *
// * @author msimon
// * @author mkain
// *
// */
//public class RabbitMqReceiver {
//	
//	private static final Logger LOG = LoggerFactory.getLogger(RabbitMqReceiver.class);
//
//	private static final String MS_USERS_TO_MS_STUDIES_USER_DELETE = "ms_users_to_ms_studies_user_delete";
//
//	@Autowired
//	private StudyUserRepository studyUserRepository;
//
//    private CountDownLatch latch = new CountDownLatch(1);
//
//    @RabbitListener(queues = MS_USERS_TO_MS_STUDIES_USER_DELETE)
//	public void receiveUserDeleteMessage(final Long userId) {
//		LOG.debug(" [x] Received User Delete Message: '" + userId + "'");
//		List<AbstractStudyUser> studyUserList = studyUserRepository.findByUserId(userId);
//		for (Iterator<AbstractStudyUser> iterator = studyUserList.iterator(); iterator.hasNext();) {
//			AbstractStudyUser studyUser = (AbstractStudyUser) iterator.next();
//			studyUserRepository.delete(studyUser);
//		}
//		latch.countDown();
//    }
//
//    public CountDownLatch getLatch() {
//        return latch;
//    }
//
//}