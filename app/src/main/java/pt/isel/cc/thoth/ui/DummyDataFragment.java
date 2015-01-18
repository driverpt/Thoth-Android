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
package pt.isel.cc.thoth.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import pt.isel.cc.thoth.AsyncTaskLoaderFixed;
import pt.isel.cc.thoth.R;

/**
 * Created by Luis Duarte on 11/09/2014.
 */
public class DummyDataFragment extends LoadingFragment implements LoaderManager.LoaderCallbacks<String[]> {
    public DummyDataFragment() {
    }

    private ListView listView;
    private ArrayAdapter<String> adapterListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dummy_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getContentContainer().findViewById(R.id.dummy_list);

        adapterListView = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new ArrayList<String>());

        listView.setAdapter(adapterListView);

        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(DEFAULT_LOADER_ID);
    }

    @Override
    public Loader<String[]> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoaderFixed<String[]>(getActivity()) {
            @Override
            public String[] loadInBackground() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new String[]{"Xpto 1", "Xpto 2", "Xpto 3"};
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] strings) {
        adapterListView.clear();
        for (String string : strings) {
            adapterListView.add(string);
        }

        adapterListView.notifyDataSetChanged();
        setContentLoading(false);
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {
        // Do nothing
    }
}
