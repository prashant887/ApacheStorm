package machineLearning;

import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

import java.util.Map;

public class pythonBolt extends ShellBolt implements IRichBolt {

    public pythonBolt(){
        super("/usr/bin/python3","/Users/pl/IdeaProjects/ApacheStorm/python/bolt.py");
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence","length"));

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
