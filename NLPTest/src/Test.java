import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;


public class Test {
	
	public static void POSTag() throws IOException {
		POSModel model = new POSModelLoader()	
			.load(new File("E:/D drive/wrkspace/NLPTest/src/en-pos-maxent.bin"));
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		POSTaggerME tagger = new POSTaggerME(model);
		boolean isQues=false;
		String input = "My name is Harish";
		ObjectStream<String> lineStream = new PlainTextByLineStream(
				new StringReader(input));
	 
		perfMon.start();
		String line;
		while ((line = lineStream.read()) != null) {
	 
			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
					.tokenize(line);
			String[] tags = tagger.tag(whitespaceTokenizerLine);
			for(int i=0;i<tags.length;i++)
			{
				if(tags[i].contains("WDT") || tags[i].contains("WP") || tags[i].contains("WRB"))
				{
					isQues=true;
				}
			}
			//POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			//System.out.println("--"+sample.toString()+"---");
			if(isQues==true)
			{
				System.out.println("Input is a question");
			}
			else
			{
				System.out.println("Input is not a question");
			}
			perfMon.incrementCounter();
		}
		perfMon.stopAndPrintFinalResult();
	}

	public static void main(String args[]) throws IOException
	{
		POSTag();
	}
}
