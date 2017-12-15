package com.yazuo.intelligent.datasource.dynamic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Setter
@Getter
public class MultipleDataSource extends AbstractRoutingDataSource {
	private String master ;
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceType = DataSourceContextHolder.getDataSourceType();
		if (dataSourceType == null) {
			return master;
		} else {
			return dataSourceType;
		}
	}


}