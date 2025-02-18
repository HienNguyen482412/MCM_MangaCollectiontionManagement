import groovyjarjarantlr.build.ANTLR.compiler

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mcm_mangacollectionmanagement"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mcm_mangacollectionmanagement"
        minSdk = 24
        targetSdk = 34
        versionCode = 8
        versionName = "1.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packaging {
        resources {
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/LICENSE.md"
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.mpandroidchart)
    implementation(libs.anychart)
    implementation(libs.multidex)
    implementation(libs.mail)
    implementation(libs.activation)
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

}