package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import controller.SettingsController;
import model.Stats;

/*
 * Main view creates all the smallers views and stiches them togeather onto a single window.
 * This class is essentually a container for all the views.
 */
@SuppressWarnings("serial")
public class MainView extends JFrame {
	JMenuBar menuBar;
	JMenu gameMenu, settingMenu;
	
	JMenuItem savePVZGame, loadPVZGame, quitPVZGame, createMap, loadMap;
	JTextArea textArea;
	JScrollPane scrollPane;
	JOptionPane optionPane;
    JFileChooser fc;
	Container contentPane;
	GridBagLayout layout;
	
	
	/* Set up for the game client view */
	GamePanel gamePanel;
	PlantSelectionPanel plantSelectionPanel;
	DecisionPanel decisionPanel;
	StatsPanel statsPanel;
	DescriptionPanel descriptionPanel;
	
	
    /*
     * Creates the main window and all the views
     */
	public MainView() {
		super("Plants Vs Zombies Interface");

        // Setup window layout and dimensions
		setLocationRelativeTo(null);
		setSize(1000, 500);

		layout = new GridBagLayout();
		contentPane = getContentPane();
		contentPane.setLayout(layout);

		setupMainView(new Stats(10, 10));
		
		/* Display the interface */
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void setupMainView(Stats stats) {
		GridBagConstraints c = new GridBagConstraints();
		
        // Create menu bar
		menuBar = new JMenuBar();
		gameMenu = new JMenu("Game");
		settingMenu = new JMenu("Setting");
		menuBar.add(gameMenu);
		menuBar.add(settingMenu);
		setJMenuBar(menuBar);
		
        // Create options for menubar
		savePVZGame = new JMenuItem("Save Game");
		loadPVZGame = new JMenuItem("Load Game");
		quitPVZGame = new JMenuItem("Quit");
        // Add created options to menu bar under game menu
		gameMenu.add(savePVZGame);
		gameMenu.add(loadPVZGame);
		gameMenu.addSeparator();
		gameMenu.add(quitPVZGame);
		
        // Create and and option for menubar under settings menu
		createMap = new JMenuItem("Create new map");
		loadMap = new JMenuItem("Load custom map");
		settingMenu.add(createMap);
		settingMenu.add(loadMap);
		
		/* This component contains the game's stats like sun flower points and zombies left to kill */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		statsPanel = new StatsPanel(stats);
		contentPane.add(statsPanel.getStatsPanel(), c);

		/* This component contains the flowers you can select to place on the game field */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 30;
		c.gridx = 0;
		c.gridy = 2;
		plantSelectionPanel = new PlantSelectionPanel();
		contentPane.add(plantSelectionPanel.getShopPanel(), c);
		
		/* This component contains the game's field of play made out of 2-D JButton slots */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		gamePanel = new GamePanel(plantSelectionPanel, statsPanel);
		contentPane.add(gamePanel.getGamePanel(), c);
		
		/* This component contains the game's instruction */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		descriptionPanel = new DescriptionPanel();
		contentPane.add(descriptionPanel.getDesciptionPanel(), c);
		
		/* This component contains the decisions you make per turn */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		decisionPanel = new DecisionPanel(statsPanel);
		contentPane.add(decisionPanel.getDecisionPanel(), c);
		

		fc = new JFileChooser();
	}

	/* Resets the main view for custom map */
	public void resetMainView() {
		layout = new GridBagLayout();
		contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(layout);

		setupMainView(Stats.getStats());

		revalidate();
		repaint();
	}
	
	/* Create action handler for each sub menu option */
	public void addActionListenerSettingsController(SettingsController handler) {
		savePVZGame.addActionListener(handler);
		loadPVZGame.addActionListener(handler);
		quitPVZGame.addActionListener(handler);
		createMap.addActionListener(handler);
		loadMap.addActionListener(handler);
	}
	
	/* Use to get savePVZGame variable from view for Controller */
	public JMenuItem getSavePVZGame() {
		return savePVZGame;
	}
	
	/* Use to get savePVZGame variable from view for Controller */
	public JMenuItem getLoadPVZGame() {
		return loadPVZGame;
	}
	
	/* Use to get createMap variable from view for Controller */
	public JMenuItem getCreateMap() {
		return createMap;
	}

	/* Use to get loadMap variable from view for Controller */
	public JMenuItem getLoadMap() {
		return loadMap;
	}

	/* Use to get fc variable from view for Controller */
	public JFileChooser getFileChooser() {
        return fc;
	}
	
	/* Use to get quitPVZGame variable from view for Controller */
	public JMenuItem getQuitPVZGame() {
		return quitPVZGame;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}
}
