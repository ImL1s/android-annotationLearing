package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;


/**
 * Created by ImL1s on 2017/5/8.
 * Description:
 */

@AutoService(OrangeDBProcessor.class)
public class OrangeDBProcessor extends AbstractProcessor {
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //        printOrangeDBObject(set, roundEnvironment);
        //        printOrangeDBClass(roundEnvironment);
        generateRealmObject(roundEnvironment);

        return false;
    }

    private void generateRealmObject(RoundEnvironment roundEnvironment) {

        System.out.println("--------------generateRealmObject----------------");

        for (Element elem : roundEnvironment.getElementsAnnotatedWith(OrangeDBObject.class)) {
            if (elem.getKind() == ElementKind.CLASS) {

                List<FieldSpec> fieldSpecList = new ArrayList<>();
                // print fields
                for (Element enclosedElement : elem.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD) {
                        Name simpleName = enclosedElement.getSimpleName();
                        System.out.println(simpleName.toString());
                        String longName = enclosedElement.asType().getClass().getCanonicalName();
                        FieldSpec f =
                                FieldSpec
                                        .builder(TypeName.get(
                                                enclosedElement.asType()),
                                                simpleName.toString(),
                                                Modifier.PRIVATE)
                                        .build();

                        fieldSpecList.add(f);

                        //                        Set<Modifier> modifiers = enclosedElement.getModifiers();
                        //                        StringBuilder sb = new StringBuilder();
                        //                        if (modifiers.contains(Modifier.PRIVATE))
                        //                        {
                        //                            sb.append("private ");
                        //                        }
                        //                        else if (modifiers.contains(Modifier.PROTECTED))
                        //                        {
                        //                            sb.append("protected ");
                        //                        }
                        //                        else if (modifiers.contains(Modifier.PUBLIC))
                        //                        {
                        //                            sb.append("public ");
                        //                        }
                        //                        if (modifiers.contains(Modifier.STATIC))
                        //                            sb.append("static ");
                        //                        if (modifiers.contains(Modifier.FINAL))
                        //                            sb.append("final ");
                        //                        sb.append(enclosedElement.asType())
                        //                                .append(" ")
                        //                                .append(enclosedElement.getSimpleName());
                        //                        System.out.println(sb);
                    }
                }

                createClass(elem.getSimpleName().toString(), fieldSpecList);
            }

            System.out.println("------------------------------");
        }
    }

    /**
     * 創建繼承Realm的指定物件
     * @param className
     * @param fieldSpecList
     */
    private void createClass(String className, List<FieldSpec> fieldSpecList) {

        // 创建main方法.
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

//        TypeSpec parent = TypeSpec.classBuilder("io.realm.RealmObject").build();

        TypeSpec helloWorld = TypeSpec.classBuilder(className + "Realm")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .addFields(fieldSpecList)
                .superclass(ClassName.get("io.realm", "RealmObject"))
                .build();

//        TypeName.get(new Type() {
//            @Override
//            public String toString() {
//                return "io.realm.RealmObject";
//            }
//        })


        try {
            // 生成 com.example.HelloWorld.java
            JavaFile javaFile = JavaFile.builder("com.example", helloWorld)
                    .addFileComment(" This codes are generated automatically. Do not modify!")
                    .build();
            //　生成文件
            javaFile.writeTo(filer);
            //                    javaFile.writeTo(filer);
            //                    javaFile.writeTo(new File("/hello"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printOrangeDBClass(RoundEnvironment roundEnvironment) {// 打印OrangeDBObject所註解的class Field
        for (Element elem : roundEnvironment.getElementsAnnotatedWith(OrangeDBObject.class)) {
            if (elem.getKind() == ElementKind.CLASS) {
                // print fields
                for (Element enclosedElement : elem.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD) {
                        Set<Modifier> modifiers = enclosedElement.getModifiers();
                        StringBuilder sb = new StringBuilder();
                        if (modifiers.contains(Modifier.PRIVATE)) {
                            sb.append("private ");
                        } else if (modifiers.contains(Modifier.PROTECTED)) {
                            sb.append("protected ");
                        } else if (modifiers.contains(Modifier.PUBLIC)) {
                            sb.append("public ");
                        }
                        if (modifiers.contains(Modifier.STATIC))
                            sb.append("static ");
                        if (modifiers.contains(Modifier.FINAL))
                            sb.append("final ");
                        sb.append(enclosedElement.asType())
                                .append(" ")
                                .append(enclosedElement.getSimpleName());
                        System.out.println(sb);
                    }
                }
            }

            System.out.println("------------------------------");
        }
    }

    private void printOrangeDBObject(Set<? extends TypeElement> set,
                                     RoundEnvironment roundEnvironment) {
        for (TypeElement element : set) {
            if (element.getQualifiedName()
                    .toString()
                    .equals(OrangeDBObject.class.getCanonicalName())) {
                System.out.println(": " + element.getQualifiedName());
            }
        }

        Set<? extends Element> elementSet =
                roundEnvironment.getElementsAnnotatedWith(OrangeDBObject.class);


        for (Element element : elementSet) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement type = (TypeElement) element;
                System.out.println(type.getSimpleName().toString());
                System.out.println(type.getAnnotation(OrangeDBObject.class).toString());

                Set<TypeElement> typeElementSet = new LinkedHashSet<>();
                typeElementSet.add(type);
                Set<VariableElement> variableElements = ElementFilter.fieldsIn(typeElementSet);
                System.out.println(variableElements.size() + "");
                for (VariableElement vElement : variableElements) {
                    System.out.println(vElement.getSimpleName());
                }

            }
        }
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(OrangeDBObject.class.getCanonicalName());
        return set;
    }
}

