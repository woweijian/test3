package com.dongbao.test_demo3.controller;

import com.dongbao.test_demo3.Service.EmpInfoService;
import com.dongbao.test_demo3.entity.EmpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private EmpInfoService empInfoService;
    @GetMapping("/hello")
    public String hello() {
        return "hello12344";
    }

    @GetMapping("/dhg")
//     @RequestMapping (value="/dhg",method= RequestMethod.GET)
     public EmpInfo getUserInfo(@RequestParam String emp_no,@RequestParam(required = false) String name1){
//         if(name1.equals("aaa")){
//             return null;
//         }
         EmpInfo empInfo=empInfoService.getUserById(emp_no);
         return empInfo;

     }

     @PutMapping("/dhg/{id}")
     public void updateUserByid(@PathVariable Long id){
         System.out.println("debugger");
           empInfoService.updateUserByid(id);
     }


}

