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
	// 加锁
	std::scoped_lock lock(origin.current_locker_,
						  origin.next_locker_,
						  this->current_locker_,
						  this->next_locker_);


	// 移动实体列表
	this->next_entities_	= std::move(origin.next_entities_);
	this->current_entities_ = std::move(origin.current_entities_);

	// 清空origin
	origin.current_entities_.clear();
	origin.next_entities_.clear();

	return *this;
}

void Block::update() {
	// 加锁
	std::lock_guard<std::mutex> lock(this->current_locker_);

	// 循环更新
	for(auto& entity : current_entities_) {
		entity->update();
	}

	return;
}

void Block::update_buffer() {
	// 加锁
	std::scoped_lock locker(this->current_locker_, this->next_locker_);

	// 交换缓冲区
	current_entities_.swap(next_entities_);

	return;
}

void Block::add(std::shared_ptr<Entity> entity) {
	// 加锁
	std::lock_guard<std::mutex> lock(this->next_locker_);

	// 加入到待添加列表
	this->next_entities_.push_back(entity);

	return;
}

void Block::remove(std::shared_ptr<utopia::Entity> entity, bool remove_all) {
	// 加锁
	std::lock_guard<std::mutex> locker(this->next_locker_);

	// 删除
	for(auto it = next_entities_.begin(); it != next_entities_.end(); it++) {
		if(it->operator->()->operator==(*entity.get())) {
			next_entities_.erase(it);

			if(remove_all) {
				// 继续删除
				it = next_entities_.begin();
			} else {
				// 只删除一个
				return;
			}
		}
	}
}
