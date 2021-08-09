/////////////////////////////////////////////////
/// @file           game/map/block.hpp
/// @author         moe-org
/// @brief          地图块的声明头文件
/// @copyright      MIT License
/////////////////////////////////////////////////
#pragma once

#include "../entity/entity.hpp"
#include <memory>
#include <shared_mutex>
#include <vector>
#include <utility>
#include <atomic>

namespace utopia {

	/// @brief 地图块的实体状态
	enum class BlockEntityState : unsigned short
	{
		Normal,		///< 正常状态
		Removed,	///< 删除状态
		Added,		///< 添加状态
	};

	/// @brief 一个地图块
	class Block {
	  public:
		Block()					   = default;
		~Block()				   = default;

		Block(const Block& origin) = delete;
		Block& operator=(const Block& origin) = delete;

		Block(Block&& origin) noexcept;
		Block& operator=(Block&& origin) noexcept;

		/// @brief 更新地图块内所有状态为Normal的实体。
		void update();

		/// @brief 更新状态。
		/// 将状态为Added的实体设置为Normal，状态为Removed的实体删除。
		void update_state();

		/// @brief 添加实体
		/// @param entity 要添加的实体
		/// @attention 添加的实体状态为Added。
		void add(std::shared_ptr<Entity> entity);

		/// @brief 删除实体
		/// @param entity 待删除的实体。将会删除operator==返回true的匹配实体。
		/// @param remove_all 如果设置为true，则删除所有匹配的实体。否则删除一个。
		/// @attention 将被删除的实体状态设置Removed。
		void remove(std::shared_ptr<Entity> entity, bool remove_all);

		/// @brief	获取所有实体
		/// @return 实体的指针
		/// @attention	将只会获取Normal状态的实体。
		std::vector<std::shared_ptr<Entity>> get_entities();

	  private:
		/// @brief 实体列表读写锁
		std::mutex locker_;

		/// @brief 实体列表
		std::vector
			< std::pair<std::shared_ptr<Entity>, BlockEntityState>>
				entities_;
	};

} // namespace utopia
