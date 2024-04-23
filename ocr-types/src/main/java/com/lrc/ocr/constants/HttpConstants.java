package com.lrc.ocr.constants;

import okhttp3.MediaType;

public final class HttpConstants {
    /**
     * 请求参数名
     */
    public static final String OCR_REQUEST_NAME = "imgUrl";

    public static final MediaType JSON_TYPE = MediaType.get("application/json;charset=utf-8");
    public static final String  IMAGE_TYPE = "image";
    public static final String  TEXT_TYPE = "text";

    public static final String NULL_RESULT = "NULL";

    public static final String SALT = "lrc";
}
