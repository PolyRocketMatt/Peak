package com.github.polyrocketmatt.peak.annotation

/**
 * Description tag used to describe a field, class or method.
 *
 * @param description: the description of the object
 */
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
