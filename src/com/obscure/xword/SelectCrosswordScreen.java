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
import danger.ui.DialogWindow;
import danger.ui.ProgressWindow;
import danger.ui.ScreenWindow;
import danger.ui.TextField;
import danger.util.DEBUG;


/**
 * TODO: documentation for SelectCrosswordScreen
 * 
 * @author paul
 * @version 1.0
 * @since 1.0
 */
public class SelectCrosswordScreen extends ScreenWindow implements Resources, Commands {
    ProgressWindow loadingDialog;
    CrosswordURLListView crosswordURLListView;
    CrosswordApp crosswordApp;
    
    
    public void onPuzzleLoaded() {
        loadingDialog.hide();
    }

    public void onLoadingPuzzle() {
        loadingDialog.show();
    }

    
    public boolean eventWidgetDown(int widgetID, Event e) {
        boolean consumed = false;
        
        switch (widgetID) {
            case Event.DEVICE_BUTTON_BACK:
                crosswordApp.showScreen();
        }
        
        return (consumed || super.eventWidgetDown(widgetID, e));
    }
    
    public boolean receiveEvent(Event e) {
        boolean consumed = false;

        switch (e.type) {
            case EVENT_ADD_PUZZLE_DONE:
                DEBUG.p("add puzzle ok");
                DialogWindow dialog = (DialogWindow) e.argument;
                String name = ((TextField) dialog.getChildWithID(ID_PUZZLE_NAME_TEXTFIELD)).getStringCRLF();
                String url = ((TextField) dialog.getChildWithID(ID_PUZZLE_URL_TEXTFIELD)).getStringCRLF();
                crosswordURLListView.addPuzzleURL(name, url);
                crosswordURLListView.invalidate();
                break;
        }
        
        return (consumed || super.receiveEvent(e));
    }
    
    public void onDecoded() {
        crosswordApp = (CrosswordApp) getApplication();
        
        loadingDialog = new ProgressWindow(getApplication().getResources().getString(ID_LOADING_PUZZLE));
        loadingDialog.setCancelButtonEvent(new Event(this, EVENT_LOAD_PUZZLE_CANCEL));
        
        crosswordURLListView = (CrosswordURLListView) getChildWithID(ID_SELECT_CROSSWORD_LISTVIEW);
    }
    
    protected void showAddNewPuzzleDialog() {
        DialogWindow dialog = getApplication().getResources().getDialog(ID_ADD_NEW_PUZZLE_DIALOG);
        dialog.setListener(this);
        dialog.show();
    }
    
    
}
