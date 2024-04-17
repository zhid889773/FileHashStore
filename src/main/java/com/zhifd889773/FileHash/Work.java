package com.zhifd889773.FileHash;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Work {
    public static String getHash(String filePath) {
        try {
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            String hashValue=DigestUtils.sha256Hex(fileData);
            return hashValue;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    public static String getHash(InputStream inputStream) {
        try {
            String hashValue= DigestUtils.sha256Hex(inputStream);
            return hashValue;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    public static void start(String hashFilePath)throws Exception{
        Config.setHashFilePath(hashFilePath);
    }
    public static void end()throws Exception{
        Config.save();
    }
    public static void putFileHashInfoByFilePath(String filePath,String destFileDirPath){
        Config.fileHashInfo.addFileHash(getHash(filePath),destFileDirPath);
    }
    public static void putFileHashInfoByHashCode(String hashCode,String destFileDirPath){
        Config.fileHashInfo.addFileHash(hashCode,destFileDirPath);
    }
    public static String getFilePathByInputStream(InputStream is){
        return Config.fileHashInfo.getFileHash(getHash(is));
    }
    public static String getFilePathByfilePath(String filePath){
        return Config.fileHashInfo.getFileHash(getHash(filePath));
    }
}
