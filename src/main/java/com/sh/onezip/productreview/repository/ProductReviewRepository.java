package com.sh.onezip.productreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sh.onezip.productreview.entity.ProductReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Query("from ProductReview pr where pr.product.id = :id")
    List<ProductReview> productReviewFindByProductid(Long id);
    @Query("from ProductReview pr where pr.product.id = :productId order by pr.id desc")
    Page<ProductReview> productReviewFindAllByProductId(Pageable pageable, Long productId);

    @Query("from ProductReview pr where pr.product.id = :productId")
    List<ProductReview> productReviewFindAllByProductId(@Param("productId") Long productId);

}
