package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

class DebugPanel extends JPanel implements Observer {
    private static final Dimension CHAT_PANEL_DIMENSION = new Dimension(600, 150);
    private final JTextArea jTextArea;

    public DebugPanel() {
        super(new BorderLayout());
        this.jTextArea = new JTextArea("");
        add(this.jTextArea);
        setPreferredSize(CHAT_PANEL_DIMENSION);
        validate();
        setVisible(true);
    }

    public void redo() {
        validate();
    }

    @Override
    public void update(final Observable obs,
                       final Object obj) {
        this.jTextArea.setText(obj.toString().trim());
        redo();
    }
}
