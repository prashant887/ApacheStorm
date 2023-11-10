package machineLearning;

import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

import java.util.Map;

public class predictBolt extends ShellBolt implements IRichBolt {

    public predictBolt() {
        super("python", "/Users/pl/IdeaProjects/ApacheStorm/python/predictBolt.py");
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence" ,"sentiment"));

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
