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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Luis Duarte on 15/09/2014.
 */
public abstract class BaseRestClientService<Simple, Complex, Key> {

    private HttpClient client;

    protected BaseRestClientService() {
        client = new DefaultHttpClient();
    }

    protected BaseRestClientService(HttpClient client) {
        this.client = client;
    }

    protected HttpResponse doRequest(HttpUriRequest base) {
        // TODO: Throw Exception
        try {
            return client.execute(base);
        } catch (IOException e) {
            return null;
        }
    }

    protected JSONObject readContentAsJson(HttpResponse response) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);

            String jsonContent = out.toString();
            return new JSONObject(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
        }
    }


    public abstract Complex getById(Key key);

    public abstract List<Simple> getAll();

}
