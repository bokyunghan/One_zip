package com.sh.onezip.productlog.service;

import com.sh.onezip.productlog.entity.ProductLog;
import com.sh.onezip.productlog.repository.ProductLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductLogService {

    @Autowired
    ProductLogRepository productLogRepository;
    public ProductLog createProductLog(ProductLog productLog) {
        return productLogRepository.save(productLog);
    }
}
