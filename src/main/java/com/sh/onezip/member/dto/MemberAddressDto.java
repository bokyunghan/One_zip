package com.sh.onezip.member.dto;

import com.sh.onezip.member.entity.AddressType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class MemberAddressDto {
    private String baseAddress;
    private String detailAddress;
    private Long id;
    private String recipientName;
    private String recipientPhone;
//    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
