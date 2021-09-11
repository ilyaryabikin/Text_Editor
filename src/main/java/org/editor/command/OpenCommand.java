package org.editor.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class OpenCommand implements EditorCommand {

    private final JFileChooser fileChooser;
    private final JTextArea textArea;

    public OpenCommand(final JFileChooser fileChooser,
                       final JTextArea textArea) {
        this.fileChooser = fileChooser;
        this.textArea = textArea;
    }

    @Override
    public void execute() throws IOException {
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            Path path = fileChooser.getSelectedFile().toPath().toAbsolutePath();
            if (Files.exists(path)) {
                textArea.setText(Files.readString(path));
            }
        }
    }
}
