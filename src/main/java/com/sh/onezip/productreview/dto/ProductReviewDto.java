package com.sh.onezip.productreview.dto;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductReviewDto {
    private Long id;
    private Member member;
    private Long productId;
    private String reviewTitle;
    private String reviewContent;
    private LocalDate reviewRegdate;
    private int starPoint;
    private Product product;
}
