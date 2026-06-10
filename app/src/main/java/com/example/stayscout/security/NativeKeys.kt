package com.example.stayscout.security

@Suppress("TooManyFunctions")
object NativeKeys {
    init {
        System.loadLibrary("stayscout_keys")
    }

    // Facebook / Meta
    external fun getFacebookAppId(): String

    external fun getFacebookClientToken(): String

    // OpenAI
    external fun getOpenAiApiKey(): String

    // Google Maps
    external fun getGoogleMapsApiKey(): String

    // Stripe
    external fun getStripePublishableKey(): String

    // Twilio
    external fun getTwilioAccountSid(): String

    external fun getTwilioAuthToken(): String

    // Mapbox
    external fun getMapboxAccessToken(): String

    // Algolia
    external fun getAlgoliaAppId(): String

    external fun getAlgoliaSearchApiKey(): String

    // Mixpanel
    external fun getMixpanelProjectToken(): String

    // Cloudinary
    external fun getCloudinaryApiKey(): String

    external fun getCloudinaryCloudName(): String

    // RevenueCat
    external fun getRevenueCatPublicSdkKey(): String
}
