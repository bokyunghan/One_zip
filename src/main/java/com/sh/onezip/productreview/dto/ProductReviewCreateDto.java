package com.sh.onezip.productreview.dto;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductReviewCreateDto {
    private Long id;
    private Member member;
    private Product product;
    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String reviewContent;
    private LocalDate reviewRegdate;
    @Max(value=5, message = "별점의 최댓값은 5입니다.")
    @Min(value=1, message = "별점의 최솟값은 1입니다.")
    private int starPoint;
}
