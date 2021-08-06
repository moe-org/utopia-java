/////////////////////////////////////////////////
/// @file           main.cc
/// @author         moe-org
/// @brief          游戏主源文件
/// @copyright      MIT License
/////////////////////////////////////////////////
// NOTE:这个文件不会成为测试可执行文件UtopiaServer-Test的一部分
// 因其使用了.cc后缀，不被Cmake捕获
// 仅作为main定义文件使用

#include "logo.hpp"
#include <backward/backward.hpp>
#include <iostream>
#include <string>

using namespace std;
using namespace utopia;

/// @brief			入口函数
/// @param argc		命令行参数数量
/// @param argv		命令行参数
/// @return			程序返回值
int main(int argc, char* argv[]) {
	print_logo();
	throw 1;
	while(true) {}


	return 0;
}
