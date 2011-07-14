package edu.washington.twitter_nlp;

import java.io.*;

public class NamedEntityWrapper {
	private Process p;
	private BufferedReader stdInput;
	private BufferedWriter stdOutput;
	private BufferedReader stdErr;
	
	public NamedEntityWrapper() {
		try {
			ProcessBuilder builder = new ProcessBuilder();
			if(System.getenv().get("TWITTER_NLP") == "") {
				System.err.println("environment variable TWITTER_NLP must be set");
				System.exit(-1);
			}
			builder.command(System.getenv("TWITTER_NLP") + "/python/ner/extractEntities2.py");
			//builder.command(System.getenv("TWITTER_NLP") + "/python/ner/test.py");
			this.p = builder.start();
			this.stdInput  = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			this.stdErr    = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public String tagTweet(String text) {
		try {
			this.stdOutput.write(text + "\n");
			this.stdOutput.flush();
			String result = this.stdInput.readLine();
			System.out.println(result);
			while(this.stdErr.ready()) {
				System.out.println(this.stdErr.readLine());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return "";
	}
}
