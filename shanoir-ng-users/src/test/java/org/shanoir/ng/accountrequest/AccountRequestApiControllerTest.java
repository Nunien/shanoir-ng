package org.shanoir.ng.accountrequest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.shanoir.ng.accountrequest.AccountRequestApiController;
import org.shanoir.ng.accountrequest.AccountRequestInfo;
import org.shanoir.ng.shared.exception.ShanoirUsersException;
import org.shanoir.ng.user.User;
import org.shanoir.ng.user.UserService;
import org.shanoir.ng.utils.ModelsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Unit tests for user controller.
 *
 * @author msimon
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountRequestApiController.class)
@AutoConfigureMockMvc(secure = false)
public class AccountRequestApiControllerTest {

	private static final String REQUEST_PATH = "/accountrequest";

	private Gson gson;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userServiceMock;

	@Before
	public void setup() throws ShanoirUsersException {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

		given(userServiceMock.save(Mockito.mock(User.class))).willReturn(new User());
	}

	@Test
	public void saveNewAccountRequestTest() throws Exception {
		final User user = ModelsUtil.createUser(null);
		user.setEmail("test@te.st");
		user.setUsername("test");
		final AccountRequestInfo info = new AccountRequestInfo();
		info.setContact("contact");
		info.setFunction("function");
		info.setInstitution("institution");
		info.setService("service");
		info.setStudy("study");
		info.setWork("work");
		user.setAccountRequestInfo(info);

		mvc.perform(MockMvcRequestBuilders.post(REQUEST_PATH).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)))
				.andExpect(status().isNoContent());
	}

}
