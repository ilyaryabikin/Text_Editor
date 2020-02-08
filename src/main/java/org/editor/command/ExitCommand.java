package org.editor.command;

import org.editor.TextEditor;

public class ExitCommand extends Command {

    public ExitCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() {
        textEditor.dispose();
    }
}
