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
import danger.app.SettingsDB;
import danger.ui.ActiveListView;
import danger.ui.Pen;
import danger.util.ActiveList;

/**
 * TODO: documentation for CrosswordURLListView
 * 
 * @author paul
 * @version 1.0
 * @since 1.0
 */
public class CrosswordURLListView extends ActiveListView implements Resources, Commands {
    private static final String URL_LIST_DB_NAME = "urlList";
    
    private CrosswordApp crosswordApp;
    private SelectCrosswordScreen selectCrosswordScreen;
    private ActiveList   list;
    private SettingsDB   urlSettings;
    private Object       currentFocusedItem;
    private CrosswordURLListItem addCrosswordItem; 
    

    public void onDecoded() {
        crosswordApp = (CrosswordApp) Application.getCurrentApp();
        selectCrosswordScreen = (SelectCrosswordScreen) CrosswordApp.getParentScreen(this, "com.obscure.xword.SelectCrosswordScreen");

        list = new ActiveList();
        setList(list);
        setItemHeight(15);

        // read in the stored URLs from the settings DB
        urlSettings = new SettingsDB(URL_LIST_DB_NAME, true);
        for (int i=0; i < urlSettings.getNumEntries(); i++) {
            String name = urlSettings.getKey(i);
            String value = urlSettings.getStringValue(i);
            list.addItem(new CrosswordURLListItem(name, value));
        }
        
        // add in the list item that pops up the add crossword URL dialog
        addCrosswordItem = new CrosswordURLListItem(crosswordApp.getResources().getString(ID_ADD_NEW_CROSSWORD_STR), "");
        list.addItem(addCrosswordItem);
    }

    protected void itemActivated(Object item) {
        if (item == addCrosswordItem) {
            selectCrosswordScreen.showAddNewPuzzleDialog();
        }
        else {
            crosswordApp.loadPuzzleFromURL(((CrosswordURLListItem) item).getURL());
        }
    }

    protected void itemFocused(Object item) {
        currentFocusedItem = item;
    }
    
    public boolean eventKeyDown(char key, Event e) {
        boolean consumed = false;

        switch (key) {
            case (char) 0x08:
                if (currentFocusedItem != null && currentFocusedItem instanceof CrosswordURLListItem) {
                    removePuzzleURL((CrosswordURLListItem) currentFocusedItem);
                }
                break;
        }
        
        return (consumed || super.eventKeyDown(key, e));
    }
    
    protected void paintItemLine(Pen pen, int left, int top, int right, int bottom, boolean focused, Object item) {
        ((CrosswordURLListItem) item).paintItem(pen, left, top, right, bottom, focused);
    }

    public void addPuzzleURL(String name, String url) {
        urlSettings.setStringValue(name, url);
        list.addItem(new CrosswordURLListItem(name, url));
    }
    
    private void removePuzzleURL(CrosswordURLListItem item) {
        list.removeItem(item);
        urlSettings.remove(item.getName());
    }
}