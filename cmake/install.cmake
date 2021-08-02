# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# * THIS FILE IS FROM MingMoe(me@kawayi.moe)                *
# * IS LICENSED UNDER MIT                                   *
# * File:     install.cmake                                 *
# * Content:  Cmake Module                                  *
# * Copyright (c) 2020-2021 MingMoe All rights reserved.    *
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

#==========================================================#
# 安装可执行文件
#==========================================================#
# 组件名称
set(UTOPIA_COMPONENT_NAME "UtopiaServerMain")

# 安装可执行文件
install(TARGETS ${CP_UTOPIASERVER_EXECUTABLE}
        # 安装到安装目录下
        RUNTIME DESTINATION .
        COMPONENT ${UTOPIA_COMPONENT_NAME})

