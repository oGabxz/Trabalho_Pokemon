package monstraria.view;

import monstraria.Monstro;
import javax.swing.*;
import java.awt.*;

/** Painel que desenha nome, tipo, nível, barra de vida (HP) e barra de XP de um monstro. */
public class BarraVida extends JPanel {
    private Monstro monstro;

    public BarraVida(Monstro monstro) {
        this.monstro = monstro;
        setOpaque(false);
        setPreferredSize(new Dimension(290, 86));
    }

    public void setMonstro(Monstro m) {
        monstro = m;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (monstro == null) return;
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.setColor(Estilo.TEXT);
        g.drawString(monstro.getNome() + "  Nv." + monstro.getNivel(), 2, 17);

        g.setFont(new Font("SansSerif", Font.BOLD, 11));
        g.setColor(monstro.getTipo().corPrincipal());
        g.drawString(monstro.getTipo().nomeExibicao().toUpperCase(), getWidth() - 60, 17);

        int bw = getWidth() - 4, by = 27, bh = 13;
        g.setColor(new Color(6, 10, 18));
        g.fillRoundRect(2, by, bw, bh, 10, 10);
        double pctVida = monstro.getVidaMaxima() == 0 ? 0 : Math.max(0, (double) monstro.getVidaAtual() / monstro.getVidaMaxima());
        int fw = (int) (bw * pctVida);
        g.setColor(pctVida > .5 ? Estilo.GREEN : (pctVida > .2 ? Estilo.GOLD : Estilo.RED));
        if (fw > 0) g.fillRoundRect(2, by, fw, bh, 10, 10);

        g.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g.setColor(Estilo.MUTED);
        g.drawString("HP  " + monstro.getVidaAtual() + " / " + monstro.getVidaMaxima(), 2, by + bh + 14);

        int xy = by + bh + 22, xh = 8;
        g.setColor(new Color(6, 10, 18));
        g.fillRoundRect(2, xy, bw, xh, 8, 8);
        double pctXp = (double) monstro.getXpAtual() / monstro.xpParaProximoNivel();
        int xw = (int) (bw * Math.max(0, Math.min(1, pctXp)));
        g.setColor(Estilo.PURPLE);
        if (xw > 0) g.fillRoundRect(2, xy, xw, xh, 8, 8);
        g.dispose();
    }
}
