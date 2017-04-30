package org.hpin.common.util;

/**
 * 将UNICODE转为中文
 * @author ybc
 * @since 2016-08-23
 */
public class ConverUnicode {
	
	private static ConverUnicode converUnicode = null;
	
	private ConverUnicode(){}
	
	public static ConverUnicode getInstance(){
		if(null == converUnicode){
			converUnicode = new ConverUnicode();
		}
		return converUnicode;
	}
	
	public static String conver2Chinese(String ori) {
		char aChar;
		int len = ori.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = ori.charAt(x++);
			if (aChar == '\\') {
				aChar = ori.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = ori.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);

		}
		return outBuffer.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(conver2Chinese("\u6210\u90fd\u534e\u9633\u7684\u66fe\u8bda\u6b63\u5728\u6d3e\u4ef6"));
	}
}
