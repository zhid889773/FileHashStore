package com.zhifd889773.FileHash;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
    String hashFilePath;
    FileHashInfo fileHashInfo;
    Gson gson=new GsonBuilder().setPrettyPrinting().setLenient().create();
    public Config(String hashFilePathPara)throws Exception {
        this.hashFilePath = hashFilePathPara;
        if (!Files.exists(Paths.get(this.hashFilePath))){
            Files.createFile(Paths.get(this.hashFilePath));
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
    public void save()throws Exception{
        FileWriter fileWriter=new FileWriter(hashFilePath);
        gson.toJson(fileHashInfo,fileWriter);
        fileWriter.close();
    }

}
