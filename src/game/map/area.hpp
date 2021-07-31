/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer area header file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
#pragma once

#include <cstdint>
#include <array>
#include <mutex>
#include <map>
#include "map.hpp"
#include "block_floor.hpp"

namespace utopia {


	/// @brief 区域类
	class Area {
	public:
		Area();
		~Area();

		Area& operator=(Area&& origin) noexcept;
		Area(Area&& origin) noexcept;

		Area(const Area& origin) = delete;
		Area& operator=(const Area& origin) = delete;

		/// @brief			获取方块			线程安全
		/// @param pos		方块的相对位置
		/// @return			获取到的方块
		std::shared_ptr<Block> get_block(const Pos3d& pos);

	private:
		// 地面
		std::unique_ptr<BlockFloor> ground_;
		
		// 其他的高度层
		std::map<utopia::map_size, std::unique_ptr<BlockFloor>> height_;

		// locker
		std::mutex locker_;
	};

}