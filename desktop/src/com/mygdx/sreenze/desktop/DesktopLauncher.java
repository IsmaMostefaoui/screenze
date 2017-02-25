package com.mygdx.sreenze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.sreenze.ApplicationCore;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;//config.width = 1920;
		config.height = 720;//config.height = 1080;
		new LwjglApplication(new ApplicationCore(), config);

	}
}
