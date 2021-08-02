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

    # Declare
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
# backward-cpp
cp_utopiaserver_add_git_library(
    "backward"
    "https://github.com/bombela/backward-cpp.git"
    "v1.5"
)

target_sources(${CP_UTOPIASERVER_EXECUTABLE} PUBLIC ${BACKWARD_ENABLE})
add_backward(${CP_UTOPIASERVER_EXECUTABLE})

#==========================================================#
# 添加头文件路径
#==========================================================#
target_include_directories(
    ${CP_UTOPIASERVER_EXECUTABLE} 
    PUBLIC ${CP_UTOPIASERVER_LIBRARY_DIR})
    
message(STATUS "Include 3rd library header directory:${CP_UTOPIASERVER_LIBRARY_DIR}")