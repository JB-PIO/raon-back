package com.pio.raonback.repository;

import com.pio.raonback.entity.Chat;
import com.pio.raonback.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

  Page<Message> findAllByIsDeletedFalseAndChatOrderBySentAtDesc(Chat chat, Pageable pageable);

}
