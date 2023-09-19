package com.imooc.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

public class SFTPUtil {

    Logger logger = LoggerFactory.getLogger(SFTPUtil.class);
    private String sftpHost;
    private String sftpUser;
    private String sftpPass;
    private String remoteWorkPath;
    private String localWorkPath;
    private String idRSA;
    private int sftpPort = 22;

    Session session = null;
    Properties config = null;
    ChannelSftp sftpChannel = null;

    public String getSftpHost() {
        return sftpHost;
    }

    public void setSftpHost(String sftpHost) {
        this.sftpHost = sftpHost;
    }

    public String getSftpUser() {
        return sftpUser;
    }

    public void setSftpUser(String sftpUser) {
        this.sftpUser = sftpUser;
    }

    public String getSftpPass() {
        return sftpPass;
    }

    public void setSftpPass(String sftpPass) {
        this.sftpPass = sftpPass;
    }

    public String getRemoteWorkPath() {
        return remoteWorkPath;
    }

    public void setRemoteWorkPath(String remoteWorkPath) {
        this.remoteWorkPath = remoteWorkPath;
    }

    public String getLocalWorkPath() {
        return localWorkPath;
    }

    public void setLocalWorkPath(String localWorkPath) {
        this.localWorkPath = localWorkPath;
    }

    public String getIdRSA() {
        return idRSA;
    }

    public void setIdRSA(String idRSA) {
        this.idRSA = idRSA;
    }

    public int getSftpPort() {
        return sftpPort;
    }

    public void setSftpPort(int sftpPort) {
        this.sftpPort = sftpPort;
    }

    public SFTPUtil(String sftpHost, String sftpUser) {
        this.sftpHost = sftpHost;
        this.sftpUser = sftpUser;
    }

    public SFTPUtil(String sftpHost, String sftpUser, String idRSA) {
        this.sftpHost = sftpHost;
        this.sftpUser = sftpUser;
        this.idRSA = idRSA;
    }

    public String connect() throws Exception {
        String sftpStatus = null;
        try{
            JSch jSch = new JSch();
            session = jSch.getSession(sftpUser,sftpHost,sftpPort);
            if(idRSA != null && !idRSA.isEmpty()){
                jSch.addIdentity(idRSA);
            }else {
                session.setPassword(sftpPass);
            }
            config = new Properties();
            config.put("StrictHostKeyChecking","no");
            session.setConfig(config);
            session.connect();

            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            logger.info("connected successfully to sftp");

        }catch (Exception e){
            sftpStatus = "Failed";
            logger.error("SFTP connection error {}.",e);
            throw new Exception("SFTP connection error.",e);
        }

        return sftpStatus;
    }

    public void disconnect(){
        try{
            if (sftpChannel != null){
                sftpChannel.exit();
                sftpChannel = null;
            }
            if(session != null){
                session.disconnect();
                session = null;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    private void reconnect() throws Exception {
        disconnect();
        if(session == null || !session.isConnected()){
            connect();
        }
        if(sftpChannel == null || sftpChannel.isClosed()){
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
        }
        logger.info("reconnect successfully to sftpHost : () ,sftpUser : {} " , sftpHost ,sftpUser);

    }

    public List listFile(String remoteDir,String clientId) throws Exception {
        List<String> filenameList = new ArrayList<>();
        logger.info("list files begin.");
        if(sftpChannel == null || !sftpChannel.isConnected()){
            connect();
        }
        logger.info("listing SFTP Dir {}", remoteDir );
        sftpChannel.cd(remoteDir);
        final List<ChannelSftp.LsEntry> result = new ArrayList<>();
        ChannelSftp.LsEntrySelector selector = new ChannelSftp.LsEntrySelector() {
            @Override
            public int select(ChannelSftp.LsEntry lsEntry) {
                if(lsEntry.getFilename().toUpperCase().contains("DOCX")){
                    result.add(lsEntry);
                }
                return CONTINUE;
            }
        };
        sftpChannel.ls(remoteDir,selector);
        result.stream().forEach(lsEntry -> {
            boolean check = false;
            long size = lsEntry.getAttrs().getSize();
            if(size>0){
                check = true;
            }
            if(check){
                filenameList.add(lsEntry.getFilename());
            }
        });
        logger.info("list files end.");
        return filenameList;
    }

    public String doFileTransfer(String fileName, GZIPInputStream ins) throws Exception {
        logger.info("Do file Transfering Begin");
        if(fileName == null){
            throw new Exception("to get file name is required");
        }
        String sftpStatus = null;
        if(sftpChannel == null || !sftpChannel.isConnected()){
            sftpStatus = connect();
        }
        if(sftpStatus != null){
            logger.info("Do file Transfering Failed.Connection status : {} " ,sftpStatus );
            return sftpStatus;
        }
        ins = new GZIPInputStream(sftpChannel.get(fileName));
        logger.info("Do file Transfering End.");
        return sftpStatus;
    }
}
