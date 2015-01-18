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
 * Created by Luis Duarte on 15/09/2014.
 */
public class Teacher extends Entity<Long> {

    private Integer number;
    private String shortName;
    private String academicEmail;
    private String fullName;
    private String gravatarUrl;

    /*
     * GETTERS
     */

    public Integer getNumber() {
        return number;
    }

    public String getShortName() {
        return shortName;
    }

    public String getAcademicEmail() {
        return academicEmail;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGravatarUrl() {
        return gravatarUrl;
    }

    /*
     * SETTERS
     */

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setAcademicEmail(String academicEmail) {
        this.academicEmail = academicEmail;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGravatarUrl(String gravatarUrl) {
        this.gravatarUrl = gravatarUrl;
    }
}
