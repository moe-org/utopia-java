/////////////////////////////////////////////////
/// @file           game/map/mapdef.cpp
/// @author         moe-org
/// @brief          地图基础定义实现文件
/// @copyright      MIT License
/////////////////////////////////////////////////

#include "mapdef.hpp"

using namespace utopia;

WorldPos::WorldPos(const WorldIdType world_id,
				   const PosType	 x,
				   const PosType	 y,
				   const PosType	 z) noexcept {
	this->x	 = x;
	this->y	 = y;
	this->z	 = z;
	this->id = world_id;
}

Pos::Pos(const PosType x, const PosType y, const PosType z) noexcept {
	this->x = x;
	this->y = y;
	this->z = z;
}

FlatPos::FlatPos(const PosType x, const PosType y) noexcept {
	this->x = x;
	this->y = y;
}
