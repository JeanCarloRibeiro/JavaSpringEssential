package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.model.Category;
import com.devsuperior.dscommerce.model.Product;
import com.devsuperior.dscommerce.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TokenUtil tokenUtil;

  @Autowired
  private ObjectMapper mapper;

  private String productName;

  private Product product;
  private ProductDTO productDTO;

  private String adminToken, clientToken, invalidToken;
  private Long existingProductId, nonExistingProductId, dependentProductId;

  @BeforeEach
  void setUp() throws Exception {
    productName = "Macbook";

    adminToken = tokenUtil.obtainAccessToken(mockMvc, "alex@gmail.com", "123456");
    clientToken = tokenUtil.obtainAccessToken(mockMvc, "maria@gmail.com", "123456");
    invalidToken = adminToken + "xpto";

    Category category = new Category(1L, "teste");
    product = new Product(null, "Xbox", "teste..................", 2000.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg");
    product.getCategories().add(category);
    productDTO = new ProductDTO(product);

    existingProductId = 1L;
    nonExistingProductId = 100L;
    dependentProductId = 3L;

  }

  @Test
  void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {
    ResultActions resultActions = mockMvc
            .perform(get("/products?name={productName}", productName)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    resultActions.andExpect(jsonPath("$.content[0].id").value(5L));
    resultActions.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
    resultActions.andExpect(jsonPath("$.content[0].price").value(1250.0));
    resultActions.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
  }

  @Test
  void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {
    productName = null;
    ResultActions resultActions = mockMvc
            .perform(get("/products?name={productName}", productName)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    resultActions.andExpect(jsonPath("$.content[0].id").value(1L));
    resultActions.andExpect(jsonPath("$.content[0].name").value("Senhor dos Anéis"));
    resultActions.andExpect(jsonPath("$.content[0].price").value(300));
    resultActions.andExpect(jsonPath("$.content[0].imgUrl").value("https://en.wikipedia.org/wiki/The_Fellowship_of_the_Ring#/media/File:The_Fellowship_of_the_Ring_cover.gif"));

  }

  @Test
  void insertShouldReturnProductDTOCreatedWhenAdminLogged() throws Exception {

    mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(productDTO);

    ResultActions resultActions = mockMvc
            .perform(post("/products")
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isCreated());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidName() throws Exception {
    productDTO.setName("AB");
    mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(productDTO);

    ResultActions resultActions = mockMvc
            .perform(post("/products")
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidDescription() throws Exception {
    productDTO.setDescription("AB");
    mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(productDTO);

    ResultActions resultActions = mockMvc
            .perform(post("/products")
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsNegative() throws Exception {
    productDTO.setPrice(-50.0);
    mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(productDTO);

    ResultActions resultActions = mockMvc
            .perform(post("/products")
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsZero() throws Exception {
    productDTO.setPrice(00.0);
    mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(productDTO);

    ResultActions resultActions = mockMvc
            .perform(post("/products")
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndProductHasNotCategory() throws Exception {
    productDTO.getCategories().clear();
    mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(productDTO);

    ResultActions resultActions = mockMvc
            .perform(post("/products")
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isUnprocessableEntity());
  }

  @Test
  void insertShouldReturnForbiddenWhenClientLogged() throws Exception {

    mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(productDTO);

    ResultActions resultActions = mockMvc
            .perform(post("/products")
                    .header("Authorization", "Bearer " + clientToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isForbidden());
  }

  @Test
  void insertShouldReturnForbiddenWhenClientOrAdminNotLogged() throws Exception {
    clientToken = null;
    mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(productDTO);

    ResultActions resultActions = mockMvc
            .perform(post("/products")
                    .header("Authorization", "Bearer " + invalidToken)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(status().isUnauthorized());
  }

  @Test
  void deleteProductShouldReturnNotContentWhenAdminNotLogged() throws Exception {

    ResultActions resultActions = mockMvc
            .perform(delete("/products/{id}", existingProductId)
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print());
    resultActions.andExpect(status().isNoContent());
  }

  @Test
  void deleteProductShouldReturnNotFoundWhenProductNotExistsAndAdminNotLogged() throws Exception {

    ResultActions resultActions = mockMvc
            .perform(delete("/products/{id}", nonExistingProductId)
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print());
    resultActions.andExpect(status().isNotFound());
  }

  @Test
  @Transactional(propagation = Propagation.SUPPORTS)
  void deleteProductShouldReturnBadRequestWhenProductDependentExistsAndAdminLogged() throws Exception {

    ResultActions resultActions = mockMvc
            .perform(delete("/products/{id}", dependentProductId)
                    .header("Authorization", "Bearer " + adminToken)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print());
    resultActions.andExpect(status().isConflict());
  }

  @Test
  void deleteProductShouldReturnForbiddenWhenClientLogged() throws Exception {

    ResultActions resultActions = mockMvc
            .perform(delete("/products/{id}", existingProductId)
                    .header("Authorization", "Bearer " + clientToken)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print());
    resultActions.andExpect(status().isForbidden());
  }

  @Test
  void deleteProductShouldReturnUnauthorizedWhenAdminOrClientNotLogged() throws Exception {

    ResultActions resultActions = mockMvc
            .perform(delete("/products/{id}", existingProductId)
                    .header("Authorization", "Bearer " + invalidToken)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print());
    resultActions.andExpect(status().isUnauthorized());
  }

}
