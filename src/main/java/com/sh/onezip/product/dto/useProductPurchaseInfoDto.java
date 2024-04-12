package com.sh.onezip.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UseProductPurchaseInfoDto {

    @PositiveOrZero
    Long productId;
    @PositiveOrZero
    int productQuantity;
    @NotBlank
    String selectOption;

}
