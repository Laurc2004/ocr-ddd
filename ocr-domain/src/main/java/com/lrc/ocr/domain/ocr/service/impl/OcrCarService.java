package com.lrc.ocr.domain.ocr.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OcrCarService extends OcrService{

    private static final Pattern PLATE_NUMBER_PATTERN = Pattern.compile(
            "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}" +
                    "[A-Z]{1}\\·?[A-Z0-9]{5}$"
    );

    @Override
    public List<String> filter(List<String> texts) {
        // 先检查每个字符串是否是完全匹配车牌号码格式的
        List<String> matchedPlateNumbers = texts.stream()
                .filter(text -> PLATE_NUMBER_PATTERN.matcher(text).matches())
                .collect(Collectors.toList());

        // 如果所有字符串都匹配车牌号码格式，直接返回这些匹配的车牌号码
        if (matchedPlateNumbers.size() == texts.size()) {
            return matchedPlateNumbers;
        } else {
            // 如果没有完全匹配的，将所有文本拼接起来并从中提取车牌号码
            String combinedText = texts.stream().collect(Collectors.joining(""));
            Matcher matcher = PLATE_NUMBER_PATTERN.matcher(combinedText);
            List<String> extractedPlateNumbers = new ArrayList<>();
            while (matcher.find()) {
                extractedPlateNumbers.add(matcher.group());
            }
            return extractedPlateNumbers;
        }
    }
}
