package com.github.hotire.spring.secuirty.basic.properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityPropertiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void user() throws Exception {
        mockMvc.perform(get("/v1/user")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMzQ1Njc4OTAiLCJyb2xlIjpbIlVTRVIiXSwiZGF0ZSI6MTUxNjIzOTAyMn0.Xb0br2KNPHk_lpSxFG_aITdtJwNtYX2jWK-VjbDPFy0VnzVO-1mNijWMv-dNGOcINZLXj7mCvWmd_bRsAO1p8i-xdpM0NAHmZTm0e6Y87Oq5DyZcEBwUYC240fug1U7ssaYSuiM3UDbtk9TF2hdyEt2tH0o4jxhW_V2nF7lvr_ivNHy0vjNjY6tCSBPa0DmX9qnBUwRmZUORfQd8EJ-IPy3FQbOAYj0rU9dvG8S3Z_VH-LNbbUq9PdNp46RZG1uG43B1wKtCk-1xzniYXz61IgB1jLEdRY4_DJ_87g3IHVp8NqrnFZcE56IbLEDfW5ETlSqkXM2_Nk6yrbyq0ngqdg"))
               .andExpect(status().isOk())
               .andDo(print());
    }

    @Test
    void admin() throws Exception {
        mockMvc.perform(post("/v1/admin")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMzQ1Njc4OTAiLCJyb2xlIjpbIlVTRVIiXSwiZGF0ZSI6MTUxNjIzOTAyMn0.Xb0br2KNPHk_lpSxFG_aITdtJwNtYX2jWK-VjbDPFy0VnzVO-1mNijWMv-dNGOcINZLXj7mCvWmd_bRsAO1p8i-xdpM0NAHmZTm0e6Y87Oq5DyZcEBwUYC240fug1U7ssaYSuiM3UDbtk9TF2hdyEt2tH0o4jxhW_V2nF7lvr_ivNHy0vjNjY6tCSBPa0DmX9qnBUwRmZUORfQd8EJ-IPy3FQbOAYj0rU9dvG8S3Z_VH-LNbbUq9PdNp46RZG1uG43B1wKtCk-1xzniYXz61IgB1jLEdRY4_DJ_87g3IHVp8NqrnFZcE56IbLEDfW5ETlSqkXM2_Nk6yrbyq0ngqdg"))
               .andExpect(status().isOk())
               .andDo(print());
    }

    @Test
    void isUnauthorized() throws Exception {
        mockMvc.perform(get("/v1/user")).
                andExpect(status().isUnauthorized())
               .andDo(print());
    }
}