package machineLearning;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.thrift.TException;
import org.apache.storm.topology.TopologyBuilder;
import wordCounter.WordReader;

public class sentimentTopology {
    public static void main(String[] args) throws Exception {

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Reader-Spout", new WordReader());
        builder.setBolt("Predict-Bolt", new predictBolt()).shuffleGrouping("Reader-Spout");

        //Configuration
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("fileToRead", "data/sentence.txt");

        //Topology run

        LocalCluster cluster = new LocalCluster();
        try{cluster.submitTopology("Sentiment-Topology", conf, builder.createTopology());
            Thread.sleep(20000);}
        finally{
            cluster.shutdown();}
    }
}
