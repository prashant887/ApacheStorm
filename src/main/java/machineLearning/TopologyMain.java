package machineLearning;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import wordCounter.WordReader;

public class TopologyMain {
    public static void main(String[] args) throws Exception {
        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Reader-Spout", new WordReader());
        builder.setBolt("Python-Bolt", new pythonBolt()).shuffleGrouping("Reader-Spout");


        //Configuration
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("fileToRead", "/Users/pl/IdeaProjects/ApacheStorm/data/sentence.txt");

        //Topology run
        LocalCluster cluster = new LocalCluster();
        try{cluster.submitTopology("Python-Topology", conf, builder.createTopology());
            Thread.sleep(10000);}
        finally{
            cluster.shutdown();}
    }
}
