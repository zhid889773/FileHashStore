package com.zhifd889773.FileHash;

import java.util.HashMap;

public class FileHashInfo {

    private HashMap<String, String>dataMap;
    public FileHashInfo() {
        // TODO Auto-generated constructor stub
        dataMap=new HashMap<String, String>();
    }

    public void addFileHash(String hashValue,String folderPathStr) {
        dataMap.put(hashValue, folderPathStr);
    }
    public String getFileHash(String hashValue) {
        return dataMap.get(hashValue);
    }
    public HashMap<String, String> getDataMap() {
        return dataMap;
    }
    public void setDataMap(HashMap<String, String> dataMap) {
        this.dataMap = dataMap;
    }
}