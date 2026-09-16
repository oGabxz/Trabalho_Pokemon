package monstraria.view;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Utilitário para dar às artes do jogo (paisagens, sprites, títulos) um
 * acabamento "pixel art" autêntico: tudo é desenhado primeiro numa
 * imagem de baixa resolução (sem suavização) e depois ampliado sem
 * interpolação suave (nearest-neighbor), exatamente como jogos 16-bit
 * clássicos faziam ao escalar sua arte para telas maiores.
 */
final class PixelUtil {

    private PixelUtil() {}

    interface Desenhador {
        void desenhar(Graphics2D g, int larguraBaixaRes, int alturaBaixaRes);
    }

    /**
     * Renderiza em baixa resolução (largura/fator x altura/fator) e amplia
     * sem suavização para o tamanho final, produzindo o efeito pixelizado.
     */
    static BufferedImage renderizarPixelArt(int larguraFinal, int alturaFinal, int fator, Desenhador desenhador) {
        larguraFinal = Math.max(1, larguraFinal);
        alturaFinal = Math.max(1, alturaFinal);
        int lw = Math.max(1, larguraFinal / fator);
        int lh = Math.max(1, alturaFinal / fator);

        BufferedImage baixaRes = new BufferedImage(lw, lh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBaixa = baixaRes.createGraphics();
        gBaixa.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        desenhador.desenhar(gBaixa, lw, lh);
        gBaixa.dispose();

        BufferedImage saida = new BufferedImage(larguraFinal, alturaFinal, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gSaida = saida.createGraphics();
        gSaida.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        gSaida.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        gSaida.drawImage(baixaRes, 0, 0, larguraFinal, alturaFinal, null);
        gSaida.dispose();
        return saida;
    }

    /** Desenha uma imagem pixel-art já pronta, ampliando sem suavizar (útil ao redimensionar sprites). */
    static void desenharAmpliado(Graphics2D g, BufferedImage img, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(img, x, y, w, h, null);
        g2.dispose();
    }

    /**
     * Gera um texto "pixelado" (título) como imagem: desenha a string em baixa
     * resolução com fonte monoespaçada em negrito e amplia sem suavizar,
     * resultando em letras com contorno bem definido, além de uma sombra
     * projetada para dar profundidade.
     */
    static BufferedImage textoPixelado(String texto, int alturaFinal, Color cor, Color contorno, Color sombra) {
        int fator = 4;
        int alturaBaixa = Math.max(6, alturaFinal / fator);
        Font fonte = new Font("Monospaced", Font.BOLD, (int) (alturaBaixa * 0.82));

        Canvas medidor = new Canvas();
        FontMetrics fm = medidor.getFontMetrics(fonte);
        int larguraTexto = fm.stringWidth(texto);
        int larguraBaixa = larguraTexto + alturaBaixa;
        int larguraFinal = larguraBaixa * fator;

        return renderizarPixelArt(larguraFinal, alturaFinal, fator, (g, lw, lh) -> {
            g.setFont(fonte);
            FontMetrics m = g.getFontMetrics();
            int tx = (lw - m.stringWidth(texto)) / 2;
            int ty = (lh + m.getAscent() - m.getDescent()) / 2;

            // sombra projetada (efeito "pixel drop shadow")
            g.setColor(sombra);
            int deslocamento = Math.max(1, alturaBaixa / 10);
            g.drawString(texto, tx + deslocamento, ty + deslocamento);

            // contorno grosso (simula outline pixelado)
            g.setColor(contorno);
            int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
            int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
            for (int i = 0; i < dx.length; i++) {
                g.drawString(texto, tx + dx[i], ty + dy[i]);
            }

            // texto principal
            g.setColor(cor);
            g.drawString(texto, tx, ty);
        });
    }
}
