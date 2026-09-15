package monstraria.view;

import monstraria.Tipo;
import javax.swing.*;
import java.awt.*;

class CartaoMonstro extends JPanel {
    CartaoMonstro(Tipo tipo, String nome, String subtitulo, String descricao, Color destaque, Runnable escolher) {
        setLayout(new BorderLayout());
        setOpaque(false);
        JPanel box = Estilo.painelArredondado(Estilo.PANEL);
        box.setLayout(new BorderLayout(0, 8));
        box.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel sprite = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                MonstroSprite.desenhar((Graphics2D) g, tipo, 1, getWidth() / 2, getHeight() / 2, 175, false);
            }
        };
        sprite.setOpaque(false);
        sprite.setPreferredSize(new Dimension(220, 230));
        box.add(sprite, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel n = Estilo.titulo(nome, 24);
        n.setAlignmentX(CENTER_ALIGNMENT);
        JLabel st = Estilo.subtitulo(subtitulo);
        st.setForeground(destaque);
        st.setAlignmentX(CENTER_ALIGNMENT);
        JTextArea d = new JTextArea(descricao);
        d.setWrapStyleWord(true);
        d.setLineWrap(true);
        d.setEditable(false);
        d.setFocusable(false);
        d.setOpaque(false);
        d.setForeground(Estilo.MUTED);
        d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        d.setAlignmentX(CENTER_ALIGNMENT);
        info.add(n);
        info.add(Box.createVerticalStrut(4));
        info.add(st);
        info.add(Box.createVerticalStrut(12));
        info.add(d);
        box.add(info, BorderLayout.CENTER);

        JButton b = new JButton("VINCULAR MONSTRO");
        Estilo.aplicarBotao(b, destaque);
        b.setAlignmentX(CENTER_ALIGNMENT);
        b.addActionListener(e -> escolher.run());
        box.add(b, BorderLayout.SOUTH);
        add(box);
    }
}
