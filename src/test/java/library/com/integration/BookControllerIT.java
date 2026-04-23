package library.com.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import jakarta.transaction.Transactional;
import library.com.util.BookUtilTest;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerIT {
	@Autowired
	MockMvc mvc;
	@Autowired
	ObjectMapper obj;
	
	@WithMockUser(roles = "ADMIN")
	@Test
	void testMethodPost() throws JacksonException, Exception {
		mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
		.content(obj.writeValueAsString(BookUtilTest.returnBookDtoGet())))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").exists());
	}
	@Test
	void testMethodGet() throws JacksonException, Exception {
		mvc.perform(get("/books"))
				.andExpect(status().isOk());
	}
	@WithMockUser(roles = "ADMIN")
	@Test
	void testMethodGetById() throws JacksonException, Exception {
		
	    long id = createBook();
	    
		mvc.perform(get("/books/"+id))
				.andExpect(status().isOk());
	}
	@WithMockUser(roles = "ADMIN")
	@Test
	void testMethodDelete() throws JacksonException, Exception {
		
		long id = createBook();
		
		mvc.perform(delete("/books/"+id)).andExpect(status().isNoContent());
		
		mvc.perform(get("/books/"+id)).andExpect(status().isBadRequest());
	}
	@WithMockUser(roles = "ADMIN")
	@Test
	void testMethodPut() throws JacksonException, Exception {
		
		long id = createBook();
		
		mvc.perform(put("/books/"+id).contentType(MediaType.APPLICATION_JSON)
		.content(obj.writeValueAsString(BookUtilTest.returnBookDtoGet())))
		.andExpect(status().isOk());
	}
	private long createBook() throws Exception {
	    String response = mvc.perform(post("/books")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(obj.writeValueAsString(BookUtilTest.returnBookDtoGet())))
	            .andReturn()
	            .getResponse()
	            .getContentAsString();

	    Number id = JsonPath.read(response, "$.id");
	    return id.longValue();
	}
}
