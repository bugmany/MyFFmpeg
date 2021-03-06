
cmake_minimum_required(VERSION 3.4.1)

include_directories(src/main/cpp/include)


add_library(my_ffmpeg
        SHARED
        src/main/cpp/my_ffmpeg.cpp
        src/main/cpp/sonic.cpp
        src/main/cpp/SonicRead.cpp
        src/main/cpp/Utils.cpp
        src/main/cpp/MyThread.cpp
        src/main/cpp/Mp4Player.cpp
        src/main/cpp/ReadAvPacket.cpp
        src/main/cpp/DecodeVideoThread.cpp
        src/main/cpp/DecodeMyAudioThread.cpp
        src/main/cpp/MyData.cpp
        src/main/cpp/Notify.cpp
        src/main/cpp/YuvPlayer.cpp
        src/main/cpp/AudioPlayer.cpp
        src/main/cpp/SeekVideo.cpp
        src/main/cpp/CallJava.cpp
        src/main/cpp/PublishStream.cpp
        src/main/cpp/CameraStream.cpp
        src/main/cpp/decode_test.cpp
        src/main/cpp/srs_librtmp.cpp
        src/main/cpp/SRSLibRtmp.cpp
        src/main/cpp/FlvParse.cpp
        src/main/cpp/AMF0.cpp
        src/main/cpp/NumUtils.cpp
        src/main/cpp/h264Parse.cpp
        src/main/cpp/AACParse.cpp
        src/main/cpp/VideoClip.cpp
        src/main/cpp/OpenGlTest.cpp
        src/main/cpp/VideoJoint.cpp
        src/main/cpp/EditParent.cpp
        src/main/cpp/VideoRunBack.cpp
        src/main/cpp/CurrentTimeBitmap.cpp
        src/main/cpp/my_open_sl_test.cpp
        src/main/cpp/NewAudioPlayer.cpp
        src/main/cpp/NewVideoJoint.cpp
        src/main/cpp/VideoFilter.cpp
        src/main/cpp/FilterParent.cpp
        src/main/cpp/GifMake.cpp
        src/main/cpp/VideoMerge.cpp
        src/main/cpp/AudioMix.cpp
        src/main/cpp/VideoDub.cpp
        src/main/cpp/VideoMusic.cpp
        src/main/cpp/VideoSpeed.cpp
        src/main/cpp/MyRtmp.cpp
        )


add_library(avcodec SHARED IMPORTED)
set_target_properties(avcodec
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavcodec.so)


add_library(avfilter SHARED IMPORTED)
set_target_properties(avfilter
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavfilter.so)


add_library(avformat SHARED IMPORTED)
set_target_properties(avformat
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavformat.so)


add_library(avutil SHARED IMPORTED)
set_target_properties(avutil
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavutil.so)


add_library(swresample SHARED IMPORTED)
set_target_properties(swresample
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libswresample.so)


add_library(swscale SHARED IMPORTED)
set_target_properties(swscale
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libswscale.so)


target_link_libraries( # Specifies the target library.
        my_ffmpeg
        avcodec
        avfilter
        avformat
        avutil
        swresample
        swscale

        OpenSLES
        EGL
        GLESv2
        android
        log)