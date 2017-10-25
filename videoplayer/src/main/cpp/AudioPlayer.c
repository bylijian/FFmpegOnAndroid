
// Created by lijian on 2017/10/25.
//

#include "AudioPlayer.h"
#include <android/log.h>

#include "include/libavformat/avformat.h"
#include "Util.h"
#include "include/libswscale/swscale.h"
#include "include/libavutil/imgutils.h"

#define TAG "AudioPlayer"
#define AUDIO_INBUF_SIZE 20480
#define AUDIO_REFILL_THRESH 4096

#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print( ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

void playMp3(const char *name);

void Java_com_lijian_videoplayer_AudioPlayer_nativePlayAudio(JNIEnv *env, jobject instance,
                                                             jstring fileName_) {
    LOGV("nativePlayAudio()");
    const char *fileName = "/storage/emulated/0/test.mp3";
//    LOGV("nativePlayAudio() fileName=%s",fileName);

    playMp3(fileName);
}

void playMp3(const char *filename) {
    LOGV("playMp3() file %s", filename);

    const AVCodec *codec;
    AVFormatContext *formatContext;
    AVCodecContext *c = NULL;
    AVPacket packet;
    AVFrame *frame;
    int audioStream = -1;

    av_register_all();
    formatContext = avformat_alloc_context();
    int ret;
    if ((ret = avformat_open_input(&formatContext, filename, NULL, NULL)) != 0) {
        LOGE("avformat_open_input() error %d", ret);
        return;
    }
    if (avformat_find_stream_info(formatContext, NULL) < 0) {
        LOGE("avformat_find_stream_info() error");
        return;
    }
    av_dump_format(formatContext, 0, filename, 0);
    if ((audioStream = findAudioStreamIndex(formatContext)) < 0) {
        LOGE("avformat_find_stream_info() error");
        return;
    }
    codec = avcodec_find_decoder(AV_CODEC_ID_MP3);
    c=avcodec_alloc_context3(codec);
    avcodec_open2(c, codec, NULL);
    av_init_packet(&packet);
    frame = av_frame_alloc();
    while (av_read_frame(formatContext, &packet) == 0) {

        if (avcodec_send_packet(c, &packet) == 0) {
            while (avcodec_receive_frame(c, frame) == 0) {
                LOGV("fram->nb_samples=%d ,size=%d", frame->nb_samples, sizeof(frame->data));
            }
        }
    }

//    codec = avcodec_find_decoder(AV_CODEC_ID_MP3);
//    c=
//    if (!codec) {
//        LOGE("avcodec_find_decoder() error");
//        return;
//    }

}


