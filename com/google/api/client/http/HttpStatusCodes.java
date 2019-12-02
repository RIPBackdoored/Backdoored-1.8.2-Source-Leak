package com.google.api.client.http;

public class HttpStatusCodes
{
    public static final int STATUS_CODE_OK = 200;
    public static final int STATUS_CODE_CREATED = 201;
    public static final int STATUS_CODE_ACCEPTED = 202;
    public static final int STATUS_CODE_NO_CONTENT = 204;
    public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;
    public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;
    public static final int STATUS_CODE_FOUND = 302;
    public static final int STATUS_CODE_SEE_OTHER = 303;
    public static final int STATUS_CODE_NOT_MODIFIED = 304;
    public static final int STATUS_CODE_TEMPORARY_REDIRECT = 307;
    public static final int STATUS_CODE_BAD_REQUEST = 400;
    public static final int STATUS_CODE_UNAUTHORIZED = 401;
    public static final int STATUS_CODE_FORBIDDEN = 403;
    public static final int STATUS_CODE_NOT_FOUND = 404;
    public static final int STATUS_CODE_METHOD_NOT_ALLOWED = 405;
    public static final int STATUS_CODE_CONFLICT = 409;
    public static final int STATUS_CODE_PRECONDITION_FAILED = 412;
    public static final int STATUS_CODE_UNPROCESSABLE_ENTITY = 422;
    public static final int STATUS_CODE_SERVER_ERROR = 500;
    public static final int STATUS_CODE_BAD_GATEWAY = 502;
    public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;
    
    public HttpStatusCodes() {
        super();
    }
    
    public static boolean isSuccess(final int a1) {
        return a1 >= 200 && a1 < 300;
    }
    
    public static boolean isRedirect(final int a1) {
        switch (a1) {
            case 301:
            case 302:
            case 303:
            case 307: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
