/////////////////////////////////////////////////
/// @file           game/map/block.cpp
/// @author         moe-org
/// @brief          地图块实现源文件
/// @copyright      MIT License
/////////////////////////////////////////////////

#include "block.hpp"
#include <memory>
#include <mutex>

using namespace utopia;

Block::Block(Block&& origin) noexcept {
	*this = std::move(origin);
	return;
}

Block& Block::operator=(Block&& origin) noexcept {
	// 写锁
	std::scoped_lock lock(origin.locker_,
						  this->locker_);

	// 移动实体列表
	this->entities_ = std::move(origin.entities_);

	// 清空origin
	origin.entities_.clear();

	return *this;
}

void Block::update_state() {
	// 写锁
	std::lock_guard lock(this->locker_);

	// 更新实体列表
	for (auto it = entities_.begin(); it != entities_.end();) {
		// 将added的实体变为normal
		if (it->second == BlockEntityState::Added) {
			it->second = BlockEntityState::Normal;
			it++;
		}	
		// 将removed的实体删除
		else if (it->second == BlockEntityState::Removed) {
			entities_.erase(it);
			it = entities_.begin();
		} else {
			it++;
		}
	}

	return;
}

void Block::update() {
	// 上锁
	std::lock_guard lock(this->locker_);

	// 只update状态为normal的实体
	for(auto entity = entities_.begin();entity != entities_.end();entity++){
		BlockEntityState state = entity->second;

		if(state == BlockEntityState::Normal) {
			entity->first->update();
		}
	}
}


void Block::add(std::shared_ptr<Entity> entity) {
	// 写锁
	std::lock_guard lock(this->locker_);

	// 写入
	entities_.push_back(std::make_pair(entity,BlockEntityState::Added));
}

void Block::remove(std::shared_ptr<Entity> entity, bool remove_all) {
	// 写锁
	std::lock_guard lock(this->locker_);

	// 删除
	for(auto it = entities_.begin(); it != entities_.end();) {
		// Normal实体才可删除
		if(it->second == BlockEntityState::Normal &&
			it->first->operator==(*entity.get())) {

			// 修改到Removed
			it->second = BlockEntityState::Removed;

			if (remove_all) {
				it = entities_.begin();
			} else {
				return;
			}
		} else {
			it++;
		}
	}

	return;
}

std::vector<std::shared_ptr<Entity>> Block::get_entities() {
	// 上锁
	std::lock_guard<std::mutex> lock(this->locker_);

	// 读取
	std::vector<std::shared_ptr<Entity>> results(entities_.size());

	for (auto& entity : entities_) {
		if (entity.second == BlockEntityState::Normal) {
			results.push_back(entity.first);
		}
	}

	return results;
}

