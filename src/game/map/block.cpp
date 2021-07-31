/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer block source file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include "block.hpp"
#include "../entity/entity.hpp"
#include <ranges>
#include <optional>
#include <memory>
#include <ranges>
#include <iterator>

using namespace utopia;
using namespace std;

void Block::add_entity(std::unique_ptr<utopia::Entity>&& entity) {
	// lock
	std::lock_guard<std::mutex>lock(locker_);

	// 获取引用计数
	std::shared_ptr<utopia::Entity> shared = std::move(entity);

	if (shared->collideable()) {
		// 不允许一个block中存在两个可碰撞实体
		if (collideable_) {
			throw new std::runtime_error("Try add collideable entity to collideable block.");
		}
		else {
			collideable_ = true;
		}
	}

	// 但允许一个block内存在多个不可通过体
	if (!shared->accessible()) {
		accessible_ = false;
	}

	// 添加实体
	entities_.push_back(shared);

	return;
}

std::optional<std::vector<std::weak_ptr<utopia::Entity>>> Block::find(const utopia::Entity& entity) {
	auto ptr = &entity;
	auto cmp = [ptr](std::shared_ptr<Entity>& other) -> bool
	{ return other->operator==(*ptr); };

	return find_(cmp);
}

std::optional<std::vector<std::weak_ptr<utopia::Entity>>> Block::find(const utopia::entity_id entity_id) {
	auto cmp = [entity_id](std::shared_ptr<Entity>& other) -> bool
	{ return other->get_entity_id() == entity_id; };

	return find_(cmp);
}

// provide search
std::optional<std::vector<std::weak_ptr<utopia::Entity>>> Block::find_(
	std::function<bool(std::shared_ptr<Entity>& other)> cmper) {
	// lock
	std::lock_guard<std::mutex>lock(locker_);

	std::vector<std::weak_ptr<utopia::Entity>> results;

	for (auto& result :
		entities_
		|
		std::ranges::views::filter(cmper)
		) {
		results.push_back(std::weak_ptr(result)); 
	}

	if (results.size() == 0) {
		return std::nullopt;
	}
	else {
		return std::make_optional(results);
	}
}

Block& Block::operator=(Block&& origin) noexcept{
	if (this == &origin)
		return *this;

	// lock 双锁
	std::lock_guard<std::mutex>lock(locker_);
	std::lock_guard<std::mutex>orgigin_lock(origin.locker_);

	// 移动值
	entities_ = std::move(origin.entities_);
	accessible_ = origin.accessible_;
	collideable_ = origin.collideable_;

	return *this;
}

Block::Block(Block&& origin) noexcept {
	*this = std::move(origin);
}

void Block::remove(const utopia::Entity& entity) {
	// 上锁
	std::lock_guard<std::mutex>lock(locker_);

	// 准备一个数以判断是否可通过
	int accessible = 0;

	// 检查
	for (auto begin = entities_.begin(); begin != entities_.end();) {
		if (begin->get()->operator==(entity)) {

			// 更新碰撞状态
			if (begin->get()->collideable()) {
				collideable_ = false;
			}

			// 删除值
			entities_.erase(begin);

			// 继续删除
			accessible = 0;
			begin = entities_.begin();
		}
		else {
			if (begin->get()->accessible())
				accessible++;

			begin++;
		}
	}

	if (accessible == 0)
		accessible_ = false;

	return;
}
