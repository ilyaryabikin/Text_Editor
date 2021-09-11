package org.editor.command;

import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class SaveCommand implements EditorCommand {

    private final JFileChooser fileChooser;
    private final JTextArea textArea;

    public SaveCommand(final JFileChooser fileChooser,
                       final JTextArea textArea) {
        this.fileChooser = fileChooser;
        this.textArea = textArea;
    }

    @Override
    public void execute() throws IOException {
        if (fileChooser.getSelectedFile() == null) {
            throw new NoSuchFileException("You should specify the file before saving. Try \"Save as...\"");
        }
        String content = textArea.getText();
        Path path = fileChooser.getSelectedFile().toPath().toAbsolutePath();
        Files.writeString(path, content, CREATE);
    }
}
