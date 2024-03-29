//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The settings.gradle is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

rootProject.name = "utopia"

// core项目作为核心依赖
include("core")

// desktop和server项目作为输出
include("desktop")
include("server")
