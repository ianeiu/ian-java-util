package com.ianeiu.demo.mapstruct.mapper;

import com.ianeiu.demo.mapstruct.anno.ExtendDeal;
import com.ianeiu.demo.mapstruct.anno.MarkDeal;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ExtendDeal
public class LinkMapper {

    public String asString(Date date) {
        return date != null ? new SimpleDateFormat("yyyy-MM-dd")
                .format(date) : null;
    }

    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat("yyyy-MM-dd")
                    .parse(date) : null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Named("NameDeal")
    public String nameUpper(String name) {
        return name == null ? "" : name.toUpperCase();
    }

    @MarkDeal
    public String ext1Blank(String mark) {
        return StringUtils.isBlank(mark) ? "暂无备注" : mark;
    }

    @Named("NumDeal")
    public Integer numDeal(Integer ext1){
        return ext1 == null ? 0 : ext1;
    }
}