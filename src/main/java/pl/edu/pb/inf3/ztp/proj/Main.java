package pl.edu.pb.inf3.ztp.proj;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {

    // Grid settings
    private final int CELL_SIZE = 40;
    private final int GRID_SIZE = 10;
    private final int BOARD_OFFSET_X = 50; // Margin from left
    private final int BOARD_OFFSET_Y = 50; // Margin from top

    public Main() {
        // Set the preferred window size based on our grid
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D for better control (antialiasing, stroke width)
        Graphics2D g2d = (Graphics2D) g;

        // Turn on Antialiasing for smoother lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- DRAW THE GRID ---
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                int x = BOARD_OFFSET_X + (col * CELL_SIZE);
                int y = BOARD_OFFSET_Y + (row * CELL_SIZE);

                // 1. Draw the cell background (Water)
                g2d.setColor(new Color(30, 144, 255)); // Dodger Blue
                g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                // 2. Draw the grid lines
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(1)); // Line thickness
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }

        // --- DRAW A SHIP (Example: A generic 3-unit ship) ---
        // Let's place it at Row 2, Column 3
        drawShip(g2d, 2, 3, 3, true); // true = horizontal
    }

    // A helper method to draw a ship easily
    private void drawShip(Graphics2D g2d, int row, int col, int length, boolean horizontal) {
        int x = BOARD_OFFSET_X + (col * CELL_SIZE);
        int y = BOARD_OFFSET_Y + (row * CELL_SIZE);

        g2d.setColor(Color.GRAY);

        if (horizontal) {
            // Fill rect spanning 'length' cells
            g2d.fillRect(x + 5, y + 5, (CELL_SIZE * length) - 10, CELL_SIZE - 10);

            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x + 5, y + 5, (CELL_SIZE * length) - 10, CELL_SIZE - 10);
        } else {
            // Vertical logic would go here
        }
    }

    public static void main(String[] args) {
        // Standard Swing Boilerplate
        JFrame frame = new JFrame("Morski Bój | DEV PREVIEW");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Main gamePanel = new Main();
        frame.add(gamePanel);

        frame.pack(); // Size frame to fit the panel
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }
}