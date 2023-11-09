package wordCounter;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class WordReader extends BaseRichSpout {

    private SpoutOutputCollector collector;

    private FileReader fileReader;
    private BufferedReader reader ;

    private boolean completed = false;
    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

        try {
            this.fileReader = new FileReader(conf.get("fileToRead").toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading file ["+conf.get("wordFile")+"]");
        }
        this.reader =  new BufferedReader(fileReader);
    }

    @Override
    public void nextTuple() {
        if (!completed) {


            try {

                String word = reader.readLine();

                if (word != null) {
                    word = word.trim();
                    word = word.toLowerCase();
                    System.out.println("WORD :"+word);
                    collector.emit(new Values(word));
                }
                else {
                    completed = true;
                    fileReader.close();
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Error reading tuple", e);
            }

        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
