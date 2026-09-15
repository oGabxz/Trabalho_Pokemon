package monstraria.view;

import monstraria.Bestiario;
import monstraria.Monstro;
import monstraria.Treinador;
import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cartas = new JPanel(cardLayout);
    private Treinador treinador;
    private final Bestiario bestiario = new Bestiario();
    private final PainelBatalha painelBatalha;
    private final PainelEquipe painelEquipe;
    private final PainelBestiario painelBestiario;

    public JanelaPrincipal() {
        setTitle("MONSTRARIA // Captura, Evolu\u00e7\u00e3o & Batalha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    Treinador getTreinador() { return treinador; }
    Bestiario getBestiario() { return bestiario; }
}
