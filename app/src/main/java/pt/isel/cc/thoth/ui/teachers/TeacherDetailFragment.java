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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import pt.isel.cc.thoth.AsyncTaskLoaderFixed;
import pt.isel.cc.thoth.R;
import pt.isel.cc.thoth.api.entity.Teacher;
import pt.isel.cc.thoth.api.service.TeacherService;
import pt.isel.cc.thoth.ui.LoadingFragment;

/**
 * Created by Luis Duarte on 15/09/2014.
 */
public class TeacherDetailFragment extends LoadingFragment implements LoaderManager.LoaderCallbacks<TeacherDetailFragment.LoaderResultHolder> {

    private static final int LOADER_TEACHER = 0x1;
    private static final int LOADER_BITMAP = 0x10;

    public static final String ARGUMENT_TEACHER_ID = "pt.isel.cc.thoth.ui.TeacherDetailFragment.ID";
    public static final String ARGUMENT_TEACHER_BITMAP_URL = "pt.isel.cc.thoth.ui.TeacherDetailFragment.BITMAP_URL";

    private Teacher teacher;
    private ImageView avatar;
    private TextView teacherName;
    private TextView teacherEmail;

    public static TeacherDetailFragment newInstance(Long teacherId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_TEACHER_ID, teacherId);

        TeacherDetailFragment fragment = new TeacherDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.teacher_detail_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        avatar = (ImageView) getContentContainer().findViewById(R.id.teacher_avatar);
        teacherName = (TextView) getContentContainer().findViewById(R.id.teacher_name);
        teacherEmail = (TextView) getContentContainer().findViewById(R.id.teacher_email);

        getLoaderManager().initLoader(LOADER_TEACHER, getArguments(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dummy_data, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent;

        switch (id) {
            case R.id.action_email:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{teacher.getAcademicEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(intent, getString(R.string.choose_email_client)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), getString(R.string.error_no_email_clients), Toast.LENGTH_LONG).show();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<LoaderResultHolder> onCreateLoader(int i, Bundle bundle) {
        final Long teacherId = bundle.getLong(ARGUMENT_TEACHER_ID);
        final String avatarUri = bundle.getString(ARGUMENT_TEACHER_BITMAP_URL);
        return new AsyncTaskLoaderFixed<LoaderResultHolder>(getActivity()) {
            @Override
            public LoaderResultHolder loadInBackground() {
                LoaderResultHolder result = new LoaderResultHolder();

                switch (getId()) {
                    case (LOADER_TEACHER):
                        TeacherService service = new TeacherService();
                        result.teacher = service.getById(teacherId);
                        break;
                    case (LOADER_BITMAP):
                        HttpClient client = new DefaultHttpClient();
                        try {
                            HttpResponse response = client.execute(new HttpGet(avatarUri));
                            result.bitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;
                }
                return result;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<LoaderResultHolder> teacherLoader, LoaderResultHolder resultHolder) {
        switch (teacherLoader.getId()) {
            case (LOADER_TEACHER):
                this.teacher = resultHolder.teacher;
                teacherName.setText(resultHolder.teacher.getShortName());
                teacherEmail.setText(resultHolder.teacher.getAcademicEmail());
                Bundle bundle = new Bundle();
                bundle.putString(ARGUMENT_TEACHER_BITMAP_URL, resultHolder.teacher.getGravatarUrl() + "?s=" + getResources().getDimensionPixelSize(R.dimen.teacher_avatar_image_size));
                getActivity().getSupportLoaderManager().initLoader(LOADER_BITMAP, bundle, this);
                setContentLoading(false);
                break;
            case (LOADER_BITMAP):
                avatar.setImageBitmap(resultHolder.bitmap);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<LoaderResultHolder> loaderResultHolderLoader) {

    }

    public static class LoaderResultHolder {
        public Teacher teacher;
        public Bitmap bitmap;
    }
}
