package monstraria.view;

import monstraria.Bestiario;
import monstraria.Monstro;
import monstraria.SaveManager;
import monstraria.Treinador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JanelaPrincipal extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cartas = new JPanel(cardLayout);
    private Treinador treinador;
    private Bestiario bestiario = new Bestiario();
    private final PainelBatalha painelBatalha;
    private final PainelEquipe painelEquipe;
    private final PainelBestiario painelBestiario;

    public JanelaPrincipal() {
        setTitle("MONSTER REALM // Captura, Evolu\u00e7\u00e3o & Batalha");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1080, 720);
        setMinimumSize(new Dimension(980, 650));
        setLocationRelativeTo(null);
        setResizable(true);

        painelBatalha = new PainelBatalha(this);
        painelEquipe = new PainelEquipe(this);
        painelBestiario = new PainelBestiario(this);

        cartas.add(new PainelMenu(this), "MENU");
        cartas.add(new PainelEscolha(this), "ESCOLHA");
        cartas.add(painelBatalha, "BATALHA");
        cartas.add(painelEquipe, "EQUIPE");
        cartas.add(painelBestiario, "BESTIARIO");
        add(cartas);
        mostrar("MENU");

        // Ao fechar pela janela (X), salva o progresso do jogador antes de sair.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sairDoJogo();
            }
        });
    }

    void mostrar(String nome) {
        if ("EQUIPE".equals(nome) && treinador != null) painelEquipe.atualizar();
        if ("BESTIARIO".equals(nome)) painelBestiario.atualizar();
        if ("BATALHA".equals(nome) && treinador != null) painelBatalha.atualizarStatus();
        cardLayout.show(cartas, nome);
    }

    void iniciarJornada(Monstro inicial) {
        treinador = new Treinador("Rithym");
        treinador.adicionarMonstro(inicial);
        bestiario.registrar(inicial);
        painelBatalha.iniciar(treinador);
        mostrar("BATALHA");
    }

    /** Carrega o progresso salvo em disco e retoma a aventura de onde parou. */
    void carregarJogoSalvo() {
        Object[] dados = SaveManager.carregar();
        if (dados == null) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum jogo salvo foi encontrado.",
                    "Carregar Jogo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        treinador = (Treinador) dados[0];
        bestiario = (Bestiario) dados[1];
        painelBatalha.iniciar(treinador);
        mostrar("BATALHA");
    }

    /** Salva a aventura atual (se houver) em disco. */
    void salvarJogoAtual() {
        SaveManager.salvar(treinador, bestiario);
    }

    /** Salva o progresso do jogador (quando existir) e encerra o jogo. */
    void sairDoJogo() {
        salvarJogoAtual();
        dispose();
        System.exit(0);
    }

    Treinador getTreinador() { return treinador; }
    Bestiario getBestiario() { return bestiario; }
}
