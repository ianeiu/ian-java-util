package com.ianeiu.demo.mapstruct.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuweimian
 * @description 示例DTO
 * @date 2020/9/7 14:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String id;
    private String fullName;
    private String birth;
    private String mark;

    private Integer isAdmin;

    private PersonExtDTO personExt;

    private String joinTime;

    private String uid;

}
