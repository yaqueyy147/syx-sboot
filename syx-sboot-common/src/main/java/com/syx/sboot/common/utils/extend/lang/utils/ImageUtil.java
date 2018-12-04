// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImageUtil.java

package com.syx.sboot.common.utils.extend.lang.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;

public class ImageUtil
{

	public ImageUtil()
	{
	}

	public static void generateThumbnails(String sourceFilePath, String targetFilePath, int width, int hight)
		throws Exception
	{
		String imgType = "JPEG";
		if (sourceFilePath.toLowerCase().endsWith(".png"))
			imgType = "PNG";
		File targetFile = new File(targetFilePath);
		File sourceFile = new File(sourceFilePath);
		BufferedImage srcImage = ImageIO.read(sourceFile);
		if (width > 0 || hight > 0)
			srcImage = thumb(srcImage, width, hight);
		ImageIO.write(srcImage, imgType, targetFile);
	}

	public static BufferedImage thumb(BufferedImage source, int width, int height)
	{
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double)width / (double)source.getWidth();
		double sy = (double)height / (double)source.getHeight();
		if (sx > sy)
		{
			sx = sy;
			width = (int)(sx * (double)source.getWidth());
		} else
		{
			sy = sx;
			height = (int)(sy * (double)source.getHeight());
		}
		if (type == 0)
		{
			ColorModel cm = source.getColorModel();
			java.awt.image.WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
		{
			target = new BufferedImage(width, height, type);
		}
		Graphics2D g = target.createGraphics();
		g.setColor(Color.WHITE);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public static String madeThumbFileName(String originalFilename, int width, int height)
	{
		return (new StringBuilder()).append("t_").append(width).append("_").append(height).append("_").append(originalFilename).toString();
	}

	public static String madeFileName(String originalFilename)
	{
		return (new StringBuilder()).append(System.currentTimeMillis()).append(originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length())).toString();
	}

	public static String getCustomerImgDomain(String domain, String folder, String customerId)
	{
		return (new StringBuilder()).append(domain).append(folder).append("/").append(customerId).append("/").toString();
	}
}
