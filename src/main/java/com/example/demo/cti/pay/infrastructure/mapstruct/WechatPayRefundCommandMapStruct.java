package com.example.demo.cti.pay.infrastructure.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.example.demo.cti.pay.application.command.WechatPayRefundCommand;
import com.example.demo.cti.pay.application.query.WechatPayRefundQuery;

@Mapper(componentModel = "spring")
public interface WechatPayRefundCommandMapStruct {

    WechatPayRefundCommandMapStruct INSTANCE = Mappers.getMapper(WechatPayRefundCommandMapStruct.class);

    @Mappings({
        @Mapping(source = "total", target = "amount.total"),
        @Mapping(source = "refund", target = "amount.refund"),
    })
    WechatPayRefundCommand wechatPayRefundQueryToWechatPayRefundCommand(WechatPayRefundQuery wechatPayRefundQuery);

}
