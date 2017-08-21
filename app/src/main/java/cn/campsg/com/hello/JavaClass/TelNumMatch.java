package cn.campsg.com.hello.JavaClass;

/**
 * Created by 八月 on 2017/5/25.
 */

public class TelNumMatch {
    static String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[12378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
    static String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
    static String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";
    //手机号码的有效性验证
    public static boolean isValidPhoneNumber(String number)
    {
        boolean flag=false;
        if(number.length()==11 && (number.matches(YD)||number.matches(LT)||number.matches(DX)))
        {
            flag=true;
        }
        return flag;
    }

}
