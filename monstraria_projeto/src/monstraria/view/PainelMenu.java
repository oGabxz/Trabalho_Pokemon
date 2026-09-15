package monstraria.view;

import javax.swing.*;
import java.awt.*;

class PainelMenu extends JPanel {
    PainelMenu(JanelaPrincipal janela) {
        setLayout(new GridBagLayout());
        setBackground(Estilo.BG);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;

        JLabel mark = new JLabel("\u25C8");
        mark.setFont(new Font("SansSerif", Font.BOLD, 74));
        mark.setForeground(Estilo.PURPLE);
        c.gridy = 0;
        c.insets = new Insets(0, 0, 4, 0);
        add(mark, c);

        JLabel titulo = Estilo.titulo("MONSTRARIA", 48);
        c.gridy = 1;
        add(titulo, c);
        JLabel sub = Estilo.subtitulo("CAPTURA  \u2022  EVOLU\u00c7\u00c3O  \u2022  BATALHA");
        sub.setFont(new Font("SansSerif", Font.BOLD, 14));
        sub.setForeground(Estilo.CYAN);
        c.gridy = 2;
        c.insets = new Insets(4, 0, 26, 0);
        add(sub, c);

        JLabel lore = Estilo.subtitulo("9 criaturas em 3 linhas evolutivas esperam pelo seu pr\u00f3ximo v\u00ednculo.");
        c.gridy = 3;
        c.insets = new Insets(0, 0, 26, 0);
        add(lore, c);

        JButton jogar = new JButton("NOVA JORNADA  \u2192");
        Estilo.aplicarBotao(jogar, Estilo.PURPLE);
        jogar.setPreferredSize(new Dimension(300, 52));
        jogar.addActionListener(e -> janela.mostrar("ESCOLHA"));
        c.gridy = 4;
        c.insets = new Insets(0, 0, 10, 0);
        add(jogar, c);

        JButton bestiario = new JButton("Besti\u00e1rio");
        Estilo.aplicarBotao(bestiario, Estilo.PANEL_2);
        bestiario.setPreferredSize(new Dimension(300, 46));
        bestiario.addActionListener(e -> janela.mostrar("BESTIARIO"));
        c.gridy = 5;
        c.insets = new Insets(0, 0, 10, 0);
        add(bestiario, c);

        JButton sair = new JButton("Sair");
        Estilo.aplicarBotao(sair, Estilo.PANEL_2);
        sair.setPreferredSize(new Dimension(300, 46));
        sair.addActionListener(e -> System.exit(0));
        c.gridy = 6;
        add(sair, c);
    }
}
