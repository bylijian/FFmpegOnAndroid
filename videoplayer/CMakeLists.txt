# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

###########################################################################

# 配置导入的FFmpeg so库路径
set(FFmpeg_DIR  ${CMAKE_SOURCE_DIR}/src/main/jniLibs )

# 配置so库对应的头文件路径
include_directories(
        ${CMAKE_SOURCE_DIR}/src/main/cpp/include
)


# ${ANDROID_ABI} 这个参数用于表示当前编译的平台比如 arm ，arm-v7a，x86等
# add_library 添加一个library
# set_target_properties 设置目标的属性
add_library(lib_avcodec SHARED IMPORTED)
set_target_properties(lib_avcodec PROPERTIES IMPORTED_LOCATION
    ${FFmpeg_DIR}/${ANDROID_ABI}/libavcodec-57.so)

add_library(lib_avdevice SHARED IMPORTED)
set_target_properties(lib_avdevice PROPERTIES IMPORTED_LOCATION
    ${FFmpeg_DIR}/${ANDROID_ABI}/libavdevice-57.so)

add_library(lib_avfilter SHARED IMPORTED)
set_target_properties(lib_avfilter PROPERTIES IMPORTED_LOCATION
    ${FFmpeg_DIR}/${ANDROID_ABI}/libavfilter-6.so)

add_library(lib_avformat SHARED IMPORTED)
set_target_properties(lib_avformat PROPERTIES IMPORTED_LOCATION
    ${FFmpeg_DIR}/${ANDROID_ABI}/libavformat-57.so)

add_library(lib_avutil SHARED IMPORTED)
set_target_properties(lib_avutil PROPERTIES IMPORTED_LOCATION
    ${FFmpeg_DIR}/${ANDROID_ABI}/libavutil-55.so)

add_library(lib_postproc SHARED IMPORTED)
set_target_properties(lib_postproc PROPERTIES IMPORTED_LOCATION
    ${FFmpeg_DIR}/${ANDROID_ABI}/libpostproc-54.so)

add_library(lib_swresample SHARED IMPORTED)
set_target_properties(lib_swresample PROPERTIES IMPORTED_LOCATION
    ${FFmpeg_DIR}/${ANDROID_ABI}/libswresample-2.so)

add_library(lib_swscale SHARED IMPORTED)
set_target_properties(lib_swscale PROPERTIES IMPORTED_LOCATION
    ${FFmpeg_DIR}/${ANDROID_ABI}/libswscale-4.so)


############################################################################

add_library( # Sets the name of the library.
             videoplayer

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/video-player.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       videoplayer

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}

                       # Must Link Android System lib
                       android

                       # Links the target library to the FFmpeglibrary
                       # included in the NDK.
                       lib_avcodec
                       lib_avdevice
                       lib_avfilter
                       lib_avformat
                       lib_avutil
                       lib_postproc
                       lib_swresample
                       lib_swscale
                        )