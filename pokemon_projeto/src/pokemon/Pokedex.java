package pokemon;

import java.util.ArrayList;

/** Registra toda criatura ja vista ou capturada, sem duplicar entradas. */
public class Pokedex {

    private ArrayList<Pokemon> registrados;

    public Pokedex() {
        this.registrados = new ArrayList<>();
    }

    /** Registra a criatura apenas se ela ainda nao consta na Pokedex. */
    public void registrar(Pokemon pokemon) {
        if (!jaRegistrado(pokemon)) {
            registrados.add(pokemon);
        }
    }

    /** Duas criaturas sao consideradas iguais se tiverem o mesmo nome. */
    public boolean jaRegistrado(Pokemon pokemon) {
        for (Pokemon p : registrados) {
            if (p.getNome().equalsIgnoreCase(pokemon.getNome())) {
                return true;
            }
        }
        return false;
    }

    public int getQuantidadeRegistrada() {
        return registrados.size();
    }

    public ArrayList<Pokemon> getRegistrados() {
        return registrados;
    }

    public void listar() {
        System.out.println("===== POKEDEX (" + registrados.size() + " registro(s)) =====");
        for (Pokemon p : registrados) {
            System.out.println(" - " + p.getNome() + " [" + p.getTipo() + "]");
        }
    }
}
