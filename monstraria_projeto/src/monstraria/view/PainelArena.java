package monstraria.view;

import monstraria.Monstro;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

class PainelArena extends JPanel {
    private Monstro jogador, selvagem;
    private boolean jogadorCaido, selvagemCaido;

    PainelArena() {
        setOpaque(false);
        setPreferredSize(new Dimension(760, 360));
    }

    void configurar(Monstro jogador, boolean jogadorCaido, Monstro selvagem, boolean selvagemCaido) {
        this.jogador = jogador;
        this.jogadorCaido = jogadorCaido;
        this.selvagem = selvagem;
        this.selvagemCaido = selvagemCaido;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        GradientPaint sky = new GradientPaint(0, 0, new Color(29, 39, 66), 0, h, new Color(12, 19, 31));
        g.setPaint(sky);
        g.fillRect(0, 0, w, h);
        g.setColor(new Color(255, 255, 255, 10));
        for (int i = 0; i < 12; i++) {
            g.fill(new Ellipse2D.Float((i * 97) % w, 30 + (i * 43) % 180, 2, 2));
        }
        g.setColor(new Color(31, 46, 45));
        g.fillRect(0, h - 92, w, 92);
        g.setColor(new Color(70, 100, 87));
        g.fillOval(40, h - 118, 370, 100);
        g.fillOval(w - 410, h - 118, 370, 100);

        if (jogador != null) {
            MonstroSprite.desenharPixelado(g, jogador.getNome(), jogador.getTipo(), jogador.getEstagio(), 180, h - 150, 190, jogadorCaido);
        }
        if (selvagem != null) {
            MonstroSprite.desenharPixelado(g, selvagem.getNome(), selvagem.getTipo(), selvagem.getEstagio(), w - 185, 135, 165, selvagemCaido);
        }
        g.dispose();
    }
}
