package monstraria.view;

import monstraria.SaveManager;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de título: fundo com paisagem de floresta em pixel art, nome do jogo
 * centralizado ("MONSTER REALM") e as quatro opções principais.
 */
class PainelMenu extends FundoFloresta {

    PainelMenu(JanelaPrincipal janela) {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;

        // ---- caixa translúcida central que abriga título e botões ----
        JPanel caixa = Estilo.painelCaixaPixel(new Color(10, 12, 22, 190));
        caixa.setLayout(new GridBagLayout());
        caixa.setBorder(BorderFactory.createEmptyBorder(30, 46, 30, 46));
        GridBagConstraints cc = new GridBagConstraints();
        cc.gridx = 0;
        cc.anchor = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel(new ImageIcon(
                PixelUtil.textoPixelado("MONSTER REALM", 64,
                        Estilo.GOLD, new Color(30, 18, 10), new Color(0, 0, 0, 160))));
        cc.gridy = 0;
        cc.insets = new Insets(0, 0, 6, 0);
        caixa.add(titulo, cc);

        JLabel sub = Estilo.subtitulo("CAPTURA  \u2022  EVOLU\u00c7\u00c3O  \u2022  BATALHA");
        sub.setFont(Estilo.fontePixel(13));
        sub.setForeground(Estilo.CYAN);
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        cc.gridy = 1;
        cc.insets = new Insets(0, 0, 28, 0);
        caixa.add(sub, cc);

        JButton novoJogo = botaoMenu("NOVO JOGO", Estilo.PURPLE);
        novoJogo.addActionListener(e -> janela.mostrar("ESCOLHA"));
        cc.gridy = 2;
        cc.insets = new Insets(0, 0, 12, 0);
        caixa.add(novoJogo, cc);

        JButton carregarJogo = botaoMenu("CARREGAR JOGO", Estilo.CYAN);
        carregarJogo.setEnabled(SaveManager.existeJogoSalvo());
        carregarJogo.addActionListener(e -> janela.carregarJogoSalvo());
        cc.gridy = 3;
        cc.insets = new Insets(0, 0, 12, 0);
        caixa.add(carregarJogo, cc);

        JButton monsterDex = botaoMenu("MONSTERDEX", Estilo.GREEN);
        monsterDex.addActionListener(e -> janela.mostrar("BESTIARIO"));
        cc.gridy = 4;
        cc.insets = new Insets(0, 0, 12, 0);
        caixa.add(monsterDex, cc);

        JButton sair = botaoMenu("SAIR", Estilo.RED);
        sair.addActionListener(e -> janela.sairDoJogo());
        cc.gridy = 5;
        caixa.add(sair, cc);

        c.gridy = 0;
        add(caixa, c);

        // reabilita "Carregar Jogo" toda vez que o menu volta a ficar visível
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                carregarJogo.setEnabled(SaveManager.existeJogoSalvo());
            }
        });
    }

    private static JButton botaoMenu(String texto, Color cor) {
        JButton b = new BotaoMadeira(texto, cor);
        b.setPreferredSize(new Dimension(330, 58));
        return b;
    }
}
