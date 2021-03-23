package com.sample.web.admin.controller.html.users.users;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 속성은 무시하면서 매핑한다.
@JsonPropertyOrder({ "사용자ID", "이름", "성", "메일주소", "전화번호1", "우편번호", "주소1" }) // CSV의 헤더순서
@Getter
@Setter
public class UserCsv implements Serializable {

    private static final long serialVersionUID = -1883999589975469540L;

    @JsonProperty("사용자ID")
    Long id;

    // 해시된 암호
    @JsonIgnore // CSV에 출력하지 않는다.
    String password;

    @JsonProperty("이름")
    String firstName;

    @JsonProperty("성")
    String lastName;

    @JsonProperty("메일주소")
    String email;

    @JsonProperty("전화번호1")
    String tel;

    @JsonProperty("우편번호")
    String zip;

    @JsonProperty("주소1")
    String address;
}
