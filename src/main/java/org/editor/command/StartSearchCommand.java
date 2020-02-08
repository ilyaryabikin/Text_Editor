package org.editor.command;

import org.editor.TextEditor;
import org.editor.searcher.Searcher;

import javax.swing.*;

public class StartSearchCommand extends Command {

    public StartSearchCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() {
        Searcher searcher = textEditor.getSearcher();
        JTextField searchField = textEditor.getSearchField();
        JTextArea textArea = textEditor.getTextArea();
        boolean isRegex = textEditor.isRegex();

        new SwingWorker<Searcher, Searcher>() {

            @Override
            protected Searcher doInBackground() {
                searcher.search(searchField.getText(), textArea.getText(), isRegex);
                return searcher;
            }

            @Override
            protected void done() {
                textArea.setCaretPosition(searcher.getStartIndex() + searcher.getWordLength());
                textArea.select(searcher.getStartIndex(), searcher.getStartIndex() + searcher.getWordLength());
                textArea.grabFocus();
            }
        }.execute();
    }
}
