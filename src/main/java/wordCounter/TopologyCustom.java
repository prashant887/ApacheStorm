package wordCounter;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
/*
Word starting with a is hanlded by a task
 */
public class TopologyCustom {

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReader());
        builder.setBolt("word-counter", new WordCounter(),2)
                .customGrouping("word-reader", new alphaGrouping());


        Config conf = new Config();
        conf.put("fileToRead", "data/sample.txt");
        conf.put("dirToWrite", "data/wordCountoutputCustom/");
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
