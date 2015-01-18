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

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import pt.isel.cc.thoth.AsyncTaskLoaderFixed;
import pt.isel.cc.thoth.R;
import pt.isel.cc.thoth.TextUtils;
import pt.isel.cc.thoth.api.entity.Teacher;
import pt.isel.cc.thoth.api.service.TeacherService;
import pt.isel.cc.thoth.ui.LoadingFragment;

/**
 * Created by Luis Duarte on 15/09/2014.
 */
public class TeachersListFragment extends LoadingFragment implements LoaderManager.LoaderCallbacks<List<Teacher>>, SearchView.OnQueryTextListener {

    private List<Teacher> teacherList;

    private ListView listView;
    private TeacherListAdapter adapter;

    private OnTeacherSelectedListener callback;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public Loader<List<Teacher>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoaderFixed<List<Teacher>>(getActivity()) {
            @Override
            public List<Teacher> loadInBackground() {
                TeacherService service = new TeacherService();
                return service.getAll();
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (teacherList == null) {
            teacherList = new LinkedList<Teacher>();
        }

        listView = (ListView) getContentContainer().findViewById(android.R.id.list);
        adapter = new TeacherListAdapter(getActivity(), teacherList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callback != null) {
                    Teacher teacher = adapter.getItem(position);
                    callback.onTeacherSelected(teacher);
                }
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchView.setQuery("", false);
                }
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onLoadFinished(Loader<List<Teacher>> listLoader, List<Teacher> teachers) {
        adapter.addAll(teachers);
        setContentLoading(false);
    }

    @Override
    public void onLoaderReset(Loader<List<Teacher>> listLoader) {
        // Do nothing
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.getFilter().filter(s);
        return true;
    }

    public OnTeacherSelectedListener getOnTeacherSelectedListener() {
        return callback;
    }

    public void setOnTeacherSelectedListener(OnTeacherSelectedListener callback) {
        this.callback = callback;
    }


    public static interface OnTeacherSelectedListener {
        public void onTeacherSelected(Teacher teacher);
    }

    private static class TeacherListAdapter extends BaseAdapter implements Filterable {

        private final Object lock = new Object();

        private Context context;
        private List<Teacher> teachersList;

        private List<Teacher> mOriginalTeachersList;
        private TeacherFilter mFilter;

        public TeacherListAdapter(Context context, List<Teacher> teacherList) {
            this.context = context;
            this.teachersList = teacherList;
        }

        public void add(Teacher teacher) {
            synchronized (lock) {
                if (mOriginalTeachersList != null) {
                    mOriginalTeachersList.add(teacher);
                } else {
                    teachersList.add(teacher);
                }
            }
        }

        public void addAll(Collection<Teacher> newCollection) {
            synchronized (lock) {
                if (mOriginalTeachersList != null) {
                    mOriginalTeachersList.addAll(newCollection);
                } else {
                    teachersList.addAll(newCollection);
                }
            }
        }

        @Override
        public int getCount() {
            return teachersList.size();
        }

        @Override
        public Teacher getItem(int position) {
            return teachersList.get(position);
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
                view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            final Teacher teacher = getItem(position);
            textView.setText(teacher.getShortName());

            return view;
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new TeacherFilter();
            }
            return mFilter;
        }

        private class TeacherFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (mOriginalTeachersList == null) {
                    synchronized (lock) {
                        mOriginalTeachersList = new LinkedList<Teacher>(teachersList);
                    }
                }

                List<Teacher> filteredList;

                if (TextUtils.isNullOrEmpty(constraint)) {
                    synchronized (lock) {
                        filteredList = new LinkedList<Teacher>(mOriginalTeachersList);
                    }
                } else {
                    synchronized (lock) {
                        filteredList = filterByName(constraint);
                    }
                }

                filterResults.count = filteredList.size();
                filterResults.values = filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                teachersList = (List<Teacher>) results.values;

                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            private List<Teacher> filterByName(CharSequence constraint) {
                if (constraint.length() == 0) {
                    return new LinkedList<Teacher>(teachersList);
                }

                String cons = constraint.toString().toLowerCase();

                List<Teacher> filteredResult = new LinkedList<Teacher>();
                for (Teacher teacher : mOriginalTeachersList) {

                    if (teacher.getShortName().toLowerCase().startsWith(cons)) {
                        filteredResult.add(teacher);
                    }
                }

                return filteredResult;
            }
        }
    }


}

