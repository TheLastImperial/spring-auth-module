package com.thelastimperial.auth.admin;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class LockUnLockUserTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void loginUnlockedUser() throws Exception {
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
    @Order(2)
    @WithMockUser(username = "019dffbe-d07e-7bfd-ab68-f566727ec561", roles = "ADMIN")
    public void lockUser() throws Exception {
        mockMvc.perform(
            get("/admin/users/lock-unlock/admin")
        )
        .andExpect(status().is3xxRedirection());
    }
    @Test
    @Order(3)
    public void loginAfterLockUser() throws Exception {
        mockMvc.perform(
            post("/auth/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "admin")
            .param("password","1234")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/auth/login?error=true"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "019dffbe-d07e-7bfd-ab68-f566727ec561", roles = "ADMIN")
    public void unlockUser() throws Exception {
        mockMvc.perform(
            get("/admin/users/lock-unlock/admin")
        )
        .andExpect(status().is3xxRedirection());
    }
    @Test
    @Order(5)
    public void loginAfterUnLockUser() throws Exception {
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
}
