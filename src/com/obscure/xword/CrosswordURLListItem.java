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

import danger.ui.Color;
import danger.ui.Font;
import danger.ui.Pen;
import danger.util.StringUtils;


/**
 * TODO: documentation for CrosswordURLListItem
 * 
 * @author paul
 * @version 1.0
 * @since 1.0
 */
public class CrosswordURLListItem implements Resources, Commands {

    private String name;
    private String url;
    
    public CrosswordURLListItem(String name, String url) {
        this.name = name;
        this.url = url;
    }
    
    public String getName() {
        return name;
    }
    
    public String getURL() {
        return url;
    }
    
    public void paintItem(Pen pen, int left, int top, int right, int bottom, boolean focused) {
        Font font = Font.findSystemFont();
        if (focused) {
            font = Font.findBoldSystemFont();
        }
        pen.setFont(font);
        
        pen.setColor(Color.BLACK);
        
        int fontDescent = font.getDescent();
        int fontHeight = fontDescent + font.getAscent();
        int baseline = (top + bottom) / 2 + (fontHeight / 2) - fontDescent;
        int width = right - left - 2 * 4;
        
        String displayString = StringUtils.makeDisplayString(name, width, font);
        pen.drawText(left + 4, baseline, displayString);
    }
}
