package com.spring.study.domain.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.study.domain.product.domain.Product;
import com.spring.study.domain.product.dto.ProductSetup;
import com.spring.study.domain.user.dto.UserSetup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@Transactional
@Rollback
class ProductIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private ProductSetup productSetup;

    @Autowired
    private UserSetup userSetup;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .build();
        userSetup.save();
    }

    @Test
    @Order(1)
    @DisplayName("상품 조회 테스트")
    void getProductTest() throws Exception {
        //given
        final Product product = productSetup.save();

        //when
        final ResultActions resultActions =
                mockMvc.perform(get("/api/v1/products/{id}", product.getId())
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andDo(
                                document("products-get", preprocessRequest(prettyPrint()),
                                        preprocessResponse(prettyPrint()),
                                        pathParameters(
                                                parameterWithName("id").description("상품 아이디")
                                        ),
                                        responseFields(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success"),
                                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("0"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("SUCCESS."),

                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("ProductResponse"),

                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                                fieldWithPath("data.currency").type(JsonFieldType.STRING).description("currency"),
                                                fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("price")
                                        )
                                )
                        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.price").exists());

    }

    @Test
    @WithUserDetails(value = "leescott@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Order(2)
    @DisplayName("상품 주문 테스트")
    void productOrderTest() throws Exception {
        //given
        final Product product = productSetup.save();

        //when
        final ResultActions resultActions = mockMvc.perform(
                post("/api/v1/products/{id}/orders", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(
                        document("products-orders-post", preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("id").description("상품 ID")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("0"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("SUCCESS."),

                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("ProductResponse"),

                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("주문 ID"),

                                        fieldWithPath("data.user").type(JsonFieldType.OBJECT).description("UserResponse"),
                                        fieldWithPath("data.user.email").type(JsonFieldType.OBJECT).description("Email"),
                                        fieldWithPath("data.user.email.value").type(JsonFieldType.STRING).description("email 주소"),
                                        fieldWithPath("data.user.email.id").type(JsonFieldType.STRING).description("email 계정"),
                                        fieldWithPath("data.user.email.host").type(JsonFieldType.STRING).description("email host"),
                                        fieldWithPath("data.user.name").type(JsonFieldType.OBJECT).description("Name"),
                                        fieldWithPath("data.user.name.first").type(JsonFieldType.STRING).description("first"),
                                        fieldWithPath("data.user.name.last").type(JsonFieldType.STRING).description("last"),
                                        fieldWithPath("data.user.name.fullName").type(JsonFieldType.STRING).description("fullName"),

                                        fieldWithPath("data.product").type(JsonFieldType.OBJECT).description("ProductResponse"),
                                        fieldWithPath("data.product.name").type(JsonFieldType.STRING).description("name"),
                                        fieldWithPath("data.product.currency").type(JsonFieldType.STRING).description("currency"),
                                        fieldWithPath("data.product.price").type(JsonFieldType.NUMBER).description("price")
                                )
                        )
                );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.user").exists())
                .andExpect(jsonPath("$.data.product").exists());
    }
}