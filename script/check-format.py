#!/usr/bin/python3
# -*- coding: utf-8 -*-
# MIT License
# Copyright (c) 2021 MingMoe
# 用于检查文件是否符合格式

import os
import sys
import glob
import subprocess

def print_help():
    print("用法: python3 check-format.py <path> <fix>")
    print("path: 要检查代码格式化的目录")
    print("fix: 如果是true，则在不符合格式的源文件中原地格式化，并且不发出警告")
    return None

args = sys.argv[1:]

if len(args) != 2:
    print("参数数量不正确")
    print_help()
    sys.exit(1)

path = args[0]
fix = args[1].lower() in ['true', '1', 't', 'y', 'yes', 'yeah', 'yup', 'aye']

# 警告列表
warn_list = []

# clang-format调用
clang_format_arguments = ['clang-format','-style','file']

if fix:
    print("格式化不符合格式的代码 - enabled")
    clang_format_arguments.append("-i")

# 处理文件
def process_file(file_name):
    if fix:
        args =  clang_format_arguments
        args.append(filename)
        subprocess.run(args,stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL)

    else:
        args =  clang_format_arguments
        args.append(filename)
        subprocess.run(args)

        process = subprocess.run(
            args,
            stdout=subprocess.PIPE,
            stdin=subprocess.DEVNULL,
            stderr=subprocess.DEVNULL,
            encoding="utf-8"
            )
        results = process.stdout

        # 比较输出结果
        with open(file_name,"r",encoding="utf-8") as fd:
            source = fd.read()

            if source != results:
                warn_list.append(file_name)


# 遍历目录
for filename in glob.iglob(path + '/**', recursive=True):

    if os.path.isfile(filename):
        if filename.endswith(".cpp") \
            or filename.endswith(".hpp") \
                or filename.endswith(".h") \
                    or filename.endswith(".c") \
                        or filename.endswith(".cc") \
                            or filename.endswith(".c++") \
                                or filename.endswith(".h++"):
            print(f"====================================")
            print(f"检查文件:{filename}")
            print(f"====================================")
            process_file(filename)
        else:
            continue
    else:
        continue

print("检查完成")

# 检查警告
if len(warn_list) > 0:
    print("下面文件不符合格式")
    for warn in warn_list:
        print(f"\t{warn}")
    
    sys.exit(1)

else:
    sys.exit(0)
