package com.zhifd889773.FileHash;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
    static String hashFilePath;
    static FileHashInfo fileHashInfo;
    static Gson gson=new GsonBuilder().setPrettyPrinting().setLenient().create();
    public static void setHashFilePath(String hashFilePathPara)throws Exception {
        hashFilePath = hashFilePathPara;
        if (!Files.exists(Paths.get(hashFilePath))){
            Files.createFile(Paths.get(hashFilePath));
            fileHashInfo=new FileHashInfo();
        }else {
            FileReader fileReader = new FileReader(hashFilePath);
            fileHashInfo = gson.fromJson(fileReader, FileHashInfo.class);
            if (fileHashInfo==null){
                fileHashInfo=new FileHashInfo();
            }
            fileReader.close();
        }
    }
    public static void save()throws Exception{
        FileWriter fileWriter=new FileWriter(hashFilePath);
        gson.toJson(fileHashInfo,fileWriter);
        fileWriter.close();

    }

}
