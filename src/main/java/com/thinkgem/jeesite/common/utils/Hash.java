package com.thinkgem.jeesite.common.utils;

import java.nio.charset.Charset;
import com.google.common.hash.Hashing;

/**
 * 常用摘要算法
 * 
 * @author ngh
 * @datetime [2016年6月17日 下午5:35:53]
 *
 */
public final class Hash {

	private Hash() {
	}

	public static String md5(CharSequence charSequence) {
		return Hashing.md5().newHasher().putString(charSequence, Charset.forName("UTF-8")).hash().toString();
	}

	public static String sha1(CharSequence charSequence) {
		return Hashing.sha1().newHasher().putString(charSequence, Charset.forName("UTF-8")).hash().toString();
	}

}
