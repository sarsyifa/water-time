//
// Created by D E L L on 26/12/2020.
//

#include <jni.h>

extern "C"
//JNIEXPORT jint JNICALL
//Java_id_ac_ui_cs_mobileprogramming_sitiauliarahmatussyifa_watertime_CalculatorActivity_calculteTarget(
//        JNIEnv *env, jobject thiz, jint weight) {
//    jlong watereach = 30;
//    return weight * watereach;
//}
extern "C"
JNIEXPORT jint JNICALL
Java_id_ac_ui_cs_mobileprogramming_sitiauliarahmatussyifa_watertime_CalculatorActivity_calculateTarget(
        JNIEnv *env, jobject thiz, jint weight) {
        jlong watereach = 30;
        return weight * watereach;
}