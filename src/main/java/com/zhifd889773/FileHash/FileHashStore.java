package com.zhifd889773.FileHash;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileHashStore {
    Config config=null;
    public static HashMap<String, String> curSoftLinkMap=null;//src,dst

    public FileHashStore(){

    }
    public static boolean checkCreateSoftLinkPrivilege() {
        try {
            Path path1=Files.createTempFile("p", "t");
            String tmpFilePathStr=path1.toString()+"1";
            Path linkPath=Paths.get(tmpFilePathStr);
            Files.createSymbolicLink(linkPath, path1);
            Files.deleteIfExists(path1);
            Files.deleteIfExists(linkPath);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return false;
        }
    }
    public void createCurSoftLink() {
        Path srcPath,dstPath;
        if (curSoftLinkMap==null) {
            return;
        }
        try {
            for (String srcPathStr : curSoftLinkMap.keySet()) {
                srcPath=Paths.get(srcPathStr);
                dstPath=Paths.get(curSoftLinkMap.get(srcPathStr));
                if (Files.notExists(srcPath)) {
                    try {
                        Files.createSymbolicLink(srcPath, dstPath);

                    } catch (Throwable e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public boolean isInFileHashInfo(String hashValue,String outPutPath) {
        try {
            if (hashValue==null) {
                return false;
            }
            String valueTmp=this.config.fileHashInfo.getFileHash(hashValue);
            if (valueTmp!=null) {
                if (Files.exists(Paths.get(valueTmp))) {//exist old path
                    if (!outPutPath.equals(valueTmp)) {//not create link to self
                        this.curSoftLinkMap.put(outPutPath,valueTmp);
                    }
                    return true;
                }else {
                    //old path is not exist,need update
                    if(Files.notExists(Paths.get(outPutPath))) {
                        Files.createDirectories(Paths.get(outPutPath));
                    }
                    this.config.fileHashInfo.addFileHash(hashValue, outPutPath);
                    return false;
                }
            }else {//not exit old path
                if(Files.notExists(Paths.get(outPutPath))) {
                    Files.createDirectories(Paths.get(outPutPath));
                }
                this.config.fileHashInfo.addFileHash(hashValue, outPutPath);
                return false;
            }
        } catch (Throwable e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }
    public String getHash(String filePath) {
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
    public String getHash(InputStream inputStream) {
        try {
            String hashValue= DigestUtils.sha256Hex(inputStream);
            return hashValue;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    public void start(String hashFilePath)throws Exception{
        this.config=new Config(hashFilePath);
        this.curSoftLinkMap=new HashMap<>();
    }
    public void end()throws Exception{
        createCurSoftLink();
        this.config.save();
    }
    public void putFileHashInfoByFilePath(String filePath,String destFileDirPath){
        this.config.fileHashInfo.addFileHash(getHash(filePath),destFileDirPath);
    }
    public void putFileHashInfoByHashCode(String hashCode,String destFileDirPath){
        this.config.fileHashInfo.addFileHash(hashCode,destFileDirPath);
    }
    public String getFilePathByInputStream(InputStream is){
        return this.config.fileHashInfo.getFileHash(getHash(is));
    }
    public String getFilePathByfilePath(String filePath){
        return this.config.fileHashInfo.getFileHash(getHash(filePath));
    }
}
