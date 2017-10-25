//
// Created by lijian on 2017/10/25.
//

#ifndef FFMPEGONANDROID_UITL_H
#define FFMPEGONANDROID_UITL_H
#include "libavformat/avformat.h"

int findVideoStreamIndex(AVFormatContext *formatContext);

int findAudioStreamIndex(AVFormatContext *formatContext);

int findStreamIndexByType(AVFormatContext *formatContext, enum AVMediaType type);

#endif //FFMPEGONANDROID_UITL_H
