package monstraria;

import java.awt.Color;

/** Tipos elementares existentes no jogo (relação de vantagem em triângulo). */
public enum Tipo {
    FOGO, AGUA, PLANTA, GELO, ELETRICO, TERRA;

    /** Cor associada ao tipo, usada pela interface gráfica para desenhar sprites e textos. */
    public Color corPrincipal() {
        switch (this) {
            case FOGO:   return new Color(230, 92, 46);
            case AGUA:   return new Color(58, 130, 214);
            case PLANTA: return new Color(87, 168, 74);
            case GELO: return new Color(93, 191, 224);
            case ELETRICO: return new Color(225, 184, 45);
            case TERRA: return new Color(164, 116, 66);
            default:     return Color.GRAY;
        }
    }

    /** Cor de destaque (mais clara) usada em brilhos e detalhes do sprite. */
    public Color corClara() {
        switch (this) {
            case FOGO:   return new Color(255, 176, 89);
            case AGUA:   return new Color(146, 214, 255);
            case PLANTA: return new Color(178, 224, 120);
            case GELO: return new Color(190, 240, 255);
            case ELETRICO: return new Color(255, 229, 105);
            case TERRA: return new Color(220, 174, 113);
            default:     return Color.LIGHT_GRAY;
        }
    }

    /** Nome do tipo formatado para exibição na interface gráfica. */
    public String nomeExibicao() {
        switch (this) {
            case FOGO:   return "Fogo";
            case AGUA:   return "Água";
            case PLANTA: return "Planta";
            case GELO: return "Gelo";
            case ELETRICO: return "Elétrico";
            case TERRA: return "Terra";
            default:     return "?";
        }
    }
}
