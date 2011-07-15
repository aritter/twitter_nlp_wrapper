package edu.washington.twitter_nlp;

import org.apache.lucene.util.AttributeImpl;
import java.io.Serializable;

public class NamedEntityTypeAttributeImpl extends AttributeImpl implements NamedEntityTypeAttribute {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NamedEntityType type = NamedEntityType.O;
	private String token;

	public NamedEntityType getType() {
		return type;
	}

	public void setType(NamedEntityType type) {
		this.type = type;
	}

	@Override
	public void clear() {
		type = NamedEntityType.O;
	}

	@Override
	public void copyTo(AttributeImpl target) {
		if (target instanceof NamedEntityTypeAttributeImpl) {
		      ((NamedEntityTypeAttributeImpl) target).setType(getType());
		    }
	}

	@Override
	public boolean equals(Object other) {
		return other != null
        && other instanceof NamedEntityTypeAttributeImpl
        && ((NamedEntityTypeAttributeImpl) other).type == this.type;
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	public String getToken() {
		// TODO Auto-generated method stub
		return this.token;
	}

	public void setToken(String token) {
		// TODO Auto-generated method stub
		this.token = token;
	}

	@Override
	public Object clone() {
		NamedEntityTypeAttribute result = (NamedEntityTypeAttribute)super.clone();
		return result;
	}
}
