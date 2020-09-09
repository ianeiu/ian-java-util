package com.ianeiu.demo.mapstruct.mapper;

import com.ianeiu.demo.mapstruct.anno.ExtendDeal;
import com.ianeiu.demo.mapstruct.anno.MarkDeal;
import com.ianeiu.demo.mapstruct.pojo.PersonDTO;
import com.ianeiu.demo.mapstruct.pojo.PersonPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LinkMapper.class)
public interface PersonLinkMapper {
    PersonLinkMapper INSTANCE = Mappers.getMapper(PersonLinkMapper.class);

    @Mapping(source = "name", target = "fullName", qualifiedByName = {"NameDeal"})
    @Mapping(source = "ext1", target = "personExt.ext1", qualifiedByName = {"NumDeal"})
    @Mapping(target = "mark", qualifiedBy = {ExtendDeal.class, MarkDeal.class})
    PersonDTO personToPersonDTO(PersonPO personPO);
}
