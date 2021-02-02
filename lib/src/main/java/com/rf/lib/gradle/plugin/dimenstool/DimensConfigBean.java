package com.rf.lib.gradle.plugin.dimenstool;

import java.util.List;

/**
 * Created by Roongf on 1/27/21
 */
public class DimensConfigBean {
    /**
     * 配置文件路径
     */
    private String configFilePath;
    /**
     * small width dp 标准
     */
    private int swDpStandard;
    /**
     * screen size px 标准(最小宽度)
     */
    private int swScreenSizeStandard;
    /**
     * 需要生成的smallWidth dp数组定义
     */
    private List<Integer> swDpValues;
    /**
     * 需要生成的screen size 尺寸数组定义
     */
    private List<ScreenPixel> swSizeValues;

    public String getConfigFilePath() {
        return configFilePath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public int getSwDpStandard() {
        return swDpStandard;
    }

    public void setSwDpStandard(int swDpStandard) {
        this.swDpStandard = swDpStandard;
    }

    public int getSwScreenSizeStandard() {
        return swScreenSizeStandard;
    }

    public void setSwScreenSizeStandard(int swScreenSizeStandard) {
        this.swScreenSizeStandard = swScreenSizeStandard;
    }

    public List<Integer> getSwDpValues() {
        return swDpValues;
    }

    public void setSwDpValues(List<Integer> swDpValues) {
        this.swDpValues = swDpValues;
    }

    public List<ScreenPixel> getSwSizeValues() {
        return swSizeValues;
    }

    public void setSwSizeValues(List<ScreenPixel> swSizeValues) {
        this.swSizeValues = swSizeValues;
    }

    public static class ScreenPixel {
        private int width;
        private int height;

        ScreenPixel(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "ScreenPixel{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DimensConfigBean{" +
                "configFilePath='" + configFilePath + '\'' +
                ", swStandard=" + swDpStandard +
                ", swDpValues=" + swDpValues +
                ", swSizeValues=" + swSizeValues +
                '}';
    }
}
