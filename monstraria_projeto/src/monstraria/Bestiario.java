package monstraria;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/** Registra toda espécie já vista ou capturada, sem duplicar entradas. */
public class Bestiario {

    private final ArrayList<Monstro> registrados = new ArrayList<>();
    private final Set<String> nomesDescobertos = new LinkedHashSet<>();

    /** Registra a criatura apenas se a espécie (pelo nome) ainda não consta no bestiário. */
    public void registrar(Monstro monstro) {
        nomesDescobertos.add(monstro.getNome());
        if (!jaRegistrado(monstro)) {
            registrados.add(monstro);
        }
    }

    public boolean jaRegistrado(Monstro monstro) {
        for (Monstro m : registrados) {
            if (m.getNome().equalsIgnoreCase(monstro.getNome())) {
                return true;
            }
        }
        return false;
    }

    public boolean foiDescoberto(String nomeEspecie) {
        return nomesDescobertos.contains(nomeEspecie);
    }

    public int getQuantidadeRegistrada() {
        return nomesDescobertos.size();
    }

    public ArrayList<Monstro> getRegistrados() {
        return registrados;
    }
}
