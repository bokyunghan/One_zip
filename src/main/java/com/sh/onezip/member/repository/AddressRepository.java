package com.sh.onezip.member.repository;

import com.sh.onezip.member.entity.Address;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Table(name = "tb_address")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


    List<Address> findAllByMemberId(Long memberId);

}
