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

/**
 * 
 * 
 * @author Paul Mietz Egli
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractPuzzle implements Puzzle {

    protected String   title;
    protected String   author;
    protected String   copyright;

    protected Cell[][] puzzle = new Cell[0][0];

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCopyright() {
        return copyright;
    }

    /**
     * @return the width of the puzzle in characters
     * @see com.obscure.xword.Puzzle#getWidth()
     */
    public int getWidth() {
        return puzzle.length;
    }

    /**
     * @return the height of the puzzle in characters
     * @see com.obscure.xword.Puzzle#getHeight()
     */
    public int getHeight() {
        return puzzle[0].length;
    }

    public Cell getCell(int x, int y) {
        if (inBounds(x, y)) {
            return puzzle[x][y];
        }
        else {
            return null;
        }
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private boolean inBounds(int x, int y) {
        return (x < puzzle.length) && (y < puzzle[0].length);
    }

    /**
     * @return
     * @see com.obscure.xword.Puzzle#isSolved()
     */
    public boolean isSolved() {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j].getGuess() != puzzle[i][j].getAnswer()) { return false; }
            }
        }

        return true;
    }

}
