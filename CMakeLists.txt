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
# 设置最低cmake版本
cmake_minimum_required (VERSION 3.20)

# 设置项目
project (UtopiaServer VERSION 0.1.0.0 LANGUAGES CXX)

# 设置c++标准版本
set(CMAKE_CXX_STANDARD 20)

#==========================================================#
# 设置编译变量
#==========================================================#
# `CP_` as `customize private_`

# 设置第三方库存储目录
set(CP_UTOPIASERVER_LIBRARY_DIR     "${CMAKE_CURRENT_SOURCE_DIR}/library")

# 设置源代码存储目录
set(CP_UTOPIASERVER_SOURCE_DIR      "${CMAKE_CURRENT_SOURCE_DIR}/src")

# 设置测试代码存储目录
set(CP_UTOPIASERVER_TEST_SOURCE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/test")

# 设置当前路径
set(CP_UTOPIASERVER_ROOT_DIR        "${CMAKE_CURRENT_SOURCE_DIR}")

# 设置输出目录
set(CP_UTOPIASERVER_BINARY_DIR      "${CMAKE_CURRENT_BINARY_DIR}")

#==========================================================#
# 引入cmake模块
#==========================================================#
set(CP_UTOPIASERVER_CMAKE_MODULE_DIR "${CP_UTOPIASERVER_ROOT_DIR}/cmake")

# 此文件用于设置Visual Sutdio源码分组
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/source_groups.cmake")

# 此文件用于设置可执行文件
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/executable.cmake")

# 此文件用于设置单元测试
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/test.cmake")

# 此文件用于设置安装
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/install.cmake")

# 此文件用于设置第三方库
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/library.cmake")

# 此文件用于设置编译选项
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/options.cmake")

# 此文件用于设置CPack
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/packing.cmake")

# 此文件用于设置config.hpp头文件
include("${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/config.cmake")
