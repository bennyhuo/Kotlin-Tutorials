package com.bennyhuo.kotlin.update16.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;

/**
 * Created by benny.
 */
public class JavaAnnotationSample {

    public static void main(String[] args) {
        SuperAnnotation annotation = new SubAnnotation("HelloWorld");
        System.out.println(annotation.value()[0]);
    }
    
    public @interface SuperAnnotation {
        String[] value();
    }

    static class SubAnnotation implements SuperAnnotation {
        private String value;

        public SubAnnotation(String value) {
            this.value = value;
        }

        @Override
        public String[] value() {
            return new String[]{value};
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return SuperAnnotation.class;
        }
    }

    @Repeatable(Container.class)
    public @interface Tag {
        String value();
    }


    public @interface Container {
        Tag[] value();
    }

    @Tag("hello")
    @Tag("world")
    class Abc {
    }

    @Container({@Tag("hello"), @Tag("world")})
    class Acb {
    }
}
