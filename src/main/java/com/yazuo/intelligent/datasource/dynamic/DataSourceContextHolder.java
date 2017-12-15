package com.yazuo.intelligent.datasource.dynamic;


public class DataSourceContextHolder {
	private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

	/**
	 *
	 * @param dataSourceType
	 */
	public static void setDataSourceType(String dataSourceType) {

		HOLDER.set(dataSourceType);
	}

	/**
	 *
	 * @return {@code String}
	 */
	public static String getDataSourceType() {
		return HOLDER.get();
	}

	/**
	 * {@code void}
	 */
	public static void clearDataSourceType() {
		HOLDER.remove();
	}
}
