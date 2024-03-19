package com.sh.onezip.product.repository;

import com.sh.onezip.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // KMJ start

    @Query("from Product p left join fetch p.member b where p.productPrice <= :price order by p.id desc")
    Page<Product> findAllByPriceUnder(Pageable pageable, int price);

    @Query("from Product p left join fetch p.member b where p.productPrice >= :price order by p.id desc")
    Page<Product> findAllByPriceUpper(Pageable pageable, int price);

    @Query("from Product p where p.productPrice <= :price")
    List<Product> findAllByPriceUnder(int price);

    @Query("from Product p where p.productPrice >= :price")
    List<Product> findAllByPriceUpper(int price);

    // KMJ end

}
