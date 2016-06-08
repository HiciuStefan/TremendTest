package com.tremend.testtremend.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.tremend.testtremend.Models.ModelBrowse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiciu on 6/8/2016.
 */
public class BrowseService extends Service {
    private final IBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        public BrowseService getService() {
            return BrowseService.this;
        }
    }

    public void getFiles(final String filePath, final Long totalFolderSizefinal, final Results results) {
        new Thread(new Runnable() {
            public void run() {
                List<ModelBrowse> files = getListFiles(new File(filePath), totalFolderSizefinal);
                results.onResults(files);
            }
        }).start();
        results.onStarted();

    }

    public interface Results {
        void onResults(List<ModelBrowse> files);

        void onStarted();
    }


    private List<ModelBrowse> getListFiles(File parentDir, Long totalFolderSizefinal) {
        ArrayList<ModelBrowse> inFiles = new ArrayList<ModelBrowse>();
        File[] files = parentDir.listFiles();
        if (files != null) {
            long size = 0;
            for (File file : files) {
                long length = file.length() / 1024;
                size += length;
                inFiles.add(new ModelBrowse(file.getAbsolutePath(), file.getName(), length, 0));
            }
            for (ModelBrowse model : inFiles) {
                if (model.totalFileSize != 0) {
                    model.layoutPercentage = (int) (model.totalFileSize / (float) size * 100);
                }
            }
        }

        return inFiles;
    }
}
