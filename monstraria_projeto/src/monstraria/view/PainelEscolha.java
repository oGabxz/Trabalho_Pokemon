package monstraria.view;

import monstraria.FabricaMonstros;
import monstraria.Tipo;
import javax.swing.*;
import java.awt.*;

class PainelEscolha extends JPanel {
    PainelEscolha(JanelaPrincipal janela) {
        setLayout(new BorderLayout(0, 20));
        setBackground(Estilo.BG);
        setBorder(BorderFactory.createEmptyBorder(34, 42, 34, 42));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JPanel t = new JPanel();
        t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
        t.setOpaque(false);
        t.add(Estilo.titulo("ESCOLHA SEU MONSTRO", 30));
        t.add(Box.createVerticalStrut(5));
        t.add(Estilo.subtitulo("Seu primeiro v\u00ednculo define o come\u00e7o da jornada \u2014 e evoluir\u00e1 duas vezes."));
        top.add(t, BorderLayout.WEST);
        JButton voltar = new JButton("\u2190 Menu");
        Estilo.aplicarBotao(voltar, Estilo.PANEL_2);
        voltar.addActionListener(e -> janela.mostrar("MENU"));
        top.add(voltar, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 22, 0));
        cards.setOpaque(false);
        cards.add(new CartaoMonstro(Tipo.FOGO, "EMBERIT", "Filhote \u00edgneo \u2022 evolui em Cindrake e Pyronix",
                "Pequeno e \u00e1gil, guarda uma chama interna que cresce a cada batalha.", Estilo.RED,
                () -> janela.iniciarJornada(FabricaMonstros.novoInicial(Tipo.FOGO))));
        cards.add(new CartaoMonstro(Tipo.AGUA, "RIPPLET", "Filhote aqu\u00e1tico \u2022 evolui em Aquabite e Leviatide",
                "Vive perto de riachos e reage rapidamente a amea\u00e7as.", Estilo.CYAN,
                () -> janela.iniciarJornada(FabricaMonstros.novoInicial(Tipo.AGUA))));
        cards.add(new CartaoMonstro(Tipo.PLANTA, "SPROUTLE", "Broto selvagem \u2022 evolui em Mossclaw e Floragon",
                "Absorve energia do ambiente para crescer com o tempo.", Estilo.GREEN,
                () -> janela.iniciarJornada(FabricaMonstros.novoInicial(Tipo.PLANTA))));
        add(cards, BorderLayout.CENTER);
    }
}
