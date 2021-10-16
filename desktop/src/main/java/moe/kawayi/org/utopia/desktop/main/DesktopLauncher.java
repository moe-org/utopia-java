//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The DesktopLauncher.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.desktop.main;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.util.Objects;

/**
 * 桌面启动器
 */
public class DesktopLauncher {

	/**
	 * 打印帮助
	 */
	private static void printHelp(){
		System.out.println("options:");
		System.out.println("-help              ---show help message then exit");
		System.out.println("-useSmallIcon      ---use small window icon(32x32)");
		System.out.println("-width [number]    ---set the window width");
		System.out.println("-height [number]   ---set the window height");
	}

	/**
	 * 桌面入口函数
	 * @param args 命令行参数
	 */
	public static void main (String[] args) {
		// 一些默认值
		int winWidth = 800;
		int winHeight = 480;
		boolean useSmallIcon = false;


		// 解析参数
		int index = 0;
		while(index < args.length){
			var arg = args[index];

			if(Objects.equals(arg, "-width")){
				index++;

				if(index >= args.length){
					System.err.println("miss option value:".concat(arg));
					System.exit(1);
				}
				else{
					winWidth = Integer.parseInt(args[index]);
				}
			}
			else if(Objects.equals(arg,"-height")){
				index++;

				if(index >= args.length){
					System.err.println("miss option value:".concat(arg));
					System.exit(1);
				}
				else{
					winHeight = Integer.parseInt(args[index]);
				}
			}
			else if(Objects.equals(arg,"-help")){
				printHelp();
				System.exit(0);
			}
			else if(Objects.equals(arg,"-useSmallIcon")){
				useSmallIcon = true;
			}
			else{
				System.err.println("unknown option:".concat(arg));
				System.exit(1);
			}

			index++;
		}


		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("utopia");
		config.setWindowSizeLimits(winWidth,winHeight,-1, -1);
		config.setResizable(true);

		config.setWindowIcon(Files.FileType.Local,useSmallIcon ? "./Utopia(32x32).png" : "./Utopia(128x128).png");


		new Lwjgl3Application(new DesktopApplicationListener(), config);
	}
}
