package cn.edu.xmu.javaee.core.aop;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @author huang zhong
 * @date 2023-dgn-free-001
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"cn.edu.xmu.javaee.core.aop.CopyFrom","cn.edu.xmu.javaee.core.aop.CopyFromOf","cn.edu.xmu.javaee.core.aop.CopyFromExclude"})
public class CopyFromProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty())
            return false;

        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder("CloneFactory")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        messager.printMessage(Diagnostic.Kind.NOTE, "CopyFromProcessor start");
        roundEnv.getElementsAnnotatedWith(CopyFrom.class).stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .map(element -> (TypeElement)element)
                .forEach(element -> {
//                    messager.printMessage(Diagnostic.Kind.NOTE, getSourceClass(element, "value").toString());
                    messager.printMessage(Diagnostic.Kind.NOTE, element.getSimpleName().toString());

                    List<ExecutableElement> targetMethods = getAllMethods(element)
                            .stream()
                            .filter(method -> method.getParameters().size() == 1 && method.getSimpleName().toString().startsWith("set"))
                            .collect(Collectors.toList());
                    messager.printMessage(Diagnostic.Kind.NOTE, targetMethods.toString());

                    getSourceClass(element, "value",CopyFrom.class).forEach(sourceClass -> {
                        List<ExecutableElement> sourceMethods = getAllMethods((TypeElement)sourceClass.asElement())
                                .stream()
                                .filter(method -> method.getParameters().isEmpty() && !method.getReturnType().getKind().equals(TypeKind.VOID) && method.getSimpleName().toString().startsWith("get"))
                                .collect(Collectors.toList());
                        messager.printMessage(Diagnostic.Kind.NOTE, sourceMethods.toString());

                        MethodSpec.Builder copyMethodBuilder = MethodSpec.methodBuilder("copy")
                                .addJavadoc("Copy all fields from source to target\n")
                                .addJavadoc("@param target the target object\n")
                                .addJavadoc("@param source the source object\n")
                                .addJavadoc("@return the copied target object\n")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .returns(TypeName.get(element.asType()))
                                .addParameter(TypeName.get(element.asType()), "target")
                                .addParameter(TypeName.get(sourceClass), "source");

                        targetMethods.stream().filter(targetMethod -> {
                            String targetMethodName = targetMethod.getSimpleName().toString().substring(3);
                            List<DeclaredType> excludeList=getSourceClass(targetMethod,"value", CopyFrom.Exclude.class);
                            List<DeclaredType> ofList=getSourceClass(targetMethod,"value", CopyFrom.Of.class);

                            if(!excludeList.isEmpty() && !ofList.isEmpty())
                            {
                                if((excludeList.stream().anyMatch(hasClass -> TypeName.get(hasClass).toString().equals(TypeName.get(sourceClass).toString())))
                                        || ofList.stream().noneMatch(hasClass-> TypeName.get(hasClass).toString().equals(TypeName.get(sourceClass).toString())))
                                {
                                    return false;
                                }
                            }else if(excludeList.isEmpty() && !ofList.isEmpty())
                            {
                                if(ofList.stream().noneMatch(hasClass-> TypeName.get(hasClass).toString().equals(TypeName.get(sourceClass).toString())))
                                {
                                    return false;
                                }
                            }else if(!excludeList.isEmpty() && ofList.isEmpty())
                            {
                                if(excludeList.stream().anyMatch(hasClass -> TypeName.get(hasClass).toString().equals(TypeName.get(sourceClass).toString())))
                                {
                                    return false;
                                }
                            }


                            return sourceMethods.stream().anyMatch(sourceMethod -> sourceMethod.getSimpleName().toString().substring(3).equals(targetMethodName));
                        }).map(method -> method.getSimpleName().toString().substring(3)).forEach(methodName -> {
                            copyMethodBuilder.addStatement("target.set" + methodName + "(source.get" + methodName + "())");
                        });

                        copyMethodBuilder.addStatement("return target");

                        typeSpecBuilder.addMethod(copyMethodBuilder.build());
                    });
                });

        JavaFile javaFile = JavaFile.builder("cn.edu.xmu.javaee.core.util", typeSpecBuilder.build()).build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (Exception e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }

        return true;
    }

    private List<ExecutableElement> getAllMethods(TypeElement type) {
        return new ArrayList<>(ElementFilter.methodsIn(type.getEnclosedElements()));
    }

    private Optional<AnnotationMirror> getAnnotationMirror(Element element, Class<?> clazz) {
        String clazzName = TypeName.get(clazz).toString();
        for(AnnotationMirror m : element.getAnnotationMirrors()) {
            if(m.getAnnotationType().toString().equals(clazzName)) {
                return Optional.ofNullable(m);
            }
        }
        return Optional.empty();
    }

    private Optional<AnnotationValue> getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet() ) {
            if(entry.getKey().getSimpleName().toString().equals(key)) {
                messager.printMessage(Diagnostic.Kind.NOTE, String.format("Entry: %s, value: %s", entry.getKey().getSimpleName().toString(), entry.getValue().toString()));
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }

    private List<DeclaredType> getSourceClass(Element clazz, String key,Class clas) {
        return getAnnotationMirror(clazz, clas)
                .flatMap(annotation -> getAnnotationValue(annotation, key))
                // ^ note that annotation value here corresponds to Class[],
                .map(annotation -> (List<AnnotationValue>)annotation.getValue())
                .map(fromClasses -> fromClasses.stream()
                        .map(fromClass -> (DeclaredType)fromClass.getValue())
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }
}
