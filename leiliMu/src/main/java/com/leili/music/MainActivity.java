package com.leili.music;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leili.music.utils.Utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends Activity implements View.OnClickListener {

    private AlertDialog.Builder builder;
    private String TAG = MainActivity.class.getSimpleName();
    private EditText search;
    private TextView music;
    private ArrayList<String> paths;
    private LinkedHashMap<String, String> musicPaths;
    private int current;
    private boolean noFirst;
    private int playOnclick;
    private Button play;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.show);
        button.setOnClickListener(this);
        Button share = (Button) findViewById(R.id.share);
        share.setOnClickListener(this);
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(this);
        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        music = (TextView) findViewById(R.id.music);

        search = (EditText) findViewById(R.id.search);
        /*search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(MainActivity.this,"asdfsadf", Toast.LENGTH_SHORT).show();
            }
        });*/

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onTextChanged: lei1100  s>>" + s + this);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                return false;
            }
        });
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this,"please",Toast.LENGTH_SHORT).show();
            } else {
              ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
            }
        } else {
            musicPaths = Utils.getMusicPaths(this);
            Log.d(TAG, "onCreate: lei1136  search>>" + search.getText()+"  musicPaths>>"+ musicPaths);
            paths = new ArrayList<>();
            paths.addAll(musicPaths.keySet());
        }
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 200);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: lei1133  newConfig>>" + newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: lei1139   search>>" + search.getText());
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: lei1138   search>>" + search.getText());
        super.onResume();
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        Log.d(TAG, "onMultiWindowModeChanged: lei1137  isInMultiWindowMode>>" + isInMultiWindowMode + "  newConfig>>" + newConfig);
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        Log.d(TAG, "onMultiWindowModeChanged: lei1137  isInPictureInPictureMode>>" + isInPictureInPictureMode + "  newConfig>>" + newConfig);

        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause isFinishing:" + isFinishing() + " isDestroyed:" + isDestroyed() + " isChangingConfigurations:" + isChangingConfigurations());
        super.onPause();
        /*search.clearFocus();
        search.getText().clear();*/
    }

    @Override
    protected void onStop() {
//        search.clearFocus();
//        search.getText().clear();
        Log.d(TAG, "onStop " + isFinishing() + this.isChangingConfigurations() + " search:" + search.getText());
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState  search>>" + search.getText());
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState  search>>" + search.getText());
    }

    @Override
    protected void onDestroy() {
        /*search.clearFocus();
        search.setText("");
        search.clearComposingText();
        search.getText().clear();*/
        //search.removeTextChangedListener();
        Log.d(TAG, "onDestroy: lei1138  search>>" + search.getText());
        search = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                builder = new AlertDialog.Builder(this, 99);
                builder.setTitle("haha");
                builder.setPositiveButton(getString(android.R.string.ok), null);
                builder.setNegativeButton(getString(android.R.string.cancel), null);
                View view = getLayoutInflater().inflate(R.layout.bluetooth_pin_confirm, null);
                builder.setView(view);
                builder.create().show();
                break;

            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("vnd.android.cursor.dir/audio");
                intent.setData(MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
                startActivity(intent);
                break;
            case R.id.clear:
//                search.getText().clear();
                String text = String.valueOf(search.getText());
                RingtoneManager.setActualDefaultRingtoneUri(this,2, Uri.parse(text));
                int i = text.indexOf("@");
                Log.d(TAG, "onClick: lei99   getScheme>>"+Uri.parse(text).getScheme()
                        +  "  getUserInfo>>"+Uri.parse(text).getUserInfo()
                        +  "  contains>>"+text.contains("@media/")
                        +  "  @>>"+text.indexOf("@media/")
                        +  "  text>>"+text
                );
                String s = MediaStore.AUTHORITY + "/external/audio/media";
                break;
            case R.id.start:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;
            case R.id.play:
                if (!paths.isEmpty()) {
                    playOnclick++;
                    Intent intent1 = new Intent(MainActivity.this, MusicService.class);
                    intent1.setAction("play");
                    String path = paths.get(current);
                    if (!noFirst) {
                        intent1.putExtra("path",path);
                        music.setText(musicPaths.get(paths.get(current)));
                        noFirst = true;
                    }
                    if (playOnclick%2 == 1) {
                        play.setText("PAUSE");
                    } else {
                        play.setText("PLAY");
                    }
                    startService(intent1);
                } else {
                    Toast.makeText(this,"没有发现可播放音乐",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.reset:
                Intent intent2 = new Intent(MainActivity.this, MusicService.class);
                intent2.setAction("reset");
                startService(intent2);
                if (playOnclick > 0) {
                    playOnclick = 1;
                    play.setText("PAUSE");
                }
                break;
            case R.id.next:
                if (!paths.isEmpty()) {
                    current++;
                    playOnclick = 1;
                    Intent next = new Intent(MainActivity.this, MusicService.class);
                    next.setAction("next");
                    String nextPath = paths.get(current);
                    next.putExtra("path",nextPath);
                    startService(next);
                    music.setText(musicPaths.get(paths.get(current)));
                    play.setText("PAUSE");
                } else {
                    Toast.makeText(this,"没有发现可播放音乐",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    public boolean hasSdCard(Context context) {
        boolean hasSDCard = false;
        final StorageManager storageManager = (StorageManager) context.getSystemService( Context.STORAGE_SERVICE );
        try {
            final Method getVolumeList = storageManager.getClass().getMethod( "getVolumeList" );
            final Class<?> storageValumeClazz = Class.forName( "android.os.storage.StorageVolume" );
            final Method getPath = storageValumeClazz.getMethod( "getPath" );
            Method isRemovable = storageValumeClazz.getMethod( "isRemovable" );

            Method mGetState = null;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                try {
                    mGetState = storageValumeClazz.getMethod( "getState" );
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            final Object invokeVolumeList = getVolumeList.invoke( storageManager );
            final int length = java.lang.reflect.Array.getLength( invokeVolumeList );
            for (int i = 0; i < length; i++) {
                final Object storageValume = java.lang.reflect.Array.get( invokeVolumeList, i );
                final String path = (String) getPath.invoke( storageValume );
                final boolean removable = (Boolean) isRemovable.invoke( storageValume );
                String state = null;
                if (mGetState != null) {
                    state = (String) mGetState.invoke( storageValume );
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        state = Environment.getStorageState( new File( path ) );
                    } else {
                        if (removable) {
                            state = EnvironmentCompat.getStorageState( new File( path ) );
                        } else {
                            state = Environment.MEDIA_MOUNTED;
                        }
                        final File externalStorageDirectory = Environment.getExternalStorageDirectory();
                        Log.e( TAG, "externalStorageDirectory==" + externalStorageDirectory );
                    }
                }
                final String msg = "path==" + path
                        + " ,removable==" + removable
                        + ",state==" + state;
                Log.e( TAG, msg );
                if (removable) {
                    hasSDCard = removable;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasSDCard;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否允许后台播放音乐？")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopService(new Intent(MainActivity.this,MusicService.class));
                        finish();
                    }
                });
        builder.show();
//        super.onBackPressed();
    }
}
