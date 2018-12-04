// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertiesUtil.java

package com.syx.sboot.common.utils.extend.lang.utils;

import java.io.*;
import java.util.*;

// Referenced classes of package com.yjf.common.lang.util:
//			StringUtil

public class PropertiesUtil
{

	private static final String keyValueSeparators = "=: \t\r\n\f";
	private static final String strictKeyValueSeparators = "=:";
	private static final String specialSaveChars = "=: \t\r\n\f#!";
	private static final String whiteSpaceChars = " \t\r\n\f";
	private static final char hexDigit[] = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'A', 'B', 'C', 'D', 'E', 'F'
	};

	public PropertiesUtil()
	{
	}

	public static String convert2String(Properties properties, boolean unicode)
	{
		if (properties == null)
			return null;
		if (properties.isEmpty())
			return "";
		StringWriter writer = new StringWriter();
		try
		{
			store(properties, writer, unicode);
		}
		catch (IOException e)
		{
			return null;
		}
		return writer.toString();
	}

	public static Properties restoreFromString(String string)
	{
		Properties properties = new Properties();
		if (StringUtil.isBlank(string))
			return properties;
		try
		{
			properties.load(new ByteArrayInputStream(string.getBytes()));
		}
		catch (Exception ex) { }
		return properties;
	}

	public static Properties restoreFromString(String string, String encoding)
	{
		Properties properties = new Properties();
		if (StringUtil.isBlank(string))
			return properties;
		InputStream in = new ByteArrayInputStream(string.getBytes());
		try
		{
			load(properties, new InputStreamReader(in, encoding));
		}
		catch (Exception ex) { }
		return properties;
	}

	public static String convert2String(Map map, boolean unicode)
	{
		return convert2String(toProperties(map), unicode);
	}

	public static Map restoreMap(String str)
	{
		return toMap(restoreFromString(str));
	}

	public static Properties toProperties(Map map)
	{
		Properties properties = new Properties();
		if (map == null)
			return properties;
		Map.Entry key;
		for (Iterator i$ = map.entrySet().iterator(); i$.hasNext(); properties.setProperty((String)key.getKey(), (String)key.getValue()))
			key = (Map.Entry)i$.next();

		return properties;
	}

	public static Map toMap(Properties properties)
	{
		Map map = new HashMap();
		if (properties == null)
			return map;
		Object key;
		for (Iterator i$ = properties.keySet().iterator(); i$.hasNext(); map.put((String)key, properties.getProperty((String)key)))
			key = i$.next();

		return map;
	}

	public static void store(Properties properties, OutputStream out, String encoding)
		throws IOException
	{
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);
		store(properties, ((Writer) (writer)), null, null, true);
	}

	public static void store(Properties properties, OutputStream out, String header, Date date, String encoding, boolean unicodes)
		throws IOException
	{
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);
		store(properties, ((Writer) (writer)), header, date, unicodes);
	}

	public static void store(Properties properties, OutputStream out, String encoding, boolean unicodes)
		throws IOException
	{
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);
		store(properties, ((Writer) (writer)), null, null, unicodes);
	}

	public static void store(Properties properties, Writer writer, boolean unicodes)
		throws IOException
	{
		store(properties, writer, null, null, unicodes);
	}

	public static void store(Properties properties, Writer writer, String header, Date date, boolean unicodes)
		throws IOException
	{
		BufferedWriter awriter = new BufferedWriter(writer);
		if (header != null)
			writeln(awriter, (new StringBuilder()).append("#").append(header).toString());
		if (date != null)
			writeln(awriter, (new StringBuilder()).append("#").append(date.toString()).toString());
		Object key;
		String val;
		for (Iterator i$ = properties.keySet().iterator(); i$.hasNext(); writeln(awriter, (new StringBuilder()).append(key).append("=").append(val).toString()))
		{
			key = i$.next();
			val = properties.getProperty((String)key);
			key = saveConvert((String)key, true);
			if (unicodes)
				val = saveConvert(val, false);
		}

		awriter.flush();
	}

	public static void load(Properties properties, InputStream inStream, String encoding)
		throws IOException
	{
		InputStreamReader reader = new InputStreamReader(inStream, encoding);
		load(properties, ((Reader) (reader)));
	}

	public static void load(Properties properties, Reader reader)
		throws IOException
	{
		BufferedReader in = new BufferedReader(reader);
		do
		{
			String line;
			int len;
			int keyStart;
			char firstChar;
			do
			{
				do
				{
					do
					{
						line = in.readLine();
						if (line == null)
							return;
					} while (line.length() <= 0);
					len = line.length();
					for (keyStart = 0; keyStart < len && " \t\r\n\f".indexOf(line.charAt(keyStart)) != -1; keyStart++);
				} while (keyStart == len);
				firstChar = line.charAt(keyStart);
			} while (firstChar == '#' || firstChar == '!');
			while (continueLine(line)) 
			{
				String nextLine = in.readLine();
				if (nextLine == null)
					nextLine = "";
				String loppedLine = line.substring(0, len - 1);
				int startIndex;
				for (startIndex = 0; startIndex < nextLine.length() && " \t\r\n\f".indexOf(nextLine.charAt(startIndex)) != -1; startIndex++);
				nextLine = nextLine.substring(startIndex, nextLine.length());
				line = new String((new StringBuilder()).append(loppedLine).append(nextLine).toString());
				len = line.length();
			}
			int separatorIndex;
			for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++)
			{
				char currentChar = line.charAt(separatorIndex);
				if (currentChar == '\\')
				{
					separatorIndex++;
					continue;
				}
				if ("=: \t\r\n\f".indexOf(currentChar) != -1)
					break;
			}

			int valueIndex;
			for (valueIndex = separatorIndex; valueIndex < len && " \t\r\n\f".indexOf(line.charAt(valueIndex)) != -1; valueIndex++);
			if (valueIndex < len && "=:".indexOf(line.charAt(valueIndex)) != -1)
				valueIndex++;
			for (; valueIndex < len && " \t\r\n\f".indexOf(line.charAt(valueIndex)) != -1; valueIndex++);
			String key = line.substring(keyStart, separatorIndex);
			String value = separatorIndex >= len ? "" : line.substring(valueIndex, len);
			key = loadConvert(key);
			value = loadConvert(value);
			properties.put(key, value);
		} while (true);
	}

	private static boolean continueLine(String line)
	{
		int slashCount = 0;
		for (int index = line.length() - 1; index >= 0 && line.charAt(index--) == '\\';)
			slashCount++;

		return Math.abs(slashCount) % 2 == 1;
	}

	private static String loadConvert(String theString)
	{
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;)
		{
			char aChar = theString.charAt(x++);
			if (aChar == '\\')
			{
				aChar = theString.charAt(x++);
				if (aChar == 'u')
				{
					int value = 0;
					for (int i = 0; i < 4; i++)
					{
						aChar = theString.charAt(x++);
						switch (aChar)
						{
						case 48: // '0'
						case 49: // '1'
						case 50: // '2'
						case 51: // '3'
						case 52: // '4'
						case 53: // '5'
						case 54: // '6'
						case 55: // '7'
						case 56: // '8'
						case 57: // '9'
							value = ((value << 4) + aChar) - 48;
							break;

						case 97: // 'a'
						case 98: // 'b'
						case 99: // 'c'
						case 100: // 'd'
						case 101: // 'e'
						case 102: // 'f'
							value = ((value << 4) + 10 + aChar) - 97;
							break;

						case 65: // 'A'
						case 66: // 'B'
						case 67: // 'C'
						case 68: // 'D'
						case 69: // 'E'
						case 70: // 'F'
							value = ((value << 4) + 10 + aChar) - 65;
							break;

						case 58: // ':'
						case 59: // ';'
						case 60: // '<'
						case 61: // '='
						case 62: // '>'
						case 63: // '?'
						case 64: // '@'
						case 71: // 'G'
						case 72: // 'H'
						case 73: // 'I'
						case 74: // 'J'
						case 75: // 'K'
						case 76: // 'L'
						case 77: // 'M'
						case 78: // 'N'
						case 79: // 'O'
						case 80: // 'P'
						case 81: // 'Q'
						case 82: // 'R'
						case 83: // 'S'
						case 84: // 'T'
						case 85: // 'U'
						case 86: // 'V'
						case 87: // 'W'
						case 88: // 'X'
						case 89: // 'Y'
						case 90: // 'Z'
						case 91: // '['
						case 92: // '\\'
						case 93: // ']'
						case 94: // '^'
						case 95: // '_'
						case 96: // '`'
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}

					outBuffer.append((char)value);
				} else
				{
					if (aChar == 't')
						aChar = '\t';
					else
					if (aChar == 'r')
						aChar = '\r';
					else
					if (aChar == 'n')
						aChar = '\n';
					else
					if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
			{
				outBuffer.append(aChar);
			}
		}

		return outBuffer.toString();
	}

	private static void writeln(BufferedWriter bw, String s)
		throws IOException
	{
		bw.write(s);
		bw.newLine();
	}

	private static String saveConvert(String theString, boolean escapeSpace)
	{
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len * 2);
		for (int x = 0; x < len; x++)
		{
			char aChar = theString.charAt(x);
			switch (aChar)
			{
			case 32: // ' '
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;

			case 92: // '\\'
				outBuffer.append('\\');
				outBuffer.append('\\');
				break;

			case 9: // '\t'
				outBuffer.append('\\');
				outBuffer.append('t');
				break;

			case 10: // '\n'
				outBuffer.append('\\');
				outBuffer.append('n');
				break;

			case 13: // '\r'
				outBuffer.append('\\');
				outBuffer.append('r');
				break;

			case 12: // '\f'
				outBuffer.append('\\');
				outBuffer.append('f');
				break;

			default:
				if (aChar < ' ' || aChar > '~')
				{
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex(aChar >> 12 & 0xf));
					outBuffer.append(toHex(aChar >> 8 & 0xf));
					outBuffer.append(toHex(aChar >> 4 & 0xf));
					outBuffer.append(toHex(aChar & 0xf));
					break;
				}
				if ("=: \t\r\n\f#!".indexOf(aChar) != -1)
					outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			}
		}

		return outBuffer.toString();
	}

	private static char toHex(int nibble)
	{
		return hexDigit[nibble & 0xf];
	}

}
