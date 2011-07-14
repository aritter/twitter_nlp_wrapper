package edu.washington.twitter_nlp;

import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.twitter.common.text.token.TokenStream;
import com.twitter.common.text.token.TokenizedCharSequence;
import com.twitter.common.text.token.TokenizedCharSequence.Builder;
import com.twitter.common.text.token.TokenizedCharSequence.Token;
import com.twitter.common.text.token.attribute.CharSequenceTermAttribute;
import com.twitter.common.text.token.attribute.PartOfSpeechAttribute;
import com.twitter.common.text.token.attribute.TokenType;
import com.twitter.common.text.token.attribute.TokenTypeAttribute;

public class NerTokenStream extends TokenStream {
	private final NamedEntityTypeAttribute neAttribute;

	private String[] tokens;
	private NamedEntityType[] tags;
	private int currIndex;
	private NamedEntityWrapper tagger;

	public NerTokenStream() {
		this.neAttribute = addAttribute(NamedEntityTypeAttribute.class);
		this.tagger = new NamedEntityWrapper();
	}

	@Override
	public boolean incrementToken() {
		if(this.currIndex == this.tokens.length) {
			return false;
		}
		this.neAttribute.setType(this.tags[this.currIndex]);
		this.neAttribute.setToken(this.tokens[this.currIndex]);
		//System.out.println("token" + Integer.toString(currIndex) + ":" + tokens[currIndex]);
		//System.out.println("tag  " + Integer.toString(currIndex) + ":" + tags[currIndex]);
		//System.out.println(this.neAttribute.getToken());
		//System.out.println(this.neAttribute.getType());
		//System.out.println();
		this.currIndex++;
		return true;
	}
	
	@Override
	public void reset(CharSequence input) {
		String[] tagged = this.tagger.tagTweet(input.toString()).split(" ");
		this.tokens = new String[tagged.length];
		this.tags   = new NamedEntityType[tagged.length];
		
		for(int i=0; i<tagged.length; i++) {
			String[] tokenTag = tagged[i].split("/");
			String token = tokenTag[0];
			for(int j=1; j<tokenTag.length-1; j++) {
				token += " " + tokenTag[j];
			}
			String tag = tokenTag[tokenTag.length-1];
			this.tokens[i] = token;
			this.tags[i]   = NamedEntityType.valueOf(tag.replace("-", ""));
		}
		this.currIndex=0;
	}
}
