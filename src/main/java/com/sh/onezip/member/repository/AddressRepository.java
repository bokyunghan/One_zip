package com.sh.onezip.member.repository;

import com.sh.onezip.member.entity.Address;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD

import java.util.List;

=======
import java.util.List;


>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
@Table(name = "tb_address")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByMemberId(Long memberId);


    List<Address> findAllByMemberId(Long memberId);

}
