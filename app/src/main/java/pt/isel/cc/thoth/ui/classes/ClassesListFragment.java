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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pt.isel.cc.thoth.AsyncTaskLoaderFixed;
import pt.isel.cc.thoth.LogUtils;
import pt.isel.cc.thoth.R;
import pt.isel.cc.thoth.api.entity.CourseClassSimple;
import pt.isel.cc.thoth.api.service.ClassesService;
import pt.isel.cc.thoth.ui.LoadingFragment;

/**
 * Created by Luis Duarte on 17/09/2014.
 */
public class ClassesListFragment extends LoadingFragment implements LoaderManager.LoaderCallbacks<List<CourseClassSimple>> {

    private static final String TAG = LogUtils.toLogTag(ClassesListFragment.class);

    private ViewPager viewPager;

    private CourseClassViewPagerAdapter mViewPagerAdapter;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classes, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getContentContainer().findViewById(R.id.view_pager);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<List<CourseClassSimple>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoaderFixed<List<CourseClassSimple>>(getActivity()) {
            @Override
            public List<CourseClassSimple> loadInBackground() {
                ClassesService service = new ClassesService();
                return service.getAll();
            }
        };
    }

    private void ensureAdapter() {
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new CourseClassViewPagerAdapter(getFragmentManager());
        }

        if (viewPager != null) {
            viewPager.setAdapter(mViewPagerAdapter);
        }
    }

    @Override
    public void onLoadFinished(Loader<List<CourseClassSimple>> listLoader, List<CourseClassSimple> data) {
        ensureAdapter();

        Map<String, List<CourseClassSimple>> groupedCourseUnitsPerSemester = new LinkedHashMap<String, List<CourseClassSimple>>();
        for (CourseClassSimple courseUnit : data) {
            String semester = courseUnit.getLectiveSemesterShortName();
            if (!groupedCourseUnitsPerSemester.containsKey(semester)) {
                groupedCourseUnitsPerSemester.put(semester, new LinkedList<CourseClassSimple>());
            }
            groupedCourseUnitsPerSemester.get(semester).add(courseUnit);
        }

        for (String semester : groupedCourseUnitsPerSemester.keySet()) {
            ClassesListSectionFragment fragment = ClassesListSectionFragment.newInstance(semester, groupedCourseUnitsPerSemester.get(semester));
            mViewPagerAdapter.add(fragment);
        }

        if (mViewPagerAdapter.getCount() != 0) {
            viewPager.setCurrentItem(0);
        }

        mViewPagerAdapter.notifyDataSetChanged();

        setContentLoading(false);
    }

    @Override
    public void onLoaderReset(Loader<List<CourseClassSimple>> listLoader) {
        Log.d(TAG, "onLoaderReset()");
    }

    private static class CourseClassViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<ClassesListSectionFragment> state;

        public CourseClassViewPagerAdapter(FragmentManager fm) {
            this(fm, null);
        }

        public CourseClassViewPagerAdapter(FragmentManager fm, List<ClassesListSectionFragment> state) {
            super(fm);
            if (state == null) {
                this.state = new LinkedList<ClassesListSectionFragment>();
            } else {
                this.state = state;
            }
        }

        @Override
        public Fragment getItem(int i) {
            return state.get(i);
        }

        @Override
        public int getCount() {
            return state.size();
        }

        public boolean add(ClassesListSectionFragment element) {
            return state.add(element);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return state.get(position).getSemester();
        }
    }
}
