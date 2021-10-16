//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopLauncher.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;

import java.util.Objects;

/**
 * 桌面启动器
 */
public class DesktopLauncher {

	/**
	 * 是否使用opengl3 es
	 */
	private static boolean useOpenGL3 = false;

	/**
	 * opengl3 es版本号
	 */
	private static int gl3Major = 3;


	/**
	 * opengl3 es版本号
	 */
	private static int gl3Minor = 2;

	/**
	 * 全屏窗口
	 */
	private static boolean fullScreen = false;

	/**
	 * 返回是否使用了Opengl3es
	 * @return	如果使用，返回true，否则返回false
	 */
	public static boolean isUseOpenGL3(){
		return useOpenGL3;
	}

	/**
	 * 返回是否处于全屏模式
	 * @return	如果全屏，返回true，否则返回false
	 */
	public static boolean isFullScreen(){
		return fullScreen;
	}

	/**
	 * 打印帮助
	 */
	private static void printHelp(){
		System.out.println("options:");
		System.out.println("-help                ---show help message then exit");
		System.out.println("-useSmallIcon        ---use small window icon(32x32)");
		System.out.println("-useOpenGL3          ---use OpenGL ES 3 emulation");
		System.out.println("-gl3Major [number]   ---set OpenGL ES 3 major version. default 3");
		System.out.println("-gl3Minor [number]   ---set OpenGL ES 3 minor version. default 2");
		System.out.println("-fullScreen          ---create full screen window");
	}

	/**
	 * 桌面入口函数
	 * @param args 命令行参数
	 */
	public static void main (String[] args) {
		// 一些默认值
		boolean useSmallIcon = false;

		// 解析参数
		int index = 0;
		while(index < args.length){
			var arg = args[index];


			if(Objects.equals(arg, "-gl3Major")){
				index++;

				if(index >= args.length){
					System.err.println("miss option value:".concat(arg));
					System.exit(1);
				}
				else{
					gl3Major = Integer.parseInt(args[index]);
				}
			}
			else if(Objects.equals(arg, "-gl3Minor")){
				index++;

				if(index >= args.length){
					System.err.println("miss option value:".concat(arg));
					System.exit(1);
				}
				else{
					gl3Minor = Integer.parseInt(args[index]);
				}
			}
			else if(Objects.equals(arg,"-useSmallIcon")){
				useSmallIcon = true;
			}
			else if(Objects.equals(arg,"-useOpenGL3")){
				useOpenGL3 = true;
			}
			else if(Objects.equals(arg,"-fullScreen")){
				fullScreen = true;
			}
			else if(Objects.equals(arg,"-help")){
				printHelp();
				System.exit(0);
			}
			else{
				System.err.println("unknown option:".concat(arg));
				System.exit(1);
			}

			index++;
		}


		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("utopia");
		config.setWindowSizeLimits(
				DesktopApplicationListener.CAMERA_DEFAULT_WIDTH,
				DesktopApplicationListener.CAMERA_DEFAULT_HEIGHT,
				-1,
				-1);
		config.setResizable(true);
		config.useOpenGL3(useOpenGL3,gl3Major,gl3Minor);
		config.setWindowIcon(Files.FileType.Local,useSmallIcon ? "./Utopia(32x32).png" : "./Utopia(128x128).png");

		// 是否启用全屏
		if(fullScreen) {
			config.setAutoIconify(true);
			config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		}

		// 启动程序
		new Lwjgl3Application(new DesktopApplicationListener(), config);
	}
}
