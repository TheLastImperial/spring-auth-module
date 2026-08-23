package com.thelastimperial.auth.auth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class RegisterTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void registerInvalidEmail() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "test@test")
            .param("password","1234asdfAS$")
            .param("passwordConfirmation", "1234asdfAS$")
        )
        .andExpect(status().isOk())
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("newUser", "username"));
    }

    @Test
    @Order(2)
    public void registerInvalidPasswordPattern() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "test@test.com")
            .param("password","123412341")
            .param("passwordConfirmation", "123412341")
        )
        .andExpect(status().isOk())
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("newUser", "password"));
    }

    @Test
    @Order(3)
    public void registerInvalidPasswordMatch() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "test@test.com")
            .param("password","asdfA1.asdf")
            .param("passwordConfirmation", "1234")
        )
        .andExpect(status().isOk())
        .andExpect(model().hasErrors())
        .andExpect(
            model()
                .attributeHasFieldErrors("newUser", "passwordConfirmation")
        );
    }
    @Test
    @Order(4)
    public void registerNewUser() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "tes@test.com")
            .param("password","1234asdfAS$")
            .param("passwordConfirmation", "1234asdfAS$")
        )
        .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void registerUserExists() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "tes@test.com")
            .param("password","1234asdfAS$")
            .param("passwordConfirmation", "1234asdfAS$")
        )
        .andExpect(status().isOk())
        .andExpect(model().hasErrors())
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "newUser", "username", "UsernameExists"
                )
        );
    }
}
