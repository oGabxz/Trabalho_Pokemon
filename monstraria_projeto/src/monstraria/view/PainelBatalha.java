package monstraria.view;

import monstraria.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

class PainelBatalha extends JPanel {
    private static final Random RANDOM = new Random();
    private final JanelaPrincipal janela;
    private final PainelArena arena = new PainelArena();
    private final BarraVida barraJogador = new BarraVida(null), barraSelvagem = new BarraVida(null);
    private final JTextArea log = new JTextArea();
    private final JLabel status = Estilo.subtitulo(" ");
    private final JLabel dica = new JLabel("Explore uma \u00e1rea para encontrar uma criatura.");
    private final JComboBox<Monstro> equipe = new JComboBox<>();
    private final JComboBox<Ataque> golpes = new JComboBox<>();
    private final JButton explorar = botao("EXPLORAR", Estilo.PURPLE);
    private final JButton atacar = botao("ATACAR", Estilo.GOLD);
    private final JButton capturar = botao("CAPTURAR", Estilo.CYAN);
    private final JButton fugir = botao("FUGIR", Estilo.PANEL_2);
    private final JButton voltarMenu = botao("← VOLTAR AO MENU", Estilo.RED);
    private Monstro jogadorAtivo, selvagemAtual;

    PainelBatalha(JanelaPrincipal janela) {
        this.janela = janela;
        setLayout(new BorderLayout(16, 16));
        setBackground(Estilo.BG);
        setBorder(BorderFactory.createEmptyBorder(22, 26, 22, 26));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JPanel title = new JPanel();
        title.setOpaque(false);
        title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
        title.add(Estilo.titulo("ZONA SELVAGEM", 26));
        title.add(Box.createVerticalStrut(3));
        title.add(status);
        top.add(title, BorderLayout.WEST);
        JButton eq = new JButton("\u25C8 EQUIPE / BESTI\u00c1RIO");
        Estilo.aplicarBotao(eq, Estilo.PANEL_2);
        eq.addActionListener(e -> janela.mostrar("EQUIPE"));
        // Retorno rápido ao menu principal, disponível durante toda a aventura.
        JPanel topoBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topoBotoes.setOpaque(false);
        topoBotoes.add(eq);
        topoBotoes.add(voltarMenu);
        top.add(topoBotoes, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setOpaque(false);
        center.add(arena, BorderLayout.CENTER);
        JPanel bars = new JPanel(new BorderLayout(22, 0));
        bars.setOpaque(false);
        bars.add(barraJogador, BorderLayout.WEST);
        bars.add(barraSelvagem, BorderLayout.EAST);
        center.add(bars, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);

        JPanel side = Estilo.painelArredondado(Estilo.PANEL);
        side.setPreferredSize(new Dimension(260, 1));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        side.add(Estilo.titulo("CONTROLE", 17));
        side.add(Box.createVerticalStrut(12));
        side.add(Estilo.subtitulo("Monstro ativo"));
        equipe.setMaximumSize(new Dimension(228, 36));
        equipe.setBackground(Estilo.PANEL_2);
        equipe.setForeground(Estilo.TEXT);
        side.add(Box.createVerticalStrut(5));
        side.add(equipe);
        side.add(Box.createVerticalStrut(13));
        side.add(Estilo.subtitulo("Golpe selecionado"));
        side.add(Box.createVerticalStrut(5));
        golpes.setMaximumSize(new Dimension(228, 36));
        side.add(golpes);
        side.add(Box.createVerticalStrut(13));
        dica.setForeground(Estilo.MUTED);
        dica.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dica.setAlignmentX(LEFT_ALIGNMENT);
        dica.setMaximumSize(new Dimension(228, 60));
        side.add(dica);
        side.add(Box.createVerticalGlue());

        log.setEditable(false);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setFont(new Font("Monospaced", Font.PLAIN, 12));
        log.setBackground(new Color(7, 11, 20));
        log.setForeground(Estilo.MUTED);
        log.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        log.setRows(8);
        JScrollPane scroll = new JScrollPane(log);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 12)));
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        side.add(scroll);
        side.add(Box.createVerticalStrut(10));
        side.add(explorar);
        side.add(Box.createVerticalStrut(7));
        side.add(atacar);
        side.add(Box.createVerticalStrut(7));
        side.add(capturar);
        side.add(Box.createVerticalStrut(7));
        side.add(fugir);
        add(side, BorderLayout.EAST);

        explorar.addActionListener(e -> explorar());
        atacar.addActionListener(e -> atacar());
        capturar.addActionListener(e -> capturar());
        fugir.addActionListener(e -> fugir());
        voltarMenu.addActionListener(e -> voltarAoMenu());
        equipe.addActionListener(e -> {
            if (selvagemAtual == null) {
                Object o = equipe.getSelectedItem();
                if (o instanceof Monstro) {
                    jogadorAtivo = (Monstro) o;
                    barraJogador.setMonstro(jogadorAtivo);
                    arena.configurar(jogadorAtivo, false, null, false);
                    atualizarGolpes();
                }
            }
        });
        atualizarBotoes(false);
    }

    private static JButton botao(String t, Color c) {
        JButton b = new JButton(t);
        Estilo.aplicarBotao(b, c);
        b.setAlignmentX(LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(228, 44));
        return b;
    }

    void iniciar(Treinador t) {
        equipe.removeAllItems();
        for (Monstro m : t.getEquipe()) equipe.addItem(m);
        if (equipe.getItemCount() > 0) equipe.setSelectedIndex(0);
        jogadorAtivo = (Monstro) equipe.getSelectedItem();
        selvagemAtual = null;
        barraJogador.setMonstro(jogadorAtivo);
        barraSelvagem.setMonstro(null);
        arena.configurar(jogadorAtivo, false, null, false);
        status.setText("TREINADOR  " + t.getNome() + "   \u2022   EQUIPE  " + t.getEquipe().size() + "/6");
        log.setText("Sistema online. Escolha seu monstro e explore a zona selvagem.\n");
        atualizarGolpes();
        atualizarBotoes(false);
    }

    void atualizarStatus() {
        if (janela.getTreinador() != null) {
            status.setText("TREINADOR  " + janela.getTreinador().getNome() + "   \u2022   EQUIPE  "
                    + janela.getTreinador().getEquipe().size() + "/6");
        }
    }

    private void atualizarGolpes() {
        golpes.removeAllItems();
        if (jogadorAtivo != null) {
            for (Ataque a : jogadorAtivo.getGolpes()) golpes.addItem(a);
        }
    }

    private void explorar() {
        if (jogadorAtivo == null || !jogadorAtivo.estaVivo()) {
            log.append("Seu monstro est\u00e1 fora de combate. Cure a equipe antes de explorar.\n");
            return;
        }
        selvagemAtual = FabricaMonstros.selvagemAleatorio();
        janela.getBestiario().registrar(selvagemAtual);
        barraJogador.setMonstro(jogadorAtivo);
        barraSelvagem.setMonstro(selvagemAtual);
        arena.configurar(jogadorAtivo, false, selvagemAtual, false);
        log.setText("\u26a1 ENCONTRO!  " + selvagemAtual.getNome() + " (Nv." + selvagemAtual.getNivel() + ") apareceu na zona selvagem.\n");
        log.append("A criatura foi registrada no Besti\u00e1rio.\n");
        dica.setText("Derrote a criatura para aumentar a chance de captura.");
        atualizarBotoes(true);
    }

    private void atacar() {
        if (!validaBatalha()) return;
        Ataque a = (Ataque) golpes.getSelectedItem();
        if (a == null) return;
        double eficacia = jogadorAtivo.calcularEfetividadeContra(selvagemAtual);
        int dano = jogadorAtivo.atacar(selvagemAtual, a);
        String efeito = eficacia > 1.0 ? "  (super efetivo!)" : eficacia < 1.0 ? "  (pouco efetivo...)" : "";
        log.append(jogadorAtivo.getNome() + " usou " + a.getNome() + "  \u2192  -" + dano + " HP" + efeito + "\n");
        barraSelvagem.repaint();
        if (!selvagemAtual.estaVivo()) {
            log.append("\u2713 " + selvagemAtual.getNome() + " foi derrotado!\n");
            arena.configurar(jogadorAtivo, false, selvagemAtual, true);
            int xp = Batalha.xpDeVitoria(selvagemAtual);
            boolean evoluiu = jogadorAtivo.ganharExperiencia(xp);
            log.append(jogadorAtivo.getNome() + " ganhou " + xp + " de XP!\n");
            if (evoluiu) {
                log.append("\u2728 " + jogadorAtivo.getNome() + " evoluiu! Agora \u00e9 est\u00e1gio " + jogadorAtivo.getEstagio() + ".\n");
                arena.configurar(jogadorAtivo, false, selvagemAtual, true);
            }
            barraJogador.repaint();
            encerrar();
            return;
        }
        contraAtaque();
    }

    private void capturar() {
        if (!validaBatalha()) return;
        double chance = janela.getTreinador().calcularChanceCaptura(selvagemAtual);
        boolean ok = janela.getTreinador().tentarCapturar(selvagemAtual);
        log.append("CAPTURA  \u2022  chance estimada: " + Math.round(chance * 100) + "%  \u2192  " + (ok ? "SUCESSO!" : "falhou.") + "\n");
        if (ok) {
            janela.getBestiario().registrar(selvagemAtual);
            log.append(selvagemAtual.getNome() + " entrou para a equipe!\n");
            encerrar();
            return;
        }
        contraAtaque();
    }

    private void voltarAoMenu() {
        if (janela.getTreinador() == null) {
            janela.mostrar("MENU");
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(this,
                "Salvar a aventura atual antes de voltar ao menu?",
                "Voltar ao Menu",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (resposta == JOptionPane.CANCEL_OPTION || resposta == JOptionPane.CLOSED_OPTION) return;
        if (resposta == JOptionPane.YES_OPTION) janela.salvarJogoAtual();
        selvagemAtual = null;
        janela.mostrar("MENU");
    }

    private void fugir() {
        if (selvagemAtual != null) log.append("Voc\u00ea deixou a zona de combate.\n");
        selvagemAtual = null;
        arena.configurar(jogadorAtivo, false, null, false);
        dica.setText("Explore uma \u00e1rea para encontrar uma criatura.");
        atualizarBotoes(false);
    }

    private void contraAtaque() {
        if (selvagemAtual == null || !selvagemAtual.estaVivo()) return;
        Ataque a = Batalha.golpeAleatorio(selvagemAtual);
        int dano = selvagemAtual.atacar(jogadorAtivo, a);
        log.append(selvagemAtual.getNome() + " revidou com " + a.getNome() + "  \u2192  -" + dano + " HP\n");
        barraJogador.repaint();
        if (!jogadorAtivo.estaVivo()) {
            log.append("\u2715 " + jogadorAtivo.getNome() + " ficou fora de combate.\n");
            arena.configurar(jogadorAtivo, true, selvagemAtual, false);
            encerrar();
        }
    }

    private boolean validaBatalha() {
        return selvagemAtual != null && selvagemAtual.estaVivo() && jogadorAtivo != null && jogadorAtivo.estaVivo();
    }

    private void encerrar() {
        selvagemAtual = null;
        atualizarBotoes(false);
        dica.setText("Encontro encerrado. Escolha outro alvo e explore novamente.");
        atualizarStatus();
        atualizarGolpes();
    }

    private void atualizarBotoes(boolean batalhando) {
        explorar.setVisible(!batalhando);
        atacar.setVisible(batalhando);
        capturar.setVisible(batalhando);
        fugir.setVisible(batalhando);
        golpes.setVisible(batalhando);
        equipe.setEnabled(!batalhando);
    }
}
