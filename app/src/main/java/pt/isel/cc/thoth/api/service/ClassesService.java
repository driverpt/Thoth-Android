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
package pt.isel.cc.thoth.api.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import pt.isel.cc.thoth.api.BaseRestClientService;
import pt.isel.cc.thoth.api.entity.CourseClass;
import pt.isel.cc.thoth.api.entity.CourseClassSimple;

/**
 * Created by Luis Duarte on 16/09/2014.
 */
public class ClassesService extends BaseRestClientService<CourseClassSimple, CourseClass, Long> {

    @Override
    public CourseClass getById(Long id) {
        String target = "http://thoth.cc.e.ipl.pt/api/v1/classes/{0}";

        if (id == null) {
            throw new IllegalArgumentException("Id must be Not null");
        }

        String uri = MessageFormat.format(target, id);

        HttpResponse response = doRequest(new HttpGet(uri));

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IllegalArgumentException(MessageFormat.format("Class with ID #{0} not found", id));
        }

        JSONObject jsonObject = readContentAsJson(response);

        if (jsonObject != null) {
            CourseClass courseClass = new CourseClass();
            try {
                courseClass.setId(jsonObject.getLong("id"));
                courseClass.setFullName(jsonObject.getString("fullName"));
                courseClass.setCourseUnitShortName(jsonObject.getString("courseUnitShortName"));
                courseClass.setLectiveSemesterShortName(jsonObject.getString("lectiveSemesterShortName"));
                courseClass.setClassName(jsonObject.getString("className"));
                courseClass.setMainTeacherShortName(jsonObject.getString("mainTeacherShortName"));
                courseClass.setCourseUnitId(jsonObject.getLong("courseUnitId"));
                courseClass.setLectiveSemesterId(jsonObject.getLong("lectiveSemesterId"));
                courseClass.setMainTeacherId(jsonObject.getLong("mainTeacherId"));
                courseClass.setMaxGroupSize(jsonObject.getInt("maxGroupSize"));

                return courseClass;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<CourseClassSimple> getAll() {
        String uri = "http://thoth.cc.e.ipl.pt/api/v1/classes/";

        List<CourseClassSimple> result = new LinkedList<CourseClassSimple>();

        HttpResponse response = doRequest(new HttpGet(uri));

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IllegalArgumentException("Cannot fetch Classes List");
        }

        JSONObject jsonObject = readContentAsJson(response);

        if (jsonObject != null) {
            try {
                JSONArray classes = jsonObject.getJSONArray("classes");

                for (int i = 0; i < classes.length(); ++i) {
                    JSONObject classObject = classes.getJSONObject(i);

                    CourseClassSimple courseClass = new CourseClassSimple();

                    courseClass.setId(classObject.getLong("id"));
                    courseClass.setFullName(classObject.getString("fullName"));
                    courseClass.setCourseUnitShortName(classObject.getString("courseUnitShortName"));
                    courseClass.setLectiveSemesterShortName(classObject.getString("lectiveSemesterShortName"));
                    courseClass.setClassName(classObject.getString("className"));
                    courseClass.setMainTeacherShortName(classObject.getString("mainTeacherShortName"));

                    result.add(courseClass);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
