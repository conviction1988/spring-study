package com.spring.study.domain.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.study.domain.auth.dto.SignInRequest;
import com.spring.study.domain.auth.dto.SignInRequestBuilder;
import com.spring.study.domain.user.dto.SignUpRequest;
import com.spring.study.domain.user.dto.SignUpRequestBuilder;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@Transactional
@Rollback
class AuthIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

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
    @DisplayName("회원가입 테스트")
    void signUpTest() throws Exception {
        //given
        final String email = "leejungyi88@gmail.com";
        final String password = "P@ssw0rd";
        final String firstName = "lee";
        final String lastName = "jungyi";
        final SignUpRequest request = SignUpRequestBuilder.build(email, password, firstName, lastName);

        //when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andDo(
                        document("auth-sign-up-post", preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").description("email"),
                                    fieldWithPath("password").description("password"),
                                    fieldWithPath("firstName").description("firstName"),
                                    fieldWithPath("lastName").description("lastName")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("0"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("SUCCESS."),

                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("UserResponse"),

                                        fieldWithPath("data.email").type(JsonFieldType.OBJECT).description("Email"),
                                        fieldWithPath("data.email.value").type(JsonFieldType.STRING).description("email 주소"),
                                        fieldWithPath("data.email.id").type(JsonFieldType.STRING).description("email 계정"),
                                        fieldWithPath("data.email.host").type(JsonFieldType.STRING).description("email host"),

                                        fieldWithPath("data.name").type(JsonFieldType.OBJECT).description("Name"),
                                        fieldWithPath("data.name.first").type(JsonFieldType.STRING).description("first"),
                                        fieldWithPath("data.name.last").type(JsonFieldType.STRING).description("last"),
                                        fieldWithPath("data.name.fullName").type(JsonFieldType.STRING).description("fullName")
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
                .andExpect(jsonPath("$.data.email.value").value(email))
                .andExpect(jsonPath("$.data.name.first").value(firstName))
                .andExpect(jsonPath("$.data.name.last").value(lastName));

    }

    @Test
    @Order(2)
    @DisplayName("로그인 테스트")
    void signInTest() throws Exception {
        //given
        final String email = "leescott@gmail.com";
        final String password = "P@ssw0rd";
        final SignInRequest request = SignInRequestBuilder.build(email, password);

        //when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andDo(
                        document("auth-sign-in-post", preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").description("email"),
                                        fieldWithPath("password").description("password")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("0"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("SUCCESS."),

                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("TokenResponse"),

                                        fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("accessToken"),
                                        fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("refreshToken")
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
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());

    }

    @Test
    @WithUserDetails(value = "leescott@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Order(3)
    @DisplayName("로그아웃 테스트")
    void signOutTest() throws Exception {
        //when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/sign-out")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(
                        document("auth-sign-out-post", preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("0"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("SUCCESS.")
                                )
                        )
                );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").exists());
    }
}