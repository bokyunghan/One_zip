package com.sh.onezip.member.entity;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.customeranswercenter.entity.AnswerCenter;
import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.member.entity.Gender;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productlog.entity.ProductLog;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productreview.entity.ProductReview;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert // null이 아닌 필드만 등록
@DynamicUpdate // 영속성컨텍스트의 엔티티와 달라진 필드만 수정
// 

// HBK write Tostring  / reason : Member Authority ,QuestionCenter, AnswerCenter stackoverflow
         

<<<<<<< HEAD
@ToString(exclude = {"authorities", "questionCenters", "answerCenters", "addresses", "products", "productQuestions", "productAnswers"})
=======
@ToString(exclude = {"authorities", "questionCenters", "answerCenters", "productQuestions"})
>>>>>>> 5434c6a07934405903206e99c77cede8199d98da
@Table(name = "tb_member")
public class Member {

    @IdedValue(strategy = GenerationType eGenerator( name = "seq_Membe sequenceName = "tb_member_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @Column(nullable = false, unique = true)
    private String memberId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column
    private String email;
    @Column(nullable = false)
    @CreationTimestamp 
    private LocalDate regDate;
    @Column(nullable = false,unique = true)
    private String nickname;
    @Column(nullable = false)
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private String phone;
    @Column
    private String hobby;
    @Column
    private String mbti;
    @Column(name  = "PROFILE_PICTURE_URL")
    private String profileUrl;

    
     
    @OneToMany(mappedBy = "member", fetc
    // @JoinColumn(name = "member_id") //authority 테이블의 컬럼명시
    private List<Authority> authorities;
//    private List<Authority> authorities = new ArrayList<>();

    @O neToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();
    //여기까지가 HSH 코드

    // HBK start
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<QuestionCenter> questionCenters = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<AnswerCenter> answerCenters = new ArrayList<>();
    // HBK end

    // KMJ start

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<ProductQuestion> productQuestions = new ArrayList<

    private List<ProductAnswer> productAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<ProductReview> productReviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductLog> productLogs = new ArrayList<>();

    // KMJ end

}