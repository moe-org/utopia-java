# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     source_grousps.cmake                          *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

#==========================================================#
# 设置源文件分组(Visual Studio)
#==========================================================#

# 源文件分组
file(
    GLOB_RECURSE
    CP_UTOPIASERVER_SOURCE_FILES_PRIVATE_PRIVATE_PRIVATE
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.cpp"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.cc"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.hpp"
    "${CP_UTOPIASERVER_SOURCE_DIR}/*.h"
    )
    
source_group(
    TREE    ${CP_UTOPIASERVER_SOURCE_DIR} 
    FILES   ${CP_UTOPIASERVER_SOURCE_FILES_PRIVATE_PRIVATE_PRIVATE})


# 测试源文件分组
file(
    GLOB_RECURSE
    CP_UTOPIASERVER_TEST_SOURCE_FILES_PRIVATE_PRIVATE_PRIVATE
    "${CP_UTOPIASERVER_TEST_SOURCE_DIR}/*.cc"
    "${CP_UTOPIASERVER_TEST_SOURCE_DIR}/*.cpp"
    "${CP_UTOPIASERVER_TEST_SOURCE_DIR}/*.hpp"
    "${CP_UTOPIASERVER_TEST_SOURCE_DIR}/*.h")
    
source_group(
    TREE    ${CP_UTOPIASERVER_TEST_SOURCE_DIR}
    FILES   ${CP_UTOPIASERVER_TEST_SOURCE_FILES_PRIVATE_PRIVATE_PRIVATE})

