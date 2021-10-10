#!/usr/bin/python3
# -*- coding: utf-8 -*-
# MIT License
# Copyright (c) 2021 MingMoe

import os
import sys
from typing import List

if len(sys.argv) != 2:
    print("unknown arguments. need a arguments as directory to process javadoc")
    sys.exit(1)

inputDir = sys.argv[1]

# 遍历文件
def findAllFile(base):
    for root, ds, fs in os.walk(base):
        for f in fs:
            if f.endswith('.html'):
                fullname = os.path.join(root, f)
                yield fullname


# 处理
def process(text:List[str],file:str) -> List[str]:

    header_content = \
        """
        <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
        <link rel="manifest" href="/site.webmanifest">
        <link rel="mask-icon" href="/safari-pinned-tab.svg" color="#000000">
        <meta name="msapplication-TileColor" content="#000000">
        <meta name="theme-color" content="#ffffff">
        """

    found = False
    
    ptr = 0

    while ptr != len(text):

        if text[ptr].strip().startswith("<head>"):
            text[ptr] = text[ptr] + header_content
            found = True
            break

        ptr = ptr + 1

    if not found:
        print(f"warning:not found <head> at file:{file}")

    return text

# 主函数
#    with open(f,"r+",encoding="UTF-8") as f:

#        lines:List[str] = f.readlines()

#        f.truncate(0)

#        for result in process(lines,f):
#            f.write(result)

for f_name in findAllFile(inputDir):
    with open(f_name,"r+",encoding="UTF-8") as f_handle:

        lines:List[str] = f_handle.readlines()

        f_handle.close()

        f_handle = open(f_name,"w",encoding="UTF-8")

        f_handle.writelines(process(lines,f_name))

        f_handle.close()


sys.exit(0)