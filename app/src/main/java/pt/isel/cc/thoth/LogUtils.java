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
package pt.isel.cc.thoth;

/**
 * Created by Luis Duarte on 09/09/2014.
 */
public final class LogUtils {
    public static final String toLogTag(Class<?> clazz) {
        return clazz.getSimpleName();
    }
}
