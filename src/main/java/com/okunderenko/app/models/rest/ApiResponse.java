
package com.okunderenko.app.models.rest;

public class ApiResponse<T> {
    private boolean success;
    private String error;
    private T response;

    public ApiResponse(boolean success) {
        this(success, null, null);
    }

    public ApiResponse(String errorMessage) {
        this(false, errorMessage, null);
    }

    public ApiResponse(T response) {
        this(true, null, response);
    }

    public ApiResponse(boolean success, String errorMessage, T response) {
        this.success = success;
        this.error = errorMessage;
        this.response = response;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public T getResponse() {
        return response;
    }

    public static class Builder<T> {
        private boolean success;
        private String errorMessage;
        private T response;

        public Builder<T> setSuccess(boolean state) {
            this.success = state;
            return this;
        }

        public Builder<T> setErrorMessage(String message) {
            this.errorMessage = message;
            return this;
        }

        public Builder<T> setResponse(T response) {
            this.response = response;
            return this;
        }

        public ApiResponse<T> build() {
            if (response != null && errorMessage == null) {
                success = true;
            }
            return new ApiResponse<T>(success, errorMessage, response);
        }
    }
}
