package com.sh.onezip.productreview.entity;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_review")
public class ProductReview {
    @Id
    @GeneratedValue(generator = "seq_review_generator")
    @SequenceGenerator(
            name = "seq_review_generator",
            sequenceName = "seq_tb_review",
            initialValue = 1,
            allocationSize = 1)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY) // 02-23 EAGER->LAZY
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "reviewContent")
    private String reviewContent;
    @Column(name = "review_regdate")
    private LocalDate reviewRegdate;
    @Column(name = "star_point")
    private int starPoint;

}
