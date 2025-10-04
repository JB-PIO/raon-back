package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Message;
import com.pio.raonback.entity.User;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MessageListItem {

  private Long messageId;
  private Long chatId;
  private UserData sender;
  private String content;
  private String imageUrl;
  private Boolean isRead;
  private Boolean isDeleted;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ZonedDateTime sentAt;

  @Getter
  private static class UserData {

    private Long userId;
    private String nickname;
    private String profileImage;
    private Boolean isDeleted;

    private UserData(User user) {
      this.userId = user.getUserId();
      this.nickname = user.getNickname();
      this.profileImage = user.getProfileImage();
      this.isDeleted = user.getIsDeleted();
    }

  }

  public MessageListItem(Message message) {
    this.messageId = message.getMessageId();
    this.chatId = message.getChat().getChatId();
    this.sender = new UserData(message.getSender());
    this.content = message.getContent();
    this.imageUrl = message.getImageUrl();
    this.isRead = message.getIsRead();
    this.isDeleted = message.getIsDeleted();
    this.sentAt = message.getSentAt();
  }

  public static List<MessageListItem> fromMessagePage(Page<Message> messagePage) {
    List<MessageListItem> list = new ArrayList<>();
    for (Message message : messagePage.getContent()) {
      MessageListItem messageListItem = new MessageListItem(message);
      list.add(messageListItem);
    }
    return list;
  }

}
