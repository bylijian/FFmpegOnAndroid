#include "AVPacketQueue.h"

extern "C" {
#include "include/libavcodec/avcodec.h"
}

/**
 * Created by lijian on 2017/6/29.
 */

static bool createMutexAndCond(PacketQueue *pQueue);

//init the PacketQueue ,notice memset 0 to the memory
void packet_queue_init(PacketQueue *q) {
    memset(q, 0, sizeof(PacketQueue));
    createMutexAndCond(q);
}

static bool createMutexAndCond(PacketQueue *pQueue) {
    if (pQueue == NULL) {
        return false;
    }
    pthread_mutexattr_init(&pQueue->attr);
    pthread_mutexattr_settype(&pQueue->attr, PTHREAD_MUTEX_RECURSIVE_NP);
    pthread_mutex_init(&pQueue->mutex, &pQueue->attr);
    pthread_cond_init(&pQueue->cond, NULL);
    return true;
}

static bool destroyMutexAndCond(PacketQueue *pQueue) {
    if (pQueue == NULL) {
        return false;
    }
    pthread_mutexattr_destroy(&pQueue->attr);
    pthread_mutex_destroy(&pQueue->mutex);
    pthread_cond_destroy(&pQueue->cond);
    return true;
}

void packet_queue_destory(PacketQueue *q) {
    AVPacketList *avPacketList = q->first_pkt;
    while (avPacketList != NULL) {
        free(avPacketList);
        avPacketList = avPacketList->next;
    }
    q->first_pkt = NULL;
    q->last_pkt = NULL;
    destroyMutexAndCond(q);
}

//put an AVPacket into PacketQueue ,but you should change AVPacket to AVPacketList
int packet_queue_put(PacketQueue *q, AVPacket *pkt) {

    AVPacketList *pkt1;
//    if (av_dup_packet(pkt) < 0) {
//        return -1;
//    }
    pkt1 = (AVPacketList *) malloc(sizeof(AVPacketList));
    if (!pkt1)
        return -1;
    pkt1->pkt = *pkt;
    pkt1->next = NULL;

    pthread_mutex_lock(&q->mutex);

    if (!q->last_pkt)
        q->first_pkt = pkt1;
    else
        q->last_pkt->next = pkt1;
    q->last_pkt = pkt1;
    q->nb_packets++;
    q->size += pkt1->pkt.size;
    pthread_cond_signal(&q->cond);
    pthread_mutex_unlock(&q->mutex);
    return 0;
}

//get AVPacket from PacketQueue
int packet_queue_get(PacketQueue *q, AVPacket *pkt, int block) {
    AVPacketList *pkt1;
    int ret;

    pthread_mutex_lock(&q->mutex);

    for (;;) {
        pkt1 = q->first_pkt;
        if (pkt1) {
            q->first_pkt = pkt1->next;
            if (!q->first_pkt)
                q->last_pkt = NULL;
            q->nb_packets--;
            q->size -= pkt1->pkt.size;
            *pkt = pkt1->pkt;
            free(pkt1);
            ret = 1;
            break;
        } else if (!block) {
            ret = 0;
            break;
        } else {
            pthread_cond_wait(&q->cond, &q->mutex);
        }
    }
    pthread_mutex_unlock(&q->mutex);
    return ret;
}