package com.sh.onezip.member.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {
    @NotNull(message = "id는 필수 입력값입니다.")
    private String memberId;
    @NotNull
    private String name;
    private String nickname;
    private String hobby;
    private String mbti;
//    private String profileKey; // S3파일 식별자
//    private String profileUrl; // S3파일 url
}
