package spring.angular.social.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import spring.angular.social.dto.ChatDto;
import spring.angular.social.dto.ChatMessageDto;
import spring.angular.social.entity.Chat;
import spring.angular.social.entity.ChatMessage;
import spring.angular.social.entity.User;
import spring.angular.social.mappers.ChatMapper;
import spring.angular.social.mappers.ChatMessageMapper;
import spring.angular.social.service.ChatService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @MockBean
    private ChatMapper mapper;

    @MockBean
    private ChatMessageMapper messageMapper;

    public User getUser() {
        User user = new User(
                1L, "surya@gmail.com", "Surya", "password");
        return user;
    }

    User friend = new User(
            2L, "raj@gmail.com", "Raj", "password");

    public ChatMessage getChatMessage() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(1L);
        chatMessage.setSender("John");
        chatMessage.setReceiver("Jack");
        chatMessage.setMessageContent("Hello World!");
        return chatMessage;
    }

    public ChatMessageDto getChatMessageDto() {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setId(1L);
        chatMessageDto.setSender("John");
        chatMessageDto.setReceiver("Jack");
        chatMessageDto.setMessageContent("Hello World!");
        return chatMessageDto;
    }

    @Test
    public void testCreateChat() throws Exception {
        Chat chat = new Chat();
        chat.setFriend(friend);
        chat.setUser(getUser());

        ChatDto chatDto = new ChatDto();
        chatDto.setUser(getUser());
        chatDto.setFriend(friend);
        ObjectMapper objectMapper = new ObjectMapper();
        String chatDtoJson = objectMapper.writeValueAsString(chatDto);

        Mockito.when(chatService.createChat(any(Chat.class))).thenReturn(chat);
        Mockito.when(mapper.toEntity(any(ChatDto.class))).thenReturn(chat);
        Mockito.when(mapper.toDto(any(Chat.class))).thenReturn(chatDto);


        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/api/chats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(chatDtoJson);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.user.username", is("Surya")))
                .andExpect(jsonPath("$.friend.username", is("Raj")));

    }

    @Test
    public void testGetChatMessages() throws Exception {
        Long chatId = 1L;
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(getChatMessage());
        when(chatService.getChatMessages(chatId)).thenReturn(messages);

        List<ChatMessageDto> messageDtos = new ArrayList<>();
        messageDtos.add(getChatMessageDto());
        when(messageMapper.toDto(messages)).thenReturn(messageDtos);

        ObjectMapper objectMapper = new ObjectMapper();
        String messagesDtoJson = objectMapper.writeValueAsString(messageDtos);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/api/chats/{chatId}/messages", chatId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(messagesDtoJson);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].messageContent", is("Hello World!")))
                .andExpect(jsonPath("$[0].sender", is("John")))
                .andExpect(jsonPath("$[0].receiver", is("Jack")));

    }

    @Test
    public void testSendChatMessage() throws Exception {
        Long chatId = 1L;

        when(chatService.sendChatMessage(eq(chatId), any(ChatMessage.class))).thenReturn(getChatMessage());
        when(messageMapper.toDto(any(ChatMessage.class))).thenReturn(getChatMessageDto());
        when(messageMapper.toEntity(any(ChatMessageDto.class))).thenReturn(getChatMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(getChatMessageDto());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/api/chats/{chatId}/messages", chatId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonString);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.messageContent", is("Hello World!")));

    }

    @Test
    public void testDeleteChatMessage() throws Exception {
        Long chatId = 1L;
        Long messageId = 2L;
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.delete("/api/chats/" + chatId + "/messages/" + messageId);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Chat message deleted successfully")));
        verify(chatService).deleteChatMessage(chatId, messageId);

    }
}



