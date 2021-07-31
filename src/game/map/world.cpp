/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer world source file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include <assert.h>
#include "world.hpp"
#include "block_floor.hpp"
#include "map.hpp"

using namespace utopia;

namespace {
	/// @brief				转换二维数组的下标到一维数组
	/// @param x_size		
	/// @param index_x 
	/// @param index_y 
	/// @return 
	[[nodiscard]] constexpr inline
		map_size get_index_of_2d_array(
			const map_size& x_size,
			const map_size& index_x,
			const map_size& index_y) noexcept {
		return x_size * index_x + index_y;
	}
}

World::World(const Pos2d& sized, const world_id_type id) 
	: id_(id),
	x_range_(0,sized.x),
	y_range_(0,sized.y), 
	safe_check_(sized.x * sized.y){
	
	assert(sized.x != 0);
	assert(sized.y != 0);

	// 分配内存
	areas_ = 
		std::unique_ptr<std::atomic<std::shared_ptr<utopia::Area>>[]>
		(new std::atomic<std::shared_ptr<Area>>[safe_check_]);

	// 初始化
	for (map_size index = 0; index < safe_check_; index++) {
		auto new_value = std::make_shared<Area>();
		areas_[index].store(new_value);
	}
	
	return;
}

world_id_type World::get_id() {
	return id_;
}

std::tuple<std::tuple<map_size, map_size>, std::tuple<map_size, map_size>>
World::get_pos_range() {
	return std::tuple(x_range_, y_range_);
}


std::shared_ptr<Block> World::load_block(const Pos3d& pos) {
	// 坐标too big
	if (pos.x >= std::get<1>(x_range_) || pos.y >= std::get<1>(y_range_)) {
		throw std::out_of_range("World::load_block pos out of range.");
	}
	else {
		// 获取坐标
		auto index = get_index_of_2d_array(
			std::get<1>(x_range_),
			static_cast<map_size>(std::floor(pos.x / STANDARD_BLOCK_FLOOR_X)),
			static_cast<map_size>(std::floor(pos.y / STANDARD_BLOCK_FLOOR_Y)));

		// checked again
		if (index == 0 || index >= safe_check_) {
			throw std::out_of_range("World::load_block pos out of range. After convert.");
		}

		// 获取坐标	↓ std::atomic<std::shared_ptr<utopia::Area>>[]
		auto ptr = areas_[index].load();

		// 获取方块
		//					↓ this function is thread safety
		auto block = ptr->get_block(
			std::move(Pos3d(
				pos.x % STANDARD_BLOCK_FLOOR_X,
				pos.y % STANDARD_BLOCK_FLOOR_Y,
				pos.z)));

		return block;
	}
}
