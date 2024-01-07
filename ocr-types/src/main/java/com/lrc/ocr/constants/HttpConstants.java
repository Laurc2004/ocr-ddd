package com.lrc.ocr.constants;

import okhttp3.MediaType;
import org.springframework.beans.factory.annotation.Value;

public final class HttpConstants {
    /**
     * 请求参数名
     */
    public static final String OCR_REQUEST_NAME = "imgUrl";

    public static final MediaType JSON_TYPE = MediaType.get("application/json;charset=utf-8");
}
