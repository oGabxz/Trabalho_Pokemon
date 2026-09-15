package monstraria;

import java.awt.Color;

/** Tipos elementares existentes no jogo (relação de vantagem em triângulo). */
public enum Tipo {
    FOGO, AGUA, PLANTA;

    /** Cor associada ao tipo, usada pela interface gráfica para desenhar sprites e textos. */
    public Color corPrincipal() {
        switch (this) {
            case FOGO:   return new Color(230, 92, 46);
            case AGUA:   return new Color(58, 130, 214);
            case PLANTA: return new Color(87, 168, 74);
            default:     return Color.GRAY;
        }
    }

    /** Cor de destaque (mais clara) usada em brilhos e detalhes do sprite. */
    public Color corClara() {
        switch (this) {
            case FOGO:   return new Color(255, 176, 89);
            case AGUA:   return new Color(146, 214, 255);
            case PLANTA: return new Color(178, 224, 120);
            default:     return Color.LIGHT_GRAY;
        }
    }

    /** Nome do tipo formatado para exibição na interface gráfica. */
    public String nomeExibicao() {
        switch (this) {
            case FOGO:   return "Fogo";
            case AGUA:   return "Água";
            case PLANTA: return "Planta";
            default:     return "?";
        }
    }
}
