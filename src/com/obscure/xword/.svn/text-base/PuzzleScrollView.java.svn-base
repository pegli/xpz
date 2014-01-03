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

import danger.app.Application;
import danger.ui.ScrollView;
import danger.ui.View;
import danger.util.DEBUG;


/**
 * The PuzzleView class is responsible for drawing the puzzle grid.
 * 
 * @author Paul Mietz Egli
 * @version 1.0
 * @since 1.0
 */
public class PuzzleScrollView extends ScrollView implements Resources, Commands {

    private CrosswordApp crosswordApp;
    private CrosswordScreen crosswordScreen;
    private int cellsPerRow = 0;
    private int cellsPerColumn = 0;
    
    public void onDecoded() {
        crosswordApp = (CrosswordApp) Application.getCurrentApp();
        crosswordScreen = (CrosswordScreen) CrosswordApp.getParentScreen(this, "com.obscure.xword.CrosswordScreen");
    }
    
    protected void doMoveDown() {
        CellWidget cellWidget = getFocusedCellWidget();
        if (cellWidget != null) {
            int currentIndex = getChildIndex(cellWidget);
            int index = currentIndex + cellsPerRow;
            for (int i = currentIndex + cellsPerRow; i < childCount(); i += cellsPerRow) {
                View child = getChildAt(i);
                if (child.isFocusable()) {
                    //crosswordApp.getFrontScreenWindow().setFocusedDescendant(child);
                    crosswordScreen.setFocusedDescendant(child);
                    break;
                }
            }
        }
    }
    
    protected void doMoveUp() {
        CellWidget cellWidget = getFocusedCellWidget();
        if (cellWidget != null) {
            int currentIndex = getChildIndex(cellWidget);
            int index = currentIndex + cellsPerRow;
            for (int i = currentIndex - cellsPerRow; i >= 0; i -= cellsPerRow) {
                View child = getChildAt(i);
                if (child.isFocusable()) {
                    // crosswordApp.getFrontScreenWindow().setFocusedDescendant(child);
                    crosswordScreen.setFocusedDescendant(child);
                    break;
                }
            }
        }
    }
    
    protected void peek() {
        CellWidget cellWidget = getFocusedCellWidget();
        cellWidget.peek();
    }
    
    /**
     * 
     * @see danger.ui.View#onAddedToParent()
     */
    protected void onPuzzleLoaded(Puzzle puzzle) {
        cellsPerRow = puzzle.getWidth();
        cellsPerColumn = puzzle.getHeight();
        
        int maxDim = Math.max(getHeight(), getWidth()) - 5;
        setHeight(maxDim);
        setWidth(maxDim);

        if (puzzle != null) {
            removeAllChildren();
            
            int index = 1;
            
            int cellSize = 0;
            calc_cell_size: {
                int maxHeight = maxDim / puzzle.getHeight();
                int maxWidth = maxDim / puzzle.getWidth();
                cellSize = Math.min(maxHeight, maxWidth);
            }
            
            for (int y=0; y < puzzle.getHeight(); y++) {
                for (int x=0; x < puzzle.getWidth(); x++) {
                    CellWidget cell = new CellWidget(puzzle.getCell(x, y));
                    if (cell != null) {
                        cell.setHeight(cellSize);
                        cell.setWidth(cellSize);
                        cell.setLeft(x * cellSize);
                        cell.setTop(y * cellSize);
                        addChild(cell);
                        cell.show();
                    }
                    else DEBUG.p("unexpected null cell at "+x+","+y);
                }
            }

            // find the first focusable cell
            for (int i=0; i < this.childCount(); i++) {
                if (getChildAt(i).isFocusable()) {
                    crosswordScreen.setFocusedDescendant(getChildAt(i));
                    break;
                }
            }
        }
        else {
            DEBUG.p("Crossword: no puzzle to draw");
        }
        
    }
    
    private CellWidget getFocusedCellWidget() {
        for (int i=0; i < this.childCount(); i++) {
            if (getChildAt(i).isFocused()) {
                return (CellWidget) getChildAt(i);
            }
        }
        return null;
    }
    
}
