package module.video.jnc.myffmpeg.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import module.video.jnc.myffmpeg.FFmpegUtils;
import module.video.jnc.myffmpeg.MyVideoGpuShow;
import module.video.jnc.myffmpeg.R;
import module.video.jnc.myffmpeg.tool.FileUtils;
import module.video.jnc.myffmpeg.widget.TitleBar;

public class VideoReverseActivity extends VideoEditParentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_reverse);
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
                    showToast("正在处理中，请稍等");
                    showLoadPorgressDialog("正在处理...");
                    return;
                }
                FileUtils.makeReverse();
                startReverse();
                showLoadPorgressDialog("正在处理...");
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!activityFoucsFlag & hasFocus && listPath.size() > 0) {
            activityFoucsFlag = true;
            startPlayThread(listPath.get(0));
        }
    }

    @Override
    protected int getProgress(){
        return FFmpegUtils.getBackRunProgress();
    }

    @Override
    protected int destroyFFmpeg(){
        FFmpegUtils.destroyBackRun();
        return 1;
    }


    private ReverseThread reverseThread ;
    private void startReverse(){
        stopReverse();
        startProgressThread();
        if(reverseThread == null){
            reverseThread = new ReverseThread();
            reverseThread.start();
        }
    }
    private void stopReverse(){
        FFmpegUtils.destroyBackRun();
        if(reverseThread != null){
            try {
                reverseThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reverseThread= null;
    }
    class ReverseThread extends Thread{
        @Override
        public void run() {
            super.run();
            dealFlag = true;
            FFmpegUtils.startBackRun(listPath.get(0), FileUtils.APP_REVERSE+"_"+System.currentTimeMillis()+".mp4");
            dealFlag = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopReverse();
    }
}
