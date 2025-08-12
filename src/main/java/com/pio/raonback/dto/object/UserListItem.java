package com.pio.raonback.dto.object;

import com.pio.raonback.entity.Chat;
import com.pio.raonback.entity.User;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class UserListItem {

  private Long userId;
  private String nickname;
  private String profileImage;

  private UserListItem(User user) {
    this.userId = user.getUserId();
    this.nickname = user.getNickname();
    this.profileImage = user.getProfileImage();
  }

  public static List<UserListItem> fromChatPage(Page<Chat> chatPage) {
    List<UserListItem> list = new java.util.ArrayList<>();
    for (Chat chat : chatPage.getContent()) {
      UserListItem userListItem = new UserListItem(chat.getBuyer());
      list.add(userListItem);
    }
    return list;
  }

}
