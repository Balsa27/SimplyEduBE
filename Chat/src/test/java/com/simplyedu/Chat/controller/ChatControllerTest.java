//package com.simplyedu.Chat.controller;
//
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.anyInt;
//import static org.mockito.Mockito.atLeast;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.simplyedu.Chat.entities.MessageType;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@ContextConfiguration(classes = {ChatController.class})
//@ExtendWith(SpringExtension.class)
//class ChatControllerTest {
//    @Autowired
//    private ChatController chatController;
//
//    @MockBean(name = "chatService")
//    private ChatService chatService;
//
//
//    /**
//     * Method under test: {@link ChatController#getAll(int, int)}
//     */
//    @Test
//    void testGetAll_pass() throws Exception {
//        //Act
//        when(chatService.getAll(anyInt(), anyInt())).thenReturn(new GetAllResponse());
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/chat/getAll/{page}/{size}", 1, 3);
//
//        //Assert
//        MockMvcBuilders.standaloneSetup(chatController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("{\"allMessages\":null}"));
//    }
//
//    /**
//     * Method under test: {@link ChatController#getAll(int, int)}
//     */
//    @Test
//    void testGetAll2_pass2() throws Exception {
//        //Arrange
//        Message message = new Message();
//        message.setContent("Not all who wander are lost");
//        message.setId(123L);
//        message.setSendDate(LocalDate.ofEpochDay(1L));
//        message.setSenderName("Sender Name");
//        message.setType(MessageType.CHAT);
//        ArrayList<Message> messageList = new ArrayList<>();
//        messageList.add(message);
//        GetAllResponse getAllResponse = new GetAllResponse(new PageImpl<>(messageList));
//
//        //Act
//        when(chatService.getAll(anyInt(), anyInt())).thenReturn(getAllResponse);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/chat/getAll/{page}/{size}", 1, 3);
//        MockMvcBuilders.standaloneSetup(chatController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
//    }
//
//    /**
//     * Method under test: {@link ChatController#getAll(int, int)}
//     */
//    @Test
//    void testGetAll_pass3() throws Exception {
//        //Act
//        when(chatService.getAll(anyInt(), anyInt())).thenReturn(new GetAllResponse());
//        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/chat/getAll/{page}/{size}", 1, 3);
//        getResult.characterEncoding("Encoding");
//
//        //Assert
//        MockMvcBuilders.standaloneSetup(chatController)
//                .build()
//                .perform(getResult)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("{\"allMessages\":null}"));
//    }
//
//
//    /**
//     * Method under test: {@link ChatController#register(MessageRequest, SimpMessageHeaderAccessor)}
//     */
//    @Test
//    void testRegister_pass() {
//
//        //Arrange
//        MessageResponse messageResponse = new MessageResponse();
//        MessageRequest messageRequest = mock(MessageRequest.class);
//
//        //Act
//        when(chatService.save(any())).thenReturn(messageResponse);
//        when(messageRequest.getType()).thenReturn(MessageType.CHAT);
//        when(messageRequest.getContent()).thenReturn("Not all who wander are lost");
//        when(messageRequest.getSenderName()).thenReturn("Sender Name");
//        SimpMessageHeaderAccessor simpMessageHeaderAccessor = mock(SimpMessageHeaderAccessor.class);
//        when(simpMessageHeaderAccessor.getSessionAttributes()).thenReturn(new HashMap<>());
//
//        //Assert
//        assertSame(messageResponse, chatController.register(messageRequest, simpMessageHeaderAccessor));
//        verify(chatService).save(any());
//        verify(messageRequest).getType();
//        verify(messageRequest).getContent();
//        verify(messageRequest, atLeast(1)).getSenderName();
//        verify(simpMessageHeaderAccessor).getSessionAttributes();
//    }
//
//    /**
//     * Method under test: {@link ChatController#send(MessageRequest)}
//     */
//    @Test
//    void testSend() {
//        MessageResponse messageResponse = new MessageResponse();
//        when(chatService.save((Message) any())).thenReturn(messageResponse);
//        assertSame(messageResponse,
//                chatController.send(new MessageRequest(MessageType.CHAT, "Not all who wander are lost", "Sender Name")));
//        verify(chatService).save((Message) any());
//    }
//
//    /**
//     * Method under test: {@link ChatController#send(MessageRequest)}
//     */
//    @Test
//    void testSend2() {
//        MessageResponse messageResponse = new MessageResponse();
//        when(chatService.save((Message) any())).thenReturn(messageResponse);
//        assertSame(messageResponse, chatController.send(null));
//        verify(chatService).save((Message) any());
//    }
//
//    /**
//     * Method under test: {@link ChatController#send(MessageRequest)}
//     */
//    @Test
//    void testSend_pass() {
//        //Arrange
//        MessageResponse messageResponse = new MessageResponse();
//
//        //Act
//        when(chatService.save(any())).thenReturn(messageResponse);
//
//        //Assert
//        assertSame(messageResponse,
//                chatController.send(new MessageRequest(MessageType.CHAT, "Not all who wander are lost", "Sender Name")));
//        verify(chatService).save(any());
//    }
//}
//
