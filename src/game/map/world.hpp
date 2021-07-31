/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer world header file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
#pragma once

#include <cstdint>
#include <memory_resource>
#include <memory>
#include <optional>
#include "area.hpp"
#include "map.hpp"
#include "block.hpp"

namespace utopia {

	/// @brief				世界标准X区域数量
	constexpr const map_size STANDARD_WORLD_AREA_X = 1024;

	/// @brief				世界标准Y区域数量
	constexpr const map_size STANDARD_WORLD_AREA_Y = 1024;

	/// @brief				世界标准x轴范围
	constexpr const std::tuple<map_size, map_size> STANDARD_WORLD_X_RANGE
	(0,
		1024 * STANDARD_BLOCK_FLOOR_X);

	/// @brief				世界标准y轴范围
	constexpr const std::tuple<map_size, map_size> STANDARD_WORLD_Y_RANGE
	(0,
		1024 * STANDARD_BLOCK_FLOOR_Y);

	/// @brief 世界类
	class World {
	public:
		~World() = default;

		World(const World& origin) = delete;
		World& operator=(const World& origin) = delete;
		World() = delete;

		World(World&& origin) = delete;
		World& operator=(World&& origin) = delete;

		/// @brief			构造一个新世界
		/// @param pos		世界大小，单位Area
		World(const Pos2d& pos,const world_id_type id);

		/// @brief			加载方块
		/// @param pos		加载方块的坐标，如果该区域未被加载，则加载
		/// @return			加载的方块的指针
		/// @exception		std::out_of_range		坐标过大
		std::shared_ptr<Block> load_block(const Pos3d& sized);

		/// @brief			获取坐标范围
		/// @return			((x轴最小索引，x轴最大索引),(y轴最小索引，y轴最大索引))
		std::tuple<std::tuple<map_size, map_size>, std::tuple<map_size, map_size>>
			get_pos_range();

		/// @brief			获取世界id
		/// @return			世界id类型
		world_id_type get_id();

	private:
		// 两轴大小
		const std::tuple<map_size, map_size> x_range_;
		const std::tuple<map_size, map_size> y_range_;
		const map_size safe_check_;

		// 世界id
		const world_id_type id_;

		// 区域
		std::unique_ptr<std::atomic<std::shared_ptr<utopia::Area>>[]> areas_;
	};


}