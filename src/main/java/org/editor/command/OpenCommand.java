package org.editor.command;

import org.editor.TextEditor;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OpenCommand extends Command {

    public OpenCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() throws IOException {
        JFileChooser fileChooser = textEditor.getFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            Path path = fileChooser.getSelectedFile().toPath().toAbsolutePath();
            String content;
            if (!Files.exists(path)) {
                content = "";
            } else {
                content = Files.readString(path);
            }
            textEditor.getTextArea().setText(content);
        }
    }
}
