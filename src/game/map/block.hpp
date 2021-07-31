/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer block define header file
 * Using GOSCPSv3 License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
#pragma once
#include <vector>
#include <mutex>
#include <utility>
#include <optional>
#include <memory>
#include <ranges>
#include <iterator>
#include <functional>
#include "../entity/entity.hpp"

namespace utopia {

	/// @brief 方块类
	class Block {
	public:
		Block() = default;
		~Block() = default;

		// 禁止拷贝
		Block(const Block& origin) = delete;
		Block& operator=(const Block& origin) = delete;

		/// @brief				移动赋值函数
		/// @param origin	
		/// @return			
		Block& operator=(Block&& origin) noexcept;

		/// @brief				移动构造函数
		/// @param origin 
		Block(Block&& origin) noexcept;

		/// @brief				增加实体
		/// @param Entity		要增加的实体
		/// @param count		要增加的实体的数量
		void add_entity(std::unique_ptr<utopia::Entity>&& entity);

		/// @brief				查找block中的实体
		/// @param entity		要查找的实体
		/// @return				返回entity.operator==(const utopia::Entity& entity)为true的所有实体
		std::optional<std::vector<std::weak_ptr<utopia::Entity>>> find(const utopia::Entity& entity);

		/// @brief				查找block中的实体
		/// @param entity		要查找的实体
		/// @return				返回entity.operator==(const utopia::Entity& entity)为true的所有实体
		std::optional<std::vector<std::weak_ptr<utopia::Entity>>> find(const entity_id entity_id);

		/// @brief				删除entity.operator==(const utopia::Entity& entity)为true的实体
		/// @param entity		要删除的实体
		void remove(const utopia::Entity& entity);

	private:
		std::mutex locker_;
		std::vector<std::shared_ptr<utopia::Entity>> entities_;
		bool collideable_ = false;
		bool accessible_ = true;

		/// @brief			Will return results that cmper return true
		/// @param cmper 
		/// @return 
		std::optional<std::vector<std::weak_ptr<utopia::Entity>>> find_
		(std::function<bool(std::shared_ptr<Entity>& other)> cmper);
	};



}