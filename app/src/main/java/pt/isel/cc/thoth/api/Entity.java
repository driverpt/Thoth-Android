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
package pt.isel.cc.thoth.api;

/**
 * Created by Luis Duarte on 16/09/2014.
 */
public abstract class Entity<Key> {

    private Key id;

    protected Entity() {
    }

    protected Entity(Key id) {
        this.id = id;
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }
}
