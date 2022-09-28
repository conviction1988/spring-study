package com.spring.study.global.common.entity;

public class EntityEnum {

	// Since we are using ordinal(), the order should be forward if it is of high importance.
	public enum UserRole implements EnumModel {
		ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

		private String value;

		UserRole(String value) {
			this.value = value;
		}

		@Override
		public String getKey() {
			return name();
		}

		@Override
		public String getValue() {
			return value;
		}
	}

	public enum Currency implements EnumModel {
		KRW("KRW");

		private String value;

		Currency(String value) {
			this.value = value;
		}

		@Override
		public String getKey() {
			return name();
		}

		@Override
		public String getValue() {
			return value;
		}
	}

}
