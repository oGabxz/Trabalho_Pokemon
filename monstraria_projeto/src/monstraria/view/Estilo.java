package monstraria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

final class Estilo {
    static final Color BG = new Color(9, 13, 24);
    static final Color PANEL = new Color(18, 25, 42);
    static final Color PANEL_2 = new Color(24, 33, 54);
    static final Color TEXT = new Color(239, 244, 255);
    static final Color MUTED = new Color(153, 166, 190);
    static final Color CYAN = new Color(76, 201, 240);
    static final Color PURPLE = new Color(151, 117, 250);
    static final Color GREEN = new Color(91, 214, 137);
    static final Color RED = new Color(241, 92, 104);
    static final Color GOLD = new Color(245, 190, 75);

    private Estilo() {}

    static void aplicarBotao(JButton b, Color cor) {
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setForeground(TEXT);
        b.setBackground(cor);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    static JLabel titulo(String texto, int tamanho) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, tamanho));
        l.setForeground(TEXT);
        return l;
    }

    static JLabel subtitulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l.setForeground(MUTED);
        return l;
    }

    static JPanel painelArredondado(Color cor) {
        return new JPanel() {
            { setOpaque(false); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
                g2.setColor(new Color(255, 255, 255, 18));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
                g2.dispose();
                super.paintComponent(g);
            }
        };
    }
}
