/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia server map datas source file
 * Using MIT License
 * Copyright (c) 2020-2021 Moe-Org 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include "mapdef.hpp"

using namespace utopia;

constexpr WorldPos::WorldPos(const WorldIdType world_id,
							 const PosType	   x,
							 const PosType	   y,
							 const PosType	   z) noexcept {
	this->x	 = x;
	this->y	 = y;
	this->z	 = z;
	this->id = world_id;
}

constexpr Pos::Pos(const PosType x, const PosType y, const PosType z) noexcept {
	this->x = x;
	this->y = y;
	this->z = z;
}

constexpr FlatPos::FlatPos(const PosType x, const PosType y) noexcept {
	this->x = x;
	this->y = y;
}
