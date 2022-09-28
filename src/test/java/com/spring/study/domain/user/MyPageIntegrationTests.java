package com.spring.study.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.study.domain.order.dto.OrderSetup;
import com.spring.study.domain.product.domain.Product;
import com.spring.study.domain.product.dto.ProductSetup;
import com.spring.study.domain.user.domain.User;
import com.spring.study.domain.user.dto.UserSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@Transactional
@Rollback
class MyPageIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private UserSetup userSetup;

    @Autowired
    private ProductSetup productSetup;

    @Autowired
    private OrderSetup orderSetup;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .build();

        User user = userSetup.save();
        Product product = productSetup.save();
        orderSetup.save(user, product);
    }

    @Test
    @WithUserDetails(value = "leescott@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("회원 주문 내역 테스트")
    void getUserOrders() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("lang", "en");
        params.add("page", "1");
        params.add("pageSize", "10");

        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/my-page/orders")
                        .params(params)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(
                        document("my-page-orders", preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("0"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("SUCCESS."),

                                        fieldWithPath("page").type(JsonFieldType.OBJECT).description("페이지네이션 정보 객체"),
                                        fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("페이지"),
                                        fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("페이징 사이즈"),
                                        fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지"),
                                        fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("전체 데이타 숫자"),
                                        fieldWithPath("page.numberOfElements").type(JsonFieldType.NUMBER).description("전체 페이지 숫자"),
                                        fieldWithPath("page.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                        fieldWithPath("page.first").type(JsonFieldType.BOOLEAN).description("첫 번째 페이지 여부"),
                                        fieldWithPath("page.empty").type(JsonFieldType.BOOLEAN).description("공백 여부"),

                                        fieldWithPath("page.sort").type(JsonFieldType.OBJECT).description("소팅관련 정보"),
                                        fieldWithPath("page.sort.sorted").type(JsonFieldType.BOOLEAN).description("").ignored(),
                                        fieldWithPath("page.sort.unsorted").type(JsonFieldType.BOOLEAN).description("").ignored(),
                                        fieldWithPath("page.sort.empty").type(JsonFieldType.BOOLEAN).description("").ignored(),

                                        fieldWithPath("page.pageable").type(JsonFieldType.OBJECT).description("페이징 정보 객체"),
                                        fieldWithPath("page.pageable.paged").type(JsonFieldType.BOOLEAN).description("").ignored(),
                                        fieldWithPath("page.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("").ignored(),
                                        fieldWithPath("page.pageable.pageNumber").type(JsonFieldType.NUMBER).description("").ignored(),
                                        fieldWithPath("page.pageable.pageSize").type(JsonFieldType.NUMBER).description("").ignored(),
                                        fieldWithPath("page.pageable.offset").type(JsonFieldType.NUMBER).description("").ignored(),
                                        fieldWithPath("page.pageable.sort").type(JsonFieldType.OBJECT).description("").ignored(),
                                        fieldWithPath("page.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("").ignored(),
                                        fieldWithPath("page.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("").ignored(),
                                        fieldWithPath("page.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("").ignored(),

                                        fieldWithPath("page.content").type(JsonFieldType.ARRAY).description("OrderResponse Array"),
                                        fieldWithPath("page.content.[0].id").type(JsonFieldType.NUMBER).description("주문 ID"),

                                        fieldWithPath("page.content.[0].user").type(JsonFieldType.OBJECT).description("UserResponse"),
                                        fieldWithPath("page.content.[0].user.email").type(JsonFieldType.OBJECT).description("Email"),
                                        fieldWithPath("page.content.[0].user.email.value").type(JsonFieldType.STRING).description("email 주소"),
                                        fieldWithPath("page.content.[0].user.email.id").type(JsonFieldType.STRING).description("email 계정"),
                                        fieldWithPath("page.content.[0].user.email.host").type(JsonFieldType.STRING).description("email host"),
                                        fieldWithPath("page.content.[0].user.name").type(JsonFieldType.OBJECT).description("Name"),
                                        fieldWithPath("page.content.[0].user.name.first").type(JsonFieldType.STRING).description("first"),
                                        fieldWithPath("page.content.[0].user.name.last").type(JsonFieldType.STRING).description("last"),
                                        fieldWithPath("page.content.[0].user.name.fullName").type(JsonFieldType.STRING).description("fullName"),

                                        fieldWithPath("page.content.[0].product").type(JsonFieldType.OBJECT).description("ProductResponse"),
                                        fieldWithPath("page.content.[0].product.name").type(JsonFieldType.STRING).description("name"),
                                        fieldWithPath("page.content.[0].product.currency").type(JsonFieldType.STRING).description("currency"),
                                        fieldWithPath("page.content.[0].product.price").type(JsonFieldType.NUMBER).description("price")
                                )
                        )
                );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.page").exists())
                .andExpect(jsonPath("$.page.content").exists())
                .andExpect(jsonPath("$.page.content.[0].user").exists())
                .andExpect(jsonPath("$.page.content.[0].product").exists())
                .andExpect(jsonPath("$.page.content.[0].id").exists());

    }
}