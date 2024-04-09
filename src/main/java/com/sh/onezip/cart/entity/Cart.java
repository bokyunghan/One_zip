package com.sh.onezip.cart.entity;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productreview.entity.ProductReview;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tb_cart")
public class Cart {
    // KMJ start

    @Id
    @GeneratedValue(generator = "seq_tb_poption_id_generator")
    @SequenceGenerator(
            name = "seq_tb_poption_id_generator",
            sequenceName = "seq_tb_poption_id",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poption_id")
    private ProductOption productOption;

    @Column(name = "cart_quantity")
    private int cartQuantity;

    // KMJ end
}
