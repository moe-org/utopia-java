/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer area source file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include "area.hpp"
#include <mutex>
#include <memory>

using namespace utopia;

Area::Area() = default;
Area::~Area() = default;

Area::Area(Area&& origin) noexcept { 
	this->operator=(std::move(origin)); 
	return;
}

Area& Area::operator=(Area&& origin) noexcept {
	std::lock_guard<std::mutex>locker(locker_);
	std::lock_guard<std::mutex>origin_locker(origin.locker_);
	
	ground_ = std::move(origin.ground_);
	height_ = std::move(origin.height_);

	return *this;
}

std::shared_ptr<Block> Area::get_block(const Pos3d& pos) {

	// 检查高度值
	if (pos.z != utopia::GROUND_HEIGHT_Z) {
		// 需从map读取
		// 加锁
		std::lock_guard<std::mutex>locker(locker_);

		// 查询高度值
		auto found = height_.find(pos.z);

		// 高度已经存在
		if (found != height_.cend()) {
			return found->second->get_block(pos.downgrade());
		}
		// 不存在
		// 构造一个新z轴高度
		else {
			auto new_floor = std::make_unique<BlockFloor>();

			auto ptr = new_floor->get_block(pos.downgrade());

			height_.insert(std::make_pair(pos.z, std::move(new_floor)));

			return ptr;
		}
	}
	else {
		// 高度值为地面
		// 直接返回
		return ground_.get()->get_block(pos.downgrade());
	}
}