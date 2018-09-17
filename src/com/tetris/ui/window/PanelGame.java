package com.tetris.ui.window;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tetris.config.FrameConfig;
import com.tetris.config.GameConfig;
import com.tetris.config.LayerConfig;
import com.tetris.control.GameControl;
import com.tetris.control.PlayerControl;
import com.tetris.dto.GameDTO;
import com.tetris.ui.IMG_SOUND;
import com.tetris.ui.Layer;

public final class PanelGame extends JPanel {

	private static final long serialVersionUID = 1L;
	// this list of layer contains all layer contained in the frame
	private List<Layer> layerList = null;
	// create a start button
	private JButton BTNStart;
	// create a pause button
	private JButton BTNCC;
	// create a gameControl object
	private GameControl gameControl = null;
	// dimensions of the buttons
	private static final int BTN_W = GameConfig.getFRAME_CONFIG().getButtonConfig().getButtonW();
	private static final int BTN_H = GameConfig.getFRAME_CONFIG().getButtonConfig().getButtonH();
	private static final int BTN_START_X = GameConfig.getFRAME_CONFIG().getButtonConfig().getStartX();
	private static final int BTN_START_Y = GameConfig.getFRAME_CONFIG().getButtonConfig().getStartY();
	private static final int BTN_CONFIG_CREDIT_X = GameConfig.getFRAME_CONFIG().getButtonConfig().getCCX();
	private static final int BTN_CONFIG_CREDIT_Y = GameConfig.getFRAME_CONFIG().getButtonConfig().getCCY();
	Rectangle BTN_START_RECTANGLE = new Rectangle(BTN_START_X, BTN_START_Y, BTN_W, BTN_H);
	Rectangle BTN_CONFIG_CREDIT_RECTANGLE = new Rectangle(BTN_CONFIG_CREDIT_X, BTN_CONFIG_CREDIT_Y, BTN_W, BTN_H);

	public PanelGame(GameDTO dto, GameControl gameControl) {
		this.gameControl = gameControl;
		this.setLayout(null);
		this.initiateLayer(dto);
		this.initButtons();
		this.addKeyListener(new PlayerControl(gameControl, dto));
	}

	// init the buttons
	private void initButtons() {
		// start button
		this.BTNStart = new JButton(IMG_SOUND.resizeIcon(IMG_SOUND.getIMG_START()));
		this.BTNStart.setBounds(BTN_START_RECTANGLE);
		this.BTNStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.startMainThread();

			}
		});
		this.add(BTNStart);
		// config and credit button

		this.BTNCC = new JButton(IMG_SOUND.resizeIcon(IMG_SOUND.resizeIcon(IMG_SOUND.getIMG_CONFIG_CREDIT())));
		this.BTNCC.setBounds(BTN_CONFIG_CREDIT_RECTANGLE);
		this.add(BTNCC);
		this.BTNCC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.showUserConfig();
			}
		});
	}

	/*
	 * initiate the layer
	 */
	private void initiateLayer(GameDTO dto) {

		// to retrieve the frame configuration
		FrameConfig fcfg = GameConfig.getFRAME_CONFIG();

		// to retrieve layer configuration
		List<LayerConfig> layersCFG = fcfg.getLayerConfig();

		// to create a list of layers
		layerList = new ArrayList<Layer>(layersCFG.size());

		try {

			// to create an array of panels from the array
			for (LayerConfig layerCFG : layersCFG) {

				// retrieve the class object
				Class<?> cls = Class.forName(layerCFG.getClassName());
				// to retrieve the constructor
				Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
				// to create a layer object with data form layerCFG
				int xPosition = layerCFG.getX();
				int yPosition = layerCFG.getY();
				int width = layerCFG.getW();
				int height = layerCFG.getH();

				Layer layer = (Layer) ctr.newInstance(xPosition, yPosition, width, height);
				layer.setDTO(dto);
				// add the layer to the layerList
				layerList.add(layer);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override

	public void paintComponent(Graphics g) {
		/*
		 * call super method
		 */
		super.paintComponent(g);

		/*
		 * to print layers from the layerList
		 */

		for (int i = 0; i < layerList.size(); i++) {
			layerList.get(i).paint(g);
		}

		/*
		 * go back to the focus
		 */
		this.requestFocus();
	}

	// control is the start button is clickable
	public void buttonSwitch(boolean onOff) {
		this.BTNStart.setEnabled(onOff);
		this.BTNCC.setEnabled(onOff);
	}

	public void setGameControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}

}
