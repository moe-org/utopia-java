# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     googletest.cmake                              *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

# 设置镜像
if(${UTOPIASERVER_GOOGLE_TEST_GITEE_MIRROR})
	set(CP_UTOPIASERVER_GOOGLE_TEST_MIRROR "https://gitee.com/mirrors/googletest.git")
else()
	set(CP_UTOPIASERVER_GOOGLE_TEST_MIRROR "https://github.com/google/googletest.git")
endif()

# For Windows: Prevent overriding the parent project's compiler/linker settings
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)

# 使用googltest测试框架
cp_utopiaserver_add_git_library(
	"googletset"
	${CP_UTOPIASERVER_GOOGLE_TEST_MIRROR}
	"release-1.11.0"
)

# 链接库
target_link_libraries(
  ${CP_UTOPIASERVER_TEST}
  gtest_main
)

# 引入测试
include(GoogleTest)
gtest_discover_tests(${CP_UTOPIASERVER_TEST})
