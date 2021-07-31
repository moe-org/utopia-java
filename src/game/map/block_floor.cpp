/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer block_floor source file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include "block_floor.hpp"
#include "map.hpp"
#include <array>
#include <memory>

using namespace utopia;

BlockFloor::BlockFloor(BlockFloor&& origin) noexcept { 
	this->operator=(std::move(origin));
	return;
}

BlockFloor& BlockFloor::operator=(BlockFloor&& origin) noexcept {
	for (auto x = 0; x < pool_.size(); x++) {
		for (auto y = 0; y < pool_[x].size(); y++) {
			pool_[x][y].store(std::move(origin.pool_[x][y]));
		}
	}

	return *this;
}

BlockFloor::BlockFloor() {
	for (auto x = 0; x < pool_.size(); x++) {
		for (auto y = 0; y < pool_[x].size(); y++) {
			pool_[x][y] = std::make_shared<Block>();
		}
	}
}

std::shared_ptr<Block> BlockFloor::get_block(const Pos2d& index) {

	if (
		index.y >= STANDARD_BLOCK_FLOOR_Y
		|| 
		index.x >= STANDARD_BLOCK_FLOOR_X
		||
		index.y < 0
		||
		index.x < 0
		)
		throw std::out_of_range("The BlockFloor::get_block index out of range");

	return pool_[index.x][index.y];
}