package com.sh.onezip.productreview.service;


import com.sh.onezip.productreview.dto.ProductReviewCreateDto;
import com.sh.onezip.productreview.dto.ProductReviewDto;
import com.sh.onezip.productreview.entity.ProductReview;
import com.sh.onezip.productreview.repository.ProductReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductReviewService {
    @Autowired
    ProductReviewRepository productReviewRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ProductReview> productReviewFindByProductid(Long id) {
        return productReviewRepository.productReviewFindByProductid(id);
    }

    public Page<ProductReviewDto> productReviewFindAllByProductId(Pageable pageable, Long productId) {
        Page<ProductReview> productReviewPage = productReviewRepository.productReviewFindAllByProductId(pageable, productId);
        return productReviewPage.map((productReview) -> convertToProductReviewDto(productReview));
    }

    private ProductReviewDto convertToProductReviewDto(ProductReview productReview) {
        ProductReviewDto productReviewDto = modelMapper.map(productReview, ProductReviewDto.class);
        return productReviewDto;
    }

    public List<ProductReviewDto> productReviewDtoFindAllByProductId(Long productId) {
        List<ProductReview> productReviews = productReviewRepository.productReviewFindAllByProductId(productId);
        List<ProductReviewDto> productReviewsDtos = new ArrayList<>();
        for (ProductReview productReview : productReviews) {
            productReviewsDtos.add(convertToProductReviewDto(productReview));
        }
        return productReviewsDtos;
    }

    public void createReview(ProductReviewCreateDto productReviewCreateDto) {
        ProductReview productReview = modelMapper.map(productReviewCreateDto, ProductReview.class);
        productReviewRepository.save(productReview);
    }

    public void deleteById(Long reviewId) {
        productReviewRepository.deleteById(reviewId);
    }

    public ProductReview findById(Long reviewId) {
        return productReviewRepository.findById(reviewId).orElse(null);
    }
}
