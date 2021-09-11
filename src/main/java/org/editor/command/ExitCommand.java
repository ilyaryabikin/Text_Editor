package org.editor.command;

import javax.swing.JFrame;

public class ExitCommand implements EditorCommand {

    private final JFrame frame;

    public ExitCommand(final JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void execute() {
        frame.dispose();
    }
}
