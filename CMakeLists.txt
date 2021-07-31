# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     CMakeLists.txt                                *
# * Content:  Cmake File                                    *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

cmake_minimum_required (VERSION 3.20)

#==========================================================
# 配置CMake
#==========================================================
# 使用最新的C++20
# set(CMAKE_CXX_STANDARD_REQUIRED True)
# set(CMAKE_CXX_STANDARD 20)
# 已在cmake/options.cmake中配置

# 设置项目
project (UtopiaServer VERSION 0.1.0.0 LANGUAGES CXX)

#==========================================================
# 设置编译变量
#==========================================================
# 设置第三方库存储目录
set(UTOPIA_LIBRARY "${PROJECT_SOURCE_DIR}/library")
# 设置源代码存储目录
set(UTOPIA_SOURCE "${PROJECT_SOURCE_DIR}/src")

#==========================================================
# 设置配置头文件路径
#==========================================================
set(UTOPIA_CONFIG_HEADER_OPT_DIR "${PROJECT_BINARY_DIR}/config")
set(UTOPIA_CONFIG_HEADER_OPT_NAME "config.hpp")

set(UTOPIA_CONFIG_HEADER_IN_DIR "${UTOPIA_SOURCE}")
set(UTOPIA_CONFIG_HEADER_IN_NAME "config.hpp.in")

# 添加配置头文件
configure_file (
  "${UTOPIA_CONFIG_HEADER_IN_DIR}/${UTOPIA_CONFIG_HEADER_IN_NAME}"
  "${UTOPIA_CONFIG_HEADER_OPT_DIR}/${UTOPIA_CONFIG_HEADER_OPT_NAME}"
  )

set(UTOPIA_CONFIG_HEADER "${UTOPIA_CONFIG_HEADER_OPT_DIR}/${UTOPIA_CONFIG_HEADER_OPT_NAME}")
#==========================================================
# 添加可执行文件
#==========================================================
file(
    GLOB_RECURSE
    UTOPIA_SOURCE_FILES 
    "${UTOPIA_SOURCE}/*.cpp"
    "${UTOPIA_SOURCE}/*.hpp"
    "${UTOPIA_SOURCE}/*.h")
    
# 设置分组
source_group(TREE ${UTOPIA_SOURCE} FILES ${UTOPIA_SOURCE_FILES})

# 添加可执行文件
add_executable(${PROJECT_NAME} ${UTOPIA_SOURCE_FILES})

#==========================================================
# 添加引用路径
#==========================================================
# 引用config文件
target_include_directories(${PROJECT_NAME} PRIVATE "${UTOPIA_CONFIG_HEADER_OPT_DIR}")

# 引用第三方库
target_include_directories(${PROJECT_NAME} PUBLIC "${UTOPIA_LIBRARY}")


#==========================================================
# 添加第三方库的辅助函数
#==========================================================
function(add_third_library_func third_lib_name link_name)

    # 添加子目录
    add_subdirectory(
        "${UTOPIA_LIBRARY}/${third_lib_name}" 
        "${PROJECT_BINARY_DIR}/${third_lib_name}"

        # 不添加到all target
        EXCLUDE_FROM_ALL)

    # 链接库
    target_link_libraries(${PROJECT_NAME} PUBLIC ${link_name})

endfunction(add_third_library_func)
#==========================================================
# 添加第三方库
#==========================================================
# mimalloc
add_third_library_func("mimalloc" "mimalloc-static")

# backward-cpp
add_subdirectory(
    "${UTOPIA_LIBRARY}/backward-cpp" 
    "${PROJECT_BINARY_DIR}/backward-cpp"
    EXCLUDE_FROM_ALL    # 不添加到all target
    )
target_sources(${PROJECT_NAME} PUBLIC ${BACKWARD_ENABLE} ${UTOPIA_SOURCE_FILES})
add_backward(${PROJECT_NAME})

#==========================================================
# 安装可执行文件
#==========================================================
# 组件名称
set(UTOPIA_COMPONENT_NAME UtopiaServerMain)

# 安装到package目录下
if(${CMAKE_INSTALL_PREFIX})
    message(WARNING "The CMAKE_INSTALL_PREFIX was set up.But always cover to ${PROJECT_SOURCE_DIR}/package")
endif()

set(CMAKE_INSTALL_PREFIX ${PROJECT_SOURCE_DIR}/package)

# 安装
install(TARGETS ${PROJECT_NAME}
        RUNTIME DESTINATION bin
        COMPONENT ${UTOPIA_COMPONENT_NAME})
#==========================================================
# 引入cmake
#==========================================================
set(UTOPIA_CMAKE_MODULE_DIR "${PROJECT_SOURCE_DIR}/cmake")

include("${UTOPIA_CMAKE_MODULE_DIR}/packing.cmake")
include("${UTOPIA_CMAKE_MODULE_DIR}/options.cmake")

