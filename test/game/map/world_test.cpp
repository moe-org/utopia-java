/////////////////////////////////////////////////
/// @file           game/map/world_test.cpp
/// @author         moe-org
/// @brief          世界测试
/// @copyright      MIT License
/////////////////////////////////////////////////

#include "game/map/world.hpp"
#include <gtest/gtest.h>
#include <set>
#include <utility>

using namespace utopia;


class WorldTest : public testing::Test {
  protected:
	World world { FlatPos(2, 2) };
};

// 遍历访问测试
TEST_F(WorldTest, AccessTest) {
	auto					 x_range = world.get_x_range();
	auto					 y_range = world.get_y_range();

	// 确保访问的不是同一块block
	std::set<utopia::Block*> ptrs;

	for(PosType x = x_range.first; x <= x_range.second; x++) {
		for(PosType y = y_range.first; y <= y_range.second; y++) {
			auto result = world.get_block_by_pos(Pos(x, y, 0));

			// 确保是有效值
			EXPECT_TRUE(result.has_value());

			// 检查指针
			auto found = ptrs.find(result.value().get());

			EXPECT_TRUE(found == ptrs.end());

			ptrs.insert(result.value().get());
		}
	}
}


// 错误访问测试
TEST_F(WorldTest, AccessNullTest) {
	auto x_range = world.get_x_range();
	auto y_range = world.get_y_range();

	for(PosType x = x_range.second; x <= x_range.second*2; x++) {
		for(PosType y = y_range.first; y >= y_range.first*2; y--) {
			auto result = world.get_block_by_pos(Pos(x, y, 0));
			EXPECT_FALSE(result.has_value());
		}
	}
}

