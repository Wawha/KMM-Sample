cmake_minimum_required(VERSION 3.22.1)
project("AppBackendCxx-project")

message("GradleCurrentDate: ${GradleCurrentDate}")

# Include JNI library
add_subdirectory(src/commonMain/cpp AppBackendCxx)
