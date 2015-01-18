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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import pt.isel.cc.thoth.R;

/**
 * Created by Luis Duarte on 10/09/2014.
 */
public class LoadingFragment extends BaseFragment {
    protected static final int DEFAULT_LOADER_ID = 0;

    public static final String ARGUMENT_CONTENT_LAYOUT = "pt.isel.cc.thoth.ui.Fragment:ARGUMENT_CONTENT_LAYOUT";

    private View mContentContainer;
    private View mProgressView;
    private View mEmptyDialogView;

    private View mContentView;

    private boolean mContentLoading;

    public static LoadingFragment newInstance(int contentViewResource) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_CONTENT_LAYOUT, contentViewResource);

        LoadingFragment loadingFragment = new LoadingFragment();
        loadingFragment.setArguments(arguments);

        return loadingFragment;
    }

    public LoadingFragment() {
    }

    public void setContentView(int layoutResourceId) {
    }

    public void setUp() {
        if (mContentContainer == null && mProgressView == null) {
            View rootView = getView();
            if (rootView == null) {
                throw new IllegalStateException("Content View must be set before any other interaction");
            }
            mContentContainer = rootView.findViewById(R.id.content_container);
            mProgressView = rootView.findViewById(R.id.progress_container);
            mEmptyDialogView = rootView.findViewById(android.R.id.empty);

            mEmptyDialogView.setVisibility(View.GONE);

            /* TODO: Continue here */
            setContentLoading(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = onCreateContentView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.loader_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUp();
        setContentView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setContentView() {
        if (mContentContainer != null) {
            if (mContentContainer instanceof ViewGroup) {
                ViewGroup group = ((ViewGroup) mContentContainer);
                if (mContentView != null) {
                    //int index = group.indexOfChild(mContentView);
                    group.addView(mContentView);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        mContentContainer = null;
        mContentView = null;
        mEmptyDialogView = null;
        mProgressView = null;
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!mContentLoading) {

        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setContentLoading(boolean show) {
        if (mContentLoading != show) {
            this.mContentLoading = show;

            if (mContentLoading) {
                mProgressView.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
                mContentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));

                mProgressView.setVisibility(View.VISIBLE);
                mContentContainer.setVisibility(View.GONE);
            } else {
                getActivity().supportInvalidateOptionsMenu();
                mContentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
                mProgressView.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));

                mProgressView.setVisibility(View.GONE);
                mContentContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    protected View getContentContainer() {
        return mContentContainer;
    }

    protected void onContentLoadFinished() {

    }

    protected void onPreContentLoad() {

    }

    protected void onContentLoad() {

    }

    protected View onCreateContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }
}
