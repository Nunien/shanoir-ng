package org.shanoir.ng.shared.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service used to generate microservices requests.
 * 
 * @author msimon
 */
@Service
public class MicroserviceRequestsService {
	
	public static final String SUBJECT = "/subject";
	public static final String STUDY = "/studies";
	public static final String CENTER = "/centers";
	public static final String COMMON = "/common";
	public static final String CENTERID = "/centerid";
	
	@Value("${ms.url.shanoir-ng-studies}")
	private String studiesMsUrl;

	/**
	 * @return the studiesMsUrl
	 */
	public String getStudiesMsUrl() {
		return studiesMsUrl;
	}

}
