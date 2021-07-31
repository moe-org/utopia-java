/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer map info source file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


#include "map.hpp"

using namespace utopia;

Pos2d::Pos2d(map_size x, map_size y) noexcept {
	this->x = x;
	this->y = y;
}

 Pos3d::Pos3d(map_size x, map_size y, map_size z) noexcept : Pos2d(x, y)  {
	this->z = z;
}

 Pos2d Pos3d::downgrade() const noexcept {
	 auto result = Pos2d(x, y);
	 return result;
 }

 WorldPos::WorldPos(world_id_type world_id, map_size x, map_size y, map_size z) noexcept
	: Pos3d(x, y, z) {
	this->world_id = world_id;
}

 Pos3d WorldPos::downgrade() const noexcept {
	 auto result = Pos3d(x, y,z);
	 return result;
 }
