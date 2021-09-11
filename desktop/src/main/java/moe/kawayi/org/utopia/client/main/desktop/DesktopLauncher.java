//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopLauncher.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.client.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import moe.kawayi.org.utopia.client.main.ClientMain;

/**
 * 桌面启动器
 */
public class DesktopLauncher {

	/**
	 * 桌面入口函数
	 * @param arg 命令行参数
	 */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ClientMain(), config);
	}
}
