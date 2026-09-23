package com.thelastimperial.auth.admin;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminNavigation {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginAdmin() throws Exception {
        mockMvc.perform(
            post("/auth/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "admin")
            .param("password","1234")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void showUser() throws Exception {
        mockMvc.perform(
            get("/admin/users/show/user")
        )
        .andExpect(status().isOk());
    }
}
