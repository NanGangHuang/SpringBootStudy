package com.imooc;

import com.imooc.controller.SFTPController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Test
    public void myTest(){
        System.out.println("test");
    }

    @Autowired
    private SFTPController sftpController;

    @Test
    public void test01() throws Exception {
        sftpController.getFileBySFTP();
    }


}
