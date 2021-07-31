/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer map info define header file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
#pragma once
#include <cstdint>
#include <atomic>

namespace utopia {

	/// @brief 地图坐标的类型
	using map_size = uint64_t;

	/// @brief 世界id类型
	using world_id_type = uint64_t;

	/// @brief 标准方块层X轴大小
	constexpr const map_size STANDARD_BLOCK_FLOOR_X = 64;

	/// @brief 标准方块层Y轴大小
	constexpr const map_size STANDARD_BLOCK_FLOOR_Y = 64;

	/// @brief 地面高度(z轴)
	constexpr const map_size GROUND_HEIGHT_Z = 0;

	/// @brief		2d世界坐标
	struct Pos2d {
		Pos2d(map_size x, map_size y) noexcept;

		/// @brief x轴
		map_size x;
		/// @brief y轴
		map_size y;
	};

	/// @brief		3d世界坐标
	struct Pos3d : public Pos2d {
		Pos3d(map_size x, map_size y, map_size z) noexcept;

		Pos2d downgrade() const noexcept;

		/// @brief z轴
		map_size z;
	};

	/// @brief		世界坐标
	struct WorldPos : public Pos3d {
		WorldPos(world_id_type world_id, map_size x, map_size y, map_size z) noexcept;

		Pos3d downgrade() const noexcept;

		/// @brief 世界id
		world_id_type world_id;
	};

}