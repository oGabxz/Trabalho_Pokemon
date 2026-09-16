package monstraria.view;

import monstraria.Tipo;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Renderizador de sprites do jogo.
 *
 * A arte é construída em baixa resolução e ampliada com nearest-neighbor,
 * mantendo contorno escuro, paleta curta, pixels de luz/sombra e silhuetas
 * diferentes por linha evolutiva. A inspiração é o visual 16-bit mostrado no
 * material de referência, sem reproduzir nenhum sprite específico.
 */
public final class MonstroSprite {
    private static final int FATOR_PIXEL = 4;

    private MonstroSprite() {}

    /** Compatibilidade com telas antigas: desenha uma criatura genérica do tipo. */
    public static void desenharPixelado(Graphics2D g, Tipo tipo, int estagio, int cx, int cy,
                                        int tamanho, boolean desmaiado) {
        desenharPixelado(g, tipo.nomeExibicao(), tipo, estagio, cx, cy, tamanho, desmaiado);
    }

    /** Desenha o sprite específico da espécie. */
    public static void desenharPixelado(Graphics2D gOriginal, String especie, Tipo tipo, int estagio,
                                        int cx, int cy, int tamanho, boolean desmaiado) {
        int caixa = Math.max(48, (int) (tamanho * 2.45));
        int interno = Math.max(18, tamanho / FATOR_PIXEL);
        BufferedImage sprite = PixelUtil.renderizarPixelArt(caixa, caixa, FATOR_PIXEL,
                (g, lw, lh) -> desenhar(g, especie, tipo, estagio, lw / 2, lh / 2, interno, desmaiado));
        PixelUtil.desenharAmpliado(gOriginal, sprite, cx - caixa / 2, cy - caixa / 2, caixa, caixa);
    }

    private static void desenhar(Graphics2D g, String especie, Tipo tipo, int estagio,
                                 int cx, int cy, int s, boolean desmaiado) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        if (desmaiado) g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .42f));

        Color base = tipo.corPrincipal();
        Color light = tipo.corClara();
        Color dark = shade(base, .45f);
        Color deep = shade(base, .28f);
        Color ink = new Color(20, 19, 30);

        // Sombra de contato em poucos pixels, como nos sprites clássicos.
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(cx - s * 3 / 10, cy + s * 3 / 10, s * 6 / 10, Math.max(2, s / 12));

        String key = especie == null ? "" : especie.toLowerCase();
        if (key.contains("ember") || key.contains("cindr") || key.contains("pyron")) {
            fogo(g, cx, cy, s, estagio, base, light, dark, deep, ink);
        } else if (key.contains("ripplet") || key.contains("aquabit") || key.contains("leviat")) {
            agua(g, cx, cy, s, estagio, base, light, dark, deep, ink);
        } else if (key.contains("sprout") || key.contains("moss") || key.contains("floragon")) {
            planta(g, cx, cy, s, estagio, base, light, dark, deep, ink);
        } else if (key.contains("glacub") || key.contains("frost") || key.contains("glacior")) {
            gelo(g, cx, cy, s, estagio, base, light, dark, deep, ink);
        } else if (key.contains("voltik") || key.contains("stormion") || key.contains("zephyr")) {
            eletrico(g, cx, cy, s, estagio, base, light, dark, deep, ink);
        } else if (key.contains("terrakid") || key.contains("terragon") || key.contains("terralord")) {
            terra(g, cx, cy, s, estagio, base, light, dark, deep, ink);
        } else {
            generico(g, cx, cy, s, estagio, base, light, dark, ink);
        }
        g.setComposite(AlphaComposite.SrcOver);
    }

    // --------------------------------------------------------------------- FOGO
    private static void fogo(Graphics2D g, int cx, int cy, int s, int e,
                              Color b, Color l, Color d, Color deep, Color ink) {
        int w = s * (e == 1 ? 42 : e == 2 ? 50 : 59) / 100;
        int h = s * (e == 1 ? 42 : e == 2 ? 49 : 55) / 100;
        // cauda flamejante atrás
        poly(g, d, cx + w / 2 - 2, cy + h / 5,
                cx + w / 2 + s / 5, cy - h / 8,
                cx + w / 2 + s / 6, cy + h / 4,
                cx + w / 2 + s / 3, cy + h / 9,
                cx + w / 2 + s / 4, cy + h / 3);
        poly(g, l, cx + w / 2 + s / 6, cy - h / 8,
                cx + w / 2 + s / 4, cy - h / 4,
                cx + w / 2 + s / 3, cy + h / 9,
                cx + w / 2 + s / 5, cy + h / 5);

        if (e >= 2) {
            // asas crescentes
            poly(g, deep, cx - w / 2, cy - h / 8, cx - w / 2 - s / 4, cy - h / 3,
                    cx - w / 2 - s / 7, cy + h / 8, cx - w / 2 + 2, cy + h / 5);
            poly(g, deep, cx + w / 2, cy - h / 8, cx + w / 2 + s / 4, cy - h / 3,
                    cx + w / 2 + s / 7, cy + h / 8, cx + w / 2 - 2, cy + h / 5);
        }
        if (e == 3) {
            horn(g, d, cx - s / 7, cy - h / 2, -1, s / 6);
            horn(g, d, cx + s / 7, cy - h / 2, 1, s / 6);
        }
        corpo(g, cx, cy + s / 15, w, h, b, d, l, ink);
        olhos(g, cx, cy - s / 7, s / 8, e, ink);
        // crista aumenta a cada evolução
        int crest = s * (e == 1 ? 1 : e == 2 ? 2 : 3) / 12;
        for (int i = 0; i < e; i++) {
            poly(g, l, cx - crest * e / 2 + i * crest, cy - h / 2,
                    cx - crest * e / 2 + i * crest + crest / 2, cy - h / 2 - crest,
                    cx - crest * e / 2 + i * crest + crest, cy - h / 2);
        }
        patas(g, cx, cy + h / 2, w, d);
        if (e == 3) brilho(g, l, cx - w / 4, cy - h / 5, Math.max(2, s / 16));
    }

    // --------------------------------------------------------------------- ÁGUA
    private static void agua(Graphics2D g, int cx, int cy, int s, int e,
                              Color b, Color l, Color d, Color deep, Color ink) {
        int w = s * (e == 1 ? 44 : e == 2 ? 53 : 64) / 100;
        int h = s * (e == 1 ? 40 : e == 2 ? 47 : 54) / 100;
        // nadadeira caudal
        poly(g, d, cx + w / 2 - 2, cy + h / 6, cx + w / 2 + s / 4, cy - h / 5,
                cx + w / 2 + s / 4, cy + h / 4, cx + w / 2 + s / 3, cy + h / 2);
        // barbatana dorsal
        poly(g, deep, cx - s / 10, cy - h / 2 + 3, cx, cy - h / 2 - s / 5,
                cx + s / 8, cy - h / 2 + 3);
        if (e >= 2) {
            poly(g, l, cx - w / 2, cy, cx - w / 2 - s / 6, cy - h / 4,
                    cx - w / 2 + 2, cy + h / 8);
            poly(g, l, cx + w / 2, cy, cx + w / 2 + s / 6, cy - h / 4,
                    cx + w / 2 - 2, cy + h / 8);
        }
        corpo(g, cx, cy + s / 18, w, h, b, d, l, ink);
        olhos(g, cx, cy - s / 9, s / 8, e, ink);
        // guelras
        g.setColor(deep);
        g.fillRect(cx - w / 2 + 2, cy + s / 8, Math.max(2, s / 15), s / 7);
        g.fillRect(cx + w / 2 - Math.max(2, s / 15) - 2, cy + s / 8, Math.max(2, s / 15), s / 7);
        patas(g, cx, cy + h / 2, w, d);
        if (e == 3) {
            brilho(g, l, cx - w / 4, cy - h / 5, Math.max(2, s / 15));
            gota(g, l, cx + w / 2 + s / 7, cy - h / 3, s / 12);
            gota(g, l, cx - w / 2 - s / 8, cy + h / 8, s / 14);
        }
    }

    // --------------------------------------------------------------------- PLANTA
    private static void planta(Graphics2D g, int cx, int cy, int s, int e,
                                Color b, Color l, Color d, Color deep, Color ink) {
        int w = s * (e == 1 ? 43 : e == 2 ? 51 : 61) / 100;
        int h = s * (e == 1 ? 39 : e == 2 ? 48 : 56) / 100;
        // folhas da cabeça: 1 -> 2 -> coroa
        int folhas = e;
        for (int i = 0; i < folhas; i++) {
            int ox = (i - (folhas - 1) / 2) * s / 8;
            poly(g, l, cx + ox, cy - h / 2,
                    cx + ox - s / 10, cy - h / 2 - s / 5,
                    cx + ox + s / 12, cy - h / 2 - s / 8);
            g.setColor(d);
            g.drawLine(cx + ox, cy - h / 2, cx + ox, cy - h / 2 - s / 7);
        }
        // trepadeira/cauda
        poly(g, d, cx + w / 2 - 2, cy + h / 4,
                cx + w / 2 + s / 5, cy + h / 8,
                cx + w / 2 + s / 4, cy + h / 3,
                cx + w / 3, cy + h / 2);
        if (e >= 2) {
            // pequenas folhas laterais
            leaf(g, l, cx - w / 2, cy + h / 8, -1, s / 6);
            leaf(g, l, cx + w / 2, cy + h / 8, 1, s / 6);
        }
        corpo(g, cx, cy + s / 18, w, h, b, d, l, ink);
        olhos(g, cx, cy - s / 10, s / 8, e, ink);
        patas(g, cx, cy + h / 2, w, d);
        if (e == 3) {
            flor(g, l, cx, cy - h / 2 - s / 8, s / 7);
            brilho(g, new Color(255, 224, 92), cx + w / 5, cy - h / 8, Math.max(2, s / 18));
        }
    }

    // --------------------------------------------------------------------- GELO
    private static void gelo(Graphics2D g, int cx, int cy, int s, int e,
                              Color b, Color l, Color d, Color deep, Color ink) {
        int w = s * (e == 1 ? 42 : e == 2 ? 53 : 64) / 100;
        int h = s * (e == 1 ? 40 : e == 2 ? 48 : 58) / 100;
        // orelhas/chifres cristalinos
        poly(g, l, cx - w / 3, cy - h / 2 + 4, cx - w / 2, cy - h / 2 - s / 6,
                cx - w / 5, cy - h / 4);
        poly(g, l, cx + w / 3, cy - h / 2 + 4, cx + w / 2, cy - h / 2 - s / 6,
                cx + w / 5, cy - h / 4);
        if (e >= 2) {
            horn(g, deep, cx - w / 5, cy - h / 2, -1, s / 7);
            horn(g, deep, cx + w / 5, cy - h / 2, 1, s / 7);
        }
        corpo(g, cx, cy + s / 15, w, h, b, d, l, ink);
        olhos(g, cx, cy - s / 10, s / 8, e, ink);
        patas(g, cx, cy + h / 2, w, d);
        // placas de gelo nas costas
        for (int i = 0; i < e + 1; i++) {
            int ox = (i - e / 2) * s / 9;
            poly(g, deep, cx + ox, cy - h / 5, cx + ox + s / 14, cy - h / 2 - s / 8,
                    cx + ox + s / 9, cy - h / 5);
        }
        if (e == 3) {
            brilho(g, l, cx - w / 4, cy - h / 5, s / 14);
            brilho(g, l, cx + w / 3, cy, s / 18);
        }
    }

    // --------------------------------------------------------------------- ELÉTRICO
    private static void eletrico(Graphics2D g, int cx, int cy, int s, int e,
                                  Color b, Color l, Color d, Color deep, Color ink) {
        int w = s * (e == 1 ? 40 : e == 2 ? 49 : 58) / 100;
        int h = s * (e == 1 ? 41 : e == 2 ? 48 : 55) / 100;
        // cauda em zigue-zague
        poly(g, l, cx + w / 2 - 2, cy + h / 6,
                cx + w / 2 + s / 7, cy - s / 8,
                cx + w / 2 + s / 5, cy - s / 20,
                cx + w / 2 + s / 8, cy + s / 10,
                cx + w / 2 + s / 4, cy + s / 6,
                cx + w / 2 + s / 3, cy - s / 10,
                cx + w / 2 + s / 7, cy + s / 7);
        // orelhas pontudas
        poly(g, l, cx - w / 3, cy - h / 3, cx - w / 2, cy - h / 2 - s / 7, cx - w / 5, cy - h / 8);
        poly(g, l, cx + w / 3, cy - h / 3, cx + w / 2, cy - h / 2 - s / 7, cx + w / 5, cy - h / 8);
        corpo(g, cx, cy + s / 15, w, h, b, d, l, ink);
        olhos(g, cx, cy - s / 10, s / 8, e, ink);
        patas(g, cx, cy + h / 2, w, d);
        // faíscas aumentam com a evolução
        int sparks = e + 1;
        for (int i = 0; i < sparks; i++) {
            int ox = (i - sparks / 2) * s / 7;
            int oy = -h / 2 - (i % 2) * s / 10;
            poly(g, l, cx + ox, cy + oy, cx + ox + s / 18, cy + oy - s / 10,
                    cx + ox + s / 12, cy + oy, cx + ox + s / 22, cy + oy + s / 9);
        }
        if (e == 3) brilho(g, l, cx - w / 4, cy - h / 5, s / 16);
    }

    // --------------------------------------------------------------------- TERRA
    private static void terra(Graphics2D g, int cx, int cy, int s, int e,
                               Color b, Color l, Color d, Color deep, Color ink) {
        int w = s * (e == 1 ? 46 : e == 2 ? 56 : 68) / 100;
        int h = s * (e == 1 ? 41 : e == 2 ? 50 : 58) / 100;
        // carapaça/placas
        if (e >= 2) {
            poly(g, deep, cx - w / 2, cy - h / 4, cx - w / 3, cy - h / 2 - s / 8,
                    cx, cy - h / 2, cx + w / 3, cy - h / 2 - s / 8,
                    cx + w / 2, cy - h / 4, cx + w / 3, cy + h / 8,
                    cx, cy + h / 5, cx - w / 3, cy + h / 8);
        }
        corpo(g, cx, cy + s / 12, w, h, b, d, l, ink);
        olhos(g, cx, cy - s / 10, s / 8, e, ink);
        patas(g, cx, cy + h / 2, w, d);
        // chifres de pedra
        horn(g, deep, cx - w / 3, cy - h / 3, -1, s / (e == 1 ? 9 : 7));
        horn(g, deep, cx + w / 3, cy - h / 3, 1, s / (e == 1 ? 9 : 7));
        if (e == 3) {
            horn(g, l, cx, cy - h / 2, 0, s / 5);
            brilho(g, l, cx - w / 4, cy - h / 6, s / 17);
        }
    }

    // --------------------------------------------------------------- genérico
    private static void generico(Graphics2D g, int cx, int cy, int s, int e,
                                  Color b, Color l, Color d, Color ink) {
        int w = s * (38 + e * 7) / 100;
        int h = s * (38 + e * 6) / 100;
        corpo(g, cx, cy, w, h, b, d, l, ink);
        olhos(g, cx, cy - s / 9, s / 8, e, ink);
        patas(g, cx, cy + h / 2, w, d);
    }

    // ------------------------------------------------------------- primitives
    private static void corpo(Graphics2D g, int cx, int cy, int w, int h,
                               Color base, Color dark, Color light, Color ink) {
        // contorno deslocado em blocos para dar leitura de sprite
        g.setColor(ink);
        g.fillRect(cx - w / 2 - 2, cy - h / 2 - 2, w + 4, h + 4);
        g.setColor(dark);
        g.fillRect(cx - w / 2, cy - h / 2, w, h);
        g.setColor(base);
        g.fillRect(cx - w / 2 + 2, cy - h / 2 + 2, w - 4, h - 4);
        // sombra lateral + barriga
        g.setColor(new Color(255, 255, 255, 35));
        g.fillRect(cx - w / 3, cy - h / 4, w / 3, Math.max(2, h / 5));
        g.setColor(light);
        g.fillRect(cx - w / 5, cy + h / 8, Math.max(2, w / 4), Math.max(2, h / 5));
        // pixels de contorno inferior
        g.setColor(ink);
        g.fillRect(cx - w / 2 - 1, cy + h / 2, Math.max(2, w / 5), 2);
        g.fillRect(cx + w / 2 - Math.max(2, w / 5) + 1, cy + h / 2, Math.max(2, w / 5), 2);
    }

    private static void olhos(Graphics2D g, int cx, int y, int tamanho, int e, Color ink) {
        int gap = Math.max(2, tamanho + 1);
        g.setColor(Color.WHITE);
        g.fillRect(cx - gap - tamanho / 2, y, tamanho, tamanho);
        g.fillRect(cx + gap - tamanho / 2, y, tamanho, tamanho);
        g.setColor(ink);
        int p = Math.max(1, tamanho / 2);
        g.fillRect(cx - gap - p / 2 + (e == 3 ? 1 : 0), y + tamanho / 3, p, p);
        g.fillRect(cx + gap - p / 2 + (e == 3 ? 1 : 0), y + tamanho / 3, p, p);
    }

    private static void patas(Graphics2D g, int cx, int y, int w, Color dark) {
        int pw = Math.max(3, w / 6);
        g.setColor(dark);
        g.fillRect(cx - w / 3, y - 1, pw, Math.max(3, w / 10));
        g.fillRect(cx + w / 3 - pw, y - 1, pw, Math.max(3, w / 10));
    }

    private static void horn(Graphics2D g, Color c, int x, int y, int dir, int size) {
        int d = dir == 0 ? 0 : dir * size;
        poly(g, c, x, y, x + d, y - size, x + d + (dir == 0 ? size / 2 : -dir * size / 3), y + size / 3);
    }

    private static void leaf(Graphics2D g, Color c, int x, int y, int dir, int size) {
        poly(g, c, x, y, x + dir * size, y - size / 2,
                x + dir * size + dir * size / 4, y + size / 5, x + dir * size / 3, y + size / 3);
    }

    private static void flor(Graphics2D g, Color c, int x, int y, int size) {
        g.setColor(c);
        int r = Math.max(2, size / 3);
        g.fillRect(x - r, y, r * 2, r);
        g.fillRect(x - r / 2, y - r, r, r * 3);
        g.setColor(new Color(255, 214, 70));
        g.fillRect(x - 1, y + r / 2, 3, 3);
    }

    private static void gota(Graphics2D g, Color c, int x, int y, int size) {
        poly(g, c, x, y - size, x - size / 2, y, x, y + size / 2, x + size / 2, y);
    }

    private static void brilho(Graphics2D g, Color c, int x, int y, int size) {
        g.setColor(c);
        g.fillRect(x, y, Math.max(2, size), Math.max(2, size));
        g.fillRect(x - size / 2, y + size / 3, Math.max(2, size), Math.max(1, size / 2));
    }

    private static void poly(Graphics2D g, Color c, int... pts) {
        Polygon p = new Polygon();
        for (int i = 0; i + 1 < pts.length; i += 2) p.addPoint(pts[i], pts[i + 1]);
        g.setColor(c);
        g.fillPolygon(p);
    }

    private static Color shade(Color c, double fator) {
        return new Color((int) (c.getRed() * fator), (int) (c.getGreen() * fator), (int) (c.getBlue() * fator));
    }
}
