package com.example.demo.cti.pay.infrastructure.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.example.demo.cti.pay.application.command.WechatPayCommand;
import com.example.demo.cti.pay.application.command.WechatPayOrderCommand;

@Mapper(componentModel = "spring")
public interface WechatPayCommandMapStruct {

    WechatPayCommandMapStruct INSTANCE = Mappers.getMapper(WechatPayCommandMapStruct.class);

    @Mappings({
        @Mapping(source = "total", target = "amount.total"),
        @Mapping(source = "openId", target = "payer.openId"),
    })
    WechatPayCommand wechatPayOrderCommandToWechatPayCommand(WechatPayOrderCommand wechatPayOrderCommand);

}
