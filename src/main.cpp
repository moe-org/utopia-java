/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer main source cpp
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
#include <string>
#include <iostream>
#include "config.hpp"
#include "logo.hpp"
#include "game/map/block.hpp"
#include "game/entity/entity.hpp"
#include "game/map/world.hpp"
#include <mimalloc/include/mimalloc-new-delete.h>
#include <backward-cpp/backward.hpp>

using namespace std;
using namespace utopia;

/// @brief			入口函数
/// @param argc		命令行参数数量
/// @param argv		命令行参数
/// @return			程序返回值
int main(int argc, char* argv[]) {
	print_logo();

	World world(Pos2d(1024, 1024), 1);

	
	while(true){}


	return 0;
}

