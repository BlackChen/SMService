package com.bsoft.xnsmservice.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


/**
 * author:caoyuxiang 2019年6月14日 下午4:47:33
 */
public class StringUtil extends StringUtils {

    /**
     * 判断是否是json 字符串
     *
     * @param jsonStr
     * @return
     */
    public static boolean isJSON(String jsonStr) {
        try {
            JSONObject.fromObject(jsonStr);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是xml结构
     */
    public static boolean isXML(String value) {
        try {
            DocumentHelper.parseText(value);
        } catch (DocumentException e) {
            return false;
        }
        return true;

    }

    public static boolean hasText(Object value) {
        if (value == null) {
            return false;
        } else {
            return !StringUtils.isEmpty(value.toString());
        }
    }

    public static boolean hasTextOB(Object value) {
        if (value == null) {
            return false;
        } else {
            return StringUtil.hasText(value);
        }
    }

    public static String getObjValue(Object value) {
        if (hasText(value)) {
            return String.valueOf(value);
        }
        return "";
    }

    /**
     * 字符串转date
     *
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr) {
        if (!StringUtil.hasText(dateStr)) {
            return null;
        } else {
            //dateStr=dateStr.replace("年", "-").replace("月", "-").replace("日", "");
            String[] dateFormatPort = {"", "-", "/"};
            String[] dateFormatPort2 = {"yyyy", "MM", "dd"};
            String[] dateFormatPort3 = {" HH", ":mm", ":ss", ".SSS"};
            List<String> dateFormatList = new ArrayList<String>();
            for (String str : dateFormatPort) {// 格式
                String dateFormatResult = "";
                for (int i = 0; i < dateFormatPort2.length; i++) {// 年月日
                    if (i == 0) {
                        dateFormatResult = dateFormatPort2[i];
                        dateFormatList.add(dateFormatResult);
                    } else {
                        dateFormatResult += str + dateFormatPort2[i];
                        dateFormatList.add(dateFormatResult);
                    }
                }
                for (int i = 0; i < dateFormatPort3.length; i++) {// 时间
                    if (i == 0) {
                        dateFormatResult += dateFormatPort3[i];
                        dateFormatList.add(dateFormatResult);
                    } else {
                        dateFormatResult += dateFormatPort3[i];
                        dateFormatList.add(dateFormatResult);
                    }
                }
            }
            String[] dateFormat = new String[dateFormatList.size()];
            dateFormatList.toArray(dateFormat);
            //String[] dateFormat={"yyyy-MM-dd","yyyyMMdd","yyyy/MM/dd","yyyy/MM","yyyy-MM","yyyyMM","yyyy-MM-dd HH:mm:ss","yyyyMMdd HH:mm:ss","yyyy/MM/dd HH:mm:ss","yyyy-MM-dd HH","yyyyMMdd HH"};

            try {
                return DateUtils.parseDate(dateStr, dateFormat);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    /**
     * 获取字符串拼音的第一个字母
     *
     * @param chinese
     * @return
     */
    public static String toFirstChar(String chinese) {
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();  //转为单个字符
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }


    /**
     * 输出  数组的值
     *
     * @param ob
     * @return
     */
    public static String arrayToString(String[] ob) {
        String result = "";
        int i = 0;
        for (String str : ob) {
            if (i == 0) {
                result += str;
            } else {
                result += "," + str;
            }
            i++;
        }
        return result;
    }

    /**
     * 晶奇农合base64加密
     *
     * @param str
     * @return
     */
    public static String encodeBase64(String str) throws UnsupportedEncodingException {
        if (!StringUtil.hasText(str)) return str;
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = str.getBytes("UTF-8");//编码
        final String encodedText = encoder.encodeToString(textByte);
        return encodedText;
    }

    /**
     * 晶奇农合base64解密
     *
     * @param str
     * @return
     */
    public static String decodeBase64(String str) throws UnsupportedEncodingException {
        if (!StringUtil.hasText(str)) return str;
        final Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(str.getBytes("UTF-8")), "GBK");
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.strToDate("2018-09-02 23:59"));
    }
}
