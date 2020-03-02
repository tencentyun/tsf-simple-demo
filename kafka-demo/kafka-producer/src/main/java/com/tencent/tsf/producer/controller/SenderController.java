package com.tencent.tsf.producer.controller;

import com.tencent.tsf.producer.schedule.ScheduleKafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/producer")
public class SenderController {

    @Autowired
    private ScheduleKafkaSender scheduleKafkaSender;


    @ResponseBody
    @RequestMapping("/send")
    public String send(){
        try{
            scheduleKafkaSender.doWork();
        }catch (Exception e){
            return "error";
        }
        return "success";
    }
}
