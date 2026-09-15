package monstraria;

import java.util.List;

/** As 9 espécies existentes no jogo (3 linhas evolutivas de 3 estágios cada). */
public final class CatalogoEspecies {

    public static final List<EspecieInfo> TODAS = List.of(
            new EspecieInfo("Emberit",   Tipo.FOGO,   1),
            new EspecieInfo("Cindrake",  Tipo.FOGO,   2),
            new EspecieInfo("Pyronix",   Tipo.FOGO,   3),
            new EspecieInfo("Ripplet",   Tipo.AGUA,   1),
            new EspecieInfo("Aquabite",  Tipo.AGUA,   2),
            new EspecieInfo("Leviatide", Tipo.AGUA,   3),
            new EspecieInfo("Sproutle",  Tipo.PLANTA, 1),
            new EspecieInfo("Mossclaw",  Tipo.PLANTA, 2),
            new EspecieInfo("Floragon",  Tipo.PLANTA, 3)
    );

    private CatalogoEspecies() {}
}
