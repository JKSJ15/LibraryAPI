package library.com.integration;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import library.com.configurations.JwtService;
import library.com.entity.User;
import library.com.entity.UserRole;
import library.com.repository.UserRepository;
import library.com.util.BookUtilTest;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper obj;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private String tokenAdm;
    private String tokenUser;
    private String bodyPostOrput;
    
    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        User admin = new User();
        admin.setEmail("admin@email.com");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setRole(UserRole.ROLE_ADMIN);
        userRepository.save(admin);
        tokenAdm = jwtService.generateToken(admin);

        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(UserRole.ROLE_USER);
        userRepository.save(user);
        tokenUser = jwtService.generateToken(user);
        
        bodyPostOrput = """
                {
                    "title": "the Learn of the stars",
                    "author": "pablo marsal",
                    "dateOfPublication": "2006-10-10",
                    "genre": "adventure",
                    "description": "the learn that palo has been sucessfull",
                    "status": "AVAILABLE"
                }
                """;

    }

    //AUTH ----------------------
    @Test
    void registerShouldRegisterNewUser() throws Exception {
        String body = """
        {
          "login":"jakson@email.com",
          "password":"123456",
          "role":"ROLE_ADMIN"
        }
        """;

        mvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void LoginShouldAuthenticateUser() throws Exception {
        String body = """
        {
          "login":"admin@email.com",
          "password":"123456"
        }
        """;

        mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyString())));
    }

    @Test
    void LoginShouldReturnForbiddenWhenBadCredentiais() throws Exception {
        String body = """
        {
           "email":"anyemail.anycom",
           "password":"0000" 
        }
        """;

        mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isForbidden());
    }
    
    //BOOKS ---------------------
    
    //POST
    @Test
    void postShouldCreateBook() throws Exception {
        mvc.perform(post("/books")
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyPostOrput))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("the Learn of the stars"));
    }

    @Test
    void postShouldReturnForbiddenWhenUserAcess() throws Exception {
        mvc.perform(post("/books")
                .header("Authorization", "Bearer " + tokenUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyPostOrput))
                .andExpect(status().isForbidden());
    }
    
    //DELETE
	@Test
	void deleteShoudDeleteBook() throws JacksonException, Exception {
		
		long id = createBook();
		
		mvc.perform(delete("/books/"+id)
                .header("Authorization", "Bearer " + tokenAdm))
                .andExpect(status().isNoContent());
		
		mvc.perform(get("/books/"+id))
                .andExpect(status().isNotFound());
	}

	@Test
    void deleteShouldReturnForbiddenWhenUserAcess() throws Exception {
		long id = createBook();
		
        mvc.perform(delete("/books/"+id)
                .header("Authorization", "Bearer " + tokenUser))
                .andExpect(status().isForbidden());
    }
	
	//PUT
	@Test
    void putShouldUpdateBook() throws Exception {
		long id = createBook();
		
        mvc.perform(put("/books/"+id)
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyPostOrput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("the Learn of the stars"));
    }

    @Test
    void putShouldReturnForbiddenWhenUserAcess() throws Exception {
    	long id = createBook();
    	
        mvc.perform(put("/books/"+id)
                .header("Authorization", "Bearer " + tokenUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyPostOrput))
                .andExpect(status().isForbidden());
    }
    
    //GET
  	@Test
  	void getShoudReturnBooks() throws JacksonException, Exception {
  		mvc.perform(get("/books"))
  				.andExpect(status().isOk());
  	}

  	@Test
  	void getShoudReturnBookById() throws JacksonException, Exception {
  	    long id = createBook();
  	    
  		mvc.perform(get("/books/"+id))
  				.andExpect(status().isOk())
  				.andExpect(jsonPath("$.id").value(id));
  	}
  	
  	//METHOD
	private long createBook() throws Exception {
	    String response = mvc.perform(post("/books")
	            .header("Authorization", "Bearer " + tokenAdm)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(obj.writeValueAsString(BookUtilTest.returnBookDtoGet())))
	            .andReturn()
	            .getResponse()
	            .getContentAsString();

	    Number id = JsonPath.read(response, "$.id");
	    return id.longValue();
	}
}