package com.sh.onezip.productanswer.service;

import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productanswer.repository.ProductAnswerRepository;
import com.sh.onezip.productquestion.repository.ProductQuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class ProductAnswerService {
    @Autowired
    ProductQuestionRepository productQuestionRepository;
    @Autowired
    ProductAnswerRepository productAnswerRepository;
    @Autowired
    ModelMapper modelMapper;

    public Optional<ProductAnswer> findById(Long id) {
        return productAnswerRepository.findById(id);
    }
}
