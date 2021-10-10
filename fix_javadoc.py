#!/usr/bin/python3
# -*- coding: utf-8 -*-
# MIT License
# Copyright (c) 2021 MingMoe


from bs4 import BeautifulSoup
import os
import sys

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
def process(text):
    soup = BeautifulSoup(text, 'html5lib')

    header = soup.head

    apple_touch_icon = soup.new_tag("link",attrs={"rel":"apple-touch-icon","sizes":"180x180","href":"/apple-touch-icon.png"})

    icon_small = soup.new_tag("link",attrs={"rel":"icon","type":"image/png","sizes":"16x16","href":"/favicon-16x16.png"})
    icon_large = soup.new_tag("link",attrs={"rel":"icon","type":"image/png","sizes":"32x32","href":"/favicon-32x32.png"})

    manifest = soup.new_tag("link",attrs={"rel":"manifest","href":"/site.webmanifest"})

    mask_icon = soup.new_tag("link",attrs={"rel":"mask-icon", "href":"/safari-pinned-tab.svg", "color":"#000000"})

    tile_color = soup.new_tag("meta",attrs={"name":"msapplication-TileColor", "content":"#000000"})

    theme_color = soup.new_tag("meta",attrs={"name":"theme-color", "content":"#ffffff"})


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
    
    header.append(apple_touch_icon)
    header.append(icon_small)
    header.append(icon_large)
    header.append(manifest)
    header.append(mask_icon)
    header.append(tile_color)
    header.append(theme_color)

    return soup.prettify()

# 主函数
for f in findAllFile(inputDir):
    with open(f,"r+",encoding="UTF-8") as f:
        lines = f.read()

        output = process(lines)

        f.truncate(0)

        f.write(output)

sys.exit(0)