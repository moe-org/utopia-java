# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     config.cmake                                  *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

#==========================================================#
# 设置配置头文件路径
#==========================================================#
set(CP_UTOPIASERVER_CONFIG_HEADER_IN_DIR    "${CP_UTOPIASERVER_SOURCE_DIR}")
set(CP_UTOPIASERVER_CONFIG_HEADER_IN_NAME   "config.hpp.in")

set(CP_UTOPIASERVER_CONFIG_HEADER_OPT_DIR   "${CP_UTOPIASERVER_BINARY_DIR}/config")
set(CP_UTOPIASERVER_CONFIG_HEADER_OPT_NAME  "config.hpp")

# 添加配置头文件
configure_file (
  "${CP_UTOPIASERVER_CONFIG_HEADER_IN_DIR}/${CP_UTOPIASERVER_CONFIG_HEADER_IN_NAME}"
  "${CP_UTOPIASERVER_CONFIG_HEADER_OPT_DIR}/${CP_UTOPIASERVER_CONFIG_HEADER_OPT_NAME}"
  )

# 设置config头文件引用路径
target_include_directories(
    ${CP_UTOPIASERVER_EXECUTABLE}
    PUBLIC ${CP_UTOPIASERVER_CONFIG_HEADER_OPT_DIR})

target_include_directories(
    ${CP_UTOPIASERVER_TEST}
    PUBLIC ${CP_UTOPIASERVER_CONFIG_HEADER_OPT_DIR})


message(STATUS "Include config header directory:${CP_UTOPIASERVER_CONFIG_HEADER_OPT_DIR}")