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
public class RegisterInvitationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order (1)
    public void invitationBadUUIDFormat() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "anotheremail@mail.com")
            .param("password","1234asdfAS$")
            .param("passwordConfirmation", "1234asdfAS$")
            .param("invitation", "dd42630f-ZXCV-473d-asd")
        )
        .andExpect(status().isOk())
        .andExpect(model().hasErrors())
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "newUser", "invitation", "InvalidInvitation"
                )
        );
    }
    @Test
    @Order (2)
    public void invitationExpired() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "anotheremail@mail.com")
            .param("password","1234asdfAS$")
            .param("passwordConfirmation", "1234asdfAS$")
            .param("invitation", "ad42630f-0b7f-473d-8c8d-8b2dabb8c0d4")
        )
        .andExpect(status().isOk())
        .andExpect(model().hasErrors())
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "newUser", "invitation", "InvalidInvitation"
                )
        );
    }

    @Test
    @Order (3)
    public void registerNewUserWithInvitation() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "anotheremail@mail.com")
            .param("password","1234asdfAS$")
            .param("passwordConfirmation", "1234asdfAS$")
            .param("invitation", "dd42630f-0b7f-473d-8c8d-8b2dabb8c0d4")
        )
        .andExpect(status().isOk());
    }

    @Test
    @Order (4)
    public void invitationUsed() throws Exception {
        mockMvc.perform(
            post("/auth/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .param("username", "anotheremail@mail.com")
            .param("password","1234asdfAS$")
            .param("passwordConfirmation", "1234asdfAS$")
            .param("invitation", "dd42630f-0b7f-473d-8c8d-8b2dabb8c0d4")
        )
        .andExpect(status().isOk())
        .andExpect(model().hasErrors())
        .andExpect(
            model()
                .attributeHasFieldErrorCode(
                    "newUser", "invitation", "InvalidInvitation"
                )
        );
    }

}
