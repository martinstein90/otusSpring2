package com.martin.servlet;


import com.martin.security.AuthenticationManagerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PagesController.class)
public class PagesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManagerImpl authenticationManagerImpl;


    //@WithAnonymousUser()
    @WithMockUser(username = "Martin", authorities = {"ADMIN"})
    @Test
    public void testPublic() throws Exception {
        mockMvc.perform(get("/public")).andExpect(status().isOk());
    }

}
