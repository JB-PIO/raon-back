package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pio.raonback.entity.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryListItem {

  private Long categoryId;
  private Long level;
  private String name;
  private Boolean isLeaf;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<CategoryListItem> children;

  public CategoryListItem(Category category) {
    this.categoryId = category.getCategoryId();
    this.level = category.getLevel();
    this.name = category.getName();
    this.isLeaf = category.getIsLeaf();
    this.children = CategoryListItem.fromParentCategories(category.getChildren());
  }

  public static List<CategoryListItem> fromParentCategories(List<Category> parentCategories) {
    List<CategoryListItem> list = new ArrayList<>();
    for (Category parentCategory : parentCategories) {
      CategoryListItem categoryListItem = new CategoryListItem(parentCategory);
      list.add(categoryListItem);
    }
    return list;
  }

}
