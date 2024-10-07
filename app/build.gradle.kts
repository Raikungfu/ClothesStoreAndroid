plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.prmproject.clothesstoremobileandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.prmproject.clothesstoremobileandroid"
        minSdk = 28
        targetSdk = 34
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
            buildConfigField("String", "BASE_URL", "\"https://example.com/\"")

        }
        debug {
            // buildConfigField("String", "BASE_URL", "\"http://192.168.1.166:7248/\"")

            buildConfigField("String", "BASE_URL", "\"https://clothesstore-ftfud4cmcjhub3fy.southeastasia-01.azurewebsites.net/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation("androidx.fragment:fragment-ktx:1.6.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.microsoft.signalr:signalr:7.0.0")
    implementation("org.slf4j:slf4j-jdk14:1.7.25")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")

    implementation ("com.auth0.android:jwtdecode:2.0.0")

}