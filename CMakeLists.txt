# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     CMakeLists.txt                                *
# * Content:  Cmake File                                    *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
#==========================================================#
# 配置CMake
#==========================================================#

cmake_minimum_required (VERSION 3.20)
# 使用最新的C++20
# set(CMAKE_CXX_STANDARD_REQUIRED True)
# set(CMAKE_CXX_STANDARD 20)
# 已在options.cmake中配置

# 设置项目
project (UtopiaServer VERSION 0.1.0.0 LANGUAGES CXX)

#==========================================================#
# 设置编译变量
#==========================================================#
# `CP_` as `customize private_`

# 设置第三方库存储目录
set(CP_UTOPIASERVER_LIBRARY_DIR     "${CMAKE_CURRENT_SOURCE_DIR}/library")

# 设置源代码存储目录
set(CP_UTOPIASERVER_SOURCE_DIR      "${CMAKE_CURRENT_SOURCE_DIR}/src")

# 设置当前路径
set(CP_UTOPIASERVER_ROOT_DIR        "${CMAKE_CURRENT_SOURCE_DIR}")

# 设置输出目录
set(CP_UTOPIASERVER_BINARY_DIR      "${CMAKE_CURRENT_BINARY_DIR}")

#==========================================================#
# 添加可执行文件
#==========================================================#
# 递归收集文件
file(
    GLOB_RECURSE
    CP_UTOPIASERVER_SOURCE_FILES 
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.cpp"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.hpp"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.h")
    
# 设置分组
source_group(TREE ${CP_UTOPIASERVER_SOURCE_DIR} FILES ${CP_UTOPIASERVER_SOURCE_FILES})

# 设置可执行文件名称
set(CP_UTOPIASERVER_EXECUTABLE ${PROJECT_NAME})

# 添加可执行文件
add_executable(${CP_UTOPIASERVER_EXECUTABLE} ${CP_UTOPIASERVER_SOURCE_FILES})

#==========================================================#
# 引入cmake模块
#==========================================================#
set(CP_UTOPIASERVER_CMAKE_MODULE_DIR "${CP_UTOPIASERVER_ROOT_DIR}/cmake")

include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/install.cmake")
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/library.cmake")
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/options.cmake")
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/packing.cmake")
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/config.cmake")