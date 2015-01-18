/*
 * Copyright 2014 Luis Duarte.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pt.isel.cc.thoth;

/**
 * Created by Luis Duarte on 08/09/2014.
 */
public class Navigation {

    public static final int NAVIGATION_TEACHERS = 0;
    public static final int NAVIGATION_CLASSES = 1;
    public static final int NAVIGATION_SETTINGS = 2;
    public static final int[] NAVIGATION_ICONS = {
            android.R.drawable.ic_menu_preferences,
            -1,
            -1,
    };

    public static final int[] NAVIGATION_ICON_RESOURCES = {
            R.drawable.ic_graduation_cap,
            R.drawable.ic_book,
            R.drawable.ic_action_settings,
    };

    public static final int[] NAVIGATION_ICON_TEXT_STRINGS = {
            R.string.teachers,
            R.string.classes,
            R.string.action_settings,
    };

    public static final int getIconResource(int index) {
        return NAVIGATION_ICON_RESOURCES[index];
    }

    public static final int getStringResource(int index) {
        return NAVIGATION_ICON_TEXT_STRINGS[index];
    }
}
