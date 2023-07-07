add_library(AppBackendCxx SHARED AppBackendCxx.cpp)
if("${CMAKE_CXX_COMPILER_ID}" STREQUAL "Clang")
    target_compile_options(AppBackendCxx PUBLIC "-fno-limit-debug-info")
endif()

set_property(TARGET AppBackendCxx PROPERTY CXX_STANDARD 17)
set_property(TARGET AppBackendCxx PROPERTY CMAKE_CXX_STANDARD_REQUIRED True)
target_compile_features(AppBackendCxx PUBLIC cxx_std_17)

if(NOT ANDROID)
    message("GradleJDKDir: ${GradleJDKDir}")

    set(JAVA_AWT_LIBRARY NotNeeded)
    set(JAVA_JVM_LIBRARY NotNeeded)
    set(JAVA_AWT_INCLUDE_PATH NotNeeded)
    set(JAVA_INCLUDE_PATH ${GradleJDKDir})
    set(JAVA_INCLUDE_PATH2 ${JAVA_INCLUDE_PATH})
    find_package(JNI REQUIRED)
    target_include_directories(AppBackendCxx PRIVATE ${JAVA_INCLUDE_PATH}/include ${JAVA_INCLUDE_PATH}/include/linux)
endif()

target_compile_options(AppBackendCxx PUBLIC "-fno-limit-debug-info")
