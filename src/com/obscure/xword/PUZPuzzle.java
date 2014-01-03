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

import java.util.Enumeration;
import java.util.Vector;


/**
 * TODO: documentation for PUZCrosswordPuzzle
 * 
 * @author paul
 * @version 1.0
 * @since 1.0
 */
public class PUZPuzzle extends AbstractPuzzle {
    private static int OFFSET_WIDTH = 0x2C;
    private static int OFFSET_HEIGHT = 0x2D;
    private static int OFFSET_ANSWERS = 0x34;
    
    private static char BLACK_SPACE_CHAR = '.';
    
    public PUZPuzzle(byte[] puzbytes) {
        puzzle = new Cell[puzbytes[OFFSET_WIDTH]][puzbytes[OFFSET_HEIGHT]];
        
        // read in the answers
        for (int i=0; i < puzzle.length; i++) {
            for (int j=0; j < puzzle.length; j++) {
                char c = (char) puzbytes[OFFSET_ANSWERS + i + (j * puzzle.length)];
                if (c == BLACK_SPACE_CHAR) {
                    puzzle[i][j] = new BlackCell();
                }
                else {
                    puzzle[i][j] = new Cell();
                    puzzle[i][j].setAnswer(c);
                }
            }
        }

        int start = OFFSET_ANSWERS + 2 * (getWidth() * getHeight());
        int end = findNextNull(puzbytes, start);
        title = new String(puzbytes, start, end-start);

        start = end + 1;
        end = findNextNull(puzbytes, start);
        this.author = new String(puzbytes, start, end-start);
        
        start = end + 1;
        end = findNextNull(puzbytes, start);
        this.copyright = new String(puzbytes, start, end-start);
        
        start = end + 1;
        
        // now "start" is positioned at the beginning of the clues, which are
        // coded as null-terminated strings and are assigned from the upper left
        // to the lower right of the puzzle.
        
        Vector clues = new Vector();
        String clue;
        do {
            end = findNextNull(puzbytes, start);
            clue = new String(puzbytes, start, end-start);
            clues.addElement(clue);
            start = end + 1;
        } while (clue != null && clue.length() > 0);

        Enumeration e = clues.elements();
        int number = 1;
        for (int y=0; y < getHeight(); y++) {
            for (int x=0; x < getWidth(); x++) {
                if (isAcrossClue(x, y)) {
                    puzzle[x][y].setAcrossClue((String) e.nextElement());
                }
                if (isDownClue(x, y)) {
                    puzzle[x][y].setDownClue((String) e.nextElement());
                }
                if (puzzle[x][y].getAcrossClue() != null || puzzle[x][y].getDownClue() != null) {
                    puzzle[x][y].setNumber(number++);
                }
            }
        }
        
    }

    /**
     * @param i
     * @param j
     * @return
     */
    private boolean isDownClue(int x, int y) {
        if (puzzle[x][y] instanceof BlackCell) {
            return false;
        }
        
        // all top cells that have a non-black cell below them have clues
        if (y == 0 && !(puzzle[x][y+1] instanceof BlackCell)) {
            return true;
        }
        
        // a cell has a down clue if the above cell is black and
        // the below cell is not
        if ((y > 0) && (y < getHeight() - 1) && (puzzle[x][y-1] instanceof BlackCell) && !(puzzle[x][y+1] instanceof BlackCell)) {
            return true;
        }
        
        return false;
    }
    
    private boolean isAcrossClue(int x, int y) {
        if (puzzle[x][y] instanceof BlackCell) {
            return false;
        }
        
        // all left-hand cells that aren't black have clues
        if (x == 0) {
            return true;
        }

        // a cell has an across clue if the previous cell is black
        // and the next cell is not
        if ((x > 0) && (x < getWidth()-1) && (puzzle[x-1][y] instanceof BlackCell) && !(puzzle[x+1][y] instanceof BlackCell)) {
            return true;
        }

        return false;
    }

    
    private int findNextNull(byte[] buf, int index) {
        int end = index;
        while (end < buf.length-1 && buf[end] != 0x00) {
            ++end;
        }
        
        return end;
    }
    
}
