package com.syx.sboot.common.utils.extend.lang.validator;

import javax.validation.Validator;

public enum YrValidatorFactory {
	INSTANCE {
		@Override
		public Validator getValidator() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	public abstract Validator getValidator();
}