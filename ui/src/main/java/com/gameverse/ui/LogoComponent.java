package com.gameverse.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Custom-painted GameVerse logo component.
 * Renders the hexagonal GAME-X logo with glow effects.
 */
public class LogoComponent extends JComponent {

    private static final Color CYAN_BRIGHT = new Color(0, 220, 255);
    private static final Color CYAN_MID    = new Color(0, 160, 220);
    private static final Color CYAN_DARK   = new Color(0, 90, 150);
    private static final Color BG_DARK     = new Color(15, 16, 26);

    public LogoComponent() {
        setPreferredSize(new Dimension(280, 50));
        setMaximumSize(new Dimension(280, 50));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int cx = 26;
        int cy = getHeight() / 2;
        int hexR = 20;

        // Glow layers behind hexagon
        for (int i = 4; i >= 1; i--) {
            g2.setColor(new Color(CYAN_BRIGHT.getRed(), CYAN_BRIGHT.getGreen(), CYAN_BRIGHT.getBlue(), 8 * (5 - i)));
            drawHexagon(g2, cx, cy, hexR + i * 3);
        }

        // Hexagon fill with gradient
        Shape hex = createHexagon(cx, cy, hexR);
        GradientPaint hexGrad = new GradientPaint(cx - hexR, cy - hexR, CYAN_DARK, cx + hexR, cy + hexR, CYAN_MID);
        g2.setPaint(hexGrad);
        g2.fill(hex);

        // Hexagon border glow
        g2.setColor(new Color(CYAN_BRIGHT.getRed(), CYAN_BRIGHT.getGreen(), CYAN_BRIGHT.getBlue(), 200));
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(hex);

        // Inner cross/X mark
        g2.setColor(BG_DARK);
        g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int xOff = 7;
        g2.drawLine(cx - xOff, cy - xOff, cx + xOff, cy + xOff);
        g2.drawLine(cx + xOff, cy - xOff, cx - xOff, cy + xOff);

        // Small dot at center
        g2.setColor(CYAN_BRIGHT);
        g2.fillOval(cx - 2, cy - 2, 5, 5);

        // GAME text
        int textX = cx + hexR + 14;
        g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(new Color(235, 238, 255));
        g2.drawString("GAME", textX, cy + 2);

        // dash in cyan
        int dashX = textX + fm.stringWidth("GAME") + 1;
        g2.setColor(CYAN_BRIGHT);
        g2.drawString("-", dashX, cy + 2);

        // X in cyan bold
        int xTextX = dashX + fm.stringWidth("-") + 1;
        g2.setColor(CYAN_BRIGHT);
        g2.drawString("X", xTextX, cy + 2);

        // Subtitle
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        g2.setColor(new Color(130, 140, 170));
        g2.drawString("NEXT-GEN GAMING PORTAL", textX, cy + 18);

        g2.dispose();
    }

    private Shape createHexagon(int cx, int cy, int r) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 6 + i * Math.PI / 3;
            hex.addPoint(cx + (int)(r * Math.cos(angle)), cy - (int)(r * Math.sin(angle)));
        }
        return hex;
    }

    private void drawHexagon(Graphics2D g2, int cx, int cy, int r) {
        Shape hex = createHexagon(cx, cy, r);
        g2.fill(hex);
    }
}
