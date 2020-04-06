package com.github.hotire.spring.secuirty.basic.account;

import com.github.hotire.spring.secuirty.basic.jwt.JwtSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(JwtSecurityConfig.class)
@WebMvcTest(JwtController.class)
class JwtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/jwt")
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMzQ1Njc4OTAiLCJyb2xlIjpbIlVTRVIiXSwiZGF0ZSI6MTUxNjIzOTAyMn0.Xb0br2KNPHk_lpSxFG_aITdtJwNtYX2jWK-VjbDPFy0VnzVO-1mNijWMv-dNGOcINZLXj7mCvWmd_bRsAO1p8i-xdpM0NAHmZTm0e6Y87Oq5DyZcEBwUYC240fug1U7ssaYSuiM3UDbtk9TF2hdyEt2tH0o4jxhW_V2nF7lvr_ivNHy0vjNjY6tCSBPa0DmX9qnBUwRmZUORfQd8EJ-IPy3FQbOAYj0rU9dvG8S3Z_VH-LNbbUq9PdNp46RZG1uG43B1wKtCk-1xzniYXz61IgB1jLEdRY4_DJ_87g3IHVp8NqrnFZcE56IbLEDfW5ETlSqkXM2_Nk6yrbyq0ngqdg"))
               .andExpect(status().isOk())
               .andDo(print());
    }
}