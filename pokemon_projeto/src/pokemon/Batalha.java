package pokemon;

/** Controla o combate por turnos entre duas criaturas. */
public class Batalha {

    /**
     * Batalha 1x1 por turnos: atacam alternadamente ate um desmaiar.
     * A vantagem de tipo e aplicada dentro de atacar() (polimorfismo).
     *
     * @return o Pokemon vencedor
     */
    public Pokemon lutar(Pokemon primeiro, Pokemon segundo) {
        System.out.println("===== BATALHA =====");
        System.out.println(primeiro.getNome() + " VS " + segundo.getNome());
        int turno = 1;

        while (primeiro.estaVivo() && segundo.estaVivo()) {
            System.out.println("--- Turno " + turno + " ---");
            primeiro.atacar(segundo);
            if (segundo.estaVivo()) {
                segundo.atacar(primeiro);
            }
            turno++;
        }

        Pokemon vencedor = primeiro.estaVivo() ? primeiro : segundo;
        Pokemon perdedor = primeiro.estaVivo() ? segundo : primeiro;
        System.out.println(perdedor.getNome() + " desmaiou! "
                + vencedor.getNome() + " venceu a batalha!");
        return vencedor;
    }
}
