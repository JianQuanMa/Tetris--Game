package com.tetris.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.tetris.config.GameConfig;

/*
 * FrameUtil class provides utility use for the ui
 */
public class FrameUtil {
	// setFrameCenter method sets the given frame the center of the screen
	public static void setFrameCenter(JFrame jf) {
		// create a ToolKit object
		Toolkit tk = Toolkit.getDefaultToolkit();
		// create a dimension object
		Dimension screen = tk.getScreenSize();
		// set the location of the game frame
		jf.setLocation((screen.width - jf.getWidth()) >> 1,
				(screen.height - jf.getHeight()) >> 1 - GameConfig.getFRAME_CONFIG().getLIFTER());
	}

}
