package com.wwsoft.mysql;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
public class ApplicationConfiguration {

	String url;
	String username;
	String password;
	String showsql;
	String dialect;
	String packagesToScan;
	
	public String getPackagesToScan() {
		return packagesToScan;
	}
	public void setPackagesToScan(String packageToScan) {
		this.packagesToScan = packageToScan;
	}
	public String getDialect() {
		return dialect;
	}
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public  String getUrl() {
		return url;
	}
	public  void setUrl(String url) {
		this.url = url;
	}
	public  String getUsername() {
		return username;
	}
	public String getShowsql() {
		return showsql;
	}
	public void setShowsql(String showsql) {
		this.showsql = showsql;
	}
	public  void setUsername(String username) {
		this.username = username;
	}
	public  String getPassword() {
		return password;
	}
	public  void setPassword(String password) {
		this.password = password;
	}
}

