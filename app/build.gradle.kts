import java.util.Properties
plugins {
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}



val localProperties = Properties()
val localPropertiesFile = File(rootDir , "local.properties")

if(localPropertiesFile.exists() && localPropertiesFile.isFile){
    localPropertiesFile.inputStream().use {
        localProperties.load(it)

    }
}

android {
    namespace = "ayush.practice"
    compileSdk = 36

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "ayush.practice"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "API_KEY",
                "\"${localProperties.getProperty("API_KEY")}\""
            )
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)

    // hilt
    implementation(libs.hiltLibrary)
    kapt(libs.hiltKapt)
    implementation(libs.hilt.navigation)

    // coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)


    // retrofit
    implementation (libs.retrofitLibrary)
    implementation (libs.gsonConverter)

    // okhttp
    implementation(libs.okhttpLibrary)
    implementation(libs.okhttpInterceptorLibrary)

    
    // room

    implementation(libs.roomDB)
    kapt(libs.roomKapt)

    implementation(libs.compose.navigation)



    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}