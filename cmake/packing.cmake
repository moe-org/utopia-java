# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     packing.cmake                                 *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


#==========================================================#
# 设置变量
#==========================================================#
# 设置包名称
set(CPACK_PACKAGE_NAME					${CP_UTOPIASERVER_EXECUTABLE})

# 设置开发商名称
set(CPACK_PACKAGE_VENDOR				"MingMoe and Contributors")

# 打包输出目录
set(CP_UTOPIASERVER_PACKAGE_DIR_NAME	"package")
set(CP_UTOPIASERVER_PACKAGE_OUT_DIR		"${CP_UTOPIASERVER_ROOT_DIR}/${CP_UTOPIASERVER_PACKAGE_DIR_NAME}")

set(CPACK_PACKAGE_DIRECTORY				"${CP_UTOPIASERVER_PACKAGE_OUT_DIR}")

# 包版本
set(CPACK_PACKAGE_VERSION_MAJOR			${UtopiaServer_VERSION_MAJOR})
set(CPACK_PACKAGE_VERSION_MINOR			${UtopiaServer_VERSION_MINOR})
set(CPACK_PACKAGE_VERSION_PATCH			${UtopiaServer_VERSION_PATCH})

# 项目描述
set(CPACK_PACKAGE_DESCRIPTION			"Server of game utopia!")

# 项目描述,too
set(CPACK_PACKAGE_DESCRIPTION_SUMMARY	${CPACK_PACKAGE_DESCRIPTION})

# README.md
set(CPACK_PACKAGE_DESCRIPTION_FILE		${CP_UTOPIASERVER_ROOT_DIR}/README.md)

# 项目许可证
set(CPACK_RESOURCE_FILE_LICENSE			${CP_UTOPIASERVER_ROOT_DIR}/LICENSE)

# 项目主页
set(CPACK_PACKAGE_HOMEPAGE_URL			"https://github.com/GOSCPS/UtopiaServer")

#==========================================================#
# 设置二进制包信息
#==========================================================#
# 设置输出文件名称
set(CPACK_PACKAGE_FILE_NAME 
"bin-${CP_UTOPIASERVER_EXECUTABLE}-v${UtopiaServer_VERSION_MAJOR}.${UtopiaServer_VERSION_MINOR}.${UtopiaServer_VERSION_PATCH}-${CMAKE_BUILD_TYPE}")

# 设置打包.tar.zst和.zip
set(CPACK_GENERATOR				"TZST;ZIP")

# 使用所有线程(即0)进行打包
set(CPACK_THREADS				0)

# 写入打包信息时自动转义
set(CPACK_VERBATIM_VARIABLES	TRUE)

#==========================================================#
# 设置源码包信息
#==========================================================#
# 设置输出文件名称
set(CPACK_SOURCE_PACKAGE_FILE_NAME
"src-${CP_UTOPIASERVER_EXECUTABLE}-v${UtopiaServer_VERSION_MAJOR}.${UtopiaServer_VERSION_MINOR}.${UtopiaServer_VERSION_PATCH}")

# 设置打包.tar.zst和.zip
set(CPACK_SOURCE_GENERATOR		"TZST;ZIP")

# 忽略一些文件
set(CPACK_SOURCE_IGNORE_FILES 
# 忽略构建缓存目录: build / out
# 忽略打包输出目录: package
# 忽略IDE配置目录: .vs / .vscode
# 忽略git配置目录: .git
# 忽略github配置: .github
"\\.vs/;\\.vscode/;(B|b)uild/;(o|O)ut/;${CP_UTOPIASERVER_PACKAGE_DIR_NAME}/;\\.git/;\\.github/")

# 使用所有线程(即0)进行打包
set(CPACK_ARCHIVE_THREADS		0)

#==========================================================#
# pack
#==========================================================#
include(CPack)
