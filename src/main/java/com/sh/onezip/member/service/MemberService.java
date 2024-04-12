package com.sh.onezip.member.service;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.authority.service.AuthorityService;
import com.sh.onezip.member.entity.Address;
import com.sh.onezip.member.entity.AddressType;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.AddressRepository;
import com.sh.onezip.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AddressRepository addressRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;


    public Member findByName(String username) {
        return memberRepository.findByName(username);
    }
    public Member findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }

    public void addAddress(Address address) {
        addressRepository.save(address);
    }
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));

        if (address.getAddressType() == AddressType.D) {
            throw new IllegalStateException("기본주소는 삭제하실 수 없습니다.");
        }

        addressRepository.delete(address);
    }

    public void setDefaultAddress(Long addressId, Long memberId) {
        List<Address> addresses = addressRepository.findAllByMemberId(memberId);
        for (Address address : addresses) {
            address.setAddressType(address.getId().equals(addressId) ? AddressType.D : AddressType.A);
        }
        addressRepository.saveAll(addresses);
    }



    public Member createMember(Member member, Address address) {
        Member savedMember = memberRepository.save(member);  // 먼저 tb_member 테이블에 저장
        Authority authority = Authority.builder()
                .member(savedMember)  // 저장된 멤버의 ID 사용
                .userType(RoleAuth.ROLE_USER)
                .build();
        authorityService.createAuthority(authority);  // tb_authority 테이블에 저장


        address.setMember(savedMember);

        addressRepository.save(address);

        return savedMember;
    }

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }
    public Member changePassword(Member member) {return memberRepository.save(member);}


    // 여기까지가 HSH코드

    // HBK start
    // HBK start
    public Page<Member> findAllMembers(Pageable pageable) {
        return memberRepository.findAllMembers(pageable);
    }


    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }


    public Member findById(Long id) {
        return memberRepository.findByMId(id);
    }

    public List<Address> getAddressesByMemberId(Long memberId) {
        return addressRepository.findAllByMemberId(memberId);
    }


    // HBK end
}

