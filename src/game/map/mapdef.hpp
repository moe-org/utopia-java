/////////////////////////////////////////////////
/// @file           game/map/mapdef.hpp
/// @author         moe-org
/// @brief          地图基础数据类型定义头文件
/// @copyright      MIT License
/////////////////////////////////////////////////
#pragma once

#include <cstdint>

namespace utopia {

	/// @brief	Pos类型
	using PosType = int64_t;

	/// @brief  世界类型
	using WorldIdType = uint64_t;

	/// @brief 用于储存2d坐标的结构体
	struct FlatPos {
		PosType x { 0 };
		PosType y { 0 };

		FlatPos(const PosType x, const PosType y) noexcept;
	};


	/// @brief 用于储存3d坐标的结构体
	struct Pos {
		PosType x { 0 };
		PosType y { 0 };
		PosType z { 0 };

		Pos(const PosType x, const PosType y, const PosType z) noexcept;

		/// @brief		降级到2d坐标
		/// @return		使用此类信息构造一个2d坐标，忽略z轴
		inline FlatPos downgrade() const noexcept {
			FlatPos flat(x, y);
			return flat;
		}
	};


	/// @brief  用于储存世界坐标的结构体
	struct WorldPos {
		PosType		x { 0 };
		PosType		y { 0 };
		PosType		z { 0 };
		WorldIdType id { 0 };

		WorldPos(const WorldIdType world_id,
				 const PosType	   x,
				 const PosType	   y,
				 const PosType	   z) noexcept;

		/// @brief		降级到3d坐标
		/// @return		使用此类信息构造一个3d坐标，忽略world id
		inline Pos downgrade() const noexcept {
			Pos pos(x, y, z);
			return pos;
		}
	};


	/// @brief 标准地图层X轴大小
	constexpr const PosType STANDARD_BLOACK_FLOOR_X = 64;

	/// @brief 标准地图层Y轴大小
	constexpr const PosType STANDARD_BLOACK_FLOOR_Y = 64;

	/// @brief 标准地面z轴
	constexpr const PosType STANDARD_GROUND_Z = 0;

	/// @brief 标准世界X轴区域数量
	constexpr const PosType STANDARD_WORLD_X_AREAS = 1024;

	/// @brief 标准世界Y轴区域数量
	constexpr const PosType STANDARD_WORLD_Y_AREAS = 1024;


} // namespace utopia
