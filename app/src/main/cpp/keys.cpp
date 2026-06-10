#include <jni.h>

extern "C" {

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getFacebookAppId(JNIEnv* env, jobject) {
    return env->NewStringUTF("fb-app-id-placeholder");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getFacebookClientToken(JNIEnv* env, jobject) {
    return env->NewStringUTF("fb-client-token-placeholder");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getOpenAiApiKey(JNIEnv* env, jobject) {
    return env->NewStringUTF("sk-placeholder-openai-api-key");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getGoogleMapsApiKey(JNIEnv* env, jobject) {
    return env->NewStringUTF("AIza-placeholder-google-maps-api-key");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getStripePublishableKey(JNIEnv* env, jobject) {
    return env->NewStringUTF("pk_test_placeholder-stripe-publishable-key");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getTwilioAccountSid(JNIEnv* env, jobject) {
    return env->NewStringUTF("AC-placeholder-twilio-account-sid");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getTwilioAuthToken(JNIEnv* env, jobject) {
    return env->NewStringUTF("placeholder-twilio-auth-token");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getMapboxAccessToken(JNIEnv* env, jobject) {
    return env->NewStringUTF("pk.eyJ1IjoicGxhY2Vob2xkZXIifQ.placeholder-mapbox-token");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getAlgoliaAppId(JNIEnv* env, jobject) {
    return env->NewStringUTF("ALGOLIA-APP-ID-PLACEHOLDER");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getAlgoliaSearchApiKey(JNIEnv* env, jobject) {
    return env->NewStringUTF("algolia-search-api-key-placeholder");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getMixpanelProjectToken(JNIEnv* env, jobject) {
    return env->NewStringUTF("mixpanel-project-token-placeholder");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getCloudinaryApiKey(JNIEnv* env, jobject) {
    return env->NewStringUTF("cloudinary-api-key-placeholder");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getCloudinaryCloudName(JNIEnv* env, jobject) {
    return env->NewStringUTF("cloudinary-cloud-name-placeholder");
}

JNIEXPORT jstring JNICALL
Java_com_example_stayscout_security_NativeKeys_getRevenueCatPublicSdkKey(JNIEnv* env, jobject) {
    return env->NewStringUTF("rcPublic_placeholder-revenuecat-sdk-key");
}

}