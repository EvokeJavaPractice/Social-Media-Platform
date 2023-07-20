package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spring.angular.social.controller.ChatController;
import spring.angular.social.entity.Chat;
import spring.angular.social.entity.User;
import spring.angular.social.service.ChatService;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    User user = new User(
            1L, "surya@gmail.com", "Surya", "password");
    User friend = new User(
            2L, "raj@gmail.com", "Raj", "password");


    @Test
    public void testCreateChat() throws Exception {

        Chat chat = new Chat();
        chat.setFriend(friend);
        chat.setUser(user);
        Mockito.when(chatService.createChat(chat)).thenReturn(chat);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/chats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(chat.toString());


        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$friend.name", is("raj")))
                .andExpect(jsonPath("$user.name", is("Surya")));
    }


}
