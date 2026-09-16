package monstraria;

import java.util.List;

/** Catálogo completo das 18 espécies (6 linhas evolutivas de 3 estágios). */
public final class CatalogoEspecies {

    public static final List<EspecieInfo> TODAS = List.of(
            new EspecieInfo("Emberit",   Tipo.FOGO,     1),
            new EspecieInfo("Cindrake",  Tipo.FOGO,     2),
            new EspecieInfo("Pyronix",   Tipo.FOGO,     3),
            new EspecieInfo("Ripplet",   Tipo.AGUA,     1),
            new EspecieInfo("Aquabite",  Tipo.AGUA,     2),
            new EspecieInfo("Leviatide", Tipo.AGUA,     3),
            new EspecieInfo("Sproutle",  Tipo.PLANTA,   1),
            new EspecieInfo("Mossclaw",  Tipo.PLANTA,   2),
            new EspecieInfo("Floragon",  Tipo.PLANTA,   3),
            new EspecieInfo("Glacub",    Tipo.GELO,     1),
            new EspecieInfo("Frosthorn", Tipo.GELO,     2),
            new EspecieInfo("Glacior",   Tipo.GELO,     3),
            new EspecieInfo("Voltik",    Tipo.ELETRICO, 1),
            new EspecieInfo("Stormion",  Tipo.ELETRICO, 2),
            new EspecieInfo("Zephyron",  Tipo.ELETRICO, 3),
            new EspecieInfo("Terrakid",  Tipo.TERRA,    1),
            new EspecieInfo("Terragon",  Tipo.TERRA,    2),
            new EspecieInfo("Terralord", Tipo.TERRA,    3)
    );

    private CatalogoEspecies() {}
}
