package com.sh.onezip.product.dto;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.entity.Attachment;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Data
public class ProductListDto {
    private Member member;
    private Long id;
    private String productName;
    // 사업자 전용 때 필요함 productPrice, discountRate
    private String productPrice;
    private double discountRate;
    private ProductType productTypecode;
    private String memberName;
    // 할인적용 후 최종 가격(리스트 노출 가격)
    private int applyPrice;
    // 사업자 등록 상품 리스트에 쓰려고 추가로 넣어놨음
    private int originalPrice;
    private LocalDate regDate;
    List<Attachment> attachmentList = new ArrayList<>();

    // 사업자 상품 수정 용
    private List<AttachmentCreateDto> attachments = new ArrayList<>(); // 사업자 상품 수정 용

    public void addAttachmentCreateDto(AttachmentCreateDto attachmentCreateDto) {
        this.attachments.add(attachmentCreateDto);
    }
}
