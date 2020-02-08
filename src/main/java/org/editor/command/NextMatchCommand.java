package org.editor.command;

import org.editor.TextEditor;
import org.editor.searcher.Searcher;

import javax.swing.*;

public class NextMatchCommand extends Command {

    public NextMatchCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() {
        Searcher searcher = textEditor.getSearcher();
        JTextArea textArea = textEditor.getTextArea();
        searcher.nextMatched();
        textArea.setCaretPosition(searcher.getStartIndex() + searcher.getWordLength());
        textArea.select(searcher.getStartIndex(), searcher.getStartIndex() + searcher.getWordLength());
        textArea.grabFocus();
    }
}
