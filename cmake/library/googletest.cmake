# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     googletest.cmake                              *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


# For Windows: Prevent overriding the parent project's compiler/linker settings
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)

# 使用googltest测试框架
cp_utopiaserver_add_git_library(
	"googletset"
	"https://github.com/google/googletest.git"
	"release-1.11.0"
)

target_link_libraries(
  ${CP_UTOPIASERVER_TEST}
  gtest_main
)

include(GoogleTest)
gtest_discover_tests(${CP_UTOPIASERVER_TEST})
