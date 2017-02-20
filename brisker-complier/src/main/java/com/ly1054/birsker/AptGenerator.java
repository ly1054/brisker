package com.ly1054.birsker;



import com.ly1054.birsker.annotation.OnClick;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;



/**
 * @项目名： MiFengJi
 * @包名： com.ly1054.brisker
 * @文件名: AptGenerator
 * @创建者: 刘勇
 * @创建时间: 2017/02/16  16:21
 * @描述： TODO
 */


public class AptGenerator {

    private String mPackageName;
    private String mClassName;
    private TypeElement mTypeElement;

    public static final String BRISKER = "Briskerable";

    public Map<Element,Integer> mBindMap = new HashMap<>();

    public Map<Element,String> mLibBindMap = new HashMap<>();

    public int mContentViewId = -1;

    public String mLibR = null;

    public String mLibContentViewId = null;

    public Map<Element,String> mIntentNameMap = new HashMap<>();

    public Map<Element,Integer> mOnClickMap = new HashMap<>();

    public  Map<Element,String> mLibOnClickMap = new HashMap<>();

    public AptGenerator(Elements elements, TypeElement typeElement){
        this.mTypeElement = typeElement;
        PackageElement packageElement = elements.getPackageOf(typeElement);
        this.mPackageName = packageElement.getQualifiedName().toString();
        this.mClassName = typeElement.getSimpleName()
                + "$$" + BRISKER;
    }



    public String genJavaCode(){
        StringBuilder builder = new StringBuilder();
        //包名、导包
        builder.append("package " )
                .append(mPackageName)
                .append(";\n\n")
                .append("import com.ly1054.brisker.Briskerable;\n");

        //导入R文件路径
        if (mLibR != null)builder.append("import " + mLibR + ";\n\n");

        //类名
        builder.append("public class ")
                .append(mClassName)
                .append(" implements " + BRISKER + "<")
                .append(mTypeElement.getQualifiedName().toString())
                .append(">")
                .append("{\n");

        //方法
        genMethods(builder);

        //结束
        builder.append("}");
        return builder.toString();

    }

    private void genMethods(StringBuilder builder) {

        builder.append("    @Override\n")
                .append("   public void inject("+ mTypeElement.getQualifiedName() +" host,Object object){\n\n");

        //activity中注解代码生成
        builder.append("        if( object instanceof android.app.Activity){\n");
        //ContentView注解
        if (mContentViewId != -1){
            builder.append("                ((android.app.Activity)host).setContentView(" + mContentViewId + ");\n" );
        }
        builder.append("        }\n");
        //Import_R Lib_ContentView Lib_Bind注解
        if (mLibR != null && !mLibR.equals("")){

            //Lib_ContentView注解
            if (mLibContentViewId != null && !mLibContentViewId.equals("")){
                builder.append("        if( object instanceof android.app.Activity){\n");
                builder.append("           host.setContentView("  + mLibContentViewId + ");\n");
                builder.append("        }\n");
            }

            //Lib_Bind注解
            for (Element element : mLibBindMap.keySet()) {
                String name = element.getSimpleName().toString();
                String type = element.asType().toString();
                builder.append("        if( object instanceof android.app.Activity){\n");
                builder .append("                 host." + name + "=").append("((" + type + ")((android.app.Activity)object).findViewById(" + mLibBindMap.get(element) + "));\n");
                builder.append("        }else if(object instanceof android.view.View){\n");
                builder.append("                  host." + name + "=").append("((" + type + ")((android.view.View)object).findViewById(" + mLibBindMap.get(element) + "));\n");
                builder.append("        }\n");
            }

            //bind注解
            for (Element element: mBindMap.keySet()) {
                String name = element.getSimpleName().toString();
                String type = element.asType().toString();
                builder.append("        if( object instanceof android.app.Activity){\n");
                builder .append("                 host." + name + "=").append("((" + type + ")((android.app.Activity)object).findViewById(" + mBindMap.get(element) + "));\n");
                builder.append("        }else if(object instanceof android.view.View){\n");
                builder.append("                  host." + name + "=").append("((" + type + ")((android.view.View)object).findViewById(" + mBindMap.get(element) + "));\n");
                builder.append("        }\n");
            }

            //Intent_Name注解
            builder.append("        if( object instanceof android.app.Activity){\n");
            for (Element element : mIntentNameMap.keySet()) {
                genIntentNameJavaCode(mIntentNameMap.get(element),element,builder);
            }

            builder.append("                final " + mTypeElement.getQualifiedName() +" activity = host;\n");
            //OnClick注解
            for (Element element:mOnClickMap.keySet()){
                String name = element.getSimpleName().toString();
                builder.append("                activity.findViewById("  +  mOnClickMap.get(element)
                        + ").setOnClickListener(new android.view.View.OnClickListener(){\n" +
                        "                   @Override\n" +
                        "                           public void onClick(android.view.View v){ \n" +
                        "                                   activity." + name + "(v);\n" +
                        "                           }\n" +
                        "                   });\n" );
            }

            //Lib_OnClick注解
            for (Element element : mLibOnClickMap.keySet()) {
                String name = element.getSimpleName().toString();
                builder.append("                activity.findViewById("  +  mLibOnClickMap.get(element)
                + ").setOnClickListener(new android.view.View.OnClickListener(){\n" +
                        "                   @Override\n" +
                        "                           public void onClick(android.view.View v){ \n" +
                        "                                   activity." + name + "(v);\n" +
                        "                           }\n" +
                        "                   });\n" );
            }

        }else {
            if (mLibBindMap.size() > 0 || mLibContentViewId != null)
                System.console().printf("Lib_R还没有设置");
        }
        builder.append("        }\n");
        builder.append("    }\n");
    }

    private void genIntentNameJavaCode(String value, Element element, StringBuilder builder) {
        String name = element.getSimpleName().toString();
        String type = element.asType().toString();
        String clazz = "class " + type;
        if (clazz.equals(String.class.toString())){
            builder.append("                host." + name+ " = host.getIntent().getStringExtra(" + '"'+ value + '"' + ");\n" );
        }else if (type.equals(int.class.toString())){
            builder.append("                host." + name+ " = host.getIntent().getIntExtra(" + '"'+ value + '"' + ",0);\n" );
        }else if (type.equals(boolean.class.toString())){
            builder.append("                host." + name+ " = host.getIntent().getBooleanExtra(" + '"'+ value + '"' + ",false);\n" );
        }else if (type.equals(long.class.toString())){
            builder.append("                host." + name+ " = host.getIntent().getLongExtra(" + '"'+ value + '"' + ",0);\n" );
        }else if (type.equals(double.class.toString())){
            builder.append("                host." + name+ " = host.getIntent().getDoubleExtra(" + '"'+ value + '"' + ",0);\n" );
        }else if (type.equals(char.class.toString())){
            builder.append("                host." + name+ " = host.getIntent().getCharExtra(" + '"'+ value + '"' + ",' ');\n" );
        }
    }


    public String getClassFullName(){
        return mPackageName + "." + mClassName;
    }

    public TypeElement getTypeElement(){
        return mTypeElement;
    }

}
