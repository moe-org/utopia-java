# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     executable.cmake                              *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

#==========================================================#
# 添加可执行文件
#==========================================================#
# 递归收集文件
file(
    GLOB_RECURSE
    CP_UTOPIASERVER_SOURCE_FILES 
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.cc"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.cpp"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.hpp"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.h")
    
# 设置可执行文件名称
set(CP_UTOPIASERVER_EXECUTABLE ${PROJECT_NAME})

# 添加可执行文件
add_executable(${CP_UTOPIASERVER_EXECUTABLE} ${CP_UTOPIASERVER_SOURCE_FILES})





