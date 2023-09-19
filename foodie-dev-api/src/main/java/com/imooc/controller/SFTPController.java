package com.imooc.controller;

import com.imooc.util.SFTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class SFTPController {

    Logger logger = (Logger) LoggerFactory.getLogger(SFTPController.class);

    public void getFileBySFTP() throws Exception {
        SFTPUtil sftpUtil = initSFTP();
        List<String> filenameList = sftpUtil.listFile("/data/sftp/test01/huanggang/","CAB");

        filenameList.stream().forEach(filename ->{
            logger.info("filename : {}" , filename);
        });
    }

    public SFTPUtil initSFTP() throws Exception {
        SFTPUtil sftpUtil = new SFTPUtil("192.168.1.15","test01");
        sftpUtil.setSftpPass("Hg@@2475");

        logger.info("connection....");
        String sftpStatus = null;

        try {
            sftpStatus = sftpUtil.connect();
        } catch (Exception e) {
            logger.error("SFTP connection error {} ." , e);
            throw new RuntimeException(e);
        }

        if(sftpStatus != null){
            throw new Exception("FTP connect error : " + sftpStatus);
        }
        logger.info("Status : {}",sftpStatus);
        return sftpUtil;
    }
}
