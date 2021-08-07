/////////////////////////////////////////////////
/// @file           game/map/world.cpp
/// @author         moe-org
/// @brief          游戏世界的实现
/// @copyright      MIT License
/////////////////////////////////////////////////


#include "world.hpp"
#include <cassert>
#include <sstream>
#include <cmath>

using namespace utopia;

WorldConstructorException::WorldConstructorException(std::string because,const FlatPos& cause) {
	std::stringstream out;
	out << "构造世界异常!\n"
		<< because << "\n"
		<< "数据: x=" << cause.x << " y= " << cause.y;
	strs = out.str();
}

World::World(World&& origin) noexcept {
	*this = std::move(origin);
}

World::~World() {
	this->locker_.lock();

}


World& World::operator=(World&& origin) noexcept {
	std::scoped_lock locker(this->locker_, origin.locker_);

	this->areas_		= std::move(origin.areas_);
	this->areas_length_ = std::move(origin.areas_length_);

	this->x_max_		= std::move(origin.x_max_);
	this->y_max_		= std::move(origin.y_max_);
	this->x_min_		= std::move(origin.x_min_);
	this->y_min_		= std::move(origin.y_min_);

	return *this;
}

World::World(const FlatPos& quadrant_size) {
	if(quadrant_size.x <= 0 || quadrant_size.y <= 0) {
		throw WorldConstructorException("象限大小必须大于0!", quadrant_size);
	}

	// 设置大小
	this->x_max_ = quadrant_size.x;
	this->x_min_ = -quadrant_size.x;
	this->y_max_ = quadrant_size.y;
	this->y_min_ = -quadrant_size.y;

	//                                     扩展  ↓
	const PosType x_size = quadrant_size.x * 2 + 1;
	const PosType y_size = quadrant_size.y * 2 + 1;
	this->areas_length_	 = x_size * y_size;

	// 初始化
	this->areas_ = std::unique_ptr<std::unique_ptr<Area>[]>(
		new std::unique_ptr<Area>[areas_length_]());

	for (PosType ptr = 0; ptr < areas_length_; ptr++) {
		areas_.get()[ptr].reset(new Area());
	}

	return;
}

std::optional<std::shared_ptr<Block>> World::get_block_by_pos(const Pos& pos) {
	// 判断坐标范围
	if(!is_within_spoce(pos.downgrade())) {
		// 不在范围内直接返回
		return std::nullopt;
	}

	// 获取areas索引
	auto index = get_index_by_area(FlatPos(
		std::floor(static_cast<long double>(pos.x)
				   / static_cast<long double>(STANDARD_BLOACK_FLOOR_X)),
		std::floor(static_cast<long double>(pos.y)
				   / static_cast<long double>(STANDARD_BLOACK_FLOOR_Y))));

	// 获取areas内索引
	PosType x_index = std::abs(pos.x) % STANDARD_BLOACK_FLOOR_X;
	PosType y_index = std::abs(pos.y) % STANDARD_BLOACK_FLOOR_Y;

	if(pos.x < 0 && x_index > 0) {
		x_index = STANDARD_BLOACK_FLOOR_X - x_index;
	}

	if(pos.y < 0 && y_index > 0) {
		y_index = STANDARD_BLOACK_FLOOR_Y - y_index;
	}

	return areas_[index]->get_block_by_pos(Pos(x_index, y_index, pos.z));
}


PosType World::get_index_by_area(const FlatPos& pos) {
	// 获取x轴长度
	//                                             需要加上扩展X轴 ↓ 见get_x_range,get_y_range函数声明
	const PosType y_length = std::abs(y_min_) + std::abs(y_max_) + 1;

	// 计算
	auto process = [](PosType num, PosType min) {
		if(num == 0) {
			return std::abs(min);
		} else if(num > 0) {
			return num + std::abs(min);
		} else /*(num < 0)*/ {
			return std::abs(min) - std::abs(num);
		}
	};

	// 获取x和y的索引
	PosType x_index = process(pos.x, this->x_min_);
	PosType y_index = process(pos.y, this->y_min_);

	// 获取最终索引
	PosType index = x_index * y_length + y_index;

	// 这个索引应该在安全范围内
	// 输入的area坐标应该是安全的
	assert(index >= 0);
	assert(index < areas_length_);

	return index;
}
