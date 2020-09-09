package com.ianeiu.demo.mapstruct.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wuweimian
 * @description 示例DTO
 * @date 2020/9/7 14:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonPO {
    private Long id;
    private String name;
    private Date birth;
    private String mark;

    private Integer ext1;
    private Integer ext2;

    private Date joinTime;

}
