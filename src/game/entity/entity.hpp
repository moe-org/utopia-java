/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer Entity define header file
 * Using MIT License
 * Copyright (c) 2020-2021 Moe-Org 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
#pragma once

#include <cstdint>
#include <memory>

namespace utopia {

	/// @brief 实体类id类型
	using EntityIdType = uint64_t;

	/// @brief 实体类接口
	class Entity {
	  public:
		Entity()		  = default;
		virtual ~Entity() = 0;

		/// @brief			获取实体的唯一id
		/// @return			id值
		virtual EntityIdType get_entity_id() const = 0;

		/// @brief			比较两个实体是否相等
		/// @param other	另一个实体
		/// @return			如果相等，返回true，否则false
		virtual bool operator==(const Entity &other) const = 0;

		/// @brief
		/// 复制自己
		/// @return 复制过后的自己
		virtual std::unique_ptr<Entity> clone() const = 0;

		/// @brief			判断实体是否可通过
		/// @return			如果实体可通过，返回true，否则false
		virtual bool accessible() const = 0;

		/// @brief			判断实体是否可碰撞
		/// @return			如果实体可碰撞，返回true，否则false
		virtual bool collideable() const = 0;

		/// @brief			实体更新
		virtual void update() = 0;

	  private:
	};

} // namespace utopia
