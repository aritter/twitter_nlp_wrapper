package edu.washington.twitter_nlp;

import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Attribute;

import com.twitter.common.text.token.TokenStream;
import com.twitter.common.text.token.TokenizedCharSequence;
import com.twitter.common.text.token.TokenizedCharSequence.Token;
import com.twitter.common.text.token.attribute.CharSequenceTermAttribute;
import com.twitter.common.text.token.attribute.TokenTypeAttribute;

import edu.washington.twitter_nlp.NamedEntityType;
import edu.washington.twitter_nlp.UwNamedEntityTagger;

/**
 * Annotated example illustrating major features of {@link DefaultTextTokenizer}.
 */
public class NerUsageExample {
  private static final String[] famousTweets = {
      // http://twitter.com/#!/BarackObama/status/992176676
      "We just made history. All of this happened because you gave your time, talent and passion."
        + "All of this happened because of you. Thanks",
      // http://twitter.com/#!/jkrums/status/1121915133
      "http://twitpic.com/135xa - There's a plane in the Hudson."
        + " I'm on the ferry going to pick up the people. Crazy.",
      // http://twitter.com/#!/carlbildt/status/73498110629904384
      "@khalidalkhalifa Trying to get in touch with you on an issue.",
      // http://twitter.com/#!/SHAQ/status/75996821360615425
      "im retiring Video: http://bit.ly/kvLtE3 #ShaqRetires"
  };

  public static void main(String[] args) {
    // This is the canonical way to create a token stream.
	  UwNamedEntityTagger tokenizer =
        new UwNamedEntityTagger.Builder().setKeepPunctuation(true).build();
    TokenStream stream = tokenizer.getDefaultTokenStream();

    // We're going to ask the token stream what type of attributes it makes available. "Attributes"
    // can be understood as "annotations" on the original text.
    System.out.println("Attributes available:");
    Iterator<Class<? extends Attribute>> iter = stream.getAttributeClassesIterator();
    while (iter.hasNext()) {
      Class<? extends Attribute> c = iter.next();
      System.out.println(" - " + c.getCanonicalName());
    }
    System.out.println("");

    // We're now going to iterate through a few tweets and tokenize each in turn.
    for (String tweet : famousTweets) {
      // We're first going to demonstrate the "token-by-token" method of consuming tweets.
      System.out.println("Processing: " + tweet);
      // Reset the token stream to process new input.
      stream.reset(tweet);

      // Now we're going to consume tokens from the stream.
      int tokenCnt = 0;
      while (stream.incrementToken()) {
        NamedEntityTypeAttribute nerAttribute = stream.getAttribute(NamedEntityTypeAttribute.class);
        System.out.println(String.format("%s\t%s", nerAttribute.getToken(), nerAttribute.getType()));
        tokenCnt++;
      }
      System.out.println("");

      // We're now going to demonstrate the TokenizedCharSequence API.
      // This should produce exactly the same result as above.
      tokenCnt = 0;
      System.out.println("Processing: " + tweet);
      List<NamedEntityTypeAttribute> tokSeq = tokenizer.tokenize(tweet);
      for (NamedEntityTypeAttribute tok : tokSeq) {
        	System.out.println(String.format("%s\t%s", tok.getToken(), tok.getType()));
      }
      System.out.println("");
    }
  }
}
