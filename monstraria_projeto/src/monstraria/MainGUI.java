package monstraria;

import monstraria.view.JanelaPrincipal;

import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da versão com interface gráfica (Swing) do jogo.
 * Abre a janela e delega toda a lógica para as classes já existentes
 * do pacote monstraria (Monstro, Treinador, Bestiario, Ataque, etc.).
 */
public class MainGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = new JanelaPrincipal();
            janela.setVisible(true);
        });
    }
}
