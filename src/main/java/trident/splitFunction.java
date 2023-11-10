package trident;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

public class splitFunction extends BaseFunction {
    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String sentence = tuple.getString(0);
        System.out.println(sentence);
        String[] words = sentence.split(" ");
        System.out.println(words);
        for(String word : words){
            word = word.trim();
            if(!word.isEmpty()){
                collector.emit(new Values(word));
            }
        }
    }
}
