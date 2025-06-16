// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
//    alias(libs.plugins.google.dagger.hilt.android) apply false
    id("com.google.dagger.hilt.android") version "2.56.2" apply false // Replace 2.50 with your desired version
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}