/////////////////////////////////////////////////
/// @file           game/map/block_floor_test.cpp
/// @author         moe-org
/// @brief          世界测试
/// @copyright      MIT License
/////////////////////////////////////////////////

#include "game/map/block_floor.hpp"
#include <gtest/gtest.h>
#include <set>
#include <utility>

using namespace std;
using namespace utopia;


class BlockFloorTest : public testing::Test {
  protected:
	BlockFloor block_floor;
};

// 遍历访问测试
TEST_F(BlockFloorTest, AccessTest) {
	for(PosType x = 0; x < STANDARD_BLOACK_FLOOR_X; x++)
		for(PosType y = 0; y < STANDARD_BLOACK_FLOOR_Y; y++) {
			auto result = block_floor.get_block_by_pos(FlatPos(x, y));

			EXPECT_TRUE(result.has_value());
		}
}

// 错误的遍历测试
TEST_F(BlockFloorTest, AssessNullTest) {
	for(PosType x = -1; x > -STANDARD_BLOACK_FLOOR_X; x--)
		for(PosType y = STANDARD_BLOACK_FLOOR_Y;
			y < STANDARD_BLOACK_FLOOR_Y * 2;
			y++) {
			auto result = block_floor.get_block_by_pos(FlatPos(x, y));

			EXPECT_FALSE(result.has_value());
		}
}



