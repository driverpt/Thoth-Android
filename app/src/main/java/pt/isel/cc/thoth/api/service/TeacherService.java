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
import pt.isel.cc.thoth.api.entity.Teacher;

/**
 * Created by Luis Duarte on 15/09/2014.
 */
public class TeacherService extends BaseRestClientService<Teacher, Teacher, Long> {

    /*
     * TODO: Extract an API Client to a separate JAR
     */

    @Override
    public Teacher getById(Long id) {
        String target = "http://thoth.cc.e.ipl.pt/api/v1/teachers/{0}";

        if (id == null) {
            throw new IllegalArgumentException("Id must be Not null");
        }

        String uri = MessageFormat.format(target, id);

        HttpResponse response = doRequest(new HttpGet(uri));

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IllegalArgumentException(MessageFormat.format("Teacher with ID #{0} not found", id));
        }

        JSONObject jsonObject = readContentAsJson(response);

        if (jsonObject != null) {
            Teacher teacher = new Teacher();
            try {
                teacher.setId(jsonObject.getLong("id"));
                teacher.setNumber(jsonObject.getInt("number"));
                teacher.setShortName(jsonObject.getString("shortName"));
                teacher.setFullName(jsonObject.getString("fullName"));
                teacher.setAcademicEmail(jsonObject.getString("academicEmail"));

                String gravatarUrl = jsonObject.getJSONObject("avatarUrl").getString("size32").split("\\?")[0];
                teacher.setGravatarUrl(gravatarUrl);

                return teacher;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Teacher> getAll() {
        String uri = "http://thoth.cc.e.ipl.pt/api/v1/teachers/";

        List<Teacher> result = new LinkedList<Teacher>();

        HttpResponse response = doRequest(new HttpGet(uri));

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IllegalArgumentException("Cannot fetch Teachers List");
        }

        JSONObject jsonObject = readContentAsJson(response);

        if (jsonObject != null) {
            try {
                JSONArray teachers = jsonObject.getJSONArray("teachers");

                for (int i = 0; i < teachers.length(); ++i) {
                    JSONObject teacherObject = teachers.getJSONObject(i);

                    Teacher teacher = new Teacher();

                    teacher.setId(teacherObject.getLong("id"));
                    teacher.setNumber(teacherObject.getInt("number"));
                    teacher.setShortName(teacherObject.getString("shortName"));
                    teacher.setAcademicEmail(teacherObject.getString("academicEmail"));

                    String gravatarUrl = teacherObject.getJSONObject("avatarUrl").getString("size32").split("\\?")[0];
                    teacher.setGravatarUrl(gravatarUrl);

                    result.add(teacher);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return result;
    }
}
