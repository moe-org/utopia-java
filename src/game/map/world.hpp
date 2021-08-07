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
#include <exception>
#include <vector>

namespace utopia {
	/// @class      World world.hpp game/map/world.hpp
	/// @brief      世界类
	class World {
	  public:
		World()					   = delete;
		~World();

		World(const World& origin) = delete;
		World& operator=(const World& origin) = delete;

		World(World&& origin) noexcept;
		World& operator=(World&& origin) noexcept;

		/// @brief
		/// 构造一个新世界
		/// @param quadrant_size 象限大小。共4个象限。单位Area
		/// @note 将会填充一个正的x轴和y轴
		/// 
		/// @exception WorldConstructorException 如果FlatPos中带负数，则抛出
		World(const FlatPos& quadrant_size);

		/// @brief 通过坐标获取地图块
		/// @param pos 坐标
		/// @return 获取到的地图块。如果坐标不在范围内返回nullopt
		std::optional<std::shared_ptr<Block>> get_block_by_pos(const Pos& pos);

		/// @brief
		/// 获取x轴范围
		/// @return 返回x轴范围。first为x轴最小值。second为x轴最大值。
		/// @note x轴等于max+1是因为构造函数扩展了x轴的正半轴
		/// 即
		///                    构造函数自己扩展的X轴
		///   ┌─────────────┐    │
		///   │ 象限大小    │    │
		///   │ 构造函数参数↓  ↓
		///   │   ┌─────────┬─────────┬─────────┐
		///   │   │         │         │         │
		///   │   │  Area   │  Area   │   Area  │
		///   │   │         │         │         │
		///   │   │         │         │         │
		///   │   │         │         │         │
		///   └─→├─────────┼─────────┼─────────┤
		///       │  Area   │  Area   │   Area  │
		///       │         │         │         │
		/// 构──→│         │ (0,0)   │         │
		/// 造    │         │Origin   │         │
		/// 函    │         │↙       │         │
		/// 数    ├────────十─────────┼─────────┤
		/// 扩    │         │         │         │
		/// 展    │         │         │         │
		/// 的    │  Area   │   Area  │  Area   │
		/// Y     │         │         │         │
		/// 轴    │         │         │         │
		///       └─────────┴─────────┴─────────┘
		inline std::pair<PosType, PosType> get_x_range() {
			return std::make_pair(
				this->x_min_ * STANDARD_BLOACK_FLOOR_X,
								//     加上扩展轴↓            正半轴下标起始为0↓
								  (this->x_max_ + 1) * STANDARD_BLOACK_FLOOR_X - 1);
		}

		/// @brief
		/// 获取y轴范围
		/// @return 返回y轴范围。first为y轴最小值。second为y轴最大值。
		/// @note y轴等于max+1是因为构造函数扩展了y轴的正半轴
		/// 即
		///                    构造函数自己扩展的X轴
		///   ┌─────────────┐    │
		///   │ 象限大小    │    │
		///   │ 构造函数参数↓  ↓
		///   │   ┌─────────┬─────────┬─────────┐
		///   │   │         │         │         │
		///   │   │  Area   │  Area   │   Area  │
		///   │   │         │         │         │
		///   │   │         │         │         │
		///   │   │         │         │         │
		///   └─→├─────────┼─────────┼─────────┤
		///       │  Area   │  Area   │   Area  │
		///       │         │         │         │
		/// 构──→│         │ (0,0)   │         │
		/// 造    │         │Origin   │         │
		/// 函    │         │↙       │         │
		/// 数    ├────────十─────────┼─────────┤
		/// 扩    │         │         │         │
		/// 展    │         │         │         │
		/// 的    │  Area   │   Area  │  Area   │
		/// Y     │         │         │         │
		/// 轴    │         │         │         │
		///       └─────────┴─────────┴─────────┘
		inline std::pair<PosType, PosType> get_y_range() {
			return std::make_pair(this->y_min_ * STANDARD_BLOACK_FLOOR_X,
								//     加上扩展轴↓            正半轴下标起始为0↓
								  (this->y_max_ + 1) * STANDARD_BLOACK_FLOOR_X - 1);
		}

		/// @brief 判断坐标是否在世界坐标范围内
		/// @param pos 坐标
		/// @return 如果在范围内，返回true，否则返回false
		inline bool is_within_spoce(const FlatPos& pos) {
			auto x = get_x_range();
			auto y = get_y_range();

			return pos.x >= x.first && pos.x <= x.second && pos.y >= y.first
				   && pos.y <= y.second;
		}

	  private:
		std::mutex locker_; ///< 锁

		PosType	   x_min_ { 0 }; ///< x轴最小大小
		PosType	   x_max_ { 0 }; ///< x轴最大大小
		PosType	   y_min_ { 0 }; ///< y轴最小大小
		PosType	   y_max_ { 0 }; ///< y轴最大大小

		std::unique_ptr<std::unique_ptr<Area>[]> areas_; ///< 区域
		PosType areas_length_ { 0 };					 ///< 最大索引大小

		/// @brief
		/// 使用areas坐标获取数组索引
		/// @param pos arae坐标
		/// @return 数组索引
		PosType get_index_by_area(const FlatPos& pos);
	};

	/// @brief 世界构造异常		\n
	/// 构造世界出现错误时抛出
	class WorldConstructorException : public std::exception {
	  public:
		WorldConstructorException(std::string because,const FlatPos& pos);

		virtual const char* what() noexcept { 
			return strs.c_str();
		}

	  private:
		std::string strs;
	};


} // namespace utopia
