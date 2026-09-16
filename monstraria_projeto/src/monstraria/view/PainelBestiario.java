package monstraria.view;

import monstraria.Ataque;
import monstraria.Bestiario;
import monstraria.CatalogoEspecies;
import monstraria.EspecieInfo;
import monstraria.FabricaMonstros;
import monstraria.Monstro;

import javax.swing.*;
import java.awt.*;

/** MonsterDex com sprites, tipagem, estágio e golpes da espécie. */
class PainelBestiario extends JPanel {
    private final JanelaPrincipal janela;
    private final JPanel lista = new JPanel();
    private final JLabel total = new JLabel();
    private final JButton voltar = new JButton();

    PainelBestiario(JanelaPrincipal janela) {
        this.janela = janela;
        setLayout(new BorderLayout(0, 18));
        setBackground(Estilo.BG);
        setBorder(BorderFactory.createEmptyBorder(30, 38, 28, 38));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JPanel t = new JPanel();
        t.setOpaque(false);
        t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
        t.add(Estilo.titulo("MONSTERDEX", 28));
        t.add(Box.createVerticalStrut(4));
        t.add(Estilo.subtitulo("As 18 espécies do jogo. Descubra criaturas, tipagens e seus golpes."));
        top.add(t, BorderLayout.WEST);
        Estilo.aplicarBotao(voltar, Estilo.PANEL_2);
        top.add(voltar, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        lista.setOpaque(false);
        lista.setLayout(new GridLayout(0, 3, 14, 14));
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        add(scroll, BorderLayout.CENTER);

        total.setFont(new Font("SansSerif", Font.BOLD, 13));
        total.setForeground(Estilo.MUTED);
        add(total, BorderLayout.SOUTH);
    }

    void atualizar() {
        boolean emJogo = janela.getTreinador() != null;
        voltar.setText(emJogo ? "\u2190 Equipe" : "\u2190 Menu");
        for (java.awt.event.ActionListener l : voltar.getActionListeners()) {
            voltar.removeActionListener(l);
        }
        voltar.addActionListener(e -> janela.mostrar(emJogo ? "EQUIPE" : "MENU"));

        Bestiario bestiario = janela.getBestiario();
        lista.removeAll();
        for (EspecieInfo info : CatalogoEspecies.TODAS) {
            lista.add(cartao(info, bestiario.foiDescoberto(info.nome)));
        }
        total.setText(bestiario.getQuantidadeRegistrada() + " / " + CatalogoEspecies.TODAS.size()
                + " CRIATURA(S) DESCOBERTA(S)");
        lista.revalidate();
        lista.repaint();
    }

    private JPanel cartao(EspecieInfo info, boolean descoberto) {
        JPanel box = Estilo.painelArredondado(Estilo.PANEL);
        box.setLayout(new BorderLayout(0, 5));
        box.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));

        JPanel sp = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                MonstroSprite.desenharPixelado((Graphics2D) g, info.nome, info.tipo, info.estagio,
                        getWidth() / 2, getHeight() / 2, 115, !descoberto);
            }
        };
        sp.setOpaque(false);
        sp.setPreferredSize(new Dimension(170, 125));
        box.add(sp, BorderLayout.NORTH);

        JLabel n = Estilo.titulo(descoberto ? info.nome : "???", 17);
        n.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel ty = Estilo.subtitulo(descoberto
                ? info.tipo.nomeExibicao().toUpperCase() + "  •  ESTÁGIO " + info.estagio + "/3"
                : "NÃO DESCOBERTO");
        ty.setForeground(descoberto ? info.tipo.corPrincipal() : Estilo.MUTED);
        ty.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel foot = new JPanel();
        foot.setOpaque(false);
        foot.setLayout(new BoxLayout(foot, BoxLayout.Y_AXIS));
        foot.add(n);
        foot.add(Box.createVerticalStrut(3));
        foot.add(ty);

        if (descoberto) {
            Monstro amostra = criarAmostra(info);
            JLabel golpes = Estilo.subtitulo("GOLPES");
            golpes.setFont(new Font("Monospaced", Font.BOLD, 11));
            golpes.setForeground(Estilo.TEXT);
            golpes.setAlignmentX(Component.CENTER_ALIGNMENT);
            foot.add(Box.createVerticalStrut(7));
            foot.add(golpes);
            for (Ataque ataque : amostra.getGolpes()) {
                JLabel g = Estilo.subtitulo("• " + ataque.getNome() + "  " + ataque.getPoder());
                g.setFont(new Font("SansSerif", Font.PLAIN, 10));
                g.setAlignmentX(Component.CENTER_ALIGNMENT);
                foot.add(g);
            }
        }

        box.add(foot, BorderLayout.CENTER);
        return box;
    }

    /** Cria a linha evolutiva correspondente e avança até o estágio da ficha. */
    private Monstro criarAmostra(EspecieInfo info) {
        Monstro m = FabricaMonstros.novoInicial(info.tipo);
        int nivel = info.estagio == 1 ? 1 : info.estagio == 2 ? Monstro.NIVEL_EVOLUCAO_1 : Monstro.NIVEL_EVOLUCAO_2;
        m.definirNivelInicial(nivel);
        return m;
    }
}
