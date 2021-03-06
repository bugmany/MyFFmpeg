package module.video.jnc.myffmpeg.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import module.video.jnc.myffmpeg.FFmpegUtils;
import module.video.jnc.myffmpeg.MyRender;
import module.video.jnc.myffmpeg.MyVideoGpuShow;
import module.video.jnc.myffmpeg.R;
import module.video.jnc.myffmpeg.widget.TitleBar;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;

public class VideoJointActivity extends VideoEditParentActivity {

    private int playProgress;
    private int jointProgress;
    private int playCount;
    private static final String outPath = "sdcard/FFmpeg/videojoint.flv";
    private int outWidth;
    private int outHeight;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if (playProgress == -100) {
                        //播放下一个
                        if (listPath.size() > (playCount + 1)) {
                            playCount++;
                            stopPlayThread();
                            startPlayThread(listPath.get(playCount));
                        }
                    }
                    break;
               /* case 3:
                    if(jointProgress == 100){
                        //拼接完成
                        stopJointProgress();
                        dismissLoadPorgressDialog();
                        showToast("拼接完成!");
                        finish();
                    }
                    setLoadPorgressDialogProgress(jointProgress);
                    break;*/
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_joint);
        findViewById();
        init();

    }


    private void findViewById() {
        titleBar = findViewById(R.id.title);
        myVideoGpuShow = (MyVideoGpuShow) findViewById(R.id.play_gl_surfaceview);
    }

    protected void init() {
        super.init();
        titleBar.setRightClickInter(new TitleBar.RightClickInter() {
            @Override
            public void clickRight() {
                //右键点击
                if (dealFlag) {
//                    showToast("正在裁剪中，请稍等");
                    showLoadPorgressDialog("正在拼接...");
                    return;
                }
                startJointThread();

                showLoadPorgressDialog("正在拼接...");
            }
        });

        outWidth = 1280;
        outHeight = 720;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!activityFoucsFlag & hasFocus && listPath.size() > 0) {
            activityFoucsFlag = true;
            startPlayThread(listPath.get(playCount));
            startPlayProgressThread();
        }
    }


    //合并视频的线程
    private JointThread jointThread;
    private void startJointThread(){
        stopJointThread();
        startProgressThread();
        jointThread = new JointThread();
        jointThread.start();
    }
    private void stopJointThread(){
        FFmpegUtils.destroyJoint();
        if(jointThread != null){
            try {
                jointThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class JointThread extends Thread{
        @Override
        public void run() {
            super.run();
            dealFlag = true;
            String path[] = new String[listPath.size()];
            listPath.toArray(path);
            FFmpegUtils.startJoint(path , outPath, outWidth, outHeight);
            dealFlag = false;
        }
    }


    @Override
    protected int getProgress(){
        return FFmpegUtils.getJointProgress();
    }

    @Override
    protected int destroyFFmpeg(){
        FFmpegUtils.destroyJoint();
        return 1;
    }



    //获取播放进度
    private PlayPositionThread thread;
    private void startPlayProgressThread() {
        stopPlayProgressThread();
        thread = new PlayPositionThread();
        thread.runFlag = true;
        thread.start();
    }

    private void stopPlayProgressThread() {
        if (thread == null) {
            return;
        }
        thread.runFlag = false;
        try {
            thread.join();
        } catch (Exception e) {

        }
        thread = null;
    }

    class PlayPositionThread extends Thread {
        boolean runFlag;

        @Override
        public void run() {
            super.run();
            while (runFlag) {


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                playProgress = FFmpegUtils.getProgress();
                Log.e("xhc", " progress  " + playProgress);
                handler.sendEmptyMessage(2);
            }
        }
    }

//    //播放的线程
//    private StartPlayThraed playThread;
//
//    private void startPlayThread(String path) {
//        playThread = new StartPlayThraed(path);
//        playThread.start();
//    }
//
//    private void stopPlayThread() {
//        FFmpegUtils.destroyMp4Play();
//        if (playThread != null) {
//            try {
//                playThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class StartPlayThraed extends Thread {
//
//        String playPath;
//
//        StartPlayThraed(String playPath) {
//            this.playPath = playPath;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            synchronized (VideoClipActivity.class) {
//                playVideo(this.playPath);
//            }
//        }
//    }
//
//    private void playVideo(String path) {
//        myVideoGpuShow.setPlayPath(path);
//    }

//    @Override
//    public void onBackPressed() {
//        Log.e("xhc" , " onbackPress");
//        if(joinFlag){
//            showAlertDialog(null, "放弃拼接?", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dismissLoadPorgressDialog();
//                    dialog.dismiss();
//                    finish();
//                }
//            });
//        }
//        else{
//            super.onBackPressed();
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopJointThread();
        stopPlayProgressThread();
    }
}
