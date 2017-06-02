package cn.edu.jlu.limf.himsc.controller.HealthExceptionAlarm;

/**
 * Created by merlin on 17-5-11.
 */
/**
  *@作者: 李铭峰-吉林大学-软件学院13级02班
  *@日期: 17-5-11 上午10:18
  *@项目用途: 《社区老年人健康数据健康方法的设计与研究》毕业论文
 */

public class HeartRateRange {
    private String max = "150";
    private String min = "60";

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
    //可以自定义心率最高值与最低值
    //如果不设定使用默认值
    public HeartRateRange(String max,String min) {
        setMin(min);
        setMax(max);
    }

    public HeartRateRange(){}

    public String getMin() {
        return min;

    }

    public void setMin(String min) {
        this.min = min;
    }

    public String isException(String heartRateMin, String heartRateMax){
        if(Integer.parseInt(heartRateMin) < Integer.parseInt(getMin())){
            return "ERROR";
        }else if(Integer.parseInt(heartRateMax)>Integer.parseInt(getMax())){
            return "ERROR";
        }else {
            return "NORMAL";
        }
    }
}
