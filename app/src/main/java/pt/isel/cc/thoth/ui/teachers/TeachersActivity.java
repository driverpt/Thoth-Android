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
package pt.isel.cc.thoth.ui.teachers;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import pt.isel.cc.thoth.Navigation;
import pt.isel.cc.thoth.R;
import pt.isel.cc.thoth.api.entity.Teacher;
import pt.isel.cc.thoth.ui.BaseActivity;

/**
 * Created by Luis Duarte on 15/09/2014.
 */
public class TeachersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TeachersListFragment fragment = new TeachersListFragment();

        fragment.setOnTeacherSelectedListener(new TeachersListFragment.OnTeacherSelectedListener() {
            @Override
            public void onTeacherSelected(Teacher teacher) {
                TeacherDetailFragment teacherDetailFragment = TeacherDetailFragment.newInstance(teacher.getId());
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, teacherDetailFragment)
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .addToBackStack(null)
                        .commit();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        ;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getNavigationDrawerCurrentItem() {
        return Navigation.NAVIGATION_TEACHERS;
    }


}