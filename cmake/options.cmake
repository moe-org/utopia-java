# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     options.cmake                                 *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

# 增加编译选项的函数
function(add_utopia_options utopia_options)
    target_compile_options(${CP_UTOPIASERVER_EXECUTABLE}    PUBLIC  ${utopia_options})
    target_compile_options(${CP_UTOPIASERVER_TEST}          PUBLIC  ${utopia_options})

    message(STATUS "Add compile options:${utopia_options}")
endfunction(add_utopia_options)

#==========================================================#

if(${CMAKE_BUILD_TYPE} STREQUAL "Debug")

    if(MSVC)
    #==========================================================#
    #                   MSVC        DEBUG选项
    #==========================================================#

        # 生成调试信息
        add_utopia_options("/DEBUG:FULL")
        add_utopia_options("/Z7")

        # 禁止优化
        add_utopia_options("/Od")

    #==========================================================#
    else() #            gcc/clang   DEBUG选项
    #==========================================================#

        # 生成调试信息
        add_utopia_options("-g")

        # 使用不影响调试的优化选项
        add_utopia_options("-Og")


    #==========================================================#
    endif()
elseif(${CMAKE_BUILD_TYPE} STREQUAL "Release")
    if(MSVC)
    #==========================================================#
    #                   MSVC        RELEASE选项
    #==========================================================#

        # 生成快速代码
        add_utopia_options("/Ox")



    #==========================================================#
    else() #            gcc/clang   RELEASE选项
    #==========================================================#

        # 优化代码
        add_utopia_options("-O3")

    #==========================================================#
    endif() #           未知编译器  未知构建类型
    #==========================================================#
else()
    message(FATAL_ERROR "Unknown CMAKE_BUILD_TYPE: ${CMAKE_BUILD_TYPE}")
endif()


if(MSVC)
#==========================================================#
#                       MSVC        全局选项
#==========================================================#

    # 开启C++20支持
    add_utopia_options("/std:c++latest")

    # 开启utf-8支持
    add_utopia_options("/utf-8")


#==========================================================#
else()  #               gcc/clang   全局选项
#==========================================================#

    # 开启所有警告
    add_utopia_options("-Wall")
    add_utopia_options("-Wextra")
    
    # 开启C++20支持
    # GCC11以上使用-std=c++20
    # 否则使用-std=c++2a
    if(CMAKE_CXX_COMPILER_ID STREQUAL "GNU")
        if(CMAKE_CXX_COMPILER_VERSION VERSION_GREATER "11.0.0")
            add_utopia_options("-std=c++20")
        else()
            add_utopia_options("-std=c++2a")
        endif()
    else()
        # 对于其他编译器 一律使用-std=c++20
        add_utopia_options("-std=c++20")
    endif()


#==========================================================#
endif()
