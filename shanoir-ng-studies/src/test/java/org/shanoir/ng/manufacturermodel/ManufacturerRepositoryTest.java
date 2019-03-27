package org.shanoir.ng.manufacturermodel;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shanoir.ng.manufacturermodel.model.Manufacturer;
import org.shanoir.ng.manufacturermodel.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

/**
 * Tests for repository 'manufacturer'.
 * 
 * @author msimon
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ManufacturerRepositoryTest {

	private static final Long MANUFACTURER_TEST_1_ID = 1L;
	private static final String MANUFACTURER_TEST_1_NAME = "GE Healthcare";
	
	@Autowired
	private ManufacturerRepository repository;
	
	/*
	 * Mocks used to avoid unsatisfied dependency exceptions.
	 */
	@MockBean
	private AuthenticationManager authenticationManager;
	@MockBean
	private DocumentationPluginsBootstrapper documentationPluginsBootstrapper;
	@MockBean
	private WebMvcRequestHandlerProvider webMvcRequestHandlerProvider;
	
	@Test
	public void findAllTest() throws Exception {
		Iterable<Manufacturer> manufacturersDb = repository.findAll();
		assertThat(manufacturersDb).isNotNull();
		int nbManufacturers = 0;
		Iterator<Manufacturer> manufacturersIt = manufacturersDb.iterator();
		while (manufacturersIt.hasNext()) {
			manufacturersIt.next();
			nbManufacturers++;
		}
		assertThat(nbManufacturers).isEqualTo(3);
	}
	
	@Test
	public void findOneTest() throws Exception {
		Manufacturer manufacturerDb = repository.findOne(MANUFACTURER_TEST_1_ID);
		assertThat(manufacturerDb).isNotNull();
		assertThat(manufacturerDb.getName()).isEqualTo(MANUFACTURER_TEST_1_NAME);
	}
	
}
