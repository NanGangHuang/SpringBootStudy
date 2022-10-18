package com.imooc.controller;

import com.imooc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService usersService;

    @GetMapping("/usernameIsExit")
    public int usernameIsExist(@RequestParam String username){
        if(StringUtils.isBlank(username)){
            return 500;
        }
        boolean isExist = usersService.queryUsernameIsExist(username);
        if(isExist){
            return 500;
        }
        return 200;
    }
}
