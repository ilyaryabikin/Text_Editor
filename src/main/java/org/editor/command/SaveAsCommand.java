package org.editor.command;

import org.editor.TextEditor;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveAsCommand extends Command {

    public SaveAsCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() throws IOException {
        JFileChooser fileChooser = textEditor.getFileChooser();
        String content = textEditor.getTextArea().getText();
        int returnValue = fileChooser.showDialog(null, "Save");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            Path path = fileChooser.getSelectedFile().toPath().toAbsolutePath();
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.writeString(path, content);
        }
    }
}
