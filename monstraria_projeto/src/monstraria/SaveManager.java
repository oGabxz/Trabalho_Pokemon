package monstraria;

import java.io.*;

/**
 * Responsável por persistir e recuperar o progresso do jogador em disco.
 * Usa serialização Java simples: um único arquivo guarda o Treinador
 * (equipe, níveis, XP...) e o Bestiário (espécies já descobertas).
 */
public final class SaveManager {

    private static final String ARQUIVO_SAVE = "monsterrealm_save.dat";

    private SaveManager() {}

    /** Indica se existe um jogo salvo em disco. */
    public static boolean existeJogoSalvo() {
        return new File(ARQUIVO_SAVE).exists();
    }

    /** Salva o progresso atual. Não faz nada se ainda não houver treinador (jornada não iniciada). */
    public static boolean salvar(Treinador treinador, Bestiario bestiario) {
        if (treinador == null) {
            return false;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_SAVE))) {
            out.writeObject(treinador);
            out.writeObject(bestiario);
            return true;
        } catch (IOException e) {
            System.err.println("Não foi possível salvar o jogo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carrega o progresso salvo.
     *
     * @return um vetor {Treinador, Bestiario} ou {@code null} se não houver save
     * ou se ocorrer algum erro de leitura.
     */
    public static Object[] carregar() {
        File arquivo = new File(ARQUIVO_SAVE);
        if (!arquivo.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            Treinador treinador = (Treinador) in.readObject();
            Bestiario bestiario = (Bestiario) in.readObject();
            return new Object[]{treinador, bestiario};
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Não foi possível carregar o jogo: " + e.getMessage());
            return null;
        }
    }
}
