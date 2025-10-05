package com.pio.raonback.dto.external.request;

import com.pio.raonback.dto.object.ProductImageListItem;
import com.pio.raonback.entity.ProductImage;
import lombok.Getter;

import java.util.List;

@Getter
public class AnalyzeImagesExternalRequestDto {

  private List<ProductImageListItem> images;

  public AnalyzeImagesExternalRequestDto(List<ProductImage> productImages) {
    this.images = ProductImageListItem.fromProductImages(productImages);
  }

}
