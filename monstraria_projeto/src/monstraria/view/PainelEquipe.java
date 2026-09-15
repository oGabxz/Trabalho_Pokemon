package monstraria.view;

import monstraria.Monstro;
import monstraria.Treinador;
import javax.swing.*;
import java.awt.*;

class PainelEquipe extends JPanel {
    private final JanelaPrincipal janela;
    private final JPanel lista = new JPanel();
    private final JLabel contador = new JLabel();

    PainelEquipe(JanelaPrincipal janela) {
        this.janela = janela;
        setLayout(new BorderLayout(0, 18));
        setBackground(Estilo.BG);
        setBorder(BorderFactory.createEmptyBorder(30, 38, 28, 38));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.add(Estilo.titulo("SUA EQUIPE", 28));
        titles.add(Box.createVerticalStrut(4));
        titles.add(Estilo.subtitulo("Gerencie seus v\u00ednculos e acompanhe o n\u00edvel de evolu\u00e7\u00e3o de cada um."));
        top.add(titles, BorderLayout.WEST);

        JButton curar = new JButton("\u2726 CURAR EQUIPE");
        Estilo.aplicarBotao(curar, Estilo.GREEN);
        curar.addActionListener(e -> curarEquipe());
        top.add(curar, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        lista.setOpaque(false);
        lista.setLayout(new GridLayout(0, 2, 14, 14));
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        contador.setForeground(Estilo.MUTED);
        contador.setFont(new Font("SansSerif", Font.BOLD, 13));
        bottom.add(contador, BorderLayout.WEST);
        JPanel bs = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        bs.setOpaque(false);
        JButton dex = new JButton("Besti\u00e1rio");
        Estilo.aplicarBotao(dex, Estilo.PANEL_2);
        dex.addActionListener(e -> janela.mostrar("BESTIARIO"));
        JButton voltar = new JButton("\u2190 Explora\u00e7\u00e3o");
        Estilo.aplicarBotao(voltar, Estilo.PURPLE);
        voltar.addActionListener(e -> janela.mostrar("BATALHA"));
        bs.add(dex);
        bs.add(voltar);
        bottom.add(bs, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }

    private void curarEquipe() {
        for (Monstro m : janela.getTreinador().getEquipe()) m.curar();
        atualizar();
    }

    void atualizar() {
        lista.removeAll();
        for (Monstro m : janela.getTreinador().getEquipe()) lista.add(cartao(m));
        contador.setText("V\u00cdNCULOS  " + janela.getTreinador().getEquipe().size() + " / " + Treinador.TAMANHO_MAXIMO_EQUIPE
                + "     \u2022     BESTI\u00c1RIO  " + janela.getBestiario().getQuantidadeRegistrada() + "/9");
        lista.revalidate();
        lista.repaint();
    }

    private JPanel cartao(Monstro m) {
        JPanel box = Estilo.painelArredondado(Estilo.PANEL);
        box.setLayout(new BorderLayout(14, 0));
        box.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        JPanel sp = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                MonstroSprite.desenhar((Graphics2D) g, m.getTipo(), m.getEstagio(), getWidth() / 2, getHeight() / 2, 105, !m.estaVivo());
            }
        };
        sp.setOpaque(false);
        sp.setPreferredSize(new Dimension(120, 125));
        box.add(sp, BorderLayout.WEST);
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel n = Estilo.titulo(m.getNome() + "  Nv." + m.getNivel(), 18);
        JLabel tipo = Estilo.subtitulo("TIPO  " + m.getTipo().nomeExibicao() + "  \u2022  EST\u00c1GIO " + m.getEstagio() + "/3");
        tipo.setForeground(m.getTipo().corPrincipal());
        JLabel hp = Estilo.subtitulo("HP  " + m.getVidaAtual() + " / " + m.getVidaMaxima());
        JLabel atk = Estilo.subtitulo("ATAQUE  " + m.getAtaque());
        JLabel xp = Estilo.subtitulo("XP  " + m.getXpAtual() + " / " + m.xpParaProximoNivel());
        info.add(n);
        info.add(Box.createVerticalStrut(6));
        info.add(tipo);
        info.add(Box.createVerticalStrut(8));
        info.add(hp);
        info.add(atk);
        info.add(xp);
        box.add(info, BorderLayout.CENTER);
        return box;
    }
}
