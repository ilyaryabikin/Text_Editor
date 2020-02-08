package org.editor.command;

import org.editor.TextEditor;

public abstract class Command {
    protected TextEditor textEditor;

    public Command(TextEditor textEditor) {
        this.textEditor = textEditor;
    }

    public abstract void execute() throws Exception;
}
