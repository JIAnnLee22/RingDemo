package com.example.ringdemo;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

class SoundPlay {

    private Context context;//声明Context
    private int frequency = 1;//次数
    private int raw;//音频资源

    private SoundPool pool;//音频播放器
    private int soundId;
    private int soundP;

    public SoundPlay() {

    }

    public SoundPlay(Context context, int raw) {
        this.context = context;
        this.raw = raw;
    }

    public void build() {
        if (pool == null) {
            //高版本使用新的初始化方式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes attr = new AudioAttributes.Builder()
                        //设置
                        .setLegacyStreamType(AudioManager.STREAM_MUSIC).build();
                pool = new SoundPool.Builder()
                        .setAudioAttributes(attr) // 设置音效池的属性
                        .build();
            } else {
                //1.最多同时播放的音频流个数，2.播放属性为系统提示音，3.音质
                pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 3);
            }
        }
        //音频id
        soundId = pool.load(context, raw, 1);
    }

    int fre;//控制次数从0开始

    public void startSound() {
        if (pool != null) {
            fre = getFrequency() - 1;//设置次数从0开始所以-1
            //音频id-左声道-右声道-优先级-次数-速率
            soundP = pool.play(soundId, 1, 1, 3, fre, 2);
        }
    }

    public void stopSound() {
        if (pool != null) {
            pool.stop(soundP);
        }
    }

    public void onResume() {
        pool.resume(soundP);
    }

    public void onPause() {
        pool.pause(soundP);
    }

    public void onDestroy() {
        if (pool != null) {
            pool.release();
            pool = null;
        }
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setRaw(int raw) {
        this.raw = raw;
        soundId = pool.load(context, raw, 1);
    }
}
