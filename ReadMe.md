[![](https://jitpack.io/v/ly1054/brisker.svg)](https://jitpack.io/#ly1054/brisker)
#Android Brisker注解框架
    打造一款编译期轻量级注解框架，参照了
    butterknife实现方式，配合databinding更加快捷迅速。
##1.使用方法：
###配置jit仓：
    allprojects {
    		repositories {
    			...
    			maven { url 'https://jitpack.io' }
    		}
    	}
###添加gradle依赖：
    dependencies {
    	        compile 'com.github.ly1054.brisker:brisker-api:0.0.1'
    	        compile 'com.github.ly1054.brisker:brisker-compiler:0.0.1'
    	}

###activity中view控件、onclick事件绑定，getIntent注入：

    Activity ContentView绑定：
    @ContentView(R.layout.main)
    public class MainActivity extends Activity{

    }

    view绑定：
    @Bind(R.id.text)
    TextView mText;

    onclick事件绑定：
    @Bind(R.id.text)
    public void onTextClick(View v){
        v.setText(“你点击了text!");
     }

     @Bind({R.id.text,R.id.text1})
     public void onClick(View v){
     }

     intent传值绑定：
     @IntentName("text")
     String mText;

    在activity中注入依赖所需代码：
    Brisker.inject(this);

###普通view绑定：

    在需要绑定view的类中注入：
    Brisker.inject(this,view);//方法同butterknife

##2.对library的支持：

    在类名处通过主注解导入library的R文件包(对非library依然有效):
    @Import_R("com.ly1054.test.R")
    @Lib_ContentView("R.layout.main")
    public Class MainActivity extends Activity{
        @Lib_Bind("R.id.text")
        TextView mText;

        @Lib_Onclick("R.id.text")
        public void onTextClick(View v){
        }
    }

##3.联系交流：QQ:2791014943;  mail:2791014943@qq.com

