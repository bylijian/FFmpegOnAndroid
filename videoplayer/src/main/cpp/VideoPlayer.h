//
// Created by lijian on 2017/6/15.
//
#include <jni.h>

#ifndef VIDEODEMO_VIDEOPLAYER_H
#define VIDEODEMO_VIDEOPLAYER_H
#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jboolean JNICALL Java_com_lijian_videoplayer_VideoPlayer_nativePlay(JNIEnv *, jobject , jobject ,
jstring );
JNIEXPORT jboolean JNICALL
Java_com_lijian_videoplayer_VideoPlayer_nativeStop(JNIEnv *env, jobject instance);
#ifdef __cplusplus
}
#endif
#endif //VIDEODEMO_VIDEOPLAYER_H
