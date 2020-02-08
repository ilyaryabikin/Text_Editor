package org.editor.command;

import org.editor.TextEditor;
import org.editor.searcher.Searcher;

import javax.swing.*;

public class PreviousMatchCommand extends Command {

    public PreviousMatchCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() {
        Searcher searcher = textEditor.getSearcher();
        JTextArea textArea = textEditor.getTextArea();
        searcher.previousMatched();
        textArea.setCaretPosition(searcher.getStartIndex() + searcher.getWordLength());
        textArea.select(searcher.getStartIndex(), searcher.getStartIndex() + searcher.getWordLength());
        textArea.grabFocus();
    }
}
