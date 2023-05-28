package com.twopark1jo.lobster.translation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TranslationForm {
    private String source;
    private String target;
    private String text;
}
