package org.editor.command;

import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class SaveAsCommand implements EditorCommand {

    private final JFileChooser fileChooser;
    private final JTextArea textArea;

    public SaveAsCommand(final JFileChooser fileChooser, final JTextArea textArea) {
        this.fileChooser = fileChooser;
        this.textArea = textArea;
    }

    @Override
    public void execute() throws IOException {
        String content = textArea.getText();
        int returnValue = fileChooser.showDialog(null, "Save");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            Path path = fileChooser.getSelectedFile().toPath().toAbsolutePath();
            Files.writeString(path, content, CREATE);
        }
    }
}
