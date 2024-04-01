package com.sh.onezip.productlog.repository;

import com.sh.onezip.productlog.entity.ProductLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLogRepository extends JpaRepository<ProductLog, Long> {

}
