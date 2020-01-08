package se.hig.dvg329.algomaze.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

class GCell {

	private JPanel panel = new JPanel();
	private Insets whiteInsets, blackInsets;
	private MatteBorder blackBorder, whiteBorder;
	private Color borderColor = Color.WHITE;
	private CompoundBorder compoundBorder;
	static final Color 
		START = Color.decode("#FFBF00"),
		END = Color.decode("#421140"),
		NONE = Color.LIGHT_GRAY,
		SOLUTION = Color.decode("#99FEC7"),
		MARKED = Color.WHITE,
		CANDIDATE = Color.decode("#FEC799"),
		VISITED = CANDIDATE;
	
	GCell(int length) {
		panel.setPreferredSize(new Dimension(length, length));
		whiteInsets = new Insets(0, 0, 0, 0);
		blackInsets = new Insets(1, 1, 1, 1);
		applyBorder();
		panel.setOpaque(true);
		panel.setBackground(NONE);
	}
	
	void setNorth(boolean north) {
		if (north) {
			blackInsets.top = 0;
			whiteInsets.top = 1;
		}
		else {
			blackInsets.top = 1;
			whiteInsets.top = 0;
		}
		applyBorder();
	}
	
	void setWest(boolean west) {
		if (west) {
			blackInsets.left = 0;
			whiteInsets.left = 1;
		}
		else {
			blackInsets.left = 1;
			whiteInsets.left = 0;
		}
		applyBorder();
	}
	
	void setSouth(boolean south) {
		if (south) {
			blackInsets.bottom = 0;
			whiteInsets.bottom = 1;
		}
		else {
			blackInsets.bottom = 1;
			whiteInsets.bottom = 0;
		}
		applyBorder();
	}
	
	void setEast(boolean east) {
		if (east) {
			blackInsets.right = 0;
			whiteInsets.right = 1;
		}
		else {
			blackInsets.right = 1;
			whiteInsets.right = 0;
		}
		applyBorder();
	}
	
	private void applyBorder() {
		blackBorder = new MatteBorder(blackInsets, Color.BLACK);
		whiteBorder = new MatteBorder(whiteInsets, borderColor);
		compoundBorder = new CompoundBorder(blackBorder, whiteBorder);
		
		panel.setBorder(compoundBorder);
	}
	
	JPanel getPanel() {
		return panel;
	}
	
	void setBorderColor(Color color) {
		borderColor = color;
		applyBorder();
	}

}
