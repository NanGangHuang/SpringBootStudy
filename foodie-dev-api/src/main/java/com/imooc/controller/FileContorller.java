package com.imooc.controller;

import com.imooc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("file")
public class FileContorller {

    @Autowired
    private FileService fileService;

    @GetMapping("/uploadFile")
    public String uploadFile(@RequestParam String subPath){
        return fileService.uploadFile("I:\\文档\\"+subPath);
    }

}
