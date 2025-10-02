package com.pio.raonback.dto.external.request;

import com.pio.raonback.entity.ProductImage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AnalyzeImagesExternalRequestDto {

  private List<ImageListItem> images;

  @Getter
  private static class ImageListItem {

    private Long imageId;
    private String imageUrl;

    private ImageListItem(ProductImage productImage) {
      this.imageId = productImage.getImageId();
      this.imageUrl = productImage.getImageUrl();
    }

    private static List<ImageListItem> fromProductImages(List<ProductImage> productImages) {
      List<ImageListItem> list = new ArrayList<>();
      for (ProductImage productImage : productImages) {
        ImageListItem imageListItem = new ImageListItem(productImage);
        list.add(imageListItem);
      }
      return list;
    }

  }

  public AnalyzeImagesExternalRequestDto(List<ProductImage> productImages) {
    this.images = ImageListItem.fromProductImages(productImages);
  }

}
