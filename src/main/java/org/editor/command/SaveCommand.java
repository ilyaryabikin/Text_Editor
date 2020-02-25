package org.editor.command;

import org.editor.TextEditor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class SaveCommand extends Command {

    public SaveCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() throws IOException {
        if (textEditor.getFileChooser().getSelectedFile() == null) {
            throw new NoSuchFileException("You should specify the file before saving. Try \"Save as...\"");
        }
        String content = textEditor.getTextArea().getText();
        Path path = textEditor.getFileChooser().getSelectedFile().toPath().toAbsolutePath();
        Files.writeString(path, content);
    }
}
