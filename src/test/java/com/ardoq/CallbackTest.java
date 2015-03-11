package com.ardoq;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.concurrent.atomic.AtomicBoolean;

public class CallbackTest implements Callback {
    private AtomicBoolean done = new AtomicBoolean(false);
    private Response response;

    public void success(Object o, Response response) {
        done.set(true);
        this.response = response;
    }

    public void failure(RetrofitError retrofitError) {
    }

    public AtomicBoolean done() {
        return this.done;
    }

    public Response getResponse() {
        return this.response;
    }
}
