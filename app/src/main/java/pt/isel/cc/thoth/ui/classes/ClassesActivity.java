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
package pt.isel.cc.thoth.ui.classes;

import android.os.Bundle;

import pt.isel.cc.thoth.Navigation;
import pt.isel.cc.thoth.R;
import pt.isel.cc.thoth.ui.BaseActivity;

/**
 * Created by Luis Duarte on 17/09/2014.
 */
public class ClassesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ClassesListFragment fragment = new ClassesListFragment();

        // TODO: Finish Classes Fragments

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        ;
    }

    @Override
    protected int getNavigationDrawerCurrentItem() {
        return Navigation.NAVIGATION_CLASSES;
    }
}
