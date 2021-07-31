/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer block_floor define header file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
#pragma once

#include <cstdint>
#include <array>
#include <memory>
#include "map.hpp"
#include "Block.hpp"

namespace utopia {

	/// @brief  方块平面类	线程安全
	class BlockFloor {
	public:
		BlockFloor();
		~BlockFloor() = default;

		BlockFloor(const BlockFloor& origin) = delete;
		BlockFloor& operator=(const BlockFloor& origin) = delete;

		BlockFloor& operator=(BlockFloor&& origin) noexcept;
		BlockFloor(BlockFloor&& origin) noexcept;

		/// @brief			访问方块
		/// @param index	索引值
		/// @return			方块的强引用
		std::shared_ptr<Block> get_block(const Pos2d& index);

	private:
		// [x][y]
		std::array<std::array<std::atomic<std::shared_ptr<Block>>, STANDARD_BLOCK_FLOOR_Y>,STANDARD_BLOCK_FLOOR_X> pool_;
	};

}