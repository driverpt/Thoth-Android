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

/**
 * Created by Luis Duarte on 16/09/2014.
 */
public class CourseClass extends CourseClassSimple {

    private Long courseUnitId;
    private Long lectiveSemesterId;
    private Long mainTeacherId;
    private Integer maxGroupSize;

    /*
     * GETTERS
     */

    public Long getCourseUnitId() {
        return courseUnitId;
    }

    public Long getLectiveSemesterId() {
        return lectiveSemesterId;
    }

    public Long getMainTeacherId() {
        return mainTeacherId;
    }

    public Integer getMaxGroupSize() {
        return maxGroupSize;
    }

    /*
     * SETTERS
     */

    public void setCourseUnitId(Long courseUnitId) {
        this.courseUnitId = courseUnitId;
    }

    public void setLectiveSemesterId(Long lectiveSemesterId) {
        this.lectiveSemesterId = lectiveSemesterId;
    }

    public void setMainTeacherId(Long mainTeacherId) {
        this.mainTeacherId = mainTeacherId;
    }

    public void setMaxGroupSize(Integer maxGroupSize) {
        this.maxGroupSize = maxGroupSize;
    }
}
