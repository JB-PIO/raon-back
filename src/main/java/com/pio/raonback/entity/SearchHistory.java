package com.pio.raonback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "search_history")
@Table(name = "search_history")
public class SearchHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "search_id", nullable = false)
  private Long searchId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "keyword", nullable = false, length = 100)
  private String keyword;

  @Column(name = "searched_at", nullable = false)
  private LocalDateTime searchedAt;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof SearchHistory)) return false;
    final SearchHistory searchHistory = (SearchHistory) obj;
    return searchHistory.getSearchId().equals(getSearchId());
  }

  @Override
  public int hashCode() {
    return getSearchId().hashCode();
  }

}
