package com.example.demo.cti.demo.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cti.common.log.annotation.NetLog;
import com.example.demo.cti.demo.application.DeviceService;
import com.example.demo.cti.demo.application.dto.StateCallbackDto;
import com.example.demo.cti.demo.domain.model.Device;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @NetLog
    @GetMapping(value = "/name/{name}")
    public Device findOne(@PathVariable String name) {
        return deviceService.findByName(name);
    }

    @GetMapping(value = "/id/{id}")
    public Device findOneById(@PathVariable String id) {
        return deviceService.findByName(id);
    }

    @PostMapping
    public Device save(@RequestBody Device device) {
        return deviceService.save(device);
    }

    @PostMapping(value = "/stateCallbackDto")
    public void saveDto(@RequestBody StateCallbackDto stateCallbackDto) {
        System.out.println("stateCallbackDto = " + stateCallbackDto);
    }

}
