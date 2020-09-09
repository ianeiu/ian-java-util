package com.ianeiu.demo.mapstruct.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Mapper
public interface ListMapMapper {
    ListMapMapper INSTANCE = Mappers.getMapper(ListMapMapper.class);


    @IterableMapping(dateFormat = "yyyy-MM-dd")
    List<Date> dateList2strList(List<String> dateList);

    @MapMapping(valueDateFormat = "yyyy-MM-dd")
    Map<Long, Date> longDataMap2strStrMap(Map<String,String> map);

    Set<String> stream2set(Stream<Integer> stream);
}
