package raf.draft.dsw.gui.swing.view.my;

import raf.draft.dsw.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyRoomOrganizer {
    private DefaultListModel<String> selectedModel = new DefaultListModel<>();
    private JList<String> selectedList;
    private JScrollPane selectedScroll;
    private final JList<String> availableList = initAvailableList();
    private JButton addButton = new JButton("Add");
    private JButton removeButton = new JButton("Remove");
    private JButton enterButton = new JButton("Organize");
    private JTextField widthField = new JTextField();
    private JTextField heightField = new JTextField();
    private JFrame frame;


    public void show() {
        frame = new JFrame("Organize My Room");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel availablePanel = new JPanel(new BorderLayout(10, 10));
        availablePanel.setBorder(BorderFactory.createTitledBorder("Available"));

        JScrollPane availableScroll = new JScrollPane(availableList);
        availablePanel.add(availableScroll, BorderLayout.CENTER);

        JPanel dimensionPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        dimensionPanel.add(new JLabel("Width:"));
        dimensionPanel.add(widthField);
        dimensionPanel.add(new JLabel("Height:"));
        dimensionPanel.add(heightField);

        availablePanel.add(dimensionPanel, BorderLayout.SOUTH);

        JPanel selectedPanel = new JPanel(new BorderLayout(10, 10));
        selectedPanel.setBorder(BorderFactory.createTitledBorder("Chosen elemenets"));

        this.selectedModel = new DefaultListModel<>();
        this.selectedList = new JList<>(selectedModel);
        this.selectedScroll = new JScrollPane(selectedList);
        selectedPanel.add(selectedScroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(enterButton);

        addButton.setPreferredSize(new Dimension(100, 30));
        removeButton.setPreferredSize(new Dimension(100, 30));
        enterButton.setPreferredSize(new Dimension(100, 30));

        selectedPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(availablePanel, BorderLayout.WEST);
        mainPanel.add(selectedPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JList<String> initAvailableList() {
        DefaultListModel<String> availableModel = new DefaultListModel<>();
        availableModel.addElement("Sto");
        availableModel.addElement("Kada");
        availableModel.addElement("Ormar");
        availableModel.addElement("Krevet");
        availableModel.addElement("Bojler");
        availableModel.addElement("Vrata");
        availableModel.addElement("Ves Masina");
        availableModel.addElement("WC solja");
        JList<String> availableList = new JList<>(availableModel);
        availableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return availableList;
    }

    public DefaultListModel<String> getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedModel(DefaultListModel<String> selectedModel) {
        this.selectedModel = selectedModel;
    }

    public JList<String> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(JList<String> selectedList) {
        this.selectedList = selectedList;
    }

    public JScrollPane getSelectedScroll() {
        return selectedScroll;
    }

    public void setSelectedScroll(JScrollPane selectedScroll) {
        this.selectedScroll = selectedScroll;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(JButton removeButton) {
        this.removeButton = removeButton;
    }

    public JButton getEnterButton() {
        return enterButton;
    }

    public void setEnterButton(JButton enterButton) {
        this.enterButton = enterButton;
    }

    public JTextField getWidthField() {
        return widthField;
    }

    public void setWidthField(JTextField widthField) {
        this.widthField = widthField;
    }

    public JTextField getHeightField() {
        return heightField;
    }

    public void setHeightField(JTextField heightField) {
        this.heightField = heightField;
    }

    public JList<String> getAvailableList() {
        return availableList;
    }

    public JFrame getFrame() {
        return frame;
    }
}
