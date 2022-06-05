package com.github.polyrocketmatt.peak.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CONSTRUCTOR, AnnotationTarget.LOCAL_VARIABLE,
)
annotation class Desc(val description: String)
