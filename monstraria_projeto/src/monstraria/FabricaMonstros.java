package monstraria;

import java.util.Random;

/**
 * Fábrica responsável por criar os monstros iniciais (escolhidos pelo
 * jogador no começo da jornada) e os monstros selvagens que aparecem
 * durante a exploração. Mantém a criação de instâncias fora da interface
 * gráfica.
 */
public final class FabricaMonstros {

    private static final Random RANDOM = new Random();

    private FabricaMonstros() {
        // classe utilitária, sem instâncias
    }

    /** Cria o monstro inicial (estágio 1) do tipo escolhido pelo jogador. */
    public static Monstro novoInicial(Tipo tipo) {
        return criar(tipo);
    }

    private static Monstro criar(Tipo tipo) {
        switch (tipo) {
            case FOGO:   return new MonstroFogo();
            case AGUA:   return new MonstroAgua();
            default:     return new MonstroPlanta();
        }
    }

    /** Gera uma criatura selvagem aleatória: tipo, nível e estatísticas variados. */
    public static Monstro selvagemAleatorio() {
        Tipo[] tipos = Tipo.values();
        Tipo tipo = tipos[RANDOM.nextInt(tipos.length)];
        Monstro monstro = criar(tipo);

        int nivel = 1 + RANDOM.nextInt(9); // 1 a 9
        monstro.definirNivelInicial(nivel);

        int bonusVida = RANDOM.nextInt(11) - 5;   // -5 a +5
        int bonusAtaque = RANDOM.nextInt(5) - 2;  // -2 a +2
        monstro.receberBonus(bonusVida, bonusAtaque);

        return monstro;
    }
}
