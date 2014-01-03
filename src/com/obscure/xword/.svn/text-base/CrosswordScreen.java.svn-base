/*
 * Copyright 2004-2008 Paul Mietz Egli
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.obscure.xword;

import danger.app.Event;
import danger.ui.AlertWindow;
import danger.ui.DialogWindow;
import danger.ui.ScreenWindow;


/**
 * TODO: documentation for CrosswordScreen
 * 
 * @author paul
 * @version 1.0
 * @since 1.0
 */
public class CrosswordScreen extends ScreenWindow implements Resources, Commands {

    private CrosswordApp crosswordApp;
    private PuzzleScrollView puzzleScrollView;
    private boolean puzzleLoaded = false;
    private Puzzle puzzle;
    
    /**
     * 
     * @see danger.ui.View#onDecoded()
     */
    public void onDecoded() {
        crosswordApp = (CrosswordApp) getApplication();
        puzzleScrollView = (PuzzleScrollView) getChildWithID(ID_PUZZLE_SCROLLVIEW);
    }
    
    
    public boolean isPuzzleLoaded() {
        return puzzleLoaded;
    }
    
    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
        puzzleScrollView.onPuzzleLoaded(puzzle);
        puzzleLoaded = true;
    }
    
    
    public boolean isPuzzleSolved() {
        return (puzzle != null ? puzzle.isSolved() : false);
    }
    
    public boolean receiveEvent(Event e) {
        boolean consumed = false;

        switch (e.type) {
            case EVENT_SELECT_CROSSWORD:
                crosswordApp.receiveEvent(e);
                break;
            case EVENT_PEEK:
                puzzleScrollView.peek();
                break;
            case EVENT_CHECK_SOLUTION:
                AlertWindow window = new AlertWindow(isPuzzleSolved() ? getApplication().getResources().getString(ID_PUZZLE_SOLVED) : getApplication().getResources().getString(ID_PUZZLE_NOT_SOLVED));
                window.show();
                break;
            case EVENT_SHOW_ABOUT:
                showAboutDialog();
                break;
        }
        return (consumed || super.receiveEvent(e));
    }
    
    
    /**
     * @param widgetID
     * @param e
     * @return
     * @see danger.ui.View#eventWidgetDown(int, danger.app.Event)
     */
    public boolean eventWidgetDown(int widgetID, Event e) {
        boolean consumed = false;
        
        switch (widgetID) {
            case Event.DEVICE_ARROW_DOWN:
                puzzleScrollView.doMoveDown();
                consumed = true;
                break;
            case Event.DEVICE_ARROW_UP:
                puzzleScrollView.doMoveUp();
                consumed = true;
                break;
            // left and right already work correctly
        }
        return (consumed || super.eventWidgetDown(widgetID, e));
    }
    
    private void showAboutDialog() {
        DialogWindow dialog = getApplication().getResources().getDialog(ID_ABOUT_DIALOG);
        dialog.show();
    }
    
}
