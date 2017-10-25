//
// Created by lijian on 2017/7/3.
//

#ifndef VIDEODEMO_AVPACKETQUEUE_H
#define VIDEODEMO_AVPACKETQUEUE_H

#include <libavformat/avformat.h>
#include <pthread.h>

typedef struct PacketQueue {
    AVPacketList *first_pkt, *last_pkt;
    int nb_packets;
    pthread_mutex_t mutex;
    pthread_mutexattr_t attr;
    pthread_cond_t cond;
    int size;
} PacketQueue;


void packet_queue_init(PacketQueue *q);

int packet_queue_put(PacketQueue *q, AVPacket *pkt);

int packet_queue_get(PacketQueue *q, AVPacket *pkt, int block);

void packet_queue_destory(PacketQueue *q);

#endif //VIDEODEMO_AVPACKETQUEUE_H
