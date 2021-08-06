/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer main test cpp
 * Using MIT License
 * Copyright (c) 2020-2021 Moe-Org 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include <gtest/gtest.h>

TEST(UtopiaServer, FirstTest) {
  EXPECT_STRNE("hello", "world");
  EXPECT_EQ(7 * 6, 42);
}

