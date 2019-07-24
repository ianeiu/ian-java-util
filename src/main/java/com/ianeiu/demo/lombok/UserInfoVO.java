package com.ianeiu.demo.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Data
//@Data = @Getter + @Setter + @ToString + @EqualsAndHashCode
// + @RequiredArgsConstructor[不存在AllArgsConstructor和NoArgsConstructor情况下]
@AllArgsConstructor(staticName = "newInstance")
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
//不适用于接收前端参数
@Accessors(chain = true)
public class UserInfoVO {

    @NonNull
    @Wither private String id;
    private String userName;
    private Integer age;

    //Singular 要和 Builder 一起使用的，会对 List、Set 等集合类生出、处理 addOne、addAll、clear 方法
    private @Singular Set<String> girlfriends;

}
