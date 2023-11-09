package wordCounter;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
/*
Each word is processed by different tasks
no 2 same words are in same processor
 */
public class TopologyFields {

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReader());
        builder.setBolt("word-counter", new WordCounter(),2)
                .fieldsGrouping("word-reader", new Fields("word"));


        Config conf = new Config();
        conf.put("fileToRead", "data/sample.txt");
        conf.put("dirToWrite", "data/wordCountoutputFields/");
        conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        try{
            cluster.submitTopology("WordCounter-Topology", conf, builder.createTopology());
            Thread.sleep(10000);
        }
        finally {
            cluster.shutdown();
        }
    }
}
