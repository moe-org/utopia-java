# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER GOSCPSv3                              *
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
set(UTOPIA_PACKAGE_OUT_DIE "package")
set(CPACK_PACKAGE_DIRECTORY "${PROJECT_SOURCE_DIR}/${UTOPIA_PACKAGE_OUT_DIE}")

# 包版本
set(CPACK_PACKAGE_VERSION_MAJOR ${UtopiaServer_VERSION_MAJOR})
set(CPACK_PACKAGE_VERSION_MINOR ${UtopiaServer_VERSION_MINOR})
set(CPACK_PACKAGE_VERSION_PATCH ${UtopiaServer_VERSION_PATCH})

# 项目描述
set(CPACK_PACKAGE_DESCRIPTION "Server of game utopia!")

# 项目描述,too
set(CPACK_PACKAGE_DESCRIPTION_SUMMARY ${CPACK_PACKAGE_DESCRIPTION})

# README.md
# set(CPACK_PACKAGE_DESCRIPTION_FILE  TODO)

# 项目许可证
# set(CPACK_RESOURCE_FILE_LICENSE TODO)

# 项目主页
set(CPACK_PACKAGE_HOMEPAGE_URL "https://github.com/GOSCPS/UtopiaServer")

#==========================================================
# 设置二进制包信息
#==========================================================
# 设置输出文件名称
set(CPACK_PACKAGE_FILE_NAME 
"bin-${PROJECT_NAME}-v${UtopiaServer_VERSION_MAJOR}.${UtopiaServer_VERSION_MINOR}.${UtopiaServer_VERSION_PATCH}-${CMAKE_BUILD_TYPE}")

# 设置打包.tar.xz和.zip
set(CPACK_GENERATOR "TXZ;ZIP")

# 打包可执行文件
set(CPACK_COMPONENTS_ALL ${UTOPIA_COMPONENT_NAME})

# 使用所有线程(即0)进行打包
set(CPACK_THREADS 0)

#==========================================================
# 设置源码包信息
#==========================================================
# 设置输出文件名称
set(CPACK_SOURCE_PACKAGE_FILE_NAME
"src-${PROJECT_NAME}-v${UtopiaServer_VERSION_MAJOR}.${UtopiaServer_VERSION_MINOR}.${UtopiaServer_VERSION_PATCH}")

# 设置打包.tar.xz和.zip
set(CPACK_SOURCE_GENERATOR "TAR;ZIP")

# 忽略一些文件
# build package 等目录
set(CPACK_SOURCE_IGNORE_FILES "(B|b)uild;${UTOPIA_PACKAGE_OUT_DIE}")

# 使用所有线程(即0)进行打包
set(CPACK_ARCHIVE_THREADS 0)

#==========================================================
# pack
#==========================================================
include(CPack)
