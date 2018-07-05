package com.leili.music.utils;

import android.content.Context;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by android on 7/2/18.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName() +"lei22:";

    public static LinkedHashMap<String,String> getMusicPaths(Context context) {
        LinkedHashMap<String, String> paths = new LinkedHashMap<>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            final Method getVolumeList = storageManager.getClass().getMethod( "getVolumeList" );
            final Class<?> storageValumeClazz = Class.forName( "android.os.storage.StorageVolume" );
            final Method getPath = storageValumeClazz.getMethod( "getPath" );
            StorageVolume[] sv = (StorageVolume[]) getVolumeList.invoke(storageManager);
            for (int i = 0; i < sv.length; i++) {
                String path = (String) getPath.invoke(sv[i]);
                Log.d(TAG, "getMusicPaths:   path>>"+path);
                Map<String, String> musicPathsInStoragePath = findMusicPathsInStoragePath(path);
                if (musicPathsInStoragePath.size() > 0) {
                    paths.putAll(musicPathsInStoragePath);
                }
            }
            Log.d(TAG, "getMusicPaths: paths>>"+paths);
        } catch (ReflectiveOperationException e) {
            Log.d(TAG, "getMusicPaths: Exception >>" +  e);
            e.printStackTrace();
        }
        return paths;
    }

    private static Map<String,String> findMusicPathsInStoragePath(String path) {
        LinkedHashMap<String, String> paths = new LinkedHashMap<>();
        File dir = new File(path);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    String absolutePath = file.getAbsolutePath();
                    String name = file.getName();
                    Log.d(TAG, "findMusicPathsInStoragePath: absolutePath00>>"+absolutePath+ "  name>>"+name);
                    if (file.isFile() && name.endsWith(".mp3")) {
                        Log.d(TAG, "findMusicPathsInStoragePath: absolutePath11>>"+absolutePath);
                        paths.put(absolutePath,name);
                    } else if (file.isDirectory()) {
                        paths.putAll(findMusicPathsInStoragePath(absolutePath));
                    }
                }
            }
        }
        return paths;
    }
}
