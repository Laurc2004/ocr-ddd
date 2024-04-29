package com.lrc.ocr.domain.ocr.model.entity;

import com.lrc.ocr.domain.ocr.model.valobj.OcrErrorVO;
import com.lrc.ocr.exception.ServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
public class FileOcrInputEntity extends OcrInputEntity {
    private MultipartFile file;

    public FileOcrInputEntity(MultipartFile file) {
        if (file.isEmpty()){
            throw new ServiceException(OcrErrorVO.IMAGE_ERROR.getCode(),OcrErrorVO.IMAGE_ERROR.getMsg());
        }
        this.file = file;
    }

    @Override
    public Object getSource() {
        return getFile();
    }
}