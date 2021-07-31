# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     packing.cmake                                 *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


#==========================================================
# 设置变量
#==========================================================
# 设置包名称
set(CPACK_PACKAGE_NAME ${PROJECT_NAME})

# 设置开发商名称
set(CPACK_PACKAGE_VENDOR "MingMoe and Contributors")

# 打包输出目录
set(UTOPIA_PACKAGE_OUT_DIR "package")
set(CPACK_PACKAGE_DIRECTORY "${PROJECT_SOURCE_DIR}/${UTOPIA_PACKAGE_OUT_DIR}")

# 包版本
set(CPACK_PACKAGE_VERSION_MAJOR ${UtopiaServer_VERSION_MAJOR})
set(CPACK_PACKAGE_VERSION_MINOR ${UtopiaServer_VERSION_MINOR})
set(CPACK_PACKAGE_VERSION_PATCH ${UtopiaServer_VERSION_PATCH})

# 项目描述
set(CPACK_PACKAGE_DESCRIPTION "Server of game utopia!")

# 项目描述,too
set(CPACK_PACKAGE_DESCRIPTION_SUMMARY ${CPACK_PACKAGE_DESCRIPTION})

# README.md
set(CPACK_PACKAGE_DESCRIPTION_FILE   ${PROJECT_SOURCE_DIR}/README.md)

# 项目许可证
set(CPACK_RESOURCE_FILE_LICENSE ${PROJECT_SOURCE_DIR}/LICENSE)

# 项目主页
set(CPACK_PACKAGE_HOMEPAGE_URL "https://github.com/GOSCPS/UtopiaServer")

#==========================================================
# 设置二进制包信息
#==========================================================
# 设置输出文件名称
set(CPACK_PACKAGE_FILE_NAME 
"bin-${PROJECT_NAME}-v${UtopiaServer_VERSION_MAJOR}.${UtopiaServer_VERSION_MINOR}.${UtopiaServer_VERSION_PATCH}-${CMAKE_BUILD_TYPE}")

# 设置打包.tar.zst和.zip
set(CPACK_GENERATOR "TZST;ZIP")

# 使用所有线程(即0)进行打包
set(CPACK_THREADS 0)

# 写入打包信息时自动转义
set(CPACK_VERBATIM_VARIABLES TRUE)

#==========================================================
# 设置源码包信息
#==========================================================
# 设置输出文件名称
set(CPACK_SOURCE_PACKAGE_FILE_NAME
"src-${PROJECT_NAME}-v${UtopiaServer_VERSION_MAJOR}.${UtopiaServer_VERSION_MINOR}.${UtopiaServer_VERSION_PATCH}")

# 设置打包.tar.zst和.zip
set(CPACK_SOURCE_GENERATOR "TZST;ZIP")

# 忽略一些文件
# build out package .vs 等目录
set(CPACK_SOURCE_IGNORE_FILES "\\.vs/;(B|b)uild/;(o|O)ut/;${UTOPIA_PACKAGE_OUT_DIR}/;\\.git/")

# 使用所有线程(即0)进行打包
set(CPACK_ARCHIVE_THREADS 0)

#==========================================================
# pack
#==========================================================
include(CPack)
