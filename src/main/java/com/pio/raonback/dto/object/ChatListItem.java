package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Chat;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatListItem {

  private Long chatId;
  private ProductData product;
  private UserData buyer;
  private UserData seller;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime lastMessageAt;

  @Getter
  private static class ProductData {

    private Long productId;
    private String thumbnail;
    private String title;
    private Boolean isSold;
    private Boolean isActive;
    private Boolean isDeleted;

    private ProductData(Product product) {
      this.productId = product.getProductId();
      this.thumbnail = product.getImages().get(0).getImageUrl();
      this.title = product.getTitle();
      this.isSold = product.getIsSold();
      this.isActive = product.getIsActive();
      this.isDeleted = product.getIsDeleted();
    }

  }

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

  public ChatListItem(Chat chat) {
    this.chatId = chat.getChatId();
    this.product = new ProductData(chat.getProduct());
    this.buyer = new UserData(chat.getBuyer());
    this.seller = new UserData(chat.getSeller());
    this.createdAt = chat.getCreatedAt();
    this.lastMessageAt = chat.getLastMessageAt();
  }

  public static List<ChatListItem> copyList(Page<Chat> chatPage) {
    List<ChatListItem> list = new ArrayList<>();
    for (Chat chat : chatPage.getContent()) {
      ChatListItem chatListItem = new ChatListItem(chat);
      list.add(chatListItem);
    }
    return list;
  }

}
