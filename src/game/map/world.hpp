/////////////////////////////////////////////////
/// @file           game/map/world.hpp
/// @author         moe-org
/// @brief          游戏世界定义文件
/// @copyright      MIT License
/////////////////////////////////////////////////

#include "area.hpp"
#include "mapdef.hpp"
#include <memory>
#include <mutex>
#include <optional>
#include <vector>

namespace utopia {
	/// @class      World world.hpp game/map/world.hpp
	/// @brief      世界类
	class World {
	  public:
		World()					   = delete;
		~World()				   = delete;

		World(const World& origin) = delete;
		World& operator=(const World& origin) = delete;

		World(World&& origin) noexcept;
		World& operator=(World&& origin) noexcept;

		/// @brief
		/// 构造一个新世界
		/// @param quadrant_size 象限大小。共4个象限
		World(const FlatPos& quadrant_size);

		/// @brief 通过坐标获取地图块
		/// @param pos 坐标
		/// @return 获取到的地图块。如果坐标不在范围内返回nullopt
		/// @bug 暂未实现
		std::optional<std::shared_ptr<Block>>
		get_block_by_pos(const FlatPos& pos);

	  private:
		std::mutex			   locker_ {}; ///< 锁

		PosType				   x_min_ { 0 }; ///< x轴最小大小
		PosType				   x_max_ { 0 }; ///< x轴最大大小
		PosType				   y_min_ { 0 }; ///< y轴最小大小
		PosType				   y_max_ { 0 }; ///< y轴最大大小

		std::unique_ptr<Area>* areas_; ///< 区域
	};
} // namespace utopia
