package com.sh.onezip.member.dto;

public class MemberProfileDto {
    private String url;
    private String key;

    public MemberProfileDto(String url, String key) {
        this.url = url;
        this.key = key;
    }

    // Getter and Setter
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
