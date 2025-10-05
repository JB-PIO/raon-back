package com.pio.raonback.dto.object;

import com.pio.raonback.entity.ProductImage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductImageListItem {

  private Long imageId;
  private String imageUrl;

  public ProductImageListItem(ProductImage productImage) {
    this.imageId = productImage.getImageId();
    this.imageUrl = productImage.getImageUrl();
  }

  public static List<ProductImageListItem> fromProductImages(List<ProductImage> productImages) {
    List<ProductImageListItem> list = new ArrayList<>();
    for (ProductImage productImage : productImages) {
      ProductImageListItem productImageListItem = new ProductImageListItem(productImage);
      list.add(productImageListItem);
    }
    return list;
  }

}
