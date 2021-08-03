#!/usr/bin/python3
# -*- coding: utf-8 -*-
# MIT License
# Copyright (c) 2021 MingMoe

import sys
import subprocess


# 不是__main__就打印一条错误信息并退出
if __name__ != '__main__':
    print('This is not exetute on main')
    sys.exit(1)


# 打印帮助信息
def print_help():
    print("usgae:python3 ./build.py [Options]")
    print("options:")
    print("\t-h,--help\t\t\t\tprint help then exit")

    print("\tdebug\t\t\t\t\tenable debug mode")
    print("\trelease\t\t\t\t\tenable release mode")

    print("\tpack-bin\t\t\t\tenable pack binary")
    print("\tpack-src\t\t\t\tenable pack source")

    print("\ttest\t\t\t\t\tenable test")

    print("\t--generate-options [option]\t\tadd a cmake generate options")

    print("\t--build-options    [option]\t\tadd a cmake build options")

    print("\t--pack-bin-options [option]\t\tadd a cmake pack binary options")
    print("\t--pack-src-options [option]\t\tadd a cmake pack source options")

    print("\t--test-options     [option]\t\tadd a cmake test options")

    print("\t--cmake-check              \t\tenable cmake warning")



def write_error(msg):
    print(f'\033[31merror:{msg}\033[0m')
    return None


def write_warn(msg):
    print(f'\033[32mwarn:{msg}\033[0m')
    return None


def write_okay(msg):
    print(f'\033[32mokay:{msg}\033[0m')
    return None


# 设置一些全局参数

# 构建类型
build_type = "Unknown"

# 打包类型
pack_bin_type = False
pack_src_type = False

# 测试类型
test_type = False

# cmake检查
cmake_check = False

# 生成命令行
generate_arguments = ['cmake','-S','.','-B','build']

# 构建命令行
build_arguments = ['cmake','--build','.']

# 打包命令行
pack_bin_arguments = ['cpack',"--config","CPackConfig.cmake"]
pack_src_arguments = ['cpack',"--config","CPackSourceConfig.cmake"]

# 测试命令行
test_arguments = ['ctest']

# 解析参数
args = sys.argv[1:]

index = 0

# 从第一个参数开始解析
while index < len(args):
    arg = args[index]

    # 打印帮助
    if arg == "-h" or arg == "--help":
        print_help()
        # 之后退出
        sys.exit(0)

    # cmake检查
    elif arg == "--cmake-check":
        cmake_check = True

    # 设置编译模式
    elif arg == "debug":
        build_type = "Debug"
    
    elif arg == "release":
        build_type = "Release"

    # 设置打包
    elif arg == "pack-bin":
        pack_bin_type = True
    
    elif arg == "pack-src":
        pack_src_type = True

    # 设置测试
    elif arg == "test":
        test_type = True

    # 设置生成参数
    elif arg == "--generate-options":
        index += 1
        generate_arguments.append(args[index])

    elif arg == "--build-options":
        index += 1
        build_arguments.append(args[index])
    
    # 设置打包参数
    elif arg == "--pack-bin-options":
        index += 1
        pack_bin_arguments.append(args[index])
    
    elif arg == "--pack-src-options":
        index += 1
        pack_src_arguments.append(args[index])
    
    # 设置测试参数
    elif arg == "--test-options":
        index += 1
        test_arguments.append(args[index])
    
    else:
        # 错误:未知的参数
        write_error(f"未知的参数:{arg}")
        print_help()
        sys.exit(1)

    index += 1

# 如果未指定build_type则打印错误并退出
if build_type == "Unknown":
    write_error("未指定构建类型")
    write_error("需要debug或release参数")
    print_help()
    sys.exit(1)


# 添加构建类型参数
generate_arguments.append("-D")
generate_arguments.append(f"CMAKE_BUILD_TYPE={build_type}")

build_arguments.append("--config")
build_arguments.append(build_type)

test_arguments.append("-C")
test_arguments.append(build_type)

pack_bin_arguments.append("-C")
pack_bin_arguments.append(build_type)

pack_src_arguments.append("-C")
pack_src_arguments.append(build_type)

# 开启cmake检查
if cmake_check:
    generate_arguments.append("-Wdev")
    generate_arguments.append("-Wdeprecated")

# 构建
write_okay("开始构建")

write_okay(f"构建类型:{build_type}")
write_okay(f"Test:{test_type}")
write_okay(f"Pack-Src:{pack_src_type}")
write_okay(f"Pack-Bin:{pack_bin_type}")

write_okay(f"生成命令行:{generate_arguments}")
write_okay(f"构建命令行:{build_arguments}")

write_okay(f"测试命令行:{test_arguments}")

write_okay(f"打包源码命令行:{pack_src_arguments}")
write_okay(f"打包输出命令行:{pack_bin_arguments}")


# 构建
try:
    subprocess.run(generate_arguments,check=True)

    write_okay("生成完成")
    write_okay("设置工作目录到build")

    subprocess.run(build_arguments,check=True,cwd="./build")
    write_okay("构建完成")

    if pack_bin_type:
        subprocess.run(pack_bin_arguments,check=True,cwd="./build")
        write_okay("打包输出完成")

    if pack_src_type:
        subprocess.run(pack_src_arguments,check=True,cwd="./build")
        write_okay("打包源码完成")

    if test_type:
        subprocess.run(test_arguments,check=True,cwd="./build")
        write_okay("测试完成")

except subprocess.CalledProcessError as err:
    print(err)
    write_error("构建失败!")
    sys.exit(1)

write_okay("构建成功!")
sys.exit(0)
