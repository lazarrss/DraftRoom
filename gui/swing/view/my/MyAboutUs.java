package raf.draft.dsw.gui.swing.view.my;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class MyAboutUs extends JFrame {
    private final JLabel jLabelLM = new JLabel("Lazar Stojanovic i Milica Mitrovic");
    public MyAboutUs(){
        super("About us" );
        initialize();

    }
    public void initialize(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        super.setSize( screenWidth/2,screenHeight/2 );
        super.setLocationRelativeTo( null );
        super.setVisible( true );

        JPanel jPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(50);
        JLabel label=new JLabel();
        ImageIcon image = new ImageIcon(getClass().getResource("/images/aboutUsSlika.jpg"));

        if (image.getIconWidth() == -1) {
            System.out.println("Slika nije učitana. Proveri putanju.");
        } else {
            Image img = image.getImage();
            Image scaledImg = img.getScaledInstance(screenWidth/5, screenHeight/4, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImg));
        }

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(label);
        centerPanel.setBackground(new Color(215,204,200));

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(jLabelLM);
        northPanel.setBackground(new Color(248, 234, 223));
        int topPadding = 40;
        int leftPadding = 10;
        int bottomPadding = 10;
        int rightPadding = 10;
        northPanel.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));

        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(SwingConstants.TOP);

        jPanel.setLayout(borderLayout);
        jPanel.add(centerPanel, BorderLayout.CENTER);
        jPanel.add(northPanel, BorderLayout.NORTH);

        jPanel.setBackground(new Color(215,204,200));

        super.add(jPanel);
    }
}
