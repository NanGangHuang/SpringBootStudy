package com.imooc.service.impl;

import com.imooc.mapper.FilestoreMapper;
import com.imooc.pojo.Filestore;
import com.imooc.service.FileService;
import org.n3r.idworker.Sid;
import org.n3r.idworker.strategy.DefaultRandomCodeStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {


    Logger logger = LoggerFactory.getLogger(DefaultRandomCodeStrategy.class);

    private static final String PATH = "I:\\文档\\";

    @Autowired
    private FilestoreMapper filestoreMapper;

    @Autowired
    private Sid sid;

    @Override
    public String uploadFile(String path) {
        getFileNameByPath(path);

        return "success";
    }

    private void getFileNameByPath(String path) {
        logger.info("path : {} ", path);
        File file = new File(path);
        File[] filesName = file.listFiles();
        for (int i = 0; i < filesName.length; i++) {
            if (filesName[i].isFile()) {
                File f = filesName[i];
                if (queryExistFileName(f.getName())) {
                    continue;
                }
                Filestore filestore = new Filestore();
                filestore.setId(sid.nextShort());
                filestore.setFilename(f.getName());
                filestore.setFilePath(f.getPath());
                filestore.setFileType(f.getName().substring(f.getName().lastIndexOf(".") + 1).toUpperCase());
                filestore.setCreateTime(new Date());
                filestore.setUpdateTime(new Date());
                filestoreMapper.insert(filestore);
            } else if (filesName[i].isDirectory()) {
                getFileNameByPath(filesName[i].getPath());
            }
        }
    }

    public boolean queryExistFileName(String filename) {
        Example fileExample = new Example(Filestore.class);
        Example.Criteria fileCriteria = fileExample.createCriteria();
        fileCriteria.andEqualTo("filename", filename);
        Filestore result = filestoreMapper.selectOneByExample(fileExample);
        return result == null ? false : true;
    }
}
