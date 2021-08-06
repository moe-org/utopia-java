/////////////////////////////////////////////////
/// @file           game/map/world.cpp
/// @author         moe-org
/// @brief          游戏世界的实现
/// @copyright      MIT License
/////////////////////////////////////////////////


#include "world.hpp"

using namespace utopia;

World::World(World&& origin) noexcept {
	*this = std::move(origin);
}


World& World::operator=(World&& origin) noexcept {
	std::scoped_lock locker(this->locker_, origin.locker_);

	this->areas_ = std::move(origin.areas_);
	this->x_max_ = std::move(origin.x_max_);
	this->y_max_ = std::move(origin.y_max_);
	this->x_min_ = std::move(origin.x_min_);
	this->y_min_ = std::move(origin.y_min_);


	return *this;
}

World::World(const FlatPos& quadrant_size) {
	std::lock_guard<std::mutex> locker(this->locker_);

	if(quadrant_size.x <= 0 || quadrant_size.y <= 0) {
		throw std::string("象限大小小于等于0!");
	}

	// 设置大小
	this->x_max_   = quadrant_size.x;
	this->x_min_   = -quadrant_size.x;
	this->y_max_   = quadrant_size.y;
	this->y_min_   = -quadrant_size.y;

	PosType x_size = quadrant_size.x * 2 + 1;
	PosType y_size = quadrant_size.y * 2 + 1;

	// 初始化
	this->areas_ = new std::unique_ptr<Area>[x_size * y_size];
}
