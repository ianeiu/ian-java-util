package com.ianeiu.demo.mapstruct.mapper;

import com.ianeiu.demo.mapstruct.pojo.PersonDTO;
import com.ianeiu.demo.mapstruct.pojo.PersonPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "name", target = "fullName")
    @Mapping(target = "mark", ignore = true)
    @Mapping(source = "birth", target = "birth", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "ext1", target = "personExt.ext1")
    @Mapping(source = "ext2", target = "personExt.ext2", defaultValue = "2")
    @Mapping(target = "joinTime", expression = "java( com.ianeiu.utils.date.DateUtil.formatDate(personPO.getJoinTime()) )")
    @Mapping(target = "uid", expression = "java( java.util.UUID.randomUUID().toString() )")
    PersonDTO personToPersonDTO(PersonPO personPO);


    @Mapping(source = "personPO.name", target = "fullName")
    @Mapping(source = "is", target = "isAdmin")
    PersonDTO personToPersonDTO(PersonPO personPO, Integer is);
}
