package com.sh.onezip.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentCreateDto {
    private Long refId;
    private String refType;
    private String originalFilename; // 업로드한 파일명
    private String key; // S3파일 식별자
    private String url; // S3파일 url
}
