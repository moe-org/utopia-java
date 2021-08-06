/////////////////////////////////////////////////
/// @file           game/map/area.cpp
/// @author         moe-org
/// @brief          区域实现源文件
/// @copyright      MIT License
/////////////////////////////////////////////////

#include "area.hpp"
#include "block.hpp"
#include "block_floor.hpp"
#include "mapdef.hpp"
#include <map>
#include <memory>
#include <mutex>
#include <optional>

using namespace utopia;

Area::Area(Area&& origin) noexcept {
	*this = std::move(origin);
}

Area& Area::operator=(Area&& origin) noexcept {
	std::scoped_lock lock(origin.locker_, this->locker_);

	this->ground_ = std::move(origin.ground_);
	this->height_ = std::move(origin.height_);

	return *this;
}


std::optional<std::shared_ptr<Block>> Area::get_block_by_pos(const Pos& pos) {
	if(pos.z != STANDARD_GROUND_Z) {
		// 其他高度层，加锁
		std::lock_guard<std::mutex> locker(this->locker_);

		// 寻找
		auto found = height_.find(pos.z);

		if(found != height_.cend()) {
			// 找到，直接使用
			return found->second->get_block_by_pos(pos.downgrade());
		} else {
			// 未找到，插入新高度层
			auto new_height = std::make_shared<BlockFloor>();

			auto block		= new_height->get_block_by_pos(pos.downgrade());

			height_.insert(std::make_pair(pos.z, new_height));

			return block;
		}
	} else {
		// 是地面
		// 直接返回
		return ground_.get_block_by_pos(pos.downgrade());
	}
}
