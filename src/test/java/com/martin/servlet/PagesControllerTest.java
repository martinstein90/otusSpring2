package com.martin.servlet;


import com.martin.security.AuthenticationManagerImpl;
import com.martin.security.SecurityConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PagesController.class)
@Import(SecurityConfiguration.class)
public class PagesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManagerImpl authenticationManagerImpl;

    @WithAnonymousUser
    @Test
    public void testPublic() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void testRedirectUnauthorized() throws Exception {
        mockMvc.perform(get("/authenticated")).andExpect(status().is3xxRedirection());
    }

    @WithMockUser
    @Test
    public void testAccessAuthorized() throws Exception {
        mockMvc.perform(get("/authenticated")).andExpect(status().isOk());
    }
}
