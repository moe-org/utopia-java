# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     backward.cmake                                *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

# backward-cpp
cp_utopiaserver_add_git_library(
    "backward"
    "https://github.com/bombela/backward-cpp.git"
    "v1.5"
)

target_sources(${CP_UTOPIASERVER_EXECUTABLE}    PUBLIC  ${BACKWARD_ENABLE})
target_sources(${CP_UTOPIASERVER_TEST}          PUBLIC  ${BACKWARD_ENABLE})

add_backward(${CP_UTOPIASERVER_EXECUTABLE})
add_backward(${CP_UTOPIASERVER_TEST})
