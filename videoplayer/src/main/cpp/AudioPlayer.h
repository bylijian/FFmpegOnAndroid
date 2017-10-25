//
// Created by lijian on 2017/10/25.
//

#ifndef FFMPEGONANDROID_AUDIOPLAYER_H
#define FFMPEGONANDROID_AUDIOPLAYER_H

#include "jni.h"

JNIEXPORT void JNICALL
Java_com_lijian_videoplayer_AudioPlayer_nativePlayAudio(JNIEnv *env, jobject instance,
                                                        jstring fileName_);

#endif //FFMPEGONANDROID_AUDIOPLAYER_H
