#!/usr/bin/python3
# -*- coding: utf-8 -*-
# MIT License
# Copyright (c) 2021 MingMoe

import json
import sys
import urllib.request

TARGET_FILE = "README.md"

AUTO_LIST_BEGIN = "<!---ubadge-auto-list-begin-->"
AUTO_LIST_END = "<!---ubadge-auto-list-end-->"

# 获取参数
if(len(sys.argv) != 2):
    print("Received wrong arguments!");
    sys.exit(1);

check_suite_url = sys.argv[1];

print(f"check suite url:\n{check_suite_url}\n");
print(f"in file:\n{TARGET_FILE}\n");
print(f"from:\n{AUTO_LIST_BEGIN}\n");
print(f"to:\n{AUTO_LIST_END}\n");



# 获取check_suite信息
check_suite_info = urllib.request.urlopen(check_suite_url);

check_suite_json = json.load(check_suite_info)



# 获取check_runs的url
jobs_url = check_suite_json["check_runs_url"]

print(f"check runs url:\n{jobs_url}\n");



# 读取check_runs
contents = urllib.request.urlopen(jobs_url).read();

all_jobs = json.loads(contents)['check_runs'];

print(f"all check runs:\n{all_jobs}");


# 生成一个markdown列表
def generate_markdown_list(texts,jobs):
    begin_index = texts.find(AUTO_LIST_BEGIN);
    end_index = texts.find(AUTO_LIST_END);

    result = "\n| Checking | Status |\n| :-------:|:------:|\n"

    for job in jobs:
        CHECK_NAME = job['name']
        CHECK_STATUS = job['conclusion']

        result += "| " + CHECK_NAME + " | ![" + CHECK_NAME + "](";

        if (CHECK_STATUS == "success"):
            result += "https://img.shields.io/badge/build-passing-green?style=for-the-badge&logo=appveyor";

        elif (CHECK_STATUS == "failure"):
            result += "https://img.shields.io/badge/build-failed-red?style=for-the-badge&logo=appveyor";

        elif (CHECK_STATUS == "cancelled"):
            result += "https://img.shields.io/badge/build-cancelled-lightgrey?style=for-the-badge&logo=appveyor";

        elif (CHECK_STATUS == "skipped"):
            result += "https://img.shields.io/badge/build-skipped-lightgrey?style=for-the-badge&logo=appveyor";

        elif (CHECK_STATUS == "timed_out"):
            result += "https://img.shields.io/badge/build-timed_out-lightgrey?style=for-the-badge&logo=appveyor";

        else:
            result += "https://img.shields.io/badge/build-unknown_status-lightgrey?style=for-the-badge&logo=appveyor";

        result += ") |\n"

    # 替换
    texts = texts[:begin_index+len(AUTO_LIST_BEGIN)] + result + texts[end_index:];

    return texts;


# 读取目标文件
output = "";

with open(TARGET_FILE, "r") as f:
    output = f.read();


# 处理jobs
#for job in all_jobs:
#    print(f"process job:`{job['name']}` status {job['conclusion']}");
#    output = process_text(output,job['name'],job['conclusion']);

# 生成列表
output = generate_markdown_list(output,all_jobs);


# 写入文件
with open(TARGET_FILE, "w") as f:
    f.write(output);