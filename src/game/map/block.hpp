/////////////////////////////////////////////////
/// @file           game/map/block.hpp
/// @author         moe-org
/// @brief          地图块的声明头文件
/// @copyright      MIT License
/////////////////////////////////////////////////
#pragma once

#include "../entity/entity.hpp"
#include <memory>
#include <mutex>
#include <vector>

namespace utopia {

	/// @brief 一个地图块
	class Block {
	  public:
		Block()					   = default;
		~Block()				   = default;

		Block(const Block& origin) = delete;
		Block& operator=(const Block& origin) = delete;

		Block(Block&& origin) noexcept;
		Block& operator=(Block&& origin) noexcept;

		/// @brief 更新地图块
		void update();

		/// @brief 更新缓冲区
		void update_buffer();

		/// @brief 添加实体
		/// @param entity 要添加的实体
		/// @attention 将会应用修改到缓冲区。待update_buffer后生效
		void add(std::shared_ptr<Entity> entity);

		/// @brief 删除实体
		/// @param entity 待删除的实体。将会删除operator==返回true的匹配实体。
		/// @param remove_all 如果设置为true，则删除所有匹配的实体。否则删除一个。
		/// @attention 将会应用修改到缓冲区。待update_buffer后生效
		void remove(std::shared_ptr<Entity> entity, bool remove_all);

		/// @brief
		/// 获取所有缓冲区中实体
		/// @return 获取所有实体
		/// @attention 获取的是缓冲区中的实体
		std::vector<std::shared_ptr<Entity>> get_all();


	  private:
		/// @brief 当前帧锁
		std::mutex current_locker_ {};

		/// @brief 下一帧锁
		std::mutex next_locker_ {};

		/// @brief 当前帧实体列表
		std::vector<std::shared_ptr<Entity>> current_entities_;

		/// @brief 下一帧实体列表
		std::vector<std::shared_ptr<Entity>> next_entities_;
	};

} // namespace utopia
