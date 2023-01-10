//package com.simplyedu.Chat.service.impl;
//
//import com.simplyedu.Chat.utils.MessageMapper;
//import com.simplyedu.Chat.entities.response.MessageResponse;
//import com.simplyedu.Chat.service.ChatService;
//import com.simplyedu.Chat.entities.response.GetAllResponse;
//import com.simplyedu.Chat.repository.ChatRepository;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//@Service("chatService")
//public class ChatServiceImpl implements ChatService {
//    
//    MessageMapper mapper = new MessageMapper();
//    private final ChatRepository chatRepository;
//
//    public ChatServiceImpl(@Qualifier("chatRepository") ChatRepository chatRepository) {
//        this.chatRepository = chatRepository;
//    }
//
//    @Override
//    public GetAllResponse getAll(int page, int size) {
//        return new GetAllResponse(chatRepository.findAll(PageRequest.of(page, size)));
//    }
//
//    @Override
//    public MessageResponse save(Message message) {
//        chatRepository.save(message);
//        return mapper.messageToResponse(message);
//    }
//}
//    
