package com.lrc.ocr.domain.ocr.model.exception;

import com.lrc.ocr.domain.ocr.model.valobj.OcrErrorVO;
import com.lrc.ocr.enums.BaseError;
import com.lrc.ocr.exception.ServiceException;

public class OcrServiceException extends ServiceException {
    public OcrServiceException(int code, String meg) {
        super(code, meg);
    }

    public OcrServiceException(String meg, Throwable e) {
        super(meg, e);
    }

    public OcrServiceException(BaseError exceptionEnum) {
        super(exceptionEnum);
    }

    public OcrServiceException(OcrErrorVO ocrErrorVO){
        super(ocrErrorVO.getCode(), ocrErrorVO.getMsg());
    }
}
