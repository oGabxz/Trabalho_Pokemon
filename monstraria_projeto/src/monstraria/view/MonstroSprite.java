package monstraria.view;

import monstraria.Tipo;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Sprites 100% vetoriais e autorais (nenhuma imagem externa é usada).
 * Cada uma das 9 espécies do jogo tem uma silhueta única, definida pela
 * combinação de tipo (cor) e estágio evolutivo:
 *   estágio 1 -> corpo pequeno e arredondado, feições dóceis;
 *   estágio 2 -> corpo maior, chifres/nadadeiras/folhas mais evidentes;
 *   estágio 3 -> corpo grande, aura de energia, feições mais imponentes.
 */
public final class MonstroSprite {

    private MonstroSprite() {}

    public static void desenhar(Graphics2D gOriginal, Tipo tipo, int estagio, int cx, int cy, int tamanho, boolean desmaiado) {
        Graphics2D g = (Graphics2D) gOriginal.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (desmaiado) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.38f));
        }

        Color corBase = desmaiado ? new Color(112, 116, 126) : tipo.corPrincipal();
        Color corClara = desmaiado ? new Color(160, 164, 174) : tipo.corClara();
        Color corEscura = corBase.darker();

        int s = Math.max(40, tamanho);

        // sombra de contato no chão
        g.setColor(new Color(0, 0, 0, 80));
        g.fillOval(cx - (int) (s * 0.38), cy + (int) (s * 0.40), (int) (s * 0.76), (int) (s * 0.14));

        if (estagio >= 3) {
            desenharAura(g, cx, cy, s, corBase);
        }

        switch (tipo) {
            case FOGO:
                desenharFogo(g, estagio, cx, cy, s, corBase, corClara, corEscura);
                break;
            case AGUA:
                desenharAgua(g, estagio, cx, cy, s, corBase, corClara, corEscura);
                break;
            case PLANTA:
            default:
                desenharPlanta(g, estagio, cx, cy, s, corBase, corClara, corEscura);
                break;
        }

        g.dispose();
    }

    // ---------------------------------------------------------------- aura

    private static void desenharAura(Graphics2D g, int cx, int cy, int s, Color cor) {
        Composite old = g.getComposite();
        for (int i = 3; i >= 1; i--) {
            float alpha = 0.10f * i;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            int raio = (int) (s * (0.62 + i * 0.07));
            g.setColor(cor);
            g.fillOval(cx - raio / 2, cy - raio / 2, raio, raio);
        }
        g.setComposite(old);
    }

    // ------------------------------------------------------------- corpo

    private static void corpoBase(Graphics2D g, int cx, int cy, int s, double fatorCorpo,
                                   Color corBase, Color corEscura) {
        int larg = (int) (s * fatorCorpo);
        int alt = (int) (s * fatorCorpo * 0.92);
        int x = cx - larg / 2;
        int y = cy - alt / 2 + (int) (s * 0.06);

        g.setColor(corEscura);
        g.fill(new RoundRectangle2D.Float(x - 2, y - 2, larg + 4, alt + 4, alt * 0.85f, alt * 0.85f));
        g.setColor(corBase);
        g.fill(new RoundRectangle2D.Float(x, y, larg, alt, alt * 0.85f, alt * 0.85f));

        // barriga mais clara
        Color barriga = new Color(255, 255, 255, 60);
        g.setColor(barriga);
        int bw = (int) (larg * 0.55);
        int bh = (int) (alt * 0.42);
        g.fillOval(cx - bw / 2, y + (int) (alt * 0.42), bw, bh);
    }

    private static void patas(Graphics2D g, int cx, int cy, int s, double fatorCorpo, Color corEscura) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int py = cy - alt / 2 + (int) (s * 0.06) + alt - (int) (s * 0.06);
        int pw = (int) (s * 0.16);
        int ph = (int) (s * 0.12);
        g.setColor(corEscura);
        g.fillOval(cx - (int) (s * 0.24) - pw / 2, py, pw, ph);
        g.fillOval(cx + (int) (s * 0.24) - pw / 2, py, pw, ph);
    }

    private static void olhos(Graphics2D g, int cx, int cy, int s, double fatorCorpo, int estagio) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int oy = cy - alt / 2 + (int) (s * 0.06) + (int) (alt * 0.30);
        int tamanhoOlho = estagio == 1 ? (int) (s * 0.15) : estagio == 2 ? (int) (s * 0.13) : (int) (s * 0.12);
        int afastamento = (int) (s * 0.14);

        g.setColor(Color.WHITE);
        g.fillOval(cx - afastamento - tamanhoOlho / 2, oy, tamanhoOlho, tamanhoOlho);
        g.fillOval(cx + afastamento - tamanhoOlho / 2, oy, tamanhoOlho, tamanhoOlho);

        g.setColor(Color.BLACK);
        int pupila = estagio == 3 ? (int) (tamanhoOlho * 0.42) : (int) (tamanhoOlho * 0.55);
        int deslocEstagio3 = estagio == 3 ? tamanhoOlho / 6 : 0;
        g.fillOval(cx - afastamento - pupila / 2 + deslocEstagio3, oy + tamanhoOlho / 4, pupila, pupila);
        g.fillOval(cx + afastamento - pupila / 2 + deslocEstagio3, oy + tamanhoOlho / 4, pupila, pupila);

        if (estagio == 3) {
            g.setStroke(new BasicStroke(Math.max(2f, s * 0.02f)));
            g.setColor(new Color(0, 0, 0, 140));
            g.drawLine(cx - afastamento - tamanhoOlho / 2 - 2, oy - 2, cx - afastamento + tamanhoOlho / 2, oy - 6);
            g.drawLine(cx + afastamento - tamanhoOlho / 2, oy - 6, cx + afastamento + tamanhoOlho / 2 + 2, oy - 2);
        }
    }

    // -------------------------------------------------------------- fogo

    private static void desenharFogo(Graphics2D g, int estagio, int cx, int cy, int s,
                                      Color corBase, Color corClara, Color corEscura) {
        double fatorCorpo = estagio == 1 ? 0.62 : estagio == 2 ? 0.74 : 0.86;
        corpoBase(g, cx, cy, s, fatorCorpo, corBase, corEscura);
        patas(g, cx, cy, s, fatorCorpo, corEscura);
        desenharCaudaChama(g, cx, cy, s, fatorCorpo, estagio, corClara, corBase);
        desenharTufoCabeca(g, cx, cy, s, fatorCorpo, estagio, corClara);
        if (estagio >= 2) desenharChifres(g, cx, cy, s, fatorCorpo, corEscura);
        olhos(g, cx, cy, s, fatorCorpo, estagio);
        if (estagio >= 2) desenharAsasOuPicos(g, cx, cy, s, fatorCorpo, estagio, corEscura, true);
    }

    private static void desenharCaudaChama(Graphics2D g, int cx, int cy, int s, double fatorCorpo,
                                            int estagio, Color corClara, Color corBase) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int baseX = cx + (int) (s * fatorCorpo * 0.42);
        int baseY = cy - alt / 2 + (int) (s * 0.06) + (int) (alt * 0.6);
        double tamanhoChama = s * (estagio == 1 ? 0.30 : estagio == 2 ? 0.42 : 0.56);

        Path2D chama = new Path2D.Double();
        chama.moveTo(baseX, baseY);
        chama.curveTo(baseX + tamanhoChama * 0.7, baseY - tamanhoChama * 0.2,
                baseX + tamanhoChama * 0.85, baseY - tamanhoChama * 0.9,
                baseX + tamanhoChama * 0.35, baseY - tamanhoChama * 1.25);
        chama.curveTo(baseX + tamanhoChama * 0.55, baseY - tamanhoChama * 0.75,
                baseX + tamanhoChama * 0.15, baseY - tamanhoChama * 0.55,
                baseX, baseY);
        chama.closePath();
        g.setColor(corBase);
        g.fill(chama);
        g.setColor(corClara);
        g.fill(new Ellipse2D.Double(baseX + tamanhoChama * 0.18, baseY - tamanhoChama * 0.75,
                tamanhoChama * 0.32, tamanhoChama * 0.5));
    }

    private static void desenharTufoCabeca(Graphics2D g, int cx, int cy, int s, double fatorCorpo,
                                            int estagio, Color corClara) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int topoY = cy - alt / 2 + (int) (s * 0.06);
        double tam = s * (estagio == 1 ? 0.18 : 0.24);
        Path2D tufo = new Path2D.Double();
        tufo.moveTo(cx - tam * 0.4, topoY + tam * 0.2);
        tufo.curveTo(cx - tam * 0.1, topoY - tam * 0.9, cx + tam * 0.1, topoY - tam * 0.9, cx + tam * 0.4, topoY + tam * 0.2);
        tufo.closePath();
        g.setColor(corClara);
        g.fill(tufo);
    }

    // -------------------------------------------------------------- água

    private static void desenharAgua(Graphics2D g, int estagio, int cx, int cy, int s,
                                      Color corBase, Color corClara, Color corEscura) {
        double fatorCorpo = estagio == 1 ? 0.64 : estagio == 2 ? 0.76 : 0.88;
        corpoBase(g, cx, cy, s, fatorCorpo, corBase, corEscura);
        patas(g, cx, cy, s, fatorCorpo, corEscura);
        desenharBarbatanaCauda(g, cx, cy, s, fatorCorpo, estagio, corClara);
        desenharBarbatanaCabeca(g, cx, cy, s, fatorCorpo, estagio, corClara);
        olhos(g, cx, cy, s, fatorCorpo, estagio);
        if (estagio >= 2) desenharAsasOuPicos(g, cx, cy, s, fatorCorpo, estagio, corEscura, false);
        if (estagio == 3) desenharGotas(g, cx, cy, s, corClara);
    }

    private static void desenharBarbatanaCauda(Graphics2D g, int cx, int cy, int s, double fatorCorpo,
                                                int estagio, Color corClara) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int baseX = cx + (int) (s * fatorCorpo * 0.44);
        int baseY = cy - alt / 2 + (int) (s * 0.06) + (int) (alt * 0.55);
        double tam = s * (estagio == 1 ? 0.24 : estagio == 2 ? 0.34 : 0.46);
        Path2D barbatana = new Path2D.Double();
        barbatana.moveTo(baseX, baseY - tam * 0.2);
        barbatana.lineTo(baseX + tam, baseY - tam * 0.55);
        barbatana.lineTo(baseX + tam * 0.65, baseY);
        barbatana.lineTo(baseX + tam, baseY + tam * 0.55);
        barbatana.closePath();
        g.setColor(corClara);
        g.fill(barbatana);
    }

    private static void desenharBarbatanaCabeca(Graphics2D g, int cx, int cy, int s, double fatorCorpo,
                                                 int estagio, Color corClara) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int topoY = cy - alt / 2 + (int) (s * 0.06);
        double tam = s * (estagio == 1 ? 0.16 : 0.22);
        Path2D barbatana = new Path2D.Double();
        barbatana.moveTo(cx, topoY);
        barbatana.lineTo(cx - tam * 0.35, topoY - tam);
        barbatana.lineTo(cx + tam * 0.35, topoY - tam * 0.4);
        barbatana.closePath();
        g.setColor(corClara);
        g.fill(barbatana);
    }

    private static void desenharGotas(Graphics2D g, int cx, int cy, int s, Color corClara) {
        g.setColor(new Color(corClara.getRed(), corClara.getGreen(), corClara.getBlue(), 170));
        int[] dx = {-1, 1, 0};
        int[] dy = {-1, -1, 1};
        for (int i = 0; i < 3; i++) {
            int gx = cx + dx[i] * (int) (s * 0.52);
            int gy = cy + dy[i] * (int) (s * 0.42) - (int)(s*0.1);
            int r = (int) (s * 0.06);
            g.fillOval(gx - r / 2, gy - r / 2, r, r);
        }
    }

    // ------------------------------------------------------------ planta

    private static void desenharPlanta(Graphics2D g, int estagio, int cx, int cy, int s,
                                        Color corBase, Color corClara, Color corEscura) {
        double fatorCorpo = estagio == 1 ? 0.64 : estagio == 2 ? 0.76 : 0.88;
        corpoBase(g, cx, cy, s, fatorCorpo, corBase, corEscura);
        patas(g, cx, cy, s, fatorCorpo, corEscura);
        desenharBrotoCauda(g, cx, cy, s, fatorCorpo, estagio, corClara);
        desenharFolhasCabeca(g, cx, cy, s, fatorCorpo, estagio, corClara, corEscura);
        olhos(g, cx, cy, s, fatorCorpo, estagio);
        if (estagio >= 2) desenharAsasOuPicos(g, cx, cy, s, fatorCorpo, estagio, corEscura, false);
    }

    private static void desenharFolhasCabeca(Graphics2D g, int cx, int cy, int s, double fatorCorpo,
                                              int estagio, Color corClara, Color corEscura) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int topoY = cy - alt / 2 + (int) (s * 0.06);
        double tam = s * (estagio == 1 ? 0.20 : estagio == 2 ? 0.28 : 0.38);
        int folhas = estagio == 1 ? 1 : estagio == 2 ? 2 : 3;
        for (int i = 0; i < folhas; i++) {
            double angulo = Math.toRadians(-90 + (i - (folhas - 1) / 2.0) * 35);
            double px = cx + Math.cos(angulo) * tam * 0.15;
            double py = topoY + Math.sin(angulo) * tam * 0.15;
            double px2 = cx + Math.cos(angulo) * tam;
            double py2 = topoY + Math.sin(angulo) * tam;
            Path2D folha = new Path2D.Double();
            folha.moveTo(px, py);
            folha.curveTo(px - tam * 0.25, py2, px2 - tam * 0.1, py2, px2, py2);
            folha.curveTo(px2 + tam * 0.1, py2, px + tam * 0.25, py2, px, py);
            folha.closePath();
            g.setColor(corClara);
            g.fill(folha);
            g.setColor(corEscura);
            g.setStroke(new BasicStroke(1.4f));
            g.draw(new java.awt.geom.Line2D.Double(px, py, px2, py2));
        }
    }

    private static void desenharBrotoCauda(Graphics2D g, int cx, int cy, int s, double fatorCorpo,
                                            int estagio, Color corClara) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int baseX = cx + (int) (s * fatorCorpo * 0.42);
        int baseY = cy - alt / 2 + (int) (s * 0.06) + (int) (alt * 0.65);
        double tam = s * (estagio == 1 ? 0.14 : estagio == 2 ? 0.20 : 0.28);
        g.setColor(corClara);
        g.fillOval(baseX, baseY - (int) tam / 2, (int) tam, (int) tam);
    }

    // --------------------------------------------------- picos / asas (estágio 2+)

    private static void desenharAsasOuPicos(Graphics2D g, int cx, int cy, int s, double fatorCorpo,
                                             int estagio, Color corEscura, boolean usarAsas) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int y = cy - alt / 2 + (int) (s * 0.06) + (int) (alt * 0.35);
        double tam = s * (estagio == 2 ? 0.16 : 0.26);

        if (usarAsas && estagio == 3) {
            Path2D asaEsq = asa(cx - (int) (s * fatorCorpo * 0.46), y, -tam, corEscura);
            Path2D asaDir = asa(cx + (int) (s * fatorCorpo * 0.46), y, tam, corEscura);
            g.setColor(new Color(corEscura.getRed(), corEscura.getGreen(), corEscura.getBlue(), 210));
            g.fill(asaEsq);
            g.fill(asaDir);
            return;
        }

        // picos nas costas
        g.setColor(corEscura);
        int qtd = estagio == 2 ? 2 : 3;
        for (int i = 0; i < qtd; i++) {
            int px = cx - (int) (tam * (qtd - 1)) / 2 + i * (int) tam;
            int py = y - (int) (s * fatorCorpo * 0.30);
            Polygon pico = new Polygon();
            pico.addPoint(px, py);
            pico.addPoint(px - (int) (tam * 0.28), py + (int) (tam * 0.6));
            pico.addPoint(px + (int) (tam * 0.28), py + (int) (tam * 0.6));
            g.fillPolygon(pico);
        }
    }

    private static void desenharChifres(Graphics2D g, int cx, int cy, int s, double fatorCorpo, Color corEscura) {
        int alt = (int) (s * fatorCorpo * 0.92);
        int topoY = cy - alt / 2 + (int) (s * 0.06) + (int) (alt * 0.08);
        int tam = (int) (s * 0.14);
        g.setColor(corEscura);
        Polygon esq = new Polygon();
        esq.addPoint(cx - (int) (s * fatorCorpo * 0.22), topoY);
        esq.addPoint(cx - (int) (s * fatorCorpo * 0.34), topoY - tam);
        esq.addPoint(cx - (int) (s * fatorCorpo * 0.12), topoY - (int)(tam*0.3));
        g.fillPolygon(esq);
        Polygon dir = new Polygon();
        dir.addPoint(cx + (int) (s * fatorCorpo * 0.22), topoY);
        dir.addPoint(cx + (int) (s * fatorCorpo * 0.34), topoY - tam);
        dir.addPoint(cx + (int) (s * fatorCorpo * 0.12), topoY - (int)(tam*0.3));
        g.fillPolygon(dir);
    }

    private static Path2D asa(int baseX, int baseY, double tam, Color cor) {
        Path2D asa = new Path2D.Double();
        asa.moveTo(baseX, baseY);
        asa.curveTo(baseX + tam * 1.4, baseY - Math.abs(tam) * 0.6,
                baseX + tam * 1.8, baseY + Math.abs(tam) * 0.3,
                baseX + tam * 0.9, baseY + Math.abs(tam) * 0.7);
        asa.curveTo(baseX + tam * 0.6, baseY + Math.abs(tam) * 0.3, baseX + tam * 0.3, baseY + Math.abs(tam) * 0.1, baseX, baseY);
        asa.closePath();
        return asa;
    }
}
