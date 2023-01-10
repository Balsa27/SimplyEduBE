//package com.simplyedu.Chat.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.simplyedu.Chat.entities.MessageType;
//
//import java.time.LocalDate;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(classes = {ChatServiceImpl.class})
//@ExtendWith(SpringExtension.class)
//class ChatServiceImplTest {
//    @MockBean(name = "chatRepository")
//    private ChatRepository chatRepository;
//
//    @Autowired
//    private ChatServiceImpl chatServiceImpl;
//
//
//    @Test
//    void testGetAll() {
//        //Arrange
//        Page<Message> pageImpl = new PageImpl<>(new ArrayList<>());
//
//        //Act
//        when(chatRepository.findAll((Pageable) any())).thenReturn(pageImpl);
//
//        //Assert
//        assertNotNull(chatServiceImpl.getAll(1, 3));
//    }
//
//    @Test
//    void testSave() {
//        //Arrange
//        Message message = new Message();
//        message.setContent("Not all who wander are lost");
//        message.setId(123L);
//        message.setSendDate(LocalDate.ofEpochDay(1L));
//        message.setSenderName("Sender Name");
//        message.setType(MessageType.CHAT);
//
//        Message mess = new Message();
//        mess.setContent("Not all who wander are lost");
//        mess.setId(123L);
//        mess.setSendDate(LocalDate.ofEpochDay(1L));
//        mess.setSenderName("Sender Name");
//        mess.setType(MessageType.CHAT);
//
//        //Act
//        MessageResponse actualSaveResult = chatServiceImpl.save(mess);
//        when(chatRepository.save(any())).thenReturn(message);
//
//        //Assert
//        assertEquals("Not all who wander are lost", actualSaveResult.getContent());
//        assertEquals(MessageType.CHAT, actualSaveResult.getType());
//        assertEquals("Sender Name", actualSaveResult.getSenderName());
//        assertEquals("1970-01-02", actualSaveResult.getSendDate().toString());
//        verify(chatRepository).save((Message) any());
//    }
//
//}
//
