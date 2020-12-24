//
// Created by kevin on 07/12/2020.
//

#include <jni.h>
#include <string>
#include <iostream>
#include<time.h>

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_kevinraikhanzain_routinecachecleaner_util_StringUtils_00024Companion_methodJNIMilisToDate(
        JNIEnv *env, jobject thiz, jint number) {
    time_t a = number;
    jstring x = (env)->NewStringUTF(ctime(&a));
    return x;
}
//Java_id_ac_ui_cs_mobileprogramming_kevinraikhanzain_routinecachecleaner_MainActivity_Method(
//        JNIEnv *env, jobject /* this */, int number) {
//    time_t a = 1439467747492 / 1000;
//    jstring x = (env)->NewStringUTF(ctime(&a));
//    return x;
//}




