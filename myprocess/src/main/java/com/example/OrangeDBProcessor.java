package com.example;

import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


/**
 * Created by ImL1s on 2017/5/8.
 * Description:
 */

@AutoService( OrangeDBProcessor.class )
public class OrangeDBProcessor extends AbstractProcessor
{
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment)
    {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment)
    {
        System.out.println("------------------------------");

        //        for (TypeElement element : set)
        //        {
        //            if (element.getQualifiedName()
        //                    .toString()
        //                    .equals(OrangeDBObject.class.getCanonicalName()))
        //            {
        //                System.out.println(": " + element.getQualifiedName());
        //            }
        //        }

        //        Set<? extends Element> elementSet =
        //                roundEnvironment.getElementsAnnotatedWith(OrangeDBObject.class);
        //
        //
        //        for (Element element : elementSet)
        //        {
        //            if (element.getKind() == ElementKind.CLASS)
        //            {
        //                TypeElement type = (TypeElement) element;
        //                System.out.println(type.getSimpleName().toString());
        //                System.out.println(type.getAnnotation(OrangeDBObject.class).toString());
        //
        //                Set<TypeElement> typeElementSet = new LinkedHashSet<>();
        //                typeElementSet.add(type);
        //                Set<VariableElement> variableElements = ElementFilter.fieldsIn();
        //                System.out.println(variableElements.size() + "");
        //                for (VariableElement vElement : variableElements)
        //                {
        //                    System.out.println(vElement.getSimpleName());
        //                }
        //
        //            }
        //        }


        // 打印OrangeDBObject所註解的class Field
        for (Element elem : roundEnvironment.getElementsAnnotatedWith(OrangeDBObject.class))
        {
            if (elem.getKind() == ElementKind.CLASS)
            {
                // print fields
                for (Element enclosedElement : elem.getEnclosedElements())
                {
                    if (enclosedElement.getKind() == ElementKind.FIELD)
                    {
                        Set<Modifier> modifiers = enclosedElement.getModifiers();
                        StringBuilder sb = new StringBuilder();
                        if (modifiers.contains(Modifier.PRIVATE))
                        {
                            sb.append("private ");
                        }
                        else if (modifiers.contains(Modifier.PROTECTED))
                        {
                            sb.append("protected ");
                        }
                        else if (modifiers.contains(Modifier.PUBLIC))
                        {
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

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        Set<String> set = new LinkedHashSet<>();
        set.add(OrangeDBObject.class.getCanonicalName());
        return set;
    }
}

