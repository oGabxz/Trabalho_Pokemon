package monstraria;

import java.util.List;
import java.util.Random;

/** Controla o combate por turnos entre duas criaturas (versão texto/console). */
public class Batalha {

    private static final Random RANDOM = new Random();

    /** Batalha 1x1 automática: atacam alternadamente com golpes aleatórios até um desmaiar. */
    public Monstro lutar(Monstro primeiro, Monstro segundo) {
        while (primeiro.estaVivo() && segundo.estaVivo()) {
            primeiro.atacar(segundo, golpeAleatorio(primeiro));
            if (segundo.estaVivo()) {
                segundo.atacar(primeiro, golpeAleatorio(segundo));
            }
        }
        return primeiro.estaVivo() ? primeiro : segundo;
    }

    public static Ataque golpeAleatorio(Monstro monstro) {
        List<Ataque> golpes = monstro.getGolpes();
        return golpes.get(RANDOM.nextInt(golpes.size()));
    }

    /** XP concedida ao vencer uma criatura selvagem, com base no nível dela. */
    public static int xpDeVitoria(Monstro selvagemDerrotado) {
        return 15 + selvagemDerrotado.getNivel() * 5;
    }
}
