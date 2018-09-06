package com.sgcc.pda.hardware.protocol.p698;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class StringUtils
{
	//private static final Log log = LogFactory.getLog(StringUtils.class);
	
	
//	/**
//	 * 根据字符串获得数值，如果字符串不合要求则直接返回null
//	 *
//	 * @param para 传入的字符串
//	 * @return 字符串符合数值要求则返回数值，否则返回null
//	 *
//	 * @增加
//	 * @执行者 吴丰辉
//	 * @时间 2013-4-24-下午07:51:43
//	 */
//	public static BigDecimal getNumberFromString(String para){
//
//		try {
//			if (log.isInfoEnabled()) {
//				log.info("##将字符串转换成数值开始");
//			}
//			return new BigDecimal(para);
//		} catch (RuntimeException e) {
//			if (log.isInfoEnabled()) {
//				log.info("##字符串不能转换成数值");
//			}
//			return null;
//		}
//	}

	public StringUtils()
	{
	}

	public static String[] split(String seperators, String list, boolean include)
	{
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String result[] = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens())
			result[i++] = tokens.nextToken();
		return result;
	}

	public static boolean booleanValue(String tfString)
	{
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}

	public static boolean isNotEmpty(String string)
	{
		return string != null && string.length() > 0;
	}

	public static boolean isEmpty(String string)
	{
		return string == null || string.length() == 0;
	}

	/**
	 * 根据提供的标示符,分割字符串。
	 * 
	 * @param str
	 * @param delim
	 * @return
	 */
	public static final String[] explodeString(String str, String delim)
	{
		if (str == null || str.equals(""))
		{
			String[] retstr = new String[1];
			retstr[0] = "";
			return retstr;
		}
		StringTokenizer st = new StringTokenizer(str, delim);
		String[] retstr = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens())
		{
			retstr[i] = st.nextToken();
			i++;
		}
		if (i == 0)
		{
			retstr[0] = str;
		}
		return retstr;
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str)
	{
		if(null==str||"".equals(str)){
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否全零
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isZero(String str)
	{
		if(null==str||"".equals(str)){
			return false;
		}
		Pattern pattern = Pattern.compile("[0]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}
	/**
	 * 判断是否为数字类型（包括小数和正负小数）
	 * @author peng
	 * @date 2012-06-15
	 * @param str
	 * @return
	 */
	public static boolean isBigDecimal(String str){
			if(null==str){
				return false;
			}
    		return str.matches("[\\+-]?[0-9]+((.)[0-9])*[0-9]*");
	 }
	public static String[][] getPKS(String pk, String flag1, String flag2, int length2) throws Exception
	{
		String[][] updatePKS = null;
		String[] updatePK = null;
		String[] pks = null;

		// 对主键数据的主键分组
		updatePK = pk.split(flag1);
		// 初始化释放主键的数组
		updatePKS = new String[updatePK.length][length2];

		// 获取每组主键的值,并存放在指定的数组中
		for (int i = 0, len = updatePK.length; i < len; i++)
		{
			pks = updatePK[i].split(",");
			for (int j = 0; j < length2; j++)
			{
				updatePKS[i][j] = pks[j];
			}
		}
		return updatePKS;
	}

	/**
	 * 判断字符串是否非空。
	 * 
	 * @param src
	 *            要判断的字符串。
	 * @return null或长度为0则返回false，否则返回true。
	 */
	public static boolean isNotNull(String src)
	{
		return (src != null && src.trim().length() > 0);
	}

	/**
	 * 判断某个字符数组是否非空。
	 * 
	 * @param src
	 *            要判断的数组。
	 * @return null或长度为0则返回false，否则返回true。
	 */
	public static boolean isNotNull(String[] src)
	{
		return (src != null && src.length > 0);
	}

	/**
	 * 过滤空值。
	 * 
	 * @param src
	 *            要过滤的对象。
	 * @param defaultValue
	 *            默认返回值。
	 * @return 过滤null之后的返回值，如果为null，则返回默认值，否则去掉两边的空格后返回。
	 */
	public static String filterNull(Object src, String defaultValue)
	{
		if (src != null)
		{
			return src.toString().trim();
		}

		return defaultValue;
	}

	/**
	 * 过滤空值。
	 * 
	 * @param src
	 *            要过滤的字符串。
	 * @param defaultValue
	 *            默认返回值。
	 * @return 过滤null之后的返回值，如果为null，则返回默认值，否则去掉两边的空格后返回。
	 */
	public static String filterNull(String src, String defaultValue)
	{
		if (src != null)
		{
			return src.trim();
		}

		return defaultValue;
	}

	/**
	 * 从左边取几个字符。
	 * 
	 * @param src
	 * @param length
	 * @param defaultValue
	 * @return
	 */
	public static String left(String src, int length, String defaultValue)
	{
		if (src != null)
		{
			if (src.length() >= length)
			{
				return src.substring(0, length);
			}

			return src;
		}

		return defaultValue;
	}

	/**
	 * 从左边取几个字符。
	 * 
	 * @param src
	 * @param length
	 * @param defaultValue
	 * @return
	 */
	public static String left(Object src, int length, String defaultValue)
	{
		if (src != null)
		{
			String temp = src.toString();

			if (temp.length() >= length)
			{
				return temp.substring(0, length);
			}

			return temp;
		}

		return defaultValue;
	}

	/**
	 * 从右边取几个字符。
	 * 
	 * @param src
	 * @param length
	 * @param defaultValue
	 * @return
	 */
	public static String right(String src, int length, String defaultValue)
	{
		if (src != null)
		{
			int srcLen = src.length();

			if (srcLen >= length)
			{
				return src.substring(srcLen - length, srcLen);
			}

			return src;
		}

		return defaultValue;
	}

	/**
	 * 从右边取几个字符。
	 * 
	 * @param src
	 * @param length
	 * @param defaultValue
	 * @return
	 */
	public static String right(Object src, int length, String defaultValue)
	{
		if (src != null)
		{
			String temp = src.toString();

			int tempLen = temp.length();

			if (tempLen >= length)
			{
				return temp.substring(tempLen - length, tempLen);
			}

			return temp;
		}

		return defaultValue;
	}

	/**
	 * 按照指定的规则拆分字符串。
	 * 
	 * @param src
	 *            要拆分的字符串。
	 * @param flag
	 *            拆分的标记。
	 * @return 返回拆分后的数组，内容为空也包含在拆分的字符串数组中。
	 */
	public static String[] split(String src, String flag)
	{
		return StringUtils.split(src, flag);
	}

	/**
	 * 获取不带扩展名的文件名。
	 * 
	 * @param filename
	 *            要处理的文件名。
	 * @return 去掉扩展名后的文件名。
	 */
	public static String getFilenameWithNoExt(String filename)
	{
		return filename.substring(0, filename.lastIndexOf("."));
	}

	/**
	 * 获取文件的扩展名。
	 * 
	 * @param filename
	 *            要处理的文件名。
	 * @return 文件名的扩展名。
	 */
	public static String getFilenameExt(String filename)
	{
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	/**
	 * 将某个值转成整型，如果出错则以默认值返回。
	 * 
	 * @param src
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(String src, int defaultValue)
	{
		try
		{
			return Integer.parseInt(src);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * 将某个值转成整型，如果出错则以默认值返回。
	 * 
	 * @param src
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(Object src, int defaultValue)
	{
		try
		{
			return Integer.parseInt(src.toString());
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	// 字符到字节转换
	public static byte[] charToByte(char ch)
	{
		int temp = (int) ch;
		byte[] b = new byte[2];
		for (int i = b.length - 1; i > -1; i--)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	// 字节到字符转换

	public static char byteToChar(byte[] b)
	{
		int s = 0;
		if (b[0] > 0)
			s += b[0];
		else
			s += 256 + b[0];
		s *= 256;
		if (b[1] > 0)
			s += b[1];
		else
			s += 256 + b[1];
		char ch = (char) s;
		return ch;
	}

	// 浮点到字节转换
	public static byte[] doubleToByte(double d)
	{
		byte[] b = new byte[8];
		long l = Double.doubleToLongBits(d);
		for (int i = 0; i < b.length; i++)
		{
			b[i] = new Long(l).byteValue();
			l = l >> 8;

		}
		return b;
	}

	// 字节到浮点转换
	public static double byteToDouble(byte[] b)
	{
		long l;

		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;

		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);

		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}

	public static boolean isBlank(String str)
	{
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 将秒数转换成时分秒字符串
	 * 
	 * @author 程伟平
	 * @date 2010-12-29
	 * @param seconds
	 *            秒数
	 */
	public static String getHhMmSs(long seconds)
	{
		String hours = "";
		String minutes = "";
		String second = "";
		// 时
		long hour = seconds / 3600;
		
		if(hour<10){
			hours="0"+hour;
		}else{
			hours=hour+"";
		}
		// 分
		seconds = seconds - 3600 * hour;
		long minute = seconds / 60;
		if(minute<10){
			minutes="0"+minute;
		}else{
			minutes=minute+"";
		}
		// 秒
		seconds = seconds - 60 * minute;
		
		if(seconds<10){
			second="0"+seconds;
		}else{
			second=seconds+"";
		}
		StringBuffer sb = new StringBuffer();

		sb.append(hours);
		sb.append("时");
		
		sb.append(minutes);
		sb.append("分");
		
		sb.append(second);
		sb.append("秒");

		return sb.toString();
	}
	
	/**
	 * 功能：转换字符串为除首字母之外的各组合字母首字母大写
	 * 例如：输入SM_IN_PAUSE_WH_RTN，输出smInPauseWhRtn
	 * @param cmd
	 * @return
	 * @author JinZhiQiang
	 */
	public static String toFirstUpperCase(String cmd){
		if(null == cmd || "".equals(cmd)){
			return null;
		}
		String[] strs = cmd.split("_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			String str = strs[i];
			if (i != 0) {
				str = str.substring(0, 1) + str.substring(1).toLowerCase();
			} else {
				str = str.substring(0, 1).toLowerCase()	+ str.substring(1).toLowerCase();
			}
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 去掉字符串前面的0
	 * 
	 * @param str
	 * @return
	 */
	public static String removeZero(String str){
		return str.replaceAll("^(0+)", "");
	}
	
	/**
	 * 去掉字符串后面的0
	 * 
	 * @param str
	 * @return
	 */
	public static String removeZeroRight(String str){
		StringBuffer sbf = new StringBuffer(str);
		int len = sbf.length();
		while(sbf != null && len != 0 && sbf.lastIndexOf("0") == len - 1){
			sbf = sbf.delete(len-1, len);
			len = sbf.length();
		}
		return sbf.toString();
	}
	
	/**
	 * 去掉ASCII字符串后面的00
	 * 
	 * @param str
	 * @return
	 */
	public static String removeAsciiZeroRight(String str){
		StringBuffer sbf = new StringBuffer(str);
		int len = sbf.length();
		while(sbf != null && len != 0 && sbf.lastIndexOf("00") == len - 2){
			sbf = sbf.delete(len-2, len);
			len = sbf.length();
		}
		return sbf.toString();
	}
	
	
	private static final char[] upcaseHexChar = "0123456789ABCDEF".toCharArray();
	private static final char[] lowerHexChar = "0123456789abcdef".toCharArray();

	
	public static final String encodeHex(byte[] bytes) {
		return encodeHex(bytes, true);
	}

	public static final String encodeHex(byte[] bytes, boolean isUpper) {
		char[] hexChar;
		if (isUpper)
			hexChar = upcaseHexChar;
		else {
			hexChar = lowerHexChar;
		}

		char[] buf = new char[bytes.length * 2];

		return encodeHex(bytes, buf, hexChar, 0, bytes.length);
	}

	public static final String encodeHex(byte[] bytes, char[] buf, int offset,
                                         int length) {
		return encodeHex(bytes, buf, upcaseHexChar, offset, length);
	}

	public static final String encodeHex(byte[] bytes, char[] buf,
                                         char[] hexChar, int offset, int length) {
		for (int i = 0; i < length; i++) {
			int code = bytes[(i + offset)] & 0xFF;
			buf[(2 * i)] = hexChar[(code >> 4)];
			buf[(2 * i + 1)] = hexChar[(code & 0xF)];
		}
		return new String(buf, 0, length * 2);
	}
	
	/**
	 * 检查字符串是否为十六进制格式
	 * @param aNumber
	 */
	public static boolean checkHexString (String aNumber) {
		 
		boolean flag=false;
		
		if(aNumber==null||aNumber.equals("")||aNumber.length()<=0){
			return false;
		}else if(aNumber.length()%2!=0){
			return false;
		}
	    String regString = "([0-9A-Fa-f]{2})+";
	    if (Pattern.matches(regString, aNumber)){
	    	flag=true;
	    }
	    return flag;
	}
	
	/**
	 * 将字符串倒序输出
	 * 
	 * @param str
	 * @return
	 */
	public String reverseStr(String str){
		StringBuffer sb = new StringBuffer();
		for(int i = str.length() ; i > 0; i--){
			sb.append(String.valueOf(str.charAt(i)));
		}
		return sb.toString();
	}
	
	
	/**
	 * 将十六进制字符串按字节倒序排列
	 * 
	 * @param str
	 * @return
	 */
	public static String reverseHexStrByByte(String str){
		if(checkHexString(str)){
			StringBuffer sb = new StringBuffer();
			for(int i = str.length() ; i > 1; i-=2){
				sb.append(str.substring(i-2,i));
			}
			return sb.toString();
		}else{
			return str;
		}
		
		
	}
	
	
	
	public static void main(String[] args)
	{
		System.out.println("时分秒："+getHhMmSs(3605));
	}
	
}
