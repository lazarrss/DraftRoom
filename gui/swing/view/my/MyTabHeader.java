package raf.draft.dsw.gui.swing.view.my;

import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyTabHeader extends JPanel implements ISubscriber {
    private String title = "";
    private JButton closeButton;
    private JLabel titleLabel = new JLabel();
    public MyTabHeader(FlowLayout flowLayout, String title, Room node) {
        super(flowLayout);
        this.setOpaque(false);
        initialize(title, node);
        node.addSubscriber(this);
    }
    public void initialize(String title, Room node){
        titleLabel.setText(title);
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(50, 16)));
        closeButton = getCloseButtonInitialize();
        this.add(closeButton);
        if(node.getParent() instanceof Building) {
            this.getTitleLabel().setForeground(((Building) node.getParent()).getColor());
        }
    }

    @Override
    public void update(Message message) {
        if(message.getContent().startsWith("value changed:")){
            title = message.getContent().substring(message.getContent().indexOf(':')+2);
            this.setTitleLabel(title);
        }
    }
    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getCloseButtonInitialize() {
        closeButton = new JButton("x");
        closeButton.setPreferredSize(new Dimension(10, 16));
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setContentAreaFilled(false);
        return closeButton;
    }
    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(String title) {
        this.titleLabel.setText(title);
    }
}
