package com.pio.raonback.repository;

import com.pio.raonback.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

  Page<MessageEntity> findAllByIsDeletedFalseAndChatIdOrderBySentAtDesc(Long chatId, Pageable pageable);

}
