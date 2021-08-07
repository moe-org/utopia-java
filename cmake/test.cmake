# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     tes.cmake			                            *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

# 启用测试
enable_testing()

# 收集源文件
file(
    GLOB_RECURSE
    CP_UTOPIASERVER_SOURCE_FILES_WITHOUT_MAIN
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.cpp"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.hpp"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.h")

# 引入测试文件
file(
    GLOB_RECURSE
    CP_UTOPIASERVER_TEST_SOURCE_FILES
    "${CP_UTOPIASERVER_TEST_SOURCE_DIR}/*.cc"
    "${CP_UTOPIASERVER_TEST_SOURCE_DIR}/*.cpp"
    "${CP_UTOPIASERVER_TEST_SOURCE_DIR}/*.hpp"
    "${CP_UTOPIASERVER_TEST_SOURCE_DIR}/*.h")

# 设置测试名称
set(CP_UTOPIASERVER_TEST "${PROJECT_NAME}-test")

# 添加测试
add_executable(
    ${CP_UTOPIASERVER_TEST} 
    ${CP_UTOPIASERVER_TEST_SOURCE_FILES}
    ${CP_UTOPIASERVER_SOURCE_FILES_WITHOUT_MAIN}
)

# 设置头文件引用路径
target_include_directories(
    ${CP_UTOPIASERVER_TEST}
    PUBLIC
    ${CP_UTOPIASERVER_SOURCE_DIR}
)
