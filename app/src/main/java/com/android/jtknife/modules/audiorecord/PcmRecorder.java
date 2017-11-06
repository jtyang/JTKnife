package com.android.jtknife.modules.audiorecord;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Process;
import android.util.Log;

import com.android.jtknife.modules.audiorecord.wav.WavFileWriter;

import java.io.IOException;

/**
 * 文件描述
 * ref:http://www.jianshu.com/p/556700f0b3fd
 *
 * 将WavWriter改为了WavFileWriter的实现方案，其实两者都是ok的。
 *
 * AUTHOR: yangjiantong
 * DATE: 2017/11/6
 */
public class PcmRecorder {

    static final String TAG = PcmRecorder.class.getSimpleName();

    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public static final int AUDIO_FORMAT_IN_BYTE = 2;

    //    WavWriter mWavWriter;//这种方式也是没问题的，只是尝试下使用WavFileWriter来写文件
    AudioRecord mAudioRecord;
    boolean mStopFlag = false;
    int mBufSize;
    int mCurAmplitude = 0;

    RecordThread mRecordThread;

    WavFileWriter wavFileWriter;

    public PcmRecorder(int sampleRate, int channelCount, String filePath) {
        int channelConfig = channelCount == 1 ? AudioFormat.CHANNEL_CONFIGURATION_MONO : AudioFormat.CHANNEL_CONFIGURATION_STEREO;
        int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, AUDIO_FORMAT);
        //20ms数据大小 = 20 * ((sampleRate/1000) * channelCount * AUDIO_FORMAT_IN_BYTE)
        //但是由于采样率为44100时除以1000会有小数，所以公式一般会优化为 (20 * sampleRate * channelCount * AUDIO_FORMAT_IN_BYTE) / 1000
        //解释：假设采样率为16000，则1ms会采集16000/1000=16次；channelCnt是声道数，单声道1，双声道2；
        //AUDIO_FORMAT_IN_BYTE是根据AUDIO_FORMAT知道的，16BIT指的是使用2个字节来表示一个量化点，8BIT指的是使用一个字节来表示
        //mBufSize = sampleRate * 20 / 1000 * channelCount * AUDIO_FORMAT_IN_BYTE;
        mBufSize = (20 * sampleRate * channelCount * AUDIO_FORMAT_IN_BYTE) / 1000;
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, AUDIO_FORMAT, 8 * minBufSize);
//        mWavWriter = new WavWriter(filePath, channelCount, sampleRate, AUDIO_FORMAT);
        wavFileWriter = new WavFileWriter();
        try {
            wavFileWriter.openFile(filePath, sampleRate, channelCount, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "state: " + mAudioRecord.getState());
    }

    public void startRecord() {
        Log.d(TAG, "startRecord");
        mRecordThread = new RecordThread();
        mRecordThread.start();
    }

    public void stopRecord() {
        Log.d(TAG, "stopRecord");
        mStopFlag = true;
        try {
            mRecordThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException " + e.getMessage());
        }
    }

    public int getAmplitude() {
        return mCurAmplitude;
    }

    class RecordThread extends Thread {
        @Override
        public void run() {
            Log.d(TAG, "thread run");
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

            if (mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
                Log.e(TAG, "unInit");
                return;
            }

            byte[] buffer = new byte[mBufSize];
            mAudioRecord.startRecording();
            while (!mStopFlag) {
                int len = mAudioRecord.read(buffer, 0, buffer.length);
//                mWavWriter.writeToFile(buffer, len);
//                setCurAmplitude(buffer, len);
                wavFileWriter.writeData(buffer, 0, len);
            }
//            mWavWriter.closeFile();
            try {
                wavFileWriter.closeFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mAudioRecord.stop();
            mAudioRecord.release();
            Log.d(TAG, "thread end");
        }
    }

    private void setCurAmplitude(byte[] readBuf, int read) {
        mCurAmplitude = 0;
        for (int i = 0; i < read / 2; i++) {
            short curSample = (short) ((readBuf[i * 2] & 0xFF) | (readBuf[i * 2 + 1] << 8));
            if (curSample > mCurAmplitude) {
                mCurAmplitude = curSample;
            }
        }
    }
}
