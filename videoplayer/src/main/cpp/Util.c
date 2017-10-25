//
// Created by lijian on 2017/10/25.
//

#include "Util.h"

int findVideoStreamIndex(AVFormatContext *formatContext) {
    return findStreamIndexByType(formatContext, AVMEDIA_TYPE_VIDEO);
}

int findAudioStreamIndex(AVFormatContext *formatContext) {
    return findStreamIndexByType(formatContext,AVMEDIA_TYPE_AUDIO);
}

int findStreamIndexByType(AVFormatContext *formatContext, enum AVMediaType type) {
    int streamIndex = -1;
    if (!formatContext || !type) {
        return streamIndex;
    }
    int i = 0;
    for (i; i < formatContext->nb_streams; i++) {
        if (formatContext->streams[i]->codecpar->codec_type == type
            && streamIndex < 0) {
            streamIndex = i;
            break;
        }
    }
    return streamIndex;
}

