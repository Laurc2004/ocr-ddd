package com.lrc.ocr.domain.ocr.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OcrIDService extends OcrService{
    private static final Pattern ID_CARD_PATTERN = Pattern.compile(
            "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)"
    );
    @Override
    protected List<String> filter(List<String> texts) {
        // 先检查每个字符串是否是完全匹配身份证号码格式的
        List<String> matchedIDCards = texts.stream()
                .filter(text -> ID_CARD_PATTERN.matcher(text).matches())
                .collect(Collectors.toList());

        if (!matchedIDCards.isEmpty()) {
            // 如果有完全匹配的，直接返回这些身份证号码
            return matchedIDCards;
        } else {
            // 如果没有完全匹配的，将所有文本拼接起来并从中提取身份证号码
            String combinedText = String.join("", texts);
            Matcher matcher = ID_CARD_PATTERN.matcher(combinedText);
            List<String> extractedIDCards = new ArrayList<>();
            while (matcher.find()) {
                extractedIDCards.add(matcher.group());
            }
            return extractedIDCards;
        }
    }
}
