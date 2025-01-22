
/**
 * AnalogDigitalClock class represents a graphical clock that displays
 * both analog and digital time.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AnalogDigitalClock extends JPanel {
    private int hour, minute, second;
    private Color startColor, endColor;

    // Constructor to initialize the clock and set its size
    public AnalogDigitalClock() {
        setPreferredSize(new Dimension(700, 700));
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new ClockTask(), 0, 1000); // Schedule the clock to update every second
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create and apply a gradient background
        GradientPaint gradient = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 40;

        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius); // Draw the clock face

        // Draw clock hands for hours, minutes, and seconds
        drawClockHand(g2d, centerX, centerY, hour % 12 * 30, 0.3 * radius, hour, 30, 30, Color.BLACK);
        drawClockHand(g2d, centerX, centerY, minute * 6, 0.6 * radius, minute, 25, 25, Color.BLACK);
        drawClockHand(g2d, centerX, centerY, second * 6, 0.9 * radius, second, 20, 20, Color.BLACK);
    }

    // Method to draw the clock hands with appropriate labels
    private void drawClockHand(Graphics2D g2d, int x, int y, double angle, double length, int value, int fontSize, int innerGap, Color labelColor) {
        g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g2d.setColor(labelColor);
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle), x, y);
        for (int i = innerGap; i < length; i += 30) {
            g2d.drawString(String.valueOf(value), x - 5, y - i);
        }
        g2d.setTransform(old);
    }

    // Inner class to update the clock time and colors every second
    private class ClockTask extends TimerTask {
        @Override
        public void run() {
            Calendar now = Calendar.getInstance();
            hour = now.get(Calendar.HOUR_OF_DAY);
            minute = now.get(Calendar.MINUTE);
            second = now.get(Calendar.SECOND);

            // Randomly change the gradient colors every second
            startColor = new Color((int)(Math.random() * 128 + 128), (int)(Math.random() * 128 + 128), (int)(Math.random() * 128 + 128));
            endColor = new Color((int)(Math.random() * 128 + 128), (int)(Math.random() * 128 + 128), (int)(Math.random() * 128 + 128));

            repaint(); // Refresh the display
        }
    }

    // Main method to create and display the clock frame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Analog Digital Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AnalogDigitalClock());
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Display the frame
    }
}
