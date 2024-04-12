package com.sh.onezip.member.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_address")
public class Address {
    @Id
    @GeneratedValue(generator = "seq_address_id_generator")
    @SequenceGenerator(
            name = "seq_address_id_generator",
            sequenceName = "address_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
<<<<<<< HEAD
    //    @Column(nullable = false, unique = true)
//    private String memberId;
=======
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
    @Column(nullable = false)
    private String recipientName;
    @Column(nullable = false)
    private String recipientPhone;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AddressType addressType;
    @Column
    private String baseAddress;
    @Column
    private String detailAddress;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
<<<<<<< HEAD
}
=======

>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
