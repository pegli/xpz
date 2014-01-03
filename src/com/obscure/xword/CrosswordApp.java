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
import danger.net.HTTPConnection;
import danger.net.HTTPTransaction;
import danger.ui.MarqueeAlert;
import danger.ui.NotificationManager;
import danger.ui.ProgressWindow;
import danger.ui.ScreenWindow;
import danger.ui.View;
import danger.util.DEBUG;

/**
 * TODO: documentation for Crossword
 * 
 * @author paul
 * @version 1.0
 * @since 1.0
 */
public class CrosswordApp extends Application implements Resources, Commands {
    private static int HTTP_SEQUENCE_ID = 1;
    
    private CrosswordScreen crosswordScreen;
    private SelectCrosswordScreen selectCrosswordScreen;
    private MarqueeAlert currentMarqueeAlert;
    private ProgressWindow progressWindow = new ProgressWindow("");
    
    
    public CrosswordApp() {
        crosswordScreen = (CrosswordScreen) getResources().getScreen(ID_CROSSWORD_SCREEN);
        selectCrosswordScreen = (SelectCrosswordScreen) getResources().getScreen(ID_SELECT_CROSSWORD_SCREEN);
    }
    
    public void clearClueText() {
        if (currentMarqueeAlert != null) {
            NotificationManager.marqueeAlertRemove(currentMarqueeAlert);
        }
    }
    
    
    public void loadPuzzleFromURL(String url) {
        selectCrosswordScreen.onLoadingPuzzle();
        try {
            HTTPConnection.get(url, "", (short) 0, HTTP_SEQUENCE_ID++);
        }
        catch (Exception e) {
            DEBUG.p("error sending GET : " + e.getMessage());
        }
    }
    
    /**
     * The application is the only one who receives network events...
     * 
     * @param s
     * @see danger.app.Application#networkEvent(java.lang.Object)
     */
    public final void networkEvent(Object s) {
        if (s instanceof HTTPTransaction) {
            HTTPTransaction t = (HTTPTransaction) s;

            if (200 == t.getResponse()) {
                Puzzle puzzle = new PUZPuzzle(t.getBytes());
                selectCrosswordScreen.onPuzzleLoaded();
                crosswordScreen.setPuzzle(puzzle);
                crosswordScreen.show();
            }
        }
    }
    
    /**
     * 
     * @see danger.app.Application#resume()
     */
    public void resume() {
        showScreen();
    }
    
    public void showScreen() {
        if (crosswordScreen.isPuzzleLoaded()) {
            crosswordScreen.show();
        }
        else {
            selectCrosswordScreen.show();
        }
    }
    
    public void setClueText(String text) {
        if (currentMarqueeAlert != null) {
            NotificationManager.marqueeAlertRemove(currentMarqueeAlert);
        }
        currentMarqueeAlert = new MarqueeAlert(text, 10);
        NotificationManager.marqueeAlertNotify(currentMarqueeAlert);
    }
    
    /**
     * 
     * @see danger.app.Application#suspend()
     */
    public void suspend() {
        clearClueText();
    }
    
    public boolean receiveEvent(Event e) {
        boolean consumed = false;
        
        switch (e.type) {
            case EVENT_SELECT_CROSSWORD:
                selectCrosswordScreen.show();
                break;
        }
        
        return (consumed || super.receiveEvent(e));
    }
    
    public static ScreenWindow getParentScreen(View child, String screenClassName) {
        if (child == null) {
            return null;
        }
        
        View result = child;
        while (result != null && !(result.getClass().getName().equals(screenClassName))) {
            result = result.getParent();
        }

        return (ScreenWindow) result;
    }
}