package com.mygdx.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.GameEngine;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setWindowedMode(1280, 720);
		config.setResizable(false);
		//config.setFullscreenMode(dm);
		config.setForegroundFPS(60);
		config.setTitle("AnimeSwamp");

		new Lwjgl3Application(new GameEngine(), config);
	}
}
