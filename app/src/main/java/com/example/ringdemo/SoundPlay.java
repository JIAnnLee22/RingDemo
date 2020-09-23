package com.example.ringdemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

class SoundPlay {

    private MediaPlayer player;//音频播放器
    private Context context;//声明Context
    private int frequency = 0;//次数
    private int raw;//音频资源


    public SoundPlay() {

    }

    public SoundPlay(Context context, int raw) {
        this.context = context;
        this.raw = raw;
    }

    public void build() {
        if (player == null) {
            player = new MediaPlayer();
        }
        player = MediaPlayer.create(context, raw);
    }


    int fre;

    SoundHandler soundHandler = new SoundHandler();

    public void startSound() {
        fre = getFrequency();
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (fre > 0) {
                    showSound();
                }
                fre--;
            }
        }.start();
    }

    private void showSound() {
        if (player != null) {
            player.start();
        }
    }

    public void stopSound() {
        if (player != null) {
            player.stop();
        }
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        if (frequency == 0) {
            return 1;
        } else {
            return frequency;
        }
    }


    static class SoundHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                Log.d(TAG, "handleMessage: ");
            }
        }
    }
}
