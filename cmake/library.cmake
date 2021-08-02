# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     library.cmake                                 *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

#==========================================================#
# 几个简单的辅助函数
#==========================================================#
# 引入库
include(FetchContent)

function(cp_utopiaserver_add_git_library lib_name git_repo git_tag )

    # 设置库
    FetchContent_Declare(
                            ${lib_name}
        GIT_REPOSITORY      ${git_repo}
        GIT_TAG             ${git_tag}
        SOURCE_DIR          ${CP_UTOPIASERVER_LIBRARY_DIR}/${lib_name}
        )

    # 确保我们只调用FetchContent_Populate一次
    # 否则会造成错误
    FetchContent_GetProperties(${lib_name})

    if(NOT ${lib_name}_POPULATED)
        FetchContent_Populate(${lib_name})
        
        # 添加到子目录
        add_subdirectory(
            ${CP_UTOPIASERVER_LIBRARY_DIR}/${lib_name}  # 设置源文件路径
            ${CP_UTOPIASERVER_BINARY_DIR}/${lib_name}   # 设置输出路径
            EXCLUDE_FROM_ALL                            # 不包含在目标当中
            )

    endif()

endfunction(cp_utopiaserver_add_git_library)

#==========================================================#
# 引入库
#==========================================================#
set(CP_UTOPIASERVER_LIBRARY_CMAKE_MODULE_DIR ${CP_UTOPIASERVER_CMAKE_MODULE_DIR}/library)

include(${CP_UTOPIASERVER_LIBRARY_CMAKE_MODULE_DIR}/backward.cmake)
include(${CP_UTOPIASERVER_LIBRARY_CMAKE_MODULE_DIR}/googletest.cmake)

#==========================================================#
# 添加头文件路径
#==========================================================#
target_include_directories(
    ${CP_UTOPIASERVER_EXECUTABLE}
    PUBLIC ${CP_UTOPIASERVER_LIBRARY_DIR})

target_include_directories(
    ${CP_UTOPIASERVER_TEST}
    PUBLIC ${CP_UTOPIASERVER_LIBRARY_DIR})

message(STATUS "Include 3rd library header directory:${CP_UTOPIASERVER_LIBRARY_DIR}")