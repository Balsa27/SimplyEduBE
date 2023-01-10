//package com.simplyedu.Chat.utils;
//
//import com.simplyedu.Chat.entities.request.MessageRequest;
//import com.simplyedu.Chat.entities.response.MessageResponse;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@NoArgsConstructor
//public class MessageMapper {
//    public Message requestToMessage(MessageRequest request) {
//        return request == null ? null : Message.builder()
//                .content(request.getContent())
//                .sendDate(LocalDate.now())
//                .senderName(request.getSenderName())
//                .type(request.getType())
//                .build();
//    }
//    
//    public MessageResponse messageToResponse(Message message) {
//        return message == null ? null : MessageResponse.builder()
//                .content(message.getContent())
//                .sendDate(message.getSendDate())
//                .senderName(message.getSenderName())
//                .type(message.getType())
//                .build();
//    }
//}
