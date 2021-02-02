# DimensTool
This is a SmallWidth dimens.xml creator

•一个简单的Android屏幕适配方案(SmallWidth)

# 使用说明

1. 引入插件
    dependencies {
        classpath 'com.rf.gradle.plugin.dimenstool:RFDimensToolsPlugin:1.0'
    }
2. 应用插件
    apply plugin: 'com.rf.gradle.plugin.dimenstool'
    
3. 定义配置文件目录
    defineDimensPath {
        configFilePath '/{Your Project}/app/'
    }

在右上角gradle task 中会生成 aRFDimensTools 任务。  
定义好需要生成的最小宽度数组，同时有需要可以直接定义好固定分辨率的尺寸数组，点击 aRFDimensTools，即可生成不同smallWidth对应的dimens.xml文件，
项目中使用的地方图片直接放在drawable-nodpi下，在编辑xml的时候控件的宽高直接使用@dimen/px_xxx的方式来定义固定的宽高。
文本 【TextView】 设置textSize的时候可直接使用@dimen/text_px_xxx的方式来定义文本的大小

# 截图示例
   
