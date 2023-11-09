package wordCounter;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WordCounter extends BaseBasicBolt {

    Map<String, Integer> counters;
    Integer id;
    String name;
    String fileName;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        this.counters = new HashMap<String, Integer>();
        this.name = context.getThisComponentId();
        this.id = context.getThisTaskId();
        this.fileName = stormConf.get("dirToWrite").toString()+
                "output"+"-" +name+id +".txt";
    }
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String word = input.getString(0);

        if(!counters.containsKey(word)) {
            counters.put(word, 1);
        }else{
            Integer c = counters.get(word) + 1;
            counters.put(word, c);
        }
    }

    @Override
    public void cleanup() {

        try{
            PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8);

            for(Map.Entry<String, Integer> entry : counters.entrySet()){
                writer.println(entry.getKey()+": "+entry.getValue());
            }

            writer.close();
        }
        catch (Exception e){}


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
