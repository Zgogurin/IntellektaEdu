package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.education.controllers.ExceptionController;
import ru.education.controllers.ProductController;
import ru.education.entity.Product;
import ru.education.exceptions.EntityIllegalArgumentException;
import ru.education.exceptions.EntityNotFoundException;
import ru.education.service.impl.DefaultProductService;
import service.mock.MockProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration (classes = {ProductController.class, MockProductService.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"ru.education.jpa"})
@EntityScan(basePackages = {"ru.education.entity"})
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    private MockMvc mockMvc;

    private final static String URL = "http://localhost:8080/api/v1/product";

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIllegalIdFoundTest() throws Exception {
        mockMvc.perform(get(URL + "/dfgd")).andExpect(status().isBadRequest());
    }

    @Test
    public void findByIdNotFoundTest() throws Exception {
        mockMvc.perform(get(URL + "/4")).andExpect(status().isNotFound());
    }

    @Test
    public void findByIdTest() throws Exception {
        mockMvc.perform(get(URL + "/2")).andExpect(status().isOk());
    }

    @Test
    public void createAlreadyExistsProductException() throws Exception {
        Product product = new Product(2,"testProduct");
        String requestJson = mapper.writeValueAsString(product);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void createTest() throws Exception {
        Product product = new Product(3,"testProduct");
        String requestJson = mapper.writeValueAsString(product);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateNotFoundProductTest() throws Exception {
        Product product = new Product(4,"bike");
        String requestJson = mapper.writeValueAsString(product);
        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateTest() throws Exception {
        Product product = new Product(2,"bike");
        String requestJson = mapper.writeValueAsString(product);
        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteIllegalProductIdTest() throws Exception {
        mockMvc.perform(delete(URL+ "/test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNotFoundProductTest() throws Exception {
        mockMvc.perform(delete(URL+ "/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteHasDetailsTest() throws Exception {
        mockMvc.perform(delete(URL+ "/2"))
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteTest() throws Exception {
        Product product = new Product(4,"testProduct");
        String requestJson = mapper.writeValueAsString(product);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson));
        mockMvc.perform(delete(URL+ "/4"))
                .andExpect(status().isNoContent());
    }
}
