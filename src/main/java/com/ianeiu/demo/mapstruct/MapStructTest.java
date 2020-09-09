package com.ianeiu.demo.mapstruct;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ianeiu.demo.mapstruct.mapper.ListMapMapper;
import com.ianeiu.demo.mapstruct.mapper.PersonLinkMapper;
import com.ianeiu.demo.mapstruct.mapper.PersonMapper;
import com.ianeiu.demo.mapstruct.pojo.PersonDTO;
import com.ianeiu.demo.mapstruct.pojo.PersonPO;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class MapStructTest {

    @Test
    public void test1() {
        PersonPO po = new PersonPO(9527L, "阿巴阿巴", new Date(), "GG", 1, null, new Date());
        PersonDTO dto = PersonMapper.INSTANCE.personToPersonDTO(po);
        PersonDTO dto2 = PersonMapper.INSTANCE.personToPersonDTO(po, 1);
        System.out.println(dto);
        System.out.println(dto2);
    }

    @Test
    public void testPersonDateMapper() {
        PersonPO po = new PersonPO(9527L, "abab", new Date(), "", null, 2, new Date());
        PersonDTO dto = PersonLinkMapper.INSTANCE.personToPersonDTO(po);
        System.out.println(dto);
    }

    @Test
    public void testMapMapper() {
        List<String> list = Lists.newArrayList();
        list.add("2019-10-15");
        list.add("2020-10-15");
        list.add("2019-06-15");
        List<Date> dataDateList = ListMapMapper.INSTANCE.dateList2strList(list);
        for (Date date : dataDateList) {
            System.out.println(date);
        }

        Map<String, String> data = Maps.newHashMap();
        data.put("1", "2020-09-08");
        data.put("2", "2019-09-08");
        data.put("3", "2020-06-08");
        Map<Long, Date> result = ListMapMapper.INSTANCE.longDataMap2strStrMap(data);
        for (Map.Entry<Long, Date> longDateEntry : result.entrySet()) {
            System.out.println(longDateEntry.getKey() + " ---> " + longDateEntry.getValue());
        }

        Stream<Integer> stream = Lists.newArrayList(1, 2, 3, 4).stream();
        for (String s : ListMapMapper.INSTANCE.stream2set(stream)) {
            System.out.println(s);
        }
    }
}
