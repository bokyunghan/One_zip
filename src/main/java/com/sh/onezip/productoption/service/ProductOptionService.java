package com.sh.onezip.productoption.service;

import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productoption.dto.ProductOptionDto;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;

@Slf4j
@Service
@Transactional

public class ProductOptionService {

    @Autowired
    ProductOptionRepository productOptionRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ProductOption> findAllByProductId(Long id) {
        return productOptionRepository.findAllByProductId(id);
    }

    public ProductOption findById(Long selectOption) {
        return productOptionRepository.findById(selectOption).orElse(null);
    }

    public void createProductOption(ProductOptionDto productOptionDto) {
        ProductOption productOption = convertToProductOption(productOptionDto);
        productOption.setId(null);
        productOptionRepository.save(productOption);
    }

    private ProductOption convertToProductOption(ProductOptionDto productOptionDto) {
        return modelMapper.map(productOptionDto, ProductOption.class);
    }

    public void productOptionCreate(List<List<Object>> optionListOfList, Product product) {

        // 옵션 집합의 갯수
        int optionSetSize = optionListOfList.get(0).size();
        System.out.println("optionSetSize: " + optionSetSize);
        // int optTypeSize = optionListOfList.size();

        ProductOption productOption[] = new ProductOption[optionSetSize];
        for (int i = 0; i < optionSetSize; i++) {
            productOption[i] = new ProductOption();
            productOption[i].setProduct(product);
            productOption[i].setNeOption(false);
            productOption[i].setId(((long) (Math.random() * 100) + 50));
        }

        // 옵션 집합의 갯수 만큼 순회
        for (int i = 0; i < optionSetSize; i++) {
            productOption[i].setOptionName((String) optionListOfList.get(0).get(i));
            productOption[i].setTotalStock((Integer) optionListOfList.get(1).get(i));
            productOption[i].setOptionCost((Integer) optionListOfList.get(2).get(i));
            productOptionRepository.save(productOption[i]);
            System.out.println("productOption[i]: " + productOption[i]);
        }
    }

    // HBK start
}
