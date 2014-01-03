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
import danger.app.Event;
import danger.ui.Color;
import danger.ui.Control;
import danger.ui.Pen;
import danger.ui.Rect;


/**
 * TODO: documentation for CellControl
 * 
 * @author paul
 * @version 1.0
 * @since 1.0
 */
public class CellWidget extends Control implements Resources, Commands {

    private CrosswordApp crosswordApp;
    private Cell cell;
    
    public CellWidget(Cell cell) {
        this.cell = cell;
        if (cell instanceof BlackCell) {
            setAcceptFocus(false);
        }
        crosswordApp = (CrosswordApp) Application.getCurrentApp();
    }
    
    protected void peek() {
        cell.setGuess(cell.getAnswer());
        invalidate();
    }
    
    /**
     * @param arg0
     * @param arg1
     * @return
     * @see danger.ui.View#eventKeyDown(char, danger.app.Event)
     */
    public boolean eventKeyDown(char c, Event e) {
        if (Character.isLetter(c)) {
            cell.setGuess(Character.toUpperCase(c));
        }
        else {
            cell.setGuess(Cell.NULL_CHAR);
        }
        invalidate();
        return true;
    }
    
    
    public void paint(Pen pen) {
        Rect bounds = getBounds();
        
        if (isFocused()) {
            pen.setColor(Color.RED);
            pen.fillRect(bounds);
            pen.setColor(Color.BLACK);
            
            StringBuffer buf = new StringBuffer();
            if (cell.getAcrossClue() != null) {
                buf.append("a: ").append(cell.getAcrossClue());
            }
            if (cell.getDownClue() != null) {
                if (buf.length() > 0) {
                    buf.append(" | ");
                }
                buf.append("d: ").append(cell.getDownClue());
            }
            if (buf.length() > 0) {
                crosswordApp.setClueText(buf.toString());
            }
            else {
                crosswordApp.clearClueText();
            }
        }
        else {
            pen.setColor(Color.BLACK);
            
            // draw an outline or a solid black cell
            if (cell instanceof BlackCell) {
                pen.fillRect(bounds);
            }
            else {
                pen.drawRect(bounds);
            }
        }
        
        
        // draw a clue indicator
        if (cell.isClueCell()) {
            pen.fillRect(bounds.left, bounds.top, bounds.left + 3, bounds.top + 3);
        }
        
        // fill in the current guess
        if (cell.getGuess() != Cell.NULL_CHAR) {
            pen.drawChar(bounds.left + 2, bounds.bottom - 2, cell.getGuess());
        }
        
    }
    
}
