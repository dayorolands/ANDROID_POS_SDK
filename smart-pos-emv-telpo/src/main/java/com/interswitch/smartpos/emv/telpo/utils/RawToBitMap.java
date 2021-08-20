package com.interswitch.smartpos.emv.telpo.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by yangw160602 on 2016/11/15.
 */

public class RawToBitMap {
	public static byte[] readByteArrayFormStream(InputStream stream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			int len = 0;
			byte[] tmp = new byte[1024];
			while ((len = stream.read(tmp)) != -1) {
				outStream.write(tmp, 0, len);
			}
			byte[] data = outStream.toByteArray();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	/**
	 * 49 * 8位灰度转Bitmap * 图像宽度必须能被4整除 52 * 53 * @param data 54 * 裸数据 55 * @param
	 * width 56 * 图像宽度 57 * @param height 58 * 图像高度 59 * @return 60
	 */
	public static Bitmap convert8bit(byte[] data, int width, int height) {
		byte[] Bits = new byte[data.length * 4]; // RGBA 数组 64 65
		int i;
		for (i = 0; i < data.length; i++) {
			// 原理：4个字节表示一个灰度，则RGB = 灰度值，最后一个Alpha = 0xff; 69
			Bits[i * 4] = Bits[i * 4 + 1] = Bits[i * 4 + 2] = data[i];
			Bits[i * 4 + 3] = -1;
		}
		// Bitmap.Config.ARGB_8888 表示：图像模式为8位 74
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bmp.copyPixelsFromBuffer(ByteBuffer.wrap(Bits));
		return bmp;
	}

	/**
	 * 82 * 24位灰度转Bitmap 83 * 84 * 图像宽度必须能被4整除 85 * 86 * @param data 87 * 裸数据 88
	 * * @param width 89 * 图像宽度 90 * @param height 91 * 图像高度 92 * @return 93
	 */
	public static Bitmap convert24bit(byte[] data, int width, int height) {
		byte[] Bits = new byte[data.length * 4]; // RGBA 数组 97 98
		int i; // data.length / 3 表示 3位为一组101
		for (i = 0; i < data.length / 3; i++) { // 原理：24位是有彩色的，所以要复制3位，最后一位Alpha
			// = 0xff;104
			Bits[i * 4] = data[i * 3];
			Bits[i * 4 + 1] = data[i * 3 + 1];
			Bits[i * 4 + 2] = data[i * 3 + 2];
			Bits[i * 4 + 3] = -1;
		}
		// Bitmap.Config.ARGB_8888 表示：图像模式为8位111
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bmp.copyPixelsFromBuffer(ByteBuffer.wrap(Bits));
		return bmp;
	}

	/**
	 * 119 * 8位灰度转Bitmap120 * 121 * @param stream122 * 输入流123 * @param width124
	 * * 图像宽度125 * @param height126 * 图像高度127 * @return128
	 */
	public static Bitmap convert8bit(InputStream stream, int width, int height) {
		return convert8bit(readByteArrayFormStream(stream), width, height);
	}

	/**
	 * 135 * 24位灰度转Bitmap136 * 137 * @param data138 * 输入流139 * @param width140 *
	 * 图像宽度141 * @param height142 * 图像高度143 * @return144
	 */
	public static Bitmap convert24bit(InputStream stream, int width, int height) {
		return convert24bit(readByteArrayFormStream(stream), width, height);
	}
}
