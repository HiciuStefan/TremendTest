package com.tremend.testtremend.Models;

/**
 * Created by Hiciu on 6/8/2016.
 */
public class ModelBrowse {
    public String completeFilePath;
    public String fileName;
    public Long totalFileSize;
    public int layoutPercentage;

    public ModelBrowse(String completeFilePath, String fileName, Long totalFileSize,  int layoutPercentage) {
        this.completeFilePath = completeFilePath;
        this.fileName = fileName;
        this.totalFileSize = totalFileSize;
        this.layoutPercentage = layoutPercentage;
    }
}
