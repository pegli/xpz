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

import danger.ui.Control;



/**
 * TODO: documentation for Cell
 * 
 * @author paul
 * @version 1.0
 * @since 1.0
 */
public class Cell extends Control {
    public static char NULL_CHAR = '\0';
    
    private char guess = NULL_CHAR;
    private char answer = NULL_CHAR;
    private String acrossClue;
    private String downClue;
    private int number = -1;
    
    
    
    public char getAnswer() {
        return answer;
    }
    
    public void setAnswer(char answer) {
        this.answer = answer;
    }
    
    public String getAcrossClue() {
        return acrossClue;
    }
    
    public void setAcrossClue(String clue) {
        this.acrossClue = clue;
    }
    
    public String getDownClue() {
        return downClue;
    }
    
    public void setDownClue(String clue) {
        this.downClue = clue;
    }
    
    public char getGuess() {
        return guess;
    }
    
    public void setGuess(char guess) {
        this.guess = guess;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return number;
    }
    
    public boolean isClueCell() {
        return acrossClue != null || downClue != null;
    }
    
    public String toString() {
        return "Cell { answer: "+(answer != NULL_CHAR ? answer : '?')+"; guess: "+(guess != NULL_CHAR ? guess : '?')+" }"; 
    }
}