package monstraria.view;

import monstraria.Bestiario;
import monstraria.EspecieInfo;
import monstraria.CatalogoEspecies;
import javax.swing.*;
import java.awt.*;

class PainelBestiario extends JPanel {
    private final JanelaPrincipal janela;
    private final JPanel lista = new JPanel();
    private final JLabel total = new JLabel();

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
        t.add(Estilo.titulo("BESTI\u00c1RIO", 28));
        t.add(Box.createVerticalStrut(4));
        t.add(Estilo.subtitulo("As 9 esp\u00e9cies do jogo. Criaturas ainda n\u00e3o encontradas aparecem como silhuetas."));
        top.add(t, BorderLayout.WEST);
        JButton voltar = new JButton("\u2190 Equipe");
        Estilo.aplicarBotao(voltar, Estilo.PANEL_2);
        voltar.addActionListener(e -> janela.mostrar("EQUIPE"));
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
        Bestiario bestiario = janela.getBestiario();
        lista.removeAll();
        for (EspecieInfo info : CatalogoEspecies.TODAS) {
            lista.add(cartao(info, bestiario.foiDescoberto(info.nome)));
        }
        total.setText(bestiario.getQuantidadeRegistrada() + " / " + CatalogoEspecies.TODAS.size() + " CRIATURA(S) DESCOBERTA(S)");
        lista.revalidate();
        lista.repaint();
    }

    private JPanel cartao(EspecieInfo info, boolean descoberto) {
        JPanel box = Estilo.painelArredondado(Estilo.PANEL);
        box.setLayout(new BorderLayout());
        box.setBorder(BorderFactory.createEmptyBorder(10, 10, 12, 10));
        JPanel sp = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (descoberto) {
                    MonstroSprite.desenhar((Graphics2D) g, info.tipo, info.estagio, getWidth() / 2, getHeight() / 2, 125, false);
                } else {
                    MonstroSprite.desenhar((Graphics2D) g, info.tipo, info.estagio, getWidth() / 2, getHeight() / 2, 125, true);
                }
            }
        };
        sp.setOpaque(false);
        sp.setPreferredSize(new Dimension(160, 135));
        box.add(sp, BorderLayout.CENTER);
        JLabel n = Estilo.titulo(descoberto ? info.nome : "???", 17);
        n.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel ty = Estilo.subtitulo(descoberto ? (info.tipo.nomeExibicao().toUpperCase() + "  \u2022  EST\u00c1GIO " + info.estagio) : "N\u00c3O DESCOBERTO");
        ty.setForeground(descoberto ? info.tipo.corPrincipal() : Estilo.MUTED);
        ty.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel foot = new JPanel();
        foot.setOpaque(false);
        foot.setLayout(new BoxLayout(foot, BoxLayout.Y_AXIS));
        foot.add(n);
        foot.add(Box.createVerticalStrut(3));
        foot.add(ty);
        box.add(foot, BorderLayout.SOUTH);
        return box;
    }
}
