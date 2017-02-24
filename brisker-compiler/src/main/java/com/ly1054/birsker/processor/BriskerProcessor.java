package com.ly1054.birsker.processor;


import com.google.auto.service.AutoService;
import com.ly1054.birsker.AptGenerator;
import com.ly1054.brisker.annotation.Bind;
import com.ly1054.brisker.annotation.ContentView;
import com.ly1054.brisker.annotation.IntentName;
import com.ly1054.brisker.annotation.Lib_Bind;
import com.ly1054.brisker.annotation.Lib_ContentView;
import com.ly1054.brisker.annotation.Import_R;
import com.ly1054.brisker.annotation.Lib_OnClick;
import com.ly1054.brisker.annotation.OnClick;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * 项目名： MiFengJi
 * 包名： com.ly1054.brisker.processor
 * 文件名: BriskerProcessor
 * 创建者: 刘勇
 * 创建时间: 2017/02/16  15:33
 * 描述： processor
 */

@AutoService(Processor.class)
public class BriskerProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElements;
    private Messager mMessager;
    private  Map<String,AptGenerator> mGeneratorMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElements = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Bind.class.getCanonicalName());
        types.add(Lib_Bind.class.getCanonicalName());
        types.add(ContentView.class.getCanonicalName());
        types.add(Lib_ContentView.class.getCanonicalName());
        types.add(Import_R.class.getCanonicalName());
        types.add(IntentName.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        types.add(Lib_OnClick.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "brisker process...");
        mGeneratorMap.clear();
        //bind
        parseElements(roundEnv,Bind.class);
        parseElements(roundEnv,Lib_Bind.class);
        parseElements(roundEnv,ContentView.class);
        parseElements(roundEnv,Lib_ContentView.class);
        parseElements(roundEnv,Import_R.class);
        parseElements(roundEnv,IntentName.class);
        parseElements(roundEnv,OnClick.class);
        parseElements(roundEnv,Lib_OnClick.class);
        for (String key : mGeneratorMap.keySet()) {
            AptGenerator generator = mGeneratorMap.get(key);
            try {
                JavaFileObject file = processingEnv.getFiler().createSourceFile(
                        generator.getClassFullName(),
                        generator.getTypeElement()
                );
                Writer writer = file.openWriter();
                writer.write(generator.genJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    private void parseElements(RoundEnvironment env, Class<? extends Annotation> clazz) {
        Set<? extends Element> elements = env.getElementsAnnotatedWith(clazz);
        for (Element element : elements) {

            TypeElement type;

            if (element.getKind() == ElementKind.FIELD
                    || element.getKind() == ElementKind.METHOD){
                type = (TypeElement) element.getEnclosingElement();
            }else {
                type = (TypeElement) element;
            }

            String key = type.getQualifiedName().toString();

            AptGenerator generator = mGeneratorMap.get(key);

            if (generator == null){
                generator = new AptGenerator(mElements,type);
                mGeneratorMap.put(key,generator);
            }
            if (clazz == Bind.class){
                if (checkCanAnnotation(element,Bind.class,ElementKind.FIELD)){
                    int value =  element.getAnnotation(Bind.class).value();
                    generator.mBindMap.put(element,value);
                }
            }else if (clazz == ContentView.class){
                if (checkCanAnnotation(element, ContentView.class, ElementKind.CLASS)){
                    int value = element.getAnnotation(ContentView.class).value();
                    generator.mContentViewId = value;
                }
            }else if (clazz == Import_R.class){
                if (checkCanAnnotation(element, Import_R.class,ElementKind.CLASS)){
                    String value = element.getAnnotation(Import_R.class).value();
                    generator.mLibR = value;
                }
            }else if (clazz == Lib_Bind.class){
                if (checkCanAnnotation(element, Lib_Bind.class,ElementKind.FIELD)){
                    String id = element.getAnnotation(Lib_Bind.class).value();
                    generator.mLibBindMap.put(element,id);
                }
            }else if (clazz == Lib_ContentView.class){
                if (checkCanAnnotation(element, Lib_ContentView.class, ElementKind.CLASS)){
                    String value = element.getAnnotation(Lib_ContentView.class).value();
                    generator.mLibContentViewId = value;
                }
            }else if (clazz == IntentName.class){
                if (checkCanAnnotation(element,IntentName.class,ElementKind.FIELD)){
                    String value = element.getAnnotation(IntentName.class).value();
                    generator.mIntentNameMap.put(element, value);
                }
            }else if (clazz == OnClick.class){
                if (checkCanAnnotation(element,OnClick.class,ElementKind.METHOD)){
                    int[] value = element.getAnnotation(OnClick.class).value();
                    generator.mOnClickMap.put(element,value);
                }
            }else if (clazz == Lib_OnClick.class){
                if (checkCanAnnotation(element,Lib_OnClick.class,ElementKind.METHOD)){
                    String[] value = element.getAnnotation(Lib_OnClick.class).value();
                    generator.mLibOnClickMap.put(element,value);
                }
            }
        }

    }


    private boolean checkCanAnnotation(Element element, Class clazz,ElementKind kind) {
        if (element.getKind() != kind){
            error(element,"%s 的注解类型不符.",clazz.getSimpleName());
            return false;
        }
        if (element.getModifiers().contains(Modifier.PRIVATE)){
            error(element,"%s 不能是私有的.",clazz.getSimpleName());
            return false;
        }
        return true;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length >  0){
            message = String.format(message,args);
        }
        mMessager.printMessage(Diagnostic.Kind.NOTE,message,element);
    }
}
