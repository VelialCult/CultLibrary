package ru.velialcult.library.java.yaml.annotation;

/**
 * YamlField
 * Created by Nilsson03 on 07.08.2024
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface YamlField {
    String value();
}