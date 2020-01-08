package se.hig.dvg329.algomaze.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import se.hig.dvg329.algomaze.control.MazeController;
import se.hig.dvg329.algomaze.model.Cell;
import se.hig.dvg329.algomaze.model.Cell.CellValue;

public class GUI implements Observer {

	private JFrame frame = new JFrame("AlgoMaze");
	private JPanel background = new JPanel();
	private JPanel mazePanel;
	private JPanel controlPanel = new JPanel();
	private JButton generateButton = new JButton("Generate maze");
	private JButton solveButton = new JButton("Solve");
	private Color backgroundColor = Color.decode("#B5D6DE");

	private MazeController mazeController;
	private GCell[][] grid;
	private int mazeHeight, mazeWidth;
	private static final int DEFAULT_MAZE_HEIGHT = 75, DEFAULT_MAZE_WIDTH = 75;

	public GUI(MazeController mazeController) {
		mazeHeight = DEFAULT_MAZE_HEIGHT;
		mazeWidth = DEFAULT_MAZE_WIDTH;
		this.mazeController = mazeController;
	}

	public void initGUI() {
		initControlPanel();
		initMazePanel();
		createMaze();
		initBackGround();
		addComponents();
		initFrame();
	}


	private void addComponents() {
		frame.add(background);

		GridBagLayout bgLayout = new GridBagLayout();
		GridBagConstraints bgConstr = new GridBagConstraints();
		bgLayout.setConstraints(background, bgConstr);
		background.setLayout(bgLayout);

		bgConstr.fill = GridBagConstraints.BOTH;

		background.add(controlPanel, bgConstr);
		background.add(mazePanel, bgConstr);

		GridBagLayout cpLayout = new GridBagLayout();
		GridBagConstraints cpConstr = new GridBagConstraints();
		cpLayout.setConstraints(controlPanel, cpConstr);
		controlPanel.setLayout(cpLayout);

		cpConstr.fill = GridBagConstraints.BOTH;
		cpConstr.insets = new Insets(5, 5, 5, 5);
		
		cpConstr.gridy = 0;
		cpConstr.gridx = 0;
		controlPanel.add(generateButton, cpConstr);

		cpConstr.gridy = 1;
		cpConstr.gridx = 0;
		controlPanel.add(solveButton, cpConstr);

		generateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mazeController.generateMaze("Prim");
			}
		});

		solveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mazeController.solveMaze("Dijkstra");
			}
		});
	}

	private void initControlPanel() {
		controlPanel.setBackground(Color.WHITE);
		controlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		controlPanel.setPreferredSize(new Dimension(300, 600));
	}

	private void initMazePanel() {
		mazePanel = new JPanel();
		mazePanel.setBackground(backgroundColor);
		Border border = BorderFactory.createMatteBorder(0, 20, 0, 0, backgroundColor);
		mazePanel.setBorder(border);
		mazePanel.setPreferredSize(new Dimension(800, 600));

		GridLayout gl = new GridLayout(mazeHeight, mazeWidth);
		mazePanel.setLayout(gl);
		background.add(mazePanel);
	}

	private void initFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setVisible(true);
	}

	private void initBackGround() {
		background.setBackground(backgroundColor);
		background.setBorder(BorderFactory.createLineBorder(backgroundColor, 30));
	}

	@Override
	public void update(Observable o, Object arg) {
		Cell cell = (Cell) o;
		GCell gCell = grid[cell.getY()][cell.getX()];

		if (cell.hasNorth()) {
			gCell.setNorth(true);
		}
		if (cell.hasWest()) {
			gCell.setWest(true);
		}
		if (cell.hasSouth()) {
			gCell.setSouth(true);
		}
		if (cell.hasEast()) {
			gCell.setEast(true);
		}
		if (cell.getValue() == CellValue.MARKED) {
			gCell.getPanel().setBackground(GCell.MARKED);
			gCell.setBorderColor(GCell.MARKED);
		} else if (cell.getValue() == CellValue.CANDIDATE) {
			gCell.getPanel().setBackground(GCell.CANDIDATE);
			gCell.setBorderColor(GCell.CANDIDATE);
		} else if (cell.getValue() == CellValue.SOLUTION) {
			gCell.getPanel().setBackground(GCell.SOLUTION);
			gCell.setBorderColor(GCell.SOLUTION);
		} else if (cell.getValue() == CellValue.VISITED) {
			gCell.getPanel().setBackground(GCell.VISITED);
			gCell.setBorderColor(GCell.VISITED);
		} else if (cell == mazeController.getMaze().getStart()) {
			gCell.getPanel().setBackground(GCell.START);
			gCell.setBorderColor(GCell.START);
		} else if (cell == mazeController.getMaze().getEnd()) {
			gCell.getPanel().setBackground(GCell.END);
			gCell.setBorderColor(GCell.END);
		} else {
			gCell.getPanel().setBackground(GCell.NONE);
			gCell.setBorderColor(Color.WHITE);
		}
	}

	private void createMaze() {
		mazeController.createMaze(mazeWidth, mazeHeight);
		mazeController.deleteObserver(this);
		mazeController.registerObserver(this);
		grid = new GCell[mazeHeight][mazeWidth];

		for (int y = 0; y < mazeController.getMaze().getHeight(); y++) {
			for (int x = 0; x < mazeController.getMaze().getWidth(); x++) {
				GCell gCell = new GCell(10);
				mazePanel.add(gCell.getPanel());
				grid[y][x] = gCell;
			}
		}
	}

}
