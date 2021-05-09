package Question1;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>FishTank.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public final class FishTank extends JPanel implements ActionListener {
    private final JButton addFishButton;
    private final DrawPanel drawPanel;
    private final FishShoal fishShoal;

    public FishTank() {
        super(new BorderLayout());
        FlatDarculaLaf.install();

        this.fishShoal = new FishShoal();

        this.drawPanel = new DrawPanel();
        this.drawPanel.setBackground(Color.DARK_GRAY);
        this.add(this.drawPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.addFishButton = new JButton("Add Fish");
        this.addFishButton.setBorderPainted(false);
        this.addFishButton.setFocusable(false);

        this.addFishButton.addActionListener(this);
        buttonPanel.add(this.addFishButton);

        Timer timer = new Timer(10, this);
        timer.start();
    }

    public static void main(String[] args) {
        FishTank fishTank = new FishTank();
        JFrame jFrame = new JFrame("Fish Bowl");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().add(fishTank);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == this.addFishButton) {
            Fish fish = new Fish(this.fishShoal);
            this.fishShoal.add(fish);

            Thread thread = new Thread(fish);
            thread.start();
        }

        this.drawPanel.repaint();
    }

    private class DrawPanel extends JPanel {
        public DrawPanel() {
            super();
            setPreferredSize(new Dimension(500, 500));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Fish.world_width = getWidth();
            Fish.world_height = getHeight();
            fishShoal.drawShoal(g);
        }
    }
}