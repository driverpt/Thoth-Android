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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import pt.isel.cc.thoth.R;
import pt.isel.cc.thoth.api.entity.CourseClassSimple;

/**
 * Created by Luis Duarte on 17/09/2014.
 */
public class ClassesListSectionFragment extends Fragment {

    private String semester;
    private List<CourseClassSimple> courseClassSimples;

    private ListView mCourseClassListView;
    private ClassesListSectionAdapter mCourseClassListAdapter;


    public static ClassesListSectionFragment newInstance(String semester, List<CourseClassSimple> courseClassSimples) {
        ClassesListSectionFragment fragment = new ClassesListSectionFragment();

        fragment.semester = semester;
        fragment.courseClassSimples = courseClassSimples;

        return fragment;
    }

    public ClassesListSectionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mCourseClassListView = (ListView) view.findViewById(android.R.id.list);

        mCourseClassListAdapter = new ClassesListSectionAdapter(getActivity(), courseClassSimples);

        mCourseClassListView.setAdapter(mCourseClassListAdapter);
        mCourseClassListAdapter.notifyDataSetChanged();

        return view;
    }

    private static class ClassesListSectionAdapter extends BaseAdapter {
        private Context context;
        private List<CourseClassSimple> classesList;

        public ClassesListSectionAdapter(Context context, List<CourseClassSimple> classesList) {
            this.context = context;
            this.classesList = classesList;
        }

        @Override
        public int getCount() {
            return classesList.size();
        }

        @Override
        public CourseClassSimple getItem(int position) {
            return classesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view;

            if (convertView == null) {
                view = layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            } else {
                view = convertView;
            }

            TextView courseUnitNameView = (TextView) view.findViewById(android.R.id.text1);
            courseUnitNameView.setText(getItem(position).getCourseUnitShortName());

            TextView classNameView = (TextView) view.findViewById(android.R.id.text2);
            classNameView.setText(getItem(position).getClassName());

            return view;
        }
    }

    public String getSemester() {
        return semester;
    }
}
