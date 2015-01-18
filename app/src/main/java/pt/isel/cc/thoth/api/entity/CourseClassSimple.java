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
package pt.isel.cc.thoth.api.entity;

import pt.isel.cc.thoth.api.Entity;

/**
 * Created by Luis Duarte on 16/09/2014.
 */
public class CourseClassSimple extends Entity<Long> {

    private String fullName;
    private String lectiveSemesterShortName;
    private String courseUnitShortName;
    private String className;
    private String mainTeacherShortName;


    /*
     * GETTERS
     */

    public String getFullName() {
        return fullName;
    }

    public String getLectiveSemesterShortName() {
        return lectiveSemesterShortName;
    }

    public String getClassName() {
        return className;
    }

    public String getMainTeacherShortName() {
        return mainTeacherShortName;
    }

    public String getCourseUnitShortName() {
        return courseUnitShortName;
    }

    /*
     * SETTERS
     */

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLectiveSemesterShortName(String lectiveSemesterShortName) {
        this.lectiveSemesterShortName = lectiveSemesterShortName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMainTeacherShortName(String mainTeacherShortName) {
        this.mainTeacherShortName = mainTeacherShortName;
    }

    public void setCourseUnitShortName(String courseUnitShortName) {
        this.courseUnitShortName = courseUnitShortName;
    }
}
