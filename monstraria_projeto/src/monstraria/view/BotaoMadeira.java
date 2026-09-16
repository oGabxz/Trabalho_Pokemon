package monstraria.view;

import javax.swing.*;
import java.awt.*;

/**
 * Botão em estilo "placa de madeira" pixel art (inspirado na imagem de
 * referência com os botões PLAY / PAUSE / START / MENU / EXIT): prancha
 * marrom com bordas em relevo, veios de madeira, sombra projetada e texto
 * pixelado com contorno escuro.
 *
 * Todo o desenho (fundo + texto) é feito num único paintComponent, nessa
 * ordem, para garantir que o texto sempre fique visível por cima do fundo
 * (o botão antigo pintava o preenchimento depois do texto, através da
 * borda, e por isso o texto "sumia").
 */
class BotaoMadeira extends JButton {

    private static final Color MADEIRA = new Color(151, 99, 61);
    private static final Color CONTORNO_TEXTO = new Color(58, 32, 20);
    private static final Color TEXTO_BASE = new Color(240, 210, 165);
    private static final Color CONTORNO_EXTERNO = new Color(35, 20, 14);
    private static final int SOMBRA = 5;
    private static final int ESPESSURA = 4;

    private final Color acento;

    BotaoMadeira(String texto, Color acento) {
        super(texto);
        this.acento = acento;
        setFont(Estilo.fontePixel(16));
        setForeground(TEXTO_BASE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getModel().addChangeListener(e -> repaint());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        int w = getWidth(), h = getHeight();
        int bw = Math.max(1, w - SOMBRA);
        int bh = Math.max(1, h - SOMBRA);

        boolean habilitado = isEnabled();
        boolean sobre = habilitado && getModel().isRollover();
        boolean pressionado = habilitado && getModel().isPressed();

        Color base = MADEIRA;
        if (!habilitado) base = dessaturar(base);
        if (sobre) base = ajustar(base, 22);
        if (pressionado) base = ajustar(base, -20);

        // sombra projetada (deslocada, sem suavização = efeito pixelado)
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(SOMBRA, SOMBRA, bw, bh);

        int ox = pressionado ? 2 : 0;
        int oy = pressionado ? 2 : 0;

        // corpo da prancha
        g.setColor(base);
        g.fillRect(ox, oy, bw, bh);

        // veios horizontais de madeira
        g.setColor(ajustar(base, -18));
        for (int y = bh / 3; y < bh - ESPESSURA; y += Math.max(6, bh / 3)) {
            g.fillRect(ox + 3, oy + y, bw - 6, 2);
        }

        // relevo: claro em cima/esquerda, escuro embaixo/direita
        g.setColor(ajustar(base, 48));
        g.fillRect(ox, oy, bw, ESPESSURA / 2);
        g.fillRect(ox, oy, ESPESSURA / 2, bh);
        g.setColor(ajustar(base, -55));
        g.fillRect(ox, oy + bh - ESPESSURA / 2, bw, ESPESSURA / 2);
        g.fillRect(ox + bw - ESPESSURA / 2, oy, ESPESSURA / 2, bh);

        // friso colorido: identifica a ação de cada botão
        Color friso = habilitado ? acento : dessaturar(acento);
        g.setColor(friso);
        g.fillRect(ox + ESPESSURA, oy + ESPESSURA, Math.max(0, bw - ESPESSURA * 2), 3);

        // contorno externo
        g.setColor(CONTORNO_EXTERNO);
        g.drawRect(ox, oy, bw - 1, bh - 1);

        desenharTexto(g, ox, oy, bw, bh, habilitado, friso);

        g.dispose();
    }

    private void desenharTexto(Graphics2D g, int ox, int oy, int bw, int bh, boolean habilitado, Color friso) {
        String texto = getText();
        if (texto == null || texto.isEmpty()) return;

        g.setFont(getFont());
        FontMetrics fm = g.getFontMetrics();
        int tx = ox + (bw - fm.stringWidth(texto)) / 2;
        int ty = oy + (bh + fm.getAscent() - fm.getDescent()) / 2 - 1;

        // contorno escuro (dá o acabamento "pixelado" às letras)
        g.setColor(habilitado ? CONTORNO_TEXTO : ajustar(CONTORNO_TEXTO, 30));
        int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
        for (int i = 0; i < dx.length; i++) {
            g.drawString(texto, tx + dx[i], ty + dy[i]);
        }

        // preenchimento (tom creme com um toque da cor de destaque do botão)
        Color preenchimento = habilitado ? misturar(TEXTO_BASE, friso, 0.22f) : new Color(190, 190, 190);
        g.setColor(preenchimento);
        g.drawString(texto, tx, ty);
    }

    private static Color ajustar(Color c, int quantidade) {
        int r = clamp(c.getRed() + quantidade);
        int gg = clamp(c.getGreen() + quantidade);
        int b = clamp(c.getBlue() + quantidade);
        return new Color(r, gg, b);
    }

    private static Color dessaturar(Color c) {
        int media = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        int cinza = clamp((media + 100) / 2);
        return new Color(cinza, cinza, cinza);
    }

    private static Color misturar(Color a, Color b, float t) {
        int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
        int gg = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        return new Color(clamp(r), clamp(gg), clamp(bl));
    }

    private static int clamp(int v) {
        return Math.max(0, Math.min(255, v));
    }
}
